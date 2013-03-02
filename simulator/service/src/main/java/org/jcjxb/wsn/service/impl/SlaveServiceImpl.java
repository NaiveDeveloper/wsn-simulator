package org.jcjxb.wsn.service.impl;

import org.apache.log4j.Logger;
import org.jcjxb.wsn.service.proto.BasicDataType.Empty;
import org.jcjxb.wsn.service.proto.SlaveService;
import org.jcjxb.wsn.service.proto.WSNConfig.SimulationConfig;
import org.jcjxb.wsn.service.sim.SlaveSimConfig;

import com.google.protobuf.RpcController;
import com.google.protobuf.ServiceException;

public class SlaveServiceImpl implements SlaveService.SService.BlockingInterface{
	
	private static Logger logger = Logger.getLogger(SlaveServiceImpl.class);

	@Override
	public Empty startSimulation(RpcController controller, SimulationConfig request) throws ServiceException {
		logger.info("A new simulation request is recieved");
		logger.debug("Message:\n" + request.toString());
		SlaveSimConfig.getInstance().initSimulation(request);
		return Empty.getDefaultInstance();
	}
}
