package org.jcjxb.wsn.service.agent;

import java.util.HashMap;
import java.util.Map;

import org.jcjxb.wsn.service.proto.BasicDataType.Host;
import org.jcjxb.wsn.service.sim.MasterSimConfig;
import org.jcjxb.wsn.service.sim.SlaveSimConfig;

public class SlaveServiceAgentManager {

	private static SlaveServiceAgentManager manager = new SlaveServiceAgentManager();

	private Map<Integer, SlaveServiceAgent> serviceAgentList = new HashMap<Integer, SlaveServiceAgent>();

	private SlaveServiceAgentManager() {
	}

	public static SlaveServiceAgentManager getInstance() {
		return manager;
	}

	public synchronized SlaveServiceAgent getServiceAgent(int slaveId, boolean forMaster) {
		SlaveServiceAgent agent = serviceAgentList.get(slaveId);
		if (agent == null) {
			Host host = null;
			if (forMaster) {
				host = MasterSimConfig.getInstance().getHostConfig().getSlaveHost(slaveId);
			} else {
				host = SlaveSimConfig.getInstance().getHostConfig().getSlaveHost(slaveId);
			}
			agent = new SlaveServiceAgent(host);
			serviceAgentList.put(slaveId, agent);
		}
		return agent;
	}
}
