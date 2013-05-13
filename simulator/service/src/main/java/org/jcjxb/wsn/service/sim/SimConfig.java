package org.jcjxb.wsn.service.sim;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jcjxb.wsn.service.proto.BasicDataType.SensorsOnHost;
import org.jcjxb.wsn.service.proto.SimulatorConfig.HostConfig;
import org.jcjxb.wsn.service.proto.WSNConfig.CommandConfig;
import org.jcjxb.wsn.service.proto.WSNConfig.SimulationConfig;

public class SimConfig {

	protected Map<Integer, Integer> sensorsToSlaveMap = null;

	protected Map<Integer, List<Integer>> slaveToSensorsMap = null;

	protected HostConfig hostConfig = null;

	protected volatile boolean isSimRunning = false;

	protected SimulationConfig simulationConfig = null;
	
	protected List<Integer> sensorIds = null;

	protected SimConfig() {
	}

	public void initSimulation(SimulationConfig simulationConfig) {
		this.clear();
		this.simulationConfig = simulationConfig;
		sensorsToSlaveMap = new HashMap<Integer, Integer>();
		slaveToSensorsMap = new HashMap<Integer, List<Integer>>();
		sensorIds = new ArrayList<Integer>();
		for (SensorsOnHost sensorsOnHost : simulationConfig.getPartitionConfig().getSensorsOnHostList().getSensorsOnHostList()) {
			addSenorToMap(sensorsToSlaveMap, sensorsOnHost);
			slaveToSensorsMap.put(sensorsOnHost.getHostIndex(), sensorsOnHost.getSensorIdList());
			sensorIds.addAll(sensorsOnHost.getSensorIdList());
		}
	}

	protected void addSenorToMap(Map<Integer, Integer> sensorsToSlaveMap, SensorsOnHost sensorsOnHost) {
		for (Integer sensorId : sensorsOnHost.getSensorIdList()) {
			sensorsToSlaveMap.put(sensorId, sensorsOnHost.getHostIndex());
		}
	}

	public void clear() {
		this.sensorsToSlaveMap = null;
		this.slaveToSensorsMap = null;
		this.isSimRunning = false;
		this.simulationConfig = null;
		this.sensorIds = null;
	}

	public int getSlaveCount() {
		return hostConfig.getSlaveHostCount();
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

	public Integer getSlaveIdWithSensorId(Integer sensorId) {
		return sensorsToSlaveMap.get(sensorId);
	}

	public int getSensorCount() {
		return simulationConfig.getDeployConfig().getSensorNodeDeployConfig().getNodeNum();
	}

	public SimulationConfig getSimulationConfig() {
		return simulationConfig;
	}

	public boolean outputDetail() {
		return simulationConfig.getCommandConfig().getOutput() == CommandConfig.Output.DETAIL;
	}
	
	public List<Integer> getAllSensors() {
		return sensorIds;
	}
}
