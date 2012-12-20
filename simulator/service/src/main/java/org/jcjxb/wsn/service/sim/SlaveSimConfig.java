package org.jcjxb.wsn.service.sim;

import java.util.HashSet;
import java.util.Set;

import org.jcjxb.wsn.service.algorithm.Algorithm;
import org.jcjxb.wsn.service.proto.SlaveService.InitSimCmd;

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

	public void initSimCmd(InitSimCmd initSimCmd) {
		super.initSimCmd(initSimCmd);
		sensorsOnThisSlave = new HashSet<Integer>(slaveToSensorsMap.get(hostIndex));
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
}
