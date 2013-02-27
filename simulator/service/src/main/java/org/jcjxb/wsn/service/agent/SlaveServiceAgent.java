package org.jcjxb.wsn.service.agent;

import java.net.UnknownHostException;

import org.jcjxb.wsn.rpc.LionRpcChannelFactory;
import org.jcjxb.wsn.service.proto.BasicDataType.Host;
import org.jcjxb.wsn.service.proto.SlaveService.SService;
import org.jcjxb.wsn.service.proto.SlaveService.SService.BlockingInterface;
import org.jcjxb.wsn.service.proto.SlaveService.SService.Interface;
import org.jcjxb.wsn.service.sim.MasterSimConfig;

import com.google.protobuf.BlockingRpcChannel;
import com.google.protobuf.RpcChannel;
import com.google.protobuf.RpcController;
import com.google.protobuf.ServiceException;

public class SlaveServiceAgent {
	
	private int slaveId;
	
	private BlockingRpcChannel blockChannel = null;

	private BlockingInterface blockServiceStub = null;
	
	private RpcChannel channel = null;
	
	private Interface serviceStub = null;

	public SlaveServiceAgent(int slaveId) {
	}
	
	private synchronized BlockingRpcChannel getBlockChannel()
			throws UnknownHostException {
		if (blockChannel != null) {
			return blockChannel;
		}
		Host host = MasterSimConfig.getInstance().getHostConfig().getSlaveHost(slaveId);
		blockChannel = LionRpcChannelFactory.newBlockingRpcChannel(host.getHost(),
				host.getPort());
		return blockChannel;
	}

	private synchronized BlockingInterface getBlockServiceStub()
			throws UnknownHostException {
		if (blockServiceStub != null) {
			return blockServiceStub;
		}
		blockServiceStub = SService.newBlockingStub(getBlockChannel());
		return blockServiceStub;
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
