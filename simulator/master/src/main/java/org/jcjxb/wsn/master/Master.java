package org.jcjxb.wsn.master;

import java.io.FileInputStream;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.jcjxb.wsn.common.Options;
import org.jcjxb.wsn.rpc.LionRpcServer;
import org.jcjxb.wsn.rpc.LionRpcSocketServer;
import org.jcjxb.wsn.service.impl.MasterServiceImpl;
import org.jcjxb.wsn.service.proto.MasterService;
import org.jcjxb.wsn.service.proto.SimulatorConfig.HostConfig;
import org.jcjxb.wsn.service.sim.MasterSimConfig;

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

	public static void main(String[] args) throws Exception {
		mainOptions.parseCommandLine(args);
		HostConfig hostConfig = null;
		if (!"".equals(hostConfigOption.getValue())) {
			hostConfig = HostConfig.parseFrom(new FileInputStream(
					hostConfigOption.getValue()));
			if (hostConfig == null) {
				logger.info("Parsing Host Config File failed");
				return;
			}
		} else {
			logger.info("Please specify host config file path");
			return;
		}
		
		// 设置 host config in SimConfig
		MasterSimConfig.getInstance().setHostConfig(hostConfig);
		MasterSimConfig.getInstance().setHost(hostConfig.getMasterHost());

		LionRpcServer rpcServer = new LionRpcSocketServer(hostConfig
				.getMasterHost().getPort(), hostConfig.getMasterHost()
				.getHost());
		// 注册服务
		rpcServer.registerBlockingService(MasterService.MService
				.newReflectiveBlockingService(new MasterServiceImpl()));
		
		rpcServer.start();
		
		logger.info(String.format("Master Server is running on port %d now...",
				hostConfig.getMasterHost().getPort()));
		try {
			rpcServer.waitEnd();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		logger.info("Master Server is exiting now...");
	}
}
