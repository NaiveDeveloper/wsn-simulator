package org.jcjxb.wsn.service.sim;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CountDownLatch;

import org.apache.log4j.Logger;
import org.jcjxb.wsn.common.FileTool;
import org.jcjxb.wsn.common.Status;
import org.jcjxb.wsn.db.Log;
import org.jcjxb.wsn.rpc.LionRpcController;
import org.jcjxb.wsn.service.agent.SlaveServiceAgentManager;
import org.jcjxb.wsn.service.proto.BasicDataType.Empty;
import org.jcjxb.wsn.service.proto.SlaveService.EventsDetail;
import org.jcjxb.wsn.service.proto.SlaveService.ExecRequest;
import org.jcjxb.wsn.service.proto.SlaveService.LVTSync;
import org.jcjxb.wsn.service.proto.SlaveService.SimulationResult;

import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcController;

public class MasterTimeLine {

	private static Logger logger = Logger.getLogger(MasterTimeLine.class);

	private static MasterTimeLine timeline = new MasterTimeLine();

	private Map<Integer, Long> localVirtualTime = null;

	private Map<Integer, Boolean> execFlag = null;

	private ControlThread controlThread = null;

	private boolean stop;

	private MasterTimeLine() {
	}

	public static MasterTimeLine getInstance() {
		return timeline;
	}

	private void init() {
		localVirtualTime = new HashMap<Integer, Long>();
		for (int i = 0; i < MasterSimConfig.getInstance().getSlaveCount(); ++i) {
			localVirtualTime.put(i, 0L);
		}
		execFlag = new HashMap<Integer, Boolean>();
		for (int i = 0; i < MasterSimConfig.getInstance().getSlaveCount(); ++i) {
			execFlag.put(i, true);
		}
		stop = false;
	}

	public void clear() {
		localVirtualTime = null;
		execFlag = null;
		stop = true;
		controlThread = null;
	}

	public void start() {
		this.init();
		controlThread = new ControlThread();
		controlThread.start();
	}

	public boolean isFinished() {
		for (long slaveTime : localVirtualTime.values()) {
			if (slaveTime != Long.MAX_VALUE) {
				return false;
			}
		}
		return false;
	}

	private long calculateGlobalVirtualTime() {
		long globalVirtualTime = Long.MAX_VALUE;
		for (long slaveTime : localVirtualTime.values()) {
			if (slaveTime < globalVirtualTime) {
				globalVirtualTime = slaveTime;
			}
		}
		return globalVirtualTime;
	}

	private List<Integer> slavesToExec(long globalVirtualTime) {
		List<Integer> slaves = new ArrayList<Integer>();
		execFlag.clear();
		if (globalVirtualTime == Long.MAX_VALUE) {
			return slaves;
		}
		for (Entry<Integer, Long> entry : localVirtualTime.entrySet()) {
			if (entry.getValue() == globalVirtualTime) {
				slaves.add(entry.getKey());
				execFlag.put(entry.getKey(), true);
			}
		}
		return slaves;
	}

	private void setSlaveVirtualTime(int slaveId, long virtualTime) {
		if (localVirtualTime.get(slaveId) > virtualTime) {
			localVirtualTime.put(slaveId, virtualTime);
		}
	}

	private void resetVirtualTime(List<Integer> slaveIds) {
		for (int slaveId : slaveIds) {
			localVirtualTime.put(slaveId, Long.MAX_VALUE);
		}
	}

	private void endSimulation() {
		int slaveCount = MasterSimConfig.getInstance().getHostConfig().getSlaveHostCount();
		final CountDownLatch latch = new CountDownLatch(slaveCount);
		final Status status = new Status(true);
		final List<SimulationResult> resultList = new ArrayList<SimulationResult>();
		for (int i = 0; i < slaveCount; ++i) {
			final RpcController localController = new LionRpcController();
			final int salveId = i;
			SlaveServiceAgentManager.getInstance().getServiceAgent(i, true)
					.endSimulation(Empty.getDefaultInstance(), localController, new RpcCallback<SimulationResult>() {
						@Override
						public void run(SimulationResult parameter) {
							if (localController.failed()) {
								logger.error(String.format("End simulation on slave [%d] failed, error message [%s]", salveId,
										localController.errorText()));
								status.setFlag(false);
							} else {
								synchronized (resultList) {
									resultList.add(parameter);
								}
							}
							latch.countDown();
						}
					});
		}

		// Wait all slaves to finish
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
			status.setFlag(false);
		}
		
		// Store simulation result in log
		Log log = MasterSimConfig.getInstance().getLog();
		log.setEndTime(System.nanoTime());
		if (status.isFlag()) {
			SimulationResult result = mergeSimulationResult(resultList);
			// Update log status to finished
			log.setState(1);
			log.setResult(result.toByteArray());
			MasterSimConfig.getInstance().getDbOperation().saveLog(log);
			logger.info("Stop simulation on slaves successfully");
		} else {
			logger.error("Not all slaves end simulation successfully");
			// Update log status to failed
			log.setState(2);
			MasterSimConfig.getInstance().getDbOperation().saveLog(log);
		}
		
		MasterSimConfig.getInstance().clear();
		MasterTimeLine.getInstance().clear();
	}

	private boolean cancelSimulation() {
		int slaveCount = MasterSimConfig.getInstance().getHostConfig().getSlaveHostCount();
		final CountDownLatch latch = new CountDownLatch(slaveCount);
		final Status status = new Status(true);
		for (int i = 0; i < slaveCount; ++i) {
			final RpcController localController = new LionRpcController();
			final int salveId = i;
			SlaveServiceAgentManager.getInstance().getServiceAgent(i, true)
					.cancelSimulation(Empty.getDefaultInstance(), localController, new RpcCallback<Empty>() {
						@Override
						public void run(Empty parameter) {
							if (localController.failed()) {
								logger.error(String.format("Cancel simulation on slave [%d] failed, error message [%s]", salveId,
										localController.errorText()));
								status.setFlag(false);
							}
							latch.countDown();
						}
					});
		}

		// Wait all slaves to finish
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
			status.setFlag(false);
		}
		return status.isFlag();
	}

	private SimulationResult mergeSimulationResult(List<SimulationResult> resultList) {
		SimulationResult.Builder builder = SimulationResult.newBuilder();
		for (SimulationResult result : resultList) {
			builder.addAllEnergyData(result.getEnergyDataList());
		}
		return builder.build();
	}
	
	private void storePartialEvents() {
		EventsDetail eventsDetail = MasterSimConfig.getInstance().buildEventsDetail();
		if(eventsDetail.getSyncCount() <= 0) {
			return;
		}
		long fromCycle = 0L;
		LVTSync startSync = eventsDetail.getSync(0);
		if(startSync.getProcessedEventCount() > 0) {
			fromCycle = startSync.getProcessedEvent(0).getStartTime();
		}
		
		long endCycle = 0L;
		LVTSync endSync = eventsDetail.getSync(eventsDetail.getSyncCount() - 1);
		if(endSync.getProcessedEventCount() > 0) {
			endCycle = endSync.getProcessedEvent(0).getStartTime();
		}
		
		Log log = MasterSimConfig.getInstance().getLog();
		String fileName = String.format("%d-%d.bin", fromCycle, endCycle);
		log.addFile(fileName);
		FileTool.storeFile(log.getEventDetailDir(), fileName, eventsDetail.toByteArray());
	}

	private class ControlThread extends Thread {

		@Override
		public void run() {
			super.run();
			while (!stop) {
				long globalVirtualTime = calculateGlobalVirtualTime();
				List<Integer> slaveIds = slavesToExec(globalVirtualTime);
				if (slaveIds.isEmpty()) {
					// Simulation end, notify all slaves to stop, and set
					// simulation status
					if(MasterSimConfig.getInstance().outputDetail()) {
						storePartialEvents();
					}
					endSimulation();
					stop = true;
					break;
				} else {
					resetVirtualTime(slaveIds);
					// Notify slaves to run
					int slaveCount = slaveIds.size();
					final CountDownLatch latch = new CountDownLatch(slaveCount);
					final Status status = new Status(true);
					ExecRequest.Builder requestbuilder = ExecRequest.newBuilder();
					requestbuilder.setGlobalVirtualTime(globalVirtualTime);
					final ExecRequest execRequest = requestbuilder.build();
					for (final int slaveId : slaveIds) {
						final RpcController localController = new LionRpcController();
						SlaveServiceAgentManager.getInstance().getServiceAgent(slaveId, true)
								.exec(execRequest, localController, new RpcCallback<LVTSync>() {
									@Override
									public void run(LVTSync lvtSync) {
										if (localController.failed()) {
											logger.error(String.format("Exec request on slave [%d] failed, error message [%s]", slaveId,
													localController.errorText()));
											status.setFlag(false);
										} else {
											synchronized (localVirtualTime) {
												// Adjust slave local virtual
												// time
												setSlaveVirtualTime(lvtSync.getSlaveId(), lvtSync.getLocalTime());
												for (LVTSync.Update update : lvtSync.getUpdateList()) {
													setSlaveVirtualTime(update.getSlaveId(), update.getLocalTime());
												}

												// If needed, save event details
												if (MasterSimConfig.getInstance().outputDetail()) {
													MasterSimConfig.getInstance().addSync(lvtSync);
												}
											}
										}
										latch.countDown();
									}
								});
					}
					// Wait all exec slaves to finish
					try {
						latch.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
						status.setFlag(false);
					}
					if (status.isFlag()) {
						logger.info(String.format("Cycle %d run on slaves %s successfully", globalVirtualTime, slaveIds.toString()));

						// If log is needed to flush to file, store it.
						if(MasterSimConfig.getInstance().outputDetail()) {
							if (MasterSimConfig.getInstance().getEventsDetailSize() >= MasterSimConfig.getInstance().getLogFlushCycle()) {
								storePartialEvents();
							}
						}
					} else {
						logger.error(String.format("Cycle %d run on slaves %s falied", globalVirtualTime, slaveIds.toString()));
						// Error happened, simulation end, notify all slaves to
						// stop, and set simulation status
						Log log = MasterSimConfig.getInstance().getLog();
						// Update log status to failed
						log.setState(2);
						MasterSimConfig.getInstance().getDbOperation().saveLog(log);
						cancelSimulation();
						stop = true;
						break;
					}
				}
			}
		}
	}
}
