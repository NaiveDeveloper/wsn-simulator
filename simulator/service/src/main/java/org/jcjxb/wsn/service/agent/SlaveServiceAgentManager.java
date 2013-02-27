package org.jcjxb.wsn.service.agent;

import java.util.HashMap;
import java.util.Map;

public class SlaveServiceAgentManager {

	private static SlaveServiceAgentManager manager = new SlaveServiceAgentManager();

	private Map<Integer, SlaveServiceAgent> serviceAgentList = new HashMap<Integer, SlaveServiceAgent>();

	private SlaveServiceAgentManager() {
	}

	public static SlaveServiceAgentManager getInstance() {
		return manager;
	}

	public synchronized SlaveServiceAgent getServiceAgent(int slaveId) {
		SlaveServiceAgent agent = serviceAgentList.get(slaveId);
		if (agent == null) {
			agent = new SlaveServiceAgent(slaveId);
			serviceAgentList.put(slaveId, agent);
		}
		return agent;
	}
}
