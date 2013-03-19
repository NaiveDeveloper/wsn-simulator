package org.jcjxb.wsn.service.impl;

import org.apache.log4j.Logger;
import org.jcjxb.wsn.service.proto.BasicDataType.Empty;
import org.jcjxb.wsn.service.proto.SlaveService;
import org.jcjxb.wsn.service.proto.SlaveService.EventsRequest;
import org.jcjxb.wsn.service.proto.SlaveService.ExecRequest;
import org.jcjxb.wsn.service.proto.SlaveService.LVTSync;
import org.jcjxb.wsn.service.proto.WSNConfig.SimulationConfig;
import org.jcjxb.wsn.service.sim.SlaveSimConfig;
import org.jcjxb.wsn.service.sim.SlaveTimeLine;

import com.google.protobuf.RpcController;
import com.google.protobuf.ServiceException;

public class SlaveServiceImpl implements SlaveService.SService.BlockingInterface {

	private static Logger logger = Logger.getLogger(SlaveServiceImpl.class);

	@Override
	public Empty startSimulation(RpcController controller, SimulationConfig request) throws ServiceException {
		logger.info("A new simulation request is recieved");
		logger.debug("Message:\n" + request.toString());
		SlaveSimConfig.getInstance().initSimulation(request);
		SlaveTimeLine.getInstance().init();
		return Empty.getDefaultInstance();
	}

	@Override
	public LVTSync exec(RpcController controller, ExecRequest request) throws ServiceException {
		LVTSync.Builder syncBuilder = LVTSync.newBuilder();
		logger.info(String.format("Running cycle [%d]", request.getGlobalVirtualTime()));
		if (SlaveTimeLine.getInstance().run(request.getGlobalVirtualTime(), syncBuilder)) {
		} else {
			controller.setFailed("Process events failed");
		}
		return syncBuilder.build();
	}

	@Override
	public Empty sendEvents(RpcController controller, EventsRequest request) throws ServiceException {
		SlaveTimeLine.getInstance().addEvents(request.getEventList());
		return Empty.getDefaultInstance();
	}

	@Override
	public Empty endSimulation(RpcController controller, Empty request) throws ServiceException {
		logger.info("The simulation end request is recieved");
		SlaveSimConfig.getInstance().clear();
		SlaveTimeLine.getInstance().clear();
		return Empty.getDefaultInstance();
	}
}
