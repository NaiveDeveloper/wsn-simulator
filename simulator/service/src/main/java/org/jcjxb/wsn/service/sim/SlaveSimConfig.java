package org.jcjxb.wsn.service.sim;

import java.util.HashSet;
import java.util.Set;

import org.jcjxb.wsn.service.algorithm.Algorithm;
import org.jcjxb.wsn.service.algorithm.AlgorithmManager;
import org.jcjxb.wsn.service.proto.WSNConfig.SimulationConfig;

public class SlaveSimConfig extends SimConfig {

	private static SlaveSimConfig simConfig = new SlaveSimConfig();

	private Algorithm algorithm = null;

	private Set<Integer> sensorsOnThisSlave = null;

	private int hostIndex = 0;

	private SlaveSimConfig() {
	}

	public static SlaveSimConfig getInstance() {
		return simConfig;
	}

	public void initSimulation(SimulationConfig simulationConfig) {
		super.initSimulation(simulationConfig);
		sensorsOnThisSlave = new HashSet<Integer>(slaveToSensorsMap.get(hostIndex));
		algorithm = AlgorithmManager.getInstance().getAlgorithm(simulationConfig.getAlgorithmConfig().getName());
	}

	public Algorithm getAlgorithm() {
		return algorithm;
	}

	@Override
	public void clear() {
		super.clear();
		this.algorithm = null;
		this.sensorsOnThisSlave = null;
	}

	public boolean isSensorOnSlave(Integer sensorId) {
		return sensorsOnThisSlave.contains(sensorId);
	}

	public int getHostIndex() {
		return hostIndex;
	}

	public void setHostIndex(int hostIndex) {
		this.hostIndex = hostIndex;
	}

	public int getSlaveId() {
		return hostIndex;
	}
}
