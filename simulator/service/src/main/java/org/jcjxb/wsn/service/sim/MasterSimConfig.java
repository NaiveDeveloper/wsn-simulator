package org.jcjxb.wsn.service.sim;

import java.util.HashMap;
import java.util.Map;

public class MasterSimConfig extends SimConfig {

	private static MasterSimConfig simConfig = new MasterSimConfig();

	private Map<Integer, Boolean> slaveReadyStatus = new HashMap<Integer, Boolean>();

	private MasterSimConfig() {
	}

	public static MasterSimConfig getInstance() {
		return simConfig;
	}

	public synchronized void slaveReady(Integer hostIndex) {
		slaveReadyStatus.put(hostIndex, true);
	}

	public synchronized boolean isAllSlaveReady() {
		return slaveReadyStatus.size() == hostConfig.getSlaveHostCount();
	}
}
