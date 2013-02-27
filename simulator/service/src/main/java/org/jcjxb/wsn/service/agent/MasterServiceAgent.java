package org.jcjxb.wsn.service.agent;

import java.net.UnknownHostException;

import org.jcjxb.wsn.rpc.LionRpcChannelFactory;
import org.jcjxb.wsn.service.proto.BasicDataType.Host;
import org.jcjxb.wsn.service.proto.MasterService.MService;
import org.jcjxb.wsn.service.proto.MasterService.MService.BlockingInterface;
import org.jcjxb.wsn.service.proto.MasterService.SlaveReadyRequest;
import org.jcjxb.wsn.service.sim.SlaveSimConfig;

import com.google.protobuf.BlockingRpcChannel;
import com.google.protobuf.RpcController;
import com.google.protobuf.ServiceException;

public class MasterServiceAgent {

	private static MasterServiceAgent serviceAgent = new MasterServiceAgent();

	private BlockingRpcChannel channel = null;

	private BlockingInterface serviceStub = null;

	private MasterServiceAgent() {
	}

	public static MasterServiceAgent getInstance() {
		return serviceAgent;
	}

	private synchronized BlockingRpcChannel getChannel()
			throws UnknownHostException {
		if (channel != null) {
			return channel;
		}
		Host host = SlaveSimConfig.getInstance().getHostConfig()
				.getMasterHost();
		channel = LionRpcChannelFactory.newBlockingRpcChannel(host.getHost(),
				host.getPort());
		return channel;
	}

	private synchronized BlockingInterface getServiceStub()
			throws UnknownHostException {
		if (serviceStub != null) {
			return serviceStub;
		}
		serviceStub = MService.newBlockingStub(getChannel());
		return serviceStub;
	}

	public void slaveReady(Integer hostIndex, RpcController controller) {
		SlaveReadyRequest request = SlaveReadyRequest.newBuilder()
				.setHostIndex(hostIndex).build();
		try {
			getServiceStub().slaveReady(controller, request);
		} catch (Exception e) {
			handleRpcException(e, controller);
		}
	}

	private void handleRpcException(Exception exception,
			RpcController controller) {
		if (exception instanceof UnknownHostException) {
			controller.setFailed("Master server IO Connection error");
		} else if (exception instanceof ServiceException) {
			controller.setFailed("Service do not support");
		}
		exception.printStackTrace();
	}
}