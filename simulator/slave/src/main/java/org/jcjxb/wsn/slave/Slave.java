package org.jcjxb.wsn.slave;

import java.io.FileInputStream;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.jcjxb.wsn.common.Options;
import org.jcjxb.wsn.rpc.LionRpcController;
import org.jcjxb.wsn.rpc.LionRpcServer;
import org.jcjxb.wsn.rpc.LionRpcSocketServer;
import org.jcjxb.wsn.service.agent.MasterServiceAgent;
import org.jcjxb.wsn.service.impl.SlaveServiceImpl;
import org.jcjxb.wsn.service.proto.SimulatorConfig.HostConfig;
import org.jcjxb.wsn.service.proto.SlaveService;
import org.jcjxb.wsn.service.sim.SlaveSimConfig;

import com.google.protobuf.RpcController;

public class Slave {

	static {
		String log4jPropertyFile = System.getProperty("log4j.properties");
		if (log4jPropertyFile != null) {
			PropertyConfigurator.configure(log4jPropertyFile);
		}
	}

	private static Logger logger = Logger.getLogger(Slave.class);

	private static Options mainOptions = new Options();

	private static Options.StringOption hostConfigOption = mainOptions.newOption("hostConfig", "hostConfig.txt",
			"This option defines master and slaves host config");

	private static Options.IntOption hostIndexOption = mainOptions.newOption("hostIndex", -1,
			"This option defines host index in host config for this slave");

	public static void main(String[] args) throws Exception {
		// Parse command line arguments
		mainOptions.parseCommandLine(args);

		// Check host config argument
		HostConfig hostConfig = null;
		if (!"".equals(hostConfigOption.getValue())) {
			hostConfig = HostConfig.parseFrom(new FileInputStream(hostConfigOption.getValue()));
			if (hostConfig == null) {
				logger.error("Parse Host Config File error, hostConfig = " + hostConfigOption.getValue());
				return;
			}
		} else {
			logger.error("Please specify host config file path");
			return;
		}

		// Check host index option
		if (hostIndexOption.getValue() < 0 || hostIndexOption.getValue() >= hostConfig.getSlaveHostCount()) {
			logger.error("Please correctly specify host index argument, hostIndex = " + hostIndexOption.getValue());
			return;
		}

		// Set host config and host index in SimConfig
		SlaveSimConfig.getInstance().setHostConfig(hostConfig);
		SlaveSimConfig.getInstance().setHostIndex(hostIndexOption.getValue());

		LionRpcServer rpcServer = new LionRpcSocketServer(hostConfig.getSlaveHost(hostIndexOption.getValue()).getPort(), hostConfig
				.getSlaveHost(hostIndexOption.getValue()).getHost());

		// Register slave services
		rpcServer.registerBlockingService(SlaveService.SService.newReflectiveBlockingService(new SlaveServiceImpl()));

		rpcServer.start();

		logger.info(String.format("Slave Server is running on port %d, ip %s now", hostConfig.getSlaveHost(hostIndexOption.getValue())
				.getPort(), hostConfig.getSlaveHost(hostIndexOption.getValue()).getHost()));

		// Send slave ready message to master
		RpcController controller = new LionRpcController();
		MasterServiceAgent.getInstance().slaveReady(hostIndexOption.getValue(), controller);
		if (controller.failed()) {
			logger.error("Send slave ready message failed, error text is " + controller.errorText());
			return;
		}

		try {
			rpcServer.waitEnd();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		logger.info("Slave server is exiting now");
	}

}
