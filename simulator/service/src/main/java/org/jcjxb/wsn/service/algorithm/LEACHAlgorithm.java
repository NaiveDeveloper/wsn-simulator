package org.jcjxb.wsn.service.algorithm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jcjxb.wsn.service.bean.LEACHSensorNode;
import org.jcjxb.wsn.service.proto.BasicDataType.Event;
import org.jcjxb.wsn.service.proto.WSNConfig.SensorNodeDeployConfig;
import org.jcjxb.wsn.service.sim.SlaveSimConfig;

public class LEACHAlgorithm extends Algorithm {

	private Map<Integer, LEACHSensorNode> sensorNodes = new HashMap<Integer, LEACHSensorNode>();

	@Override
	public void initHandlers() {
	}

	@Override
	public List<Event> start() {
		sensorNodes.clear();
		init();
		return null;
	}

	@Override
	public void end() {
		sensorNodes.clear();
	}

	private void init() {
		Set<Integer> sensors = SlaveSimConfig.getInstance().getSensorsOnThisSlave();
		SensorNodeDeployConfig deplyConfig = SlaveSimConfig.getInstance().getSimulationConfig().getDeployConfig()
				.getSensorNodeDeployConfig();
		for(Integer sensorId : sensors) {
			LEACHSensorNode node = new LEACHSensorNode();
			node.setEnergy(deplyConfig.getSensorConfig().getEnergy());
			node.setState(0);
			node.setX(deplyConfig.getPostionList().getPostion(sensorId).getX());
			node.setY(deplyConfig.getPostionList().getPostion(sensorId).getY());
			sensorNodes.put(sensorId, node);
		}
	}
}
