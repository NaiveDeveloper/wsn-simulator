package org.jcjxb.wsn.service.sim;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.jcjxb.wsn.service.algorithm.Algorithm;
import org.jcjxb.wsn.service.proto.BasicDataType.Host;
import org.jcjxb.wsn.service.proto.BasicDataType.SensorsOnHost;
import org.jcjxb.wsn.service.proto.SlaveService.InitSimCmd;

public class SimConfig {
	
	private Algorithm algorithm = null;
	
	private static SimConfig simConfig = null;
	
	private Set<Integer> sensorsOnThisSlave = null;
	
	private Map<Integer, Host> sensorsToSlaveMap = null;
	
	private SimConfig() {
	}
	
	public synchronized static SimConfig sharedSimConfig() {
		if(simConfig == null) {
			simConfig = new SimConfig();
		}
		return simConfig;
	}
	
	private SimConfig init(InitSimCmd initSimCmd) {
		this.clear();
		sensorsOnThisSlave = new HashSet<Integer>(initSimCmd.getSendorsOnThisHost().getSensorIdList());
		sensorsToSlaveMap = new HashMap<Integer, Host>();
		addSenorToMap(sensorsToSlaveMap, initSimCmd.getSendorsOnThisHost());
		for(SensorsOnHost sensorsOnHost : initSimCmd.getSendorsOnLeftHostsList()) {
			addSenorToMap(sensorsToSlaveMap, sensorsOnHost);
		}
		return this;
	}
	
	private void addSenorToMap(Map<Integer, Host> sensorsToSlaveMap, SensorsOnHost sensorsOnHost) {
		for(Integer sensorId: sensorsOnHost.getSensorIdList()) {
			sensorsToSlaveMap.put(sensorId, sensorsOnHost.getHost());
		}
	}

	public void clear() {
		this.algorithm = null;
		this.sensorsOnThisSlave = null;
		this.sensorsToSlaveMap = null;
	}
	
	public boolean isSensorInThisSlave(Integer sensorId) {
		return sensorsOnThisSlave.contains(sensorId);
	}
	
	public Algorithm getAlgorithm() {
		return algorithm;
	}
}
