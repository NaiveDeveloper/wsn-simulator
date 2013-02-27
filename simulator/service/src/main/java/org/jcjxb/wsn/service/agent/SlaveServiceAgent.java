package org.jcjxb.wsn.service.agent;

import java.net.UnknownHostException;

import org.jcjxb.wsn.rpc.LionRpcChannelFactory;
import org.jcjxb.wsn.service.proto.BasicDataType.Host;
import org.jcjxb.wsn.service.proto.SlaveService.SService;
import org.jcjxb.wsn.service.proto.SlaveService.SService.BlockingInterface;
import org.jcjxb.wsn.service.sim.MasterSimConfig;

import com.google.protobuf.BlockingRpcChannel;
import com.google.protobuf.RpcController;
import com.google.protobuf.ServiceException;

public class SlaveServiceAgent {
	
	private int slaveId;
	
	private BlockingRpcChannel channel = null;

	private BlockingInterface serviceStub = null;

	public SlaveServiceAgent(int slaveId) {
	}
	
	private synchronized BlockingRpcChannel getChannel()
			throws UnknownHostException {
		if (channel != null) {
			return channel;
		}
		Host host = MasterSimConfig.getInstance().getHostConfig().getSlaveHost(slaveId);
		channel = LionRpcChannelFactory.newBlockingRpcChannel(host.getHost(),
				host.getPort());
		return channel;
	}

	private synchronized BlockingInterface getServiceStub()
			throws UnknownHostException {
		if (serviceStub != null) {
			return serviceStub;
		}
		serviceStub = SService.newBlockingStub(getChannel());
		return serviceStub;
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
