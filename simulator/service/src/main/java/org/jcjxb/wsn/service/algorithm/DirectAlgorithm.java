package org.jcjxb.wsn.service.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jcjxb.wsn.common.CommonTool;
import org.jcjxb.wsn.service.energy.EnergyConsume;
import org.jcjxb.wsn.service.energy.EnergyConsumeManager;
import org.jcjxb.wsn.service.node.SensorNode;
import org.jcjxb.wsn.service.proto.BasicDataType.Event;
import org.jcjxb.wsn.service.proto.BasicDataType.Position;
import org.jcjxb.wsn.service.proto.WSNConfig.EnergyConsumeConfig;
import org.jcjxb.wsn.service.proto.WSNConfig.SensorNodeDeployConfig;
import org.jcjxb.wsn.service.proto.WSNConfig.SinkNodeDeployConfig;
import org.jcjxb.wsn.service.sim.SlaveSimConfig;

public class DirectAlgorithm extends Algorithm {

	protected Map<Integer, SensorNode> sensorNodes = new HashMap<Integer, SensorNode>();

	@Override
	protected void initHandlers() {
		super.initHandlers();
		handlerList.put("ER", new EREventHandler());
		handlerList.put("EF", new EFEventHandler());
	}

	@Override
	public List<Event> start() {
		super.start();
		initSensors();
		List<Event> events = new ArrayList<Event>(1);
		events.add(generateEGEvent(1L));
		return events;
	}

	@Override
	public void end() {
		super.end();
	}

	private void initSensors() {
		Set<Integer> sensors = SlaveSimConfig.getInstance().getSensorsOnThisSlave();
		SensorNodeDeployConfig deplyConfig = SlaveSimConfig.getInstance().getSimulationConfig().getDeployConfig()
				.getSensorNodeDeployConfig();
		for (Integer sensorId : sensors) {
			SensorNode node = new SensorNode();
			node.setId(sensorId);
			node.setEnergy(deplyConfig.getSensorConfig().getEnergy());
			node.setState(0);
			node.setX(deplyConfig.getPostionList().getPostion(sensorId).getX());
			node.setY(deplyConfig.getPostionList().getPostion(sensorId).getY());
			sensorNodes.put(sensorId, node);
		}
	}

	// ER means event receive, which happens on sensor node
	private class EREventHandler implements EventHandler {

		@Override
		public List<Event> handle(Event event) {
			Event.Builder builder = Event.newBuilder();
			EnergyConsumeConfig energyConfig = SlaveSimConfig.getInstance().getSimulationConfig().getEnergyConsumeConfig();
			EnergyConsume consume = EnergyConsumeManager.getInstance().getEnergyConsume(energyConfig.getConsumeType());
			Position eventPosition = event.getPostion();
			for (int sensorId : event.getSensorIdList()) {
				SensorNode node = sensorNodes.get(sensorId);
				double energyCost = consume.sense(event.getDataSize(),
						CommonTool.distance(eventPosition.getX(), eventPosition.getY(), node.getX(), node.getY()), energyConfig);
				if (node.getState() == 0 && node.getEnergy() > energyCost) {
					node.setEnergy(node.getEnergy() - energyCost);
					builder.addSensorId(sensorId);
				} else if (node.getEnergy() <= energyCost) {
					node.setEnergy(0);
					node.setState(2);
				}
			}
			if (!builder.hasSenderNodeId()) {
				return null;
			}
			builder.setDataSize(event.getDataSize());
			builder.setEventId(nextEventTime());
			builder.setFromEventId(event.getEventId());
			builder.setStartTime(event.getStartTime() + eventProcessCycle);
			builder.setType("EF");
			List<Event> events = new ArrayList<Event>();
			events.add(builder.build());
			return events;
		}
	}

	// EF means event forward, which happens on sensor node
	private class EFEventHandler implements EventHandler {

		@Override
		public List<Event> handle(Event event) {
			EnergyConsumeConfig energyConfig = SlaveSimConfig.getInstance().getSimulationConfig().getEnergyConsumeConfig();
			EnergyConsume consume = EnergyConsumeManager.getInstance().getEnergyConsume(energyConfig.getConsumeType());
			SinkNodeDeployConfig sinkConfig = SlaveSimConfig.getInstance().getSimulationConfig().getDeployConfig()
					.getSinkNodeDeployConfig();
			Position sinkPos = sinkConfig.getPostionList().getPostion(0);
			for (int sensorId : event.getSensorIdList()) {
				SensorNode node = sensorNodes.get(sensorId);
				double energyCost = consume.send(event.getDataSize(),
						CommonTool.distance(sinkPos.getX(), sinkPos.getY(), node.getX(), node.getY()), energyConfig);
				if (node.getEnergy() <= energyCost) {
					node.setEnergy(0);
					node.setState(2);
				} else {
					node.setEnergy(node.getEnergy() - energyCost);
				}
			}
			return null;
		}
	}
}
