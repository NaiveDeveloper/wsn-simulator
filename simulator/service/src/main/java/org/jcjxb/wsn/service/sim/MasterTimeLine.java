package org.jcjxb.wsn.service.sim;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CountDownLatch;

import org.apache.log4j.Logger;
import org.jcjxb.wsn.common.Status;
import org.jcjxb.wsn.rpc.LionRpcController;
import org.jcjxb.wsn.service.agent.SlaveServiceAgentManager;
import org.jcjxb.wsn.service.proto.SlaveService.ExecRequest;
import org.jcjxb.wsn.service.proto.SlaveService.LVTSync;

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
		for (Entry<Integer, Long> entry : localVirtualTime.entrySet()) {
			if (entry.getValue() == globalVirtualTime) {
				slaves.add(entry.getKey());
				execFlag.put(entry.getKey(), true);
			}
		}
		return slaves;
	}
	
	private void setSlaveVirtualTime(int slaveId, long virtualTime) {
		synchronized (localVirtualTime) {
			if(localVirtualTime.get(slaveId) > virtualTime) {
				localVirtualTime.put(slaveId, virtualTime);
			}
		}
	}
	
	private void resetVirtualTime(List<Integer> slaveIds) {
		for (int slaveId : slaveIds) {
			localVirtualTime.put(slaveId, Long.MAX_VALUE);
		}
	}
	
	private class ControlThread extends Thread {

		@Override
		public void run() {
			super.run();
			while(!stop) {
				long globalVirtualTime = calculateGlobalVirtualTime();
				List<Integer> slaveIds = slavesToExec(globalVirtualTime);
				if(slaveIds.isEmpty()) {
					// Simulation end, notify all slaves to stop, and set simulation status
					// TODO
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
						SlaveServiceAgentManager.getInstance().getServiceAgent(slaveId)
								.exec(execRequest, localController, new RpcCallback<LVTSync>() {
									@Override
									public void run(LVTSync lvtSync) {
										if (localController.failed()) {
											logger.error(String.format("Exec request on slave [%d] failed, error message [%s]", slaveId,
													localController.errorText()));
											status.setFlag(false);
										} else {
											setSlaveVirtualTime(lvtSync.getSlaveId(), lvtSync.getLocalTime());
											for(LVTSync.Update update : lvtSync.getUpdateList()) {
												setSlaveVirtualTime(update.getSlaveId(), update.getLocalTime());
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
					} else {
						logger.error(String.format("Cycle %d run on slaves %s falied", globalVirtualTime, slaveIds.toString()));
						// Error happened, simulation end, notify all slaves to stop, and set simulation status
						// TODO
					}
				}
			}
		}
	}
}
