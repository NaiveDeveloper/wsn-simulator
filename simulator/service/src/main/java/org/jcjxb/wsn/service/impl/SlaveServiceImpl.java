package org.jcjxb.wsn.service.impl;

import org.jcjxb.wsn.service.proto.BasicDataType.Empty;
import org.jcjxb.wsn.service.proto.SlaveService;
import org.jcjxb.wsn.service.proto.WSNConfig.SimulationConfig;

import com.google.protobuf.RpcController;
import com.google.protobuf.ServiceException;

public class SlaveServiceImpl implements SlaveService.SService.BlockingInterface{

	@Override
	public Empty startSimulation(RpcController controller, SimulationConfig request) throws ServiceException {
		return null;
	}
}
