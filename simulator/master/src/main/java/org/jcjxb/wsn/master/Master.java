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

	private static Options.StringOption hostConfigOption = mainOptions.newOption("hostConfig", "hostConfig.bin",
			"This option defines master and slaves host config");

	private static Options.IntOption logFlushCycleOption = mainOptions.newOption("logFlushCycle", 100,
			"Split logs into different files, this value control how to split");

	private static Options.StringOption dbHostOption = mainOptions.newOption("dbHost", "127.0.0.1",
			"The database to store log information");

	private static Options.StringOption logPathOption = mainOptions.newOption("logPath", "/var/tssim/data",
			"Directory to store event detail log");

	public static void main(String[] args) throws Exception {
		// Parse command line arguments
		mainOptions.parseCommandLine(args);

		// Check host config argument
		HostConfig hostConfig = null;
		if (!"".equals(hostConfigOption.getValue())) {
			hostConfig = HostConfig.parseFrom(new FileInputStream(hostConfigOption.getValue()));
			if (hostConfig == null) {
				logger.error("Parsing host config file fails, hostConfig = " + hostConfigOption.getValue());
				return;
			}
		} else {
			logger.error("Please specify host config file path");
			return;
		}

		// Check log flush cycle option
		if (logFlushCycleOption.getValue() <= 0) {
			logger.error("Please correctly specify log flush cycle, logFlushCycle = " + logFlushCycleOption.getValue());
			return;
		}

		// Check db host option
		if ("".equals(dbHostOption.getValue())) {
			logger.error("Please correctly specify db host, dbHost = " + dbHostOption.getValue());
			return;
		}

		// Check log path option
		if ("".equals(logPathOption.getValue())) {
			logger.error("Please correctly specify log path, logPath = " + logPathOption.getValue());
			return;
		}

		// Set options in SimConfig
		MasterSimConfig.getInstance().setHostConfig(hostConfig);
		MasterSimConfig.getInstance().setLogFlushCycle(logFlushCycleOption.getValue());
		MasterSimConfig.getInstance().setDbHost(dbHostOption.getValue());
		MasterSimConfig.getInstance().setLogPath(logPathOption.getValue());

		LionRpcServer rpcServer = new LionRpcSocketServer(hostConfig.getMasterHost().getPort(), hostConfig.getMasterHost().getHost());

		// Register master services
		rpcServer.registerBlockingService(MasterService.MService.newReflectiveBlockingService(new MasterServiceImpl()));

		rpcServer.start();

		logger.info(String.format("Master Server is running on port %d, ip %s now", hostConfig.getMasterHost().getPort(), hostConfig
				.getMasterHost().getHost()));

		try {
			rpcServer.waitEnd();
		} catch (InterruptedException e) {
			logger.error("Exception happens", e);
		}
		logger.info("Master Server is exiting now");
	}
}
