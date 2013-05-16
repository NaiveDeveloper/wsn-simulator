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
import org.jcjxb.wsn.service.proto.BasicDataType.Position;
import org.jcjxb.wsn.service.proto.BasicDataType.PositionList;
import org.jcjxb.wsn.service.proto.BasicDataType.ProcessJournal.DeadJournal;
import org.jcjxb.wsn.service.proto.WSNConfig.DeployConfig;
import org.jcjxb.wsn.service.proto.WSNConfig.EnergyConsumeConfig;
import org.jcjxb.wsn.service.proto.WSNConfig.LeachConfig;
import org.jcjxb.wsn.service.proto.WSNConfig.SensorNodeDeployConfig;
import org.jcjxb.wsn.service.sim.SlaveSimConfig;

public class LeachAlgorithm extends Algorithm {

	private static int clusterDeclareBit = 100;

	private static int clusterJoinBit = 100;

	private static int clusterSchduleBit = 500;

	private static int clusterBuiltCycle = 100; // Make sure CD finish before
												// CBE

	private int round = 0;

	private double allRound = 20;

	private int dataTimeEver = 0;

	private Map<Integer, LEACHSensorNode> sensorNodes = new HashMap<Integer, LEACHSensorNode>();

	@Override
	protected void initHandlers() {
		super.initHandlers();
		handlerList.put("CBB", new CBBEventHandler());
		handlerList.put("CDR", new CDREventHandler());
		handlerList.put("CJS", new CJSEventHandler());
		handlerList.put("CJR", new CJREventHandler());
		handlerList.put("CSS", new CSSEventHandler());
		handlerList.put("CSR", new CSREventHandler());
		handlerList.put("CBE", new CBEEventHandler());
		handlerList.put("SPB", new SPBEventHandler());
		handlerList.put("SPE", new SPEEventHandler());
	}

	@Override
	public List<Event> start() {
		super.start();
		round = 0;
		LeachConfig leachConfig = SlaveSimConfig.getInstance().getSimulationConfig().getAlgorithmConfig().getLeachConfig();
		allRound = SlaveSimConfig.getInstance().getSensorCount() / leachConfig.getClusterNum();
		dataTimeEver = 0;
		initSensors();
		return generateCBBEvent(1L);
	}

	@Override
	public void end() {
		super.end();
		sensorNodes.clear();
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

	private List<Event> generateCBBEvent(long virtualTime) {
		Event.Builder builder = Event.newBuilder();
		builder.setType("CBB");
		builder.setStartTime(virtualTime);
		builder.setEventId(nextEventTime());
		builder.setSensorEvent(false);
		List<Event> events = new ArrayList<Event>();
		events.add(builder.build());
		return events;
	}

	private Event generateCSSEvent(long virtualTime, long fromEventId) {
		Event.Builder builder = Event.newBuilder();
		builder.setType("CSS");
		builder.setStartTime(virtualTime);
		builder.setEventId(nextEventTime());
		builder.setSensorEvent(false);
		builder.setFromEventId(fromEventId);
		return builder.build();
	}

	private Event generateCJSEvent(long virtualTime, long fromEventId) {
		Event.Builder builder = Event.newBuilder();
		builder.setType("CJS");
		builder.setStartTime(virtualTime);
		builder.setEventId(nextEventTime());
		builder.setSensorEvent(false);
		builder.setFromEventId(fromEventId);
		return builder.build();
	}

	private Event generateCBEEvent(long virtualTime, long fromEventId) {
		Event.Builder builder = Event.newBuilder();
		builder.setType("CBE");
		builder.setStartTime(virtualTime);
		builder.setEventId(nextEventTime());
		builder.setSensorEvent(false);
		builder.setFromEventId(fromEventId);
		return builder.build();
	}

	// CBB means cluster build begin
	private class CBBEventHandler implements EventHandler {

		@Override
		public List<Event> handle(Event event) {
			Event.Builder builder = Event.newBuilder();
			Random random = new Random();
			double tn = (double) allRound / (allRound - (round % allRound));
			EnergyConsumeConfig energyConfig = SlaveSimConfig.getInstance().getSimulationConfig().getEnergyConsumeConfig();
			EnergyConsume consume = EnergyConsumeManager.getInstance().getEnergyConsume(energyConfig.getConsumeType());
			DeployConfig deployConfig = SlaveSimConfig.getInstance().getSimulationConfig().getDeployConfig();
			for (Integer nodeId : SlaveSimConfig.getInstance().getSensorsOnThisSlave()) {
				LEACHSensorNode node = sensorNodes.get(nodeId);
				if (node.getState() == 0 && !node.isEverHead()) {
					double rp = random.nextDouble();
					if (rp < tn) {
						double energyCost = consume.send(clusterDeclareBit,
								CommonTool.distance(0, 0, deployConfig.getWidth(), deployConfig.getHeight()), energyConfig);
						if (node.getEnergy() > energyCost) {
							node.setEnergy(node.getEnergy() - energyCost);
							node.setEverHead(true);
							node.setHead(true);
							builder.addSenderNodeId(nodeId);
						} else if (node.getEnergy() <= energyCost) {
							node.setEnergy(0);
							node.setState(2);
							processJournalBuilder.addDeadJournal(DeadJournal.newBuilder().setEventId(event.getEventId())
									.addSensorId(node.getId()));
						}
					}
				}
			}
			++round;
			List<Event> events = new ArrayList<Event>();
			if (builder.getSenderNodeIdCount() > 0) {
				builder.setType("CDR");
				builder.setStartTime(event.getStartTime() + toReceiveEventInterval + eventProcessCycle);
				builder.setEventId(nextEventTime());
				builder.addAllSensorId(SlaveSimConfig.getInstance().getAllSensors());
				builder.setFromEventId(event.getEventId());
				builder.setDataSize(clusterDeclareBit);
				events.add(builder.build());
			}
			events.add(generateCJSEvent(event.getStartTime() + toReceiveEventInterval + eventProcessCycle * 2, event.getEventId()));
			events.add(generateCSSEvent(event.getStartTime() + toReceiveEventInterval * 2 + eventProcessCycle * 3, event.getEventId()));
			events.add(generateCBEEvent(event.getStartTime() + clusterBuiltCycle, event.getEventId()));
			return events;
		}
	}

	// CDR means cluster declare receive
	private class CDREventHandler implements EventHandler {

		@Override
		public List<Event> handle(Event event) {
			EnergyConsumeConfig energyConfig = SlaveSimConfig.getInstance().getSimulationConfig().getEnergyConsumeConfig();
			EnergyConsume consume = EnergyConsumeManager.getInstance().getEnergyConsume(energyConfig.getConsumeType());
			PositionList allNodesPosList = SlaveSimConfig.getInstance().getSimulationConfig().getDeployConfig().getSensorNodeDeployConfig()
					.getPostionList();

			for (Integer sensorId : event.getSensorIdList()) {
				LEACHSensorNode node = sensorNodes.get(sensorId);
				if (node.isHead() || node.getState() == 2) {
					continue;
				}

				int resultCHId = -1;
				double dis = Double.MAX_VALUE;
				for (Integer chId : event.getSenderNodeIdList()) {
					Position chPos = allNodesPosList.getPostion(chId);
					double tempDis = CommonTool.distance(chPos.getX(), chPos.getY(), node.getX(), node.getY());
					if (tempDis < dis) {
						resultCHId = chId;
						dis = tempDis;
					}
				}

				// Compute receive energy cost
				// Should multiply CH count
				double energyCost = consume.receive(clusterDeclareBit, dis, energyConfig) * event.getSenderNodeIdCount();
				if (node.getEnergy() > energyCost) {
					node.setEnergy(node.getEnergy() - energyCost);
				} else {
					node.setEnergy(0);
					node.setState(2);
					processJournalBuilder.addDeadJournal(DeadJournal.newBuilder().setEventId(event.getEventId()).addSensorId(node.getId()));
					continue;
				}

				node.setHeadId(resultCHId);
				node.setDistanceToHead(dis);
			}
			return null;
		}
	}

	// CJS means cluster join send
	private class CJSEventHandler implements EventHandler {

		@Override
		public List<Event> handle(Event event) {
			Map<Integer, List<Integer>> chMap = new HashMap<Integer, List<Integer>>();

			EnergyConsumeConfig energyConfig = SlaveSimConfig.getInstance().getSimulationConfig().getEnergyConsumeConfig();
			EnergyConsume consume = EnergyConsumeManager.getInstance().getEnergyConsume(energyConfig.getConsumeType());
			for (LEACHSensorNode node : sensorNodes.values()) {
				if (node.getState() == 2 || node.getHeadId() == -1 || node.isHead()) {
					continue;
				}
				// Compute receive energy cost
				double energyCost = consume.send(clusterJoinBit, node.getDistanceToHead(), energyConfig);
				if (node.getEnergy() > energyCost) {
					node.setEnergy(node.getEnergy() - energyCost);
				} else {
					node.setEnergy(0);
					node.setState(2);
					processJournalBuilder.addDeadJournal(DeadJournal.newBuilder().setEventId(event.getEventId()).addSensorId(node.getId()));
					continue;
				}
				int chId = node.getHeadId();
				List<Integer> nodeList = chMap.get(chId);
				if (nodeList == null) {
					nodeList = new ArrayList<Integer>();
					chMap.put(chId, nodeList);
				}
				nodeList.add(node.getId());
			}

			List<Event> events = new ArrayList<Event>();
			// Build CJ events from map
			for (Map.Entry<Integer, List<Integer>> entry : chMap.entrySet()) {
				Event.Builder builder = Event.newBuilder();
				builder.setEventId(nextEventTime());
				builder.addSensorId(entry.getKey());
				builder.addAllSenderNodeId(entry.getValue());
				builder.setType("CJR");
				builder.setStartTime(event.getStartTime() + toReceiveEventInterval);
				builder.setFromEventId(event.getEventId());
				events.add(builder.build());
			}
			return events;
		}

	}

	// CJR means cluster join receive
	private class CJREventHandler implements EventHandler {

		@Override
		public List<Event> handle(Event event) {
			if (event.getSensorIdCount() == 0) {
				return null;
			}
			LEACHSensorNode node = sensorNodes.get(event.getSensorId(0));
			if (node.getState() == 2 || !node.isHead()) {
				return null;
			}
			EnergyConsumeConfig energyConfig = SlaveSimConfig.getInstance().getSimulationConfig().getEnergyConsumeConfig();
			EnergyConsume consume = EnergyConsumeManager.getInstance().getEnergyConsume(energyConfig.getConsumeType());
			PositionList allNodesPosList = SlaveSimConfig.getInstance().getSimulationConfig().getDeployConfig().getSensorNodeDeployConfig()
					.getPostionList();
			// Compute receive energy cost
			double energyCost = 0.0;
			for (Integer nodeId : event.getSenderNodeIdList()) {
				Position nodePos = allNodesPosList.getPostion(nodeId);
				double tempDis = CommonTool.distance(nodePos.getX(), nodePos.getY(), node.getX(), node.getY());
				energyCost += consume.receive(clusterJoinBit, tempDis, energyConfig);
			}
			if (node.getEnergy() > energyCost) {
				node.setEnergy(node.getEnergy() - energyCost);
				node.addAllMember(event.getSenderNodeIdList());
			} else {
				node.setEnergy(0);
				node.setState(2);
				processJournalBuilder.addDeadJournal(DeadJournal.newBuilder().setEventId(event.getEventId()).addSensorId(node.getId()));
				return null;
			}
			return null;
		}
	}

	// CSS means cluster join send
	private class CSSEventHandler implements EventHandler {

		@Override
		public List<Event> handle(Event event) {

			return null;
		}

	}

	// CSR means cluster join receive
	private class CSREventHandler implements EventHandler {

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
