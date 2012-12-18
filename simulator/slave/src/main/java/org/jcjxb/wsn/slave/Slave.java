package org.jcjxb.wsn.slave;

import java.io.FileInputStream;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.jcjxb.wsn.common.Options;
import org.jcjxb.wsn.rpc.LionRpcServer;
import org.jcjxb.wsn.rpc.LionRpcSocketServer;
import org.jcjxb.wsn.service.impl.SlaveServiceImpl;
import org.jcjxb.wsn.service.proto.SimulatorConfig.HostConfig;
import org.jcjxb.wsn.service.proto.SlaveService;

public class Slave {

	static {
		String log4jPropertyFile = System.getProperty("log4j.properties");
		if (log4jPropertyFile != null) {
			PropertyConfigurator.configure(log4jPropertyFile);
		}
	}

	private static Logger logger = Logger.getLogger(Slave.class);

	private static Options mainOptions = new Options();

	private static Options.StringOption hostConfigOption = mainOptions
			.newOption("hostConfig", "hostConfig.txt",
					"This option defines master and slaves host config");

	private static Options.IntOption hostIndexOption = mainOptions.newOption(
			"hostIndex", -1,
			"This option defines host index in host config for this slave");

	public static void main(String[] args) throws Exception {
		mainOptions.parseCommandLine(args);
		HostConfig hostConfig = null;
		if (!"".equals(hostConfigOption.getValue())) {
			hostConfig = HostConfig.parseFrom(new FileInputStream(
					hostConfigOption.getValue()));
			if (hostConfig == null) {
				logger.info("Parse Host Config File error");
				return;
			}
		} else {
			logger.info("Please specify host config file path");
			return;
		}

		if (hostIndexOption.getValue() < 0
				|| hostIndexOption.getValue() >= hostConfig.getSlaveHostCount()) {
			logger.info("Please correctly specify host index argument");
			return;
		}

		LionRpcServer rpcServer = new LionRpcSocketServer(hostConfig
				.getSlaveHost(hostIndexOption.getValue()).getPort(), hostConfig
				.getSlaveHost(hostIndexOption.getValue()).getHost());
		// 注册服务
		rpcServer.registerBlockingService(SlaveService.SService
				.newReflectiveBlockingService(new SlaveServiceImpl()));

		rpcServer.start();

		logger.info(String.format("Slave Server is running on port %d now...",
				hostConfig.getSlaveHost(hostIndexOption.getValue()).getPort()));
		try {
			rpcServer.waitEnd();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		logger.info("Slave Server is exiting now...");
	}

}
