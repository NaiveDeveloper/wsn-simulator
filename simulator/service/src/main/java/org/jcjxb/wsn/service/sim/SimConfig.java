package org.jcjxb.wsn.service.sim;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.jcjxb.wsn.service.proto.BasicDataType.Host;
import org.jcjxb.wsn.service.proto.BasicDataType.SensorsOnHost;
import org.jcjxb.wsn.service.proto.SimulatorConfig.HostConfig;
import org.jcjxb.wsn.service.proto.SlaveService.InitSimCmd;

public class SimConfig {

	protected Map<Integer, Host> sensorsToSlaveMap = null;

	protected Map<Host, List<Integer>> slaveToSensorsMap = null;

	protected HostConfig hostConfig = null;

	protected volatile boolean isSimRunning = false;

	protected Host host = null;

	protected void initSimCmd(InitSimCmd initSimCmd) {
		this.clear();
		sensorsToSlaveMap = new HashMap<Integer, Host>();
		slaveToSensorsMap = new TreeMap<Host, List<Integer>>(new HostComparator());
		for (SensorsOnHost sensorsOnHost : initSimCmd.getSendorsOnHostsList()) {
			addSenorToMap(sensorsToSlaveMap, sensorsOnHost);
			slaveToSensorsMap.put(sensorsOnHost.getHost(), sensorsOnHost.getSensorIdList());
		}
	}

	protected void addSenorToMap(Map<Integer, Host> sensorsToSlaveMap,
			SensorsOnHost sensorsOnHost) {
		for (Integer sensorId : sensorsOnHost.getSensorIdList()) {
			sensorsToSlaveMap.put(sensorId, sensorsOnHost.getHost());
		}
	}

	public void clear() {
		this.sensorsToSlaveMap = null;
		this.isSimRunning = false;
	}

	public HostConfig getHostConfig() {
		return hostConfig;
	}

	public void setHostConfig(HostConfig hostConfig) {
		this.hostConfig = hostConfig;
	}

	public boolean isSimRunning() {
		return isSimRunning;
	}

	public void setSimRunning(boolean isSimRunning) {
		this.isSimRunning = isSimRunning;
	}

	public Host getHost() {
		return host;
	}

	public void setHost(Host host) {
		this.host = host;
	}

	private class HostComparator implements Comparator<Host> {

		@Override
		public int compare(Host left, Host right) {
			int hostCompare = left.getHost().compareTo(right.getHost());
			if (hostCompare < 0) {
				return -1;
			} else if (hostCompare == 0) {
				return left.getPort() - right.getPort();
			} else {
				return 1;
			}
		}
	}
}
