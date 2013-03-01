package org.jcjxb.wsn.service.agent;

import org.jcjxb.wsn.service.proto.BasicDataType.Host;
import org.jcjxb.wsn.service.sim.SlaveSimConfig;

public class MasterServicAgentManager {

	private static MasterServicAgentManager manager = new MasterServicAgentManager();

	private MasterServiceAgent serviceAgent = null;

	private MasterServicAgentManager() {
	}

	public static MasterServicAgentManager getInstance() {
		return manager;
	}

	public synchronized MasterServiceAgent getServiceAgent() {
		if (serviceAgent == null) {
			serviceAgent = new MasterServiceAgent(SlaveSimConfig.getInstance().getHostConfig().getMasterHost());
		}
		return serviceAgent;
	}

	public synchronized MasterServiceAgent getServiceAgent(String host, int port) {
		if (serviceAgent == null) {
			Host.Builder builder = Host.newBuilder();
			builder.setHost(host);
			builder.setPort(port);
			serviceAgent = new MasterServiceAgent(builder.build());
		}
		return serviceAgent;
	}
}
