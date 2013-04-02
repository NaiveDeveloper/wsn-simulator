package org.jcjxb.wsn.service.agent;

import java.net.UnknownHostException;

import org.jcjxb.wsn.rpc.LionRpcChannelFactory;
import org.jcjxb.wsn.service.proto.BasicDataType.Empty;
import org.jcjxb.wsn.service.proto.BasicDataType.Host;
import org.jcjxb.wsn.service.proto.SlaveService.EventsRequest;
import org.jcjxb.wsn.service.proto.SlaveService.ExecRequest;
import org.jcjxb.wsn.service.proto.SlaveService.LVTSync;
import org.jcjxb.wsn.service.proto.SlaveService.SService;
import org.jcjxb.wsn.service.proto.SlaveService.SService.BlockingInterface;
import org.jcjxb.wsn.service.proto.SlaveService.SService.Interface;
import org.jcjxb.wsn.service.proto.WSNConfig.SimulationConfig;

import com.google.protobuf.BlockingRpcChannel;
import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcChannel;
import com.google.protobuf.RpcController;
import com.google.protobuf.ServiceException;

public class SlaveServiceAgent {

	private Host host;

	private BlockingRpcChannel blockChannel = null;

	private BlockingInterface blockServiceStub = null;

	private RpcChannel channel = null;

	private Interface serviceStub = null;

	public SlaveServiceAgent(Host host) {
		this.host = host;
	}

	private synchronized BlockingRpcChannel getBlockChannel() throws UnknownHostException {
		if (blockChannel != null) {
			return blockChannel;
		}
		blockChannel = LionRpcChannelFactory.newBlockingRpcChannel(host.getHost(), host.getPort());
		return blockChannel;
	}

	private synchronized BlockingInterface getBlockServiceStub() throws UnknownHostException {
		if (blockServiceStub != null) {
			return blockServiceStub;
		}
		blockServiceStub = SService.newBlockingStub(getBlockChannel());
		return blockServiceStub;
	}

	private synchronized RpcChannel getChannel() throws UnknownHostException {
		if (channel != null) {
			return channel;
		}
		channel = LionRpcChannelFactory.newRpcChannel(host.getHost(), host.getPort());
		return channel;
	}

	private synchronized Interface getServiceStub() throws UnknownHostException {
		if (serviceStub != null) {
			return serviceStub;
		}
		serviceStub = SService.newStub(getChannel());
		return serviceStub;
	}

	public void startSimulation(SimulationConfig simulationConfig, RpcController controller) {
		try {
			getBlockServiceStub().startSimulation(controller, simulationConfig);
		} catch (Exception e) {
			handleRpcException(e, controller);
		}
	}

	public void startSimulation(SimulationConfig simulationConfig, RpcController controller, RpcCallback<Empty> done) {
		try {
			getServiceStub().startSimulation(controller, simulationConfig, done);
		} catch (Exception e) {
			handleRpcException(e, controller);
		}
	}

	public void exec(ExecRequest execRequest, RpcController controller, RpcCallback<LVTSync> done) {
		try {
			getServiceStub().exec(controller, execRequest, done);
		} catch (Exception e) {
			handleRpcException(e, controller);
		}
	}

	public void sendEvents(EventsRequest eventsRequest, RpcController controller, RpcCallback<Empty> done) {
		try {
			getServiceStub().sendEvents(controller, eventsRequest, done);
		} catch (Exception e) {
			handleRpcException(e, controller);
		}
	}

	public void endSimulation(Empty empty, RpcController controller, RpcCallback<Empty> done) {
		try {
			getServiceStub().endSimulation(controller, empty, done);
		} catch (Exception e) {
			handleRpcException(e, controller);
		}
	}

	private void handleRpcException(Exception exception, RpcController controller) {
		if (exception instanceof UnknownHostException) {
			controller.setFailed("Master server IO Connection error");
		} else if (exception instanceof ServiceException) {
			controller.setFailed("Service do not support");
		}
		exception.printStackTrace();
	}
}
