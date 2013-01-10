package org.jcjxb.wsn.service.impl;

import org.jcjxb.wsn.service.proto.BasicDataType.Empty;
import org.jcjxb.wsn.service.proto.MasterService;
import org.jcjxb.wsn.service.proto.MasterService.LVTSyncRequest;
import org.jcjxb.wsn.service.proto.MasterService.SlaveReadyRequest;
import org.jcjxb.wsn.service.proto.SimulationConfig.DeployConfig;
import org.jcjxb.wsn.service.sim.MasterSimConfig;

import com.google.protobuf.RpcController;
import com.google.protobuf.ServiceException;

public class MasterServiceImpl implements
		MasterService.MService.BlockingInterface {

	@Override
	public Empty slaveReady(RpcController controller, SlaveReadyRequest request)
			throws ServiceException {
		MasterSimConfig.getInstance().slaveReady(request.getHostIndex());
		return Empty.getDefaultInstance();
	}

	@Override
	public Empty startSimulation(RpcController controller, DeployConfig request)
			throws ServiceException {
		if (!MasterSimConfig.getInstance().isAllSlaveReady()) {
			controller.setFailed("All slaves are not ready");
			return Empty.getDefaultInstance();
		}
		if (MasterSimConfig.getInstance().isSimRunning()) {
			controller.setFailed("A simulation is running, stop it first");
			return Empty.getDefaultInstance();
		}
		// to do 准备 InitSimCmd

		return Empty.getDefaultInstance();
	}

	@Override
	public Empty sync(RpcController controller, LVTSyncRequest request)
			throws ServiceException {
		return null;
	}

	@Override
	public Empty stopSimulation(RpcController controller, Empty request)
			throws ServiceException {
		// to do 通知所有Slave停止模拟

		MasterSimConfig.getInstance().clear();
		return Empty.getDefaultInstance();
	}
}
