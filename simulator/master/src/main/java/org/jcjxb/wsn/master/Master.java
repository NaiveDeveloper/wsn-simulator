package org.jcjxb.wsn.master;

import java.net.UnknownHostException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.jcjxb.wsn.common.Options;
import org.jcjxb.wsn.rpc.LionRpcServer;
import org.jcjxb.wsn.rpc.LionRpcSocketServer;

public class Master {

	static {
		String log4jPropertyFile = System.getProperty("log4j.properties");
		if (log4jPropertyFile != null) {
			PropertyConfigurator.configure(log4jPropertyFile);
		}
	}

	private static Logger logger = Logger.getLogger(Master.class);

	private static Options mainOptions = new Options();

	private static Options.StringOption hostConfigOption = mainOptions
			.newOption("hostConfig", "hostConfig.txt",
					"This option defines master and slaves host config");

	private static Options.IntOption hostIndexOption = mainOptions.newOption(
			"hostIndex", 1,
			"This option defines this slave's index in host config");

	public static void main(String[] args) throws UnknownHostException {
		mainOptions.parseCommandLine(args);
		if(!"".equals(hostConfigOption.getValue())) {
			
		}
		int port = 8080;
		LionRpcServer rpcServer = new LionRpcSocketServer(port, "127.0.0.1");
		// 注册服务
		rpcServer.start();
		logger.info(String.format("Master Server is running on port %d now...",
				port));
		try {
			rpcServer.waitEnd();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		logger.info("Master Server is exiting now...");
	}
}
