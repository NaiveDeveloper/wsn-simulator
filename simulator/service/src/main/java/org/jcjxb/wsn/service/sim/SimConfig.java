package org.jcjxb.wsn.service.sim;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jcjxb.wsn.service.proto.BasicDataType.SensorsOnHost;
import org.jcjxb.wsn.service.proto.SimulatorConfig.HostConfig;
import org.jcjxb.wsn.service.proto.WSNConfig.SimulationConfig;

public class SimConfig {

	protected Map<Integer, Integer> sensorsToSlaveMap = null;

	protected Map<Integer, List<Integer>> slaveToSensorsMap = null;

	protected HostConfig hostConfig = null;

	protected volatile boolean isSimRunning = false;

	protected SimulationConfig simulationConfig = null;

	protected SimConfig() {
	}

	public void initSimulation(SimulationConfig simulationConfig) {
		this.clear();
		this.simulationConfig = simulationConfig;
		sensorsToSlaveMap = new HashMap<Integer, Integer>();
		slaveToSensorsMap = new HashMap<Integer, List<Integer>>();
		for (SensorsOnHost sensorsOnHost : simulationConfig.getPartitionConfig().getSensorsOnHostList().getSensorsOnHostList()) {
			addSenorToMap(sensorsToSlaveMap, sensorsOnHost);
			slaveToSensorsMap.put(sensorsOnHost.getHostIndex(), sensorsOnHost.getSensorIdList());
		}
	}

	protected void addSenorToMap(Map<Integer, Integer> sensorsToSlaveMap, SensorsOnHost sensorsOnHost) {
		for (Integer sensorId : sensorsOnHost.getSensorIdList()) {
			sensorsToSlaveMap.put(sensorId, sensorsOnHost.getHostIndex());
		}
	}

	public void clear() {
		this.sensorsToSlaveMap = null;
		this.isSimRunning = false;
		this.simulationConfig = null;
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
}
