package org.jcjxb.wsn.slave;

import java.net.UnknownHostException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.jcjxb.wsn.rpc.LionRpcChannelFactory;
import org.jcjxb.wsn.rpc.LionRpcController;
import org.jcjxb.wsn.service.proto.MasterService;

import com.google.protobuf.ServiceException;

public class Slave {
	
	static {
		String log4jPropertyFile = System.getProperty("log4j.properties");
		if (log4jPropertyFile != null) {
			PropertyConfigurator.configure(log4jPropertyFile);
		}
	}

	private static Logger logger = Logger.getLogger(Slave.class);

	public static void main(String[] args) throws UnknownHostException {
		LionRpcController rpcController = new LionRpcController();
		MasterService.TimeSyncService.BlockingInterface stub = MasterService.TimeSyncService
				.newBlockingStub(LionRpcChannelFactory.newBlockingRpcChannel(
						"127.0.0.1", 8080));
		MasterService.TimeSyncRequest.Builder builder = MasterService.TimeSyncRequest
				.newBuilder();
		builder.setLocalTime(100);
		try {
			MasterService.TimeAckResponse rpcResponse = stub.sync(
					rpcController, builder.build());
			if (rpcController.failed()) {
				logger.info(rpcController.errorText());
			} else {
				logger.info(rpcResponse.toString());
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		logger.info("Slave Server is exiting now...");
	}

}
