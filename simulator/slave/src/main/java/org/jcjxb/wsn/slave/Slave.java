package org.jcjxb.wsn.slave;

import java.net.UnknownHostException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.jcjxb.wsn.rpc.LionRpcController;

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
		
		logger.info("Slave Server is exiting now...");
	}

}
