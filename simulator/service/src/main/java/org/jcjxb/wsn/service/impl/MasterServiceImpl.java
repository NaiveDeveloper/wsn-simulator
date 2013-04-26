package org.jcjxb.wsn.service.impl;

import java.util.concurrent.CountDownLatch;

import org.apache.log4j.Logger;
import org.jcjxb.wsn.common.Status;
import org.jcjxb.wsn.db.Log;
import org.jcjxb.wsn.rpc.LionRpcController;
import org.jcjxb.wsn.service.agent.SlaveServiceAgentManager;
import org.jcjxb.wsn.service.deploy.DeployStrategyManager;
import org.jcjxb.wsn.service.partition.PartitionStrategyManager;
import org.jcjxb.wsn.service.proto.BasicDataType.Empty;
import org.jcjxb.wsn.service.proto.MasterService;
import org.jcjxb.wsn.service.proto.MasterService.LVTSyncRequest;
import org.jcjxb.wsn.service.proto.MasterService.SlaveReadyRequest;
import org.jcjxb.wsn.service.proto.WSNConfig.DeployConfig;
import org.jcjxb.wsn.service.proto.WSNConfig.PartitionConfig;
import org.jcjxb.wsn.service.proto.WSNConfig.SimulationConfig;
import org.jcjxb.wsn.service.sim.MasterSimConfig;
import org.jcjxb.wsn.service.sim.MasterTimeLine;

import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcController;
import com.google.protobuf.ServiceException;

public class MasterServiceImpl implements MasterService.MService.BlockingInterface {

	private static Logger logger = Logger.getLogger(MasterServiceImpl.class);

	@Override
	public Empty slaveReady(RpcController controller, SlaveReadyRequest request) throws ServiceException {
		MasterSimConfig.getInstance().slaveReady(request.getHostIndex());
		logger.info(String.format("Salve with index %d is ready", request.getHostIndex()));
		return Empty.getDefaultInstance();
	}

	@Override
	public Empty startSimulation(RpcController controller, SimulationConfig request) throws ServiceException {
		logger.info("A new simulation request is recieved");
		logger.debug("Message:\n" + request.toString());
		if (!MasterSimConfig.getInstance().isAllSlaveReady()) {
			controller.setFailed("All slaves are not ready");
			logger.error("A start simulation rquest is recieved when all slaves are not ready");
			return Empty.getDefaultInstance();
		}

		// Just allow one thread
		synchronized (MasterSimConfig.getInstance()) {
			if (MasterSimConfig.getInstance().isSimRunning()) {
				controller.setFailed("A simulation is running, stop it first");
				logger.error("A start simulation rquest is recieved when a simulation is running");
				return Empty.getDefaultInstance();
			}

			// Complete SimulationConfig unfilled data
			SimulationConfig.Builder simulationConfigBuilder = SimulationConfig.newBuilder(request);

			// Generate deploy data
			DeployConfig.Builder deployConfigBuilder = simulationConfigBuilder.getDeployConfigBuilder();
			DeployStrategyManager.getInstance().deploy(deployConfigBuilder, request.getDeployConfig());

			// Divide sensors to slaves
			PartitionConfig.Builder partitionConfigBuilder = simulationConfigBuilder.getPartitionConfigBuilder();
			PartitionStrategyManager.getInstance().partition(partitionConfigBuilder, request.getPartitionConfig(),
					MasterSimConfig.getInstance().getSlaveCount(), deployConfigBuilder.getSensorNodeDeployConfig().getPostionList());

			SimulationConfig simulationConfig = simulationConfigBuilder.build();

			// Initialize simulation
			MasterSimConfig.getInstance().initSimulation(simulationConfig);

			// Insert log to db
			Log log = MasterSimConfig.getInstance().getLog();
			log.setState(0);
			if (!MasterSimConfig.getInstance().getDbOperation().saveLog(log)) {
				controller.setFailed("Insert start up log into db failed");
				logger.error("Insert initialization log to db failed");
				return Empty.getDefaultInstance();
			}
			
			if(MasterSimConfig.getInstance().outputDetail()) {
				log.setEventDetailDir(MasterSimConfig.getInstance().getLogPath() + log.getId() + "/");
			}

			int slaveCount = MasterSimConfig.getInstance().getHostConfig().getSlaveHostCount();
			final CountDownLatch latch = new CountDownLatch(slaveCount);
			final Status status = new Status(true);
			for (int i = 0; i < slaveCount; ++i) {
				final RpcController localController = new LionRpcController();
				final int salveId = i;
				SlaveServiceAgentManager.getInstance().getServiceAgent(i, true)
						.startSimulation(simulationConfig, localController, new RpcCallback<Empty>() {
							@Override
							public void run(Empty parameter) {
								if (localController.failed()) {
									logger.error(String.format("Start simulation on slave [%d] failed, error message [%s]", salveId,
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
			if (status.isFlag()) {
				logger.info("Start simulation on slaves successfully");
				logger.debug("Simulation message:\n" + simulationConfig.toString());
				MasterSimConfig.getInstance().setSimRunning(true);
				MasterTimeLine.getInstance().start();
			} else {
				controller.setFailed("All slaves are not ready");
				logger.error("Not all slaves start simulation successfully");

				// Update log status to failed
				log.setState(2);
				MasterSimConfig.getInstance().getDbOperation().saveLog(log);

				// cancel simulation
				cancelSimulation();
			}
		}
		return Empty.getDefaultInstance();
	}

	@Override
	public Empty sync(RpcController controller, LVTSyncRequest request) throws ServiceException {
		return null;
	}

	@Override
	public Empty stopSimulation(RpcController controller, Empty request) throws ServiceException {
		// Notify all slaves to stop simulation
		// TODO
		MasterSimConfig.getInstance().clear();
		return Empty.getDefaultInstance();
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
}
