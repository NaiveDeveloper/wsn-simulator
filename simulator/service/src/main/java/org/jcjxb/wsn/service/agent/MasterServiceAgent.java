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

	private static BlockingRpcChannel channel = null;

	private static BlockingInterface serviceStub = null;

	private MasterServiceAgent() {
	}

	public static MasterServiceAgent getInstance() {
		return serviceAgent;
	}

	private static synchronized BlockingRpcChannel getChannel()
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

	private static synchronized BlockingInterface getServiceStub()
			throws UnknownHostException {
		if (serviceStub != null) {
			return serviceStub;
		}
		serviceStub = MService.newBlockingStub(getChannel());
		return null;
	}

	public void slaveReady(Integer hostIndex, RpcController controller) {
		SlaveReadyRequest request = SlaveReadyRequest.newBuilder()
				.setHostIndex(hostIndex).build();
		try {
			getServiceStub().slaveReady(controller, request);
		} catch (UnknownHostException e) {
			controller.setFailed("Master server IO Connection error");
			e.printStackTrace();
		} catch (ServiceException e) {
			controller.setFailed("Service do not support");
			e.printStackTrace();
		}
	}
}
