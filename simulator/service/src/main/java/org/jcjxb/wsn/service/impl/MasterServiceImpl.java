package org.jcjxb.wsn.service.impl;

import org.jcjxb.wsn.service.proto.BasicDataType.Empty;
import org.jcjxb.wsn.service.proto.BasicDataType.Host;
import org.jcjxb.wsn.service.proto.MasterService;
import org.jcjxb.wsn.service.proto.MasterService.LVTSyncRequest;
import org.jcjxb.wsn.service.proto.SimulationConfig.DeployConfig;

import com.google.protobuf.RpcController;
import com.google.protobuf.ServiceException;

public class MasterServiceImpl implements
		MasterService.MService.BlockingInterface {

	@Override
	public Empty slaveReady(RpcController controller, Host request)
			throws ServiceException {
		return null;
	}

	@Override
	public Empty startSimulation(RpcController controller, DeployConfig request)
			throws ServiceException {
		return null;
	}

	@Override
	public Empty sync(RpcController controller, LVTSyncRequest request)
			throws ServiceException {
		return null;
	}
}
