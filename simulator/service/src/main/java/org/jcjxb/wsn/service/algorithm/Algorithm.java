package org.jcjxb.wsn.service.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.jcjxb.wsn.service.proto.BasicDataType.Event;
import org.jcjxb.wsn.service.proto.BasicDataType.Position;
import org.jcjxb.wsn.service.proto.BasicDataType.PositionList;
import org.jcjxb.wsn.service.proto.BasicDataType.ProcessJournal;
import org.jcjxb.wsn.service.proto.SlaveService.SimulationResult;
import org.jcjxb.wsn.service.proto.WSNConfig.DeployConfig;
import org.jcjxb.wsn.service.proto.WSNConfig.SensorNodeDeployConfig;
import org.jcjxb.wsn.service.proto.WSNConfig.SourceEventDeployConfig;
import org.jcjxb.wsn.service.sim.SlaveSimConfig;

public abstract class Algorithm {

	public static long toReceiveEventInterval = 2L;

	public static long eventProcessCycle = 1L;

	protected Map<String, EventHandler> handlerList = new HashMap<String, EventHandler>();

	protected int sourceEventTime = 0;

	protected long eventTime = 0;

	protected boolean generateEventAutomatic = true;

	// This is not a good design pattern
	public ProcessJournal.Builder processJournalBuilder;

	public Algorithm() {
		this.initHandlers();
	}

	public List<Event> generate(Event event) {
		EventHandler handler = handlerList.get(event.getType());
		if (handler != null) {
			return handler.handle(event);
		}
		return null;
	}

	protected List<Event> generateEREvent(long virtualTime) {
		List<Event> events = new ArrayList<Event>();
		SourceEventDeployConfig sourceConfig = SlaveSimConfig.getInstance().getSimulationConfig().getDeployConfig()
				.getSourceEventDeployConfig();

		Event.Builder builder = Event.newBuilder();
		builder.setType("ER");
		builder.setStartTime(virtualTime);
		builder.setDataSize(sourceConfig.getEventBit());

		// Distribute source generation task to all slaves
		if (sourceConfig.getDeployType() == SourceEventDeployConfig.DeployType.ALLNODES) {
			builder.setEventId(nextEventTime());
			builder.addAllSensorId(SlaveSimConfig.getInstance().getSensorsOnThisSlave());
			events.add(builder.build());
		} else {
			DeployConfig deployConfig = SlaveSimConfig.getInstance().getSimulationConfig().getDeployConfig();
			SensorNodeDeployConfig sensorConfig = SlaveSimConfig.getInstance().getSimulationConfig().getDeployConfig()
					.getSensorNodeDeployConfig();
			int slaveNum = SlaveSimConfig.getInstance().getSlaveCount();

			PositionList.Builder sourcePositionsBuilder = PositionList.newBuilder();
			int slaveId = SlaveSimConfig.getInstance().getSlaveId();
			if (sourceConfig.getDeployType() == SourceEventDeployConfig.DeployType.STATIC) {
				for (int i = slaveId % slaveNum; i < sourceConfig.getPostionList().getPostionCount(); i += slaveNum) {
					sourcePositionsBuilder.addPostion(sourceConfig.getPostionList().getPostion(i));
				}
			} else if (sourceConfig.getDeployType() == SourceEventDeployConfig.DeployType.RANDOM) {
				// If sourceNum not set, default is 1
				int soueceNum = 1;
				if (sourceConfig.hasEventNum()) {
					soueceNum = sourceConfig.getEventNum();
				}
				Random random = new Random();
				for (int i = slaveId % slaveNum; i < soueceNum; i += slaveNum) {
					int x = random.nextInt((int) deployConfig.getWidth());
					int y = random.nextInt((int) deployConfig.getHeight());
					sourcePositionsBuilder.addPostion(Position.newBuilder().setX(x).setY(y));
				}
			}

			double sourceRadius = sourceConfig.getRadius();
			PositionList sensorPositionList = sensorConfig.getPostionList();
			// Generate events according to source events position list
			// TODO Loop is not an elegant way, to improve it
			for (Position sourcePos : sourcePositionsBuilder.getPostionList()) {
				for (int j = 0; j < sensorPositionList.getPostionCount(); ++j) {
					Position pos = sensorPositionList.getPostion(j);
					double xDis = sourcePos.getX() - pos.getX();
					double yDis = sourcePos.getY() - pos.getY();
					if (xDis * xDis + yDis * yDis < sourceRadius * sourceRadius) {
						builder.addSensorId(j);
					}
				}
				if (builder.getSensorIdCount() <= 0) {
					continue;
				}
				builder.setEventId(nextEventTime());
				// Put X and Y position data to event data property.
				Position.Builder positionBuilder = Position.newBuilder();
				positionBuilder.setX(sourcePos.getX());
				positionBuilder.setY(sourcePos.getY());
				builder.setPostion(positionBuilder.build());
				events.add(builder.build());
			}
		}
		return events;
	}

	protected long nextEventTime() {
		long oldEventTime = eventTime;
		++eventTime;
		int slaveNum = SlaveSimConfig.getInstance().getSlaveCount();
		int slaveId = SlaveSimConfig.getInstance().getHostIndex();
		return oldEventTime * slaveNum + slaveId;
	}

	protected void initHandlers() {
		handlerList.put("EG", new EGEventHandler());
	}

	public List<Event> start() {
		sourceEventTime = 0;
		eventTime = 0L;
		return null;
	}

	public void end() {
		sourceEventTime = 0;
		eventTime = 0L;
	}

	public void collectSimResult(SimulationResult.Builder builder) {
	}

	protected Event generateEGEvent(long startTime) {
		Event.Builder builder = Event.newBuilder();
		builder.setType("EG");
		builder.setStartTime(startTime);
		builder.setSensorEvent(false);
		builder.setEventId(nextEventTime());
		++sourceEventTime;
		return builder.build();
	}

	protected boolean hasNextEGEvent() {
		SourceEventDeployConfig sourceConfig = SlaveSimConfig.getInstance().getSimulationConfig().getDeployConfig()
				.getSourceEventDeployConfig();
		if (!sourceConfig.hasTimes() || sourceConfig.getTimes() < 0 || sourceEventTime <= sourceConfig.getTimes()) {
			return true;
		}
		return false;
	}

	public interface EventHandler {
		public List<Event> handle(Event event);
	}

	// EG means event generator
	private class EGEventHandler implements EventHandler {

		@Override
		public List<Event> handle(Event event) {
			long nextVirtualTime = event.getStartTime() + toReceiveEventInterval;
			List<Event> events = generateEREvent(nextVirtualTime);

			if (generateEventAutomatic) {
				if (hasNextEGEvent()) {
					events.add(generateEGEvent(event.getStartTime()
							+ SlaveSimConfig.getInstance().getSimulationConfig().getDeployConfig().getSourceEventDeployConfig()
									.getEventInterval()));
				}
			}
			return events;
		}
	}
}
