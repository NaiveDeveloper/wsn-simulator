package org.jcjxb.wsn.service.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.jcjxb.wsn.common.CommonTool;
import org.jcjxb.wsn.service.energy.EnergyConsume;
import org.jcjxb.wsn.service.energy.EnergyConsumeManager;
import org.jcjxb.wsn.service.node.LEACHSensorNode;
import org.jcjxb.wsn.service.proto.BasicDataType.Event;
import org.jcjxb.wsn.service.proto.WSNConfig.DeployConfig;
import org.jcjxb.wsn.service.proto.WSNConfig.EnergyConsumeConfig;
import org.jcjxb.wsn.service.proto.WSNConfig.LeachConfig;
import org.jcjxb.wsn.service.proto.WSNConfig.SensorNodeDeployConfig;
import org.jcjxb.wsn.service.sim.SlaveSimConfig;

public class LeachAlgorithm extends Algorithm {

	private static int clusterDeclareBit = 100;

	private static int clusterJoinBit = 100;

	private static int clusterSchduleBit = 500;

	private int round = 0;

	private double allRound = 20;

	private Map<Integer, LEACHSensorNode> sensorNodes = new HashMap<Integer, LEACHSensorNode>();

	@Override
	protected void initHandlers() {
		super.initHandlers();
		handlerList.put("CBB", new CBBEventHandler());
		handlerList.put("CD", new CDEventHandler());
		handlerList.put("CJ", new CJEventHandler());
		handlerList.put("CBE", new CBEEventHandler());
		handlerList.put("SPB", new SPBEventHandler());
		handlerList.put("SPE", new SPEEventHandler());
	}

	@Override
	public List<Event> start() {
		super.start();
		round = 1;
		LeachConfig leachConfig = SlaveSimConfig.getInstance().getSimulationConfig().getAlgorithmConfig().getLeachConfig();
		allRound = SlaveSimConfig.getInstance().getSensorCount() / leachConfig.getClusterNum();
		initSensors();
		return generateCBEvent(1L);
	}

	private void initSensors() {
		Set<Integer> sensors = SlaveSimConfig.getInstance().getSensorsOnThisSlave();
		SensorNodeDeployConfig deplyConfig = SlaveSimConfig.getInstance().getSimulationConfig().getDeployConfig()
				.getSensorNodeDeployConfig();
		for (Integer sensorId : sensors) {
			LEACHSensorNode node = new LEACHSensorNode();
			node.setId(sensorId);
			node.setEnergy(deplyConfig.getSensorConfig().getEnergy());
			node.setState(0);
			node.setX(deplyConfig.getPostionList().getPostion(sensorId).getX());
			node.setY(deplyConfig.getPostionList().getPostion(sensorId).getY());
			sensorNodes.put(sensorId, node);
		}
	}

	private List<Event> generateCBEvent(long virtualTime) {
		Event.Builder builder = Event.newBuilder();
		builder.setType("CBB");
		builder.setStartTime(virtualTime);
		builder.setEventId(nextEventTime());
		builder.setSensorEvent(false);
		List<Event> events = new ArrayList<Event>();
		events.add(builder.build());
		return events;
	}

	// CBB means cluster build begin
	private class CBBEventHandler implements EventHandler {

		@Override
		public List<Event> handle(Event event) {
			Event.Builder builder = Event.newBuilder();
			builder.setType("CD");
			builder.setStartTime(event.getStartTime() + toReceiveEventInterval);
			builder.setEventId(nextEventTime());
			builder.addAllSensorId(SlaveSimConfig.getInstance().getAllSensors());
			builder.setFromEventId(event.getEventId());
			builder.setDataSize(clusterDeclareBit);
			Random random = new Random();
			double tn = (double) allRound / (allRound - (round % allRound));
			EnergyConsumeConfig energyConfig = SlaveSimConfig.getInstance().getSimulationConfig().getEnergyConsumeConfig();
			EnergyConsume consume = EnergyConsumeManager.getInstance().getEnergyConsume(energyConfig.getConsumeType());
			DeployConfig deployConfig = SlaveSimConfig.getInstance().getSimulationConfig().getDeployConfig();
			for (Integer nodeId : SlaveSimConfig.getInstance().getSensorsOnThisSlave()) {
				LEACHSensorNode node = sensorNodes.get(nodeId);
				if (!node.isEverHead()) {
					double rp = random.nextDouble();
					if (rp < tn) {
						double energyCost = consume.sense(clusterDeclareBit,
								CommonTool.distance(0, 0, deployConfig.getWidth(), deployConfig.getHeight()), energyConfig);
						if (node.getState() == 0 && node.getEnergy() > energyCost) {
							node.setEnergy(node.getEnergy() - energyCost);
							builder.addSenderNodeId(nodeId);
						} else if (node.getEnergy() <= energyCost) {
							node.setEnergy(0);
							node.setState(2);
						}
					}
				}
			}
			++round;
			if (builder.getSenderNodeIdCount() > 0) {
				List<Event> events = new ArrayList<Event>();
				events.add(builder.build());
				return events;
			} else {
				return null;
			}
		}
	}

	// CJ means cluster declare
	private class CDEventHandler implements EventHandler {

		@Override
		public List<Event> handle(Event event) {
			Set<Integer> chSet = new HashSet<Integer>();
			Map<Integer, Record> chMap = new HashMap<Integer, Record>();

			return null;
		}

		private class Record {

			public int chId;

			public double dis;
		}
	}

	// CJ means cluster join
	private class CJEventHandler implements EventHandler {

		@Override
		public List<Event> handle(Event event) {
			return null;
		}

	}

	// CBE means cluster build end
	private class CBEEventHandler implements EventHandler {

		@Override
		public List<Event> handle(Event event) {
			Random random = new Random();

			return null;
		}
	}

	// SPB means stable period begin
	private class SPBEventHandler implements EventHandler {

		@Override
		public List<Event> handle(Event event) {
			Random random = new Random();

			return null;
		}
	}

	// SPE means stable period end
	private class SPEEventHandler implements EventHandler {

		@Override
		public List<Event> handle(Event event) {
			Random random = new Random();

			return null;
		}
	}
}
