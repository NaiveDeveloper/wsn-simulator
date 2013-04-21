package org.jcjxb.wsn.service.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.jcjxb.wsn.service.proto.BasicDataType.Event;
import org.jcjxb.wsn.service.proto.BasicDataType.Position;
import org.jcjxb.wsn.service.proto.BasicDataType.PositionList;
import org.jcjxb.wsn.service.proto.WSNConfig.DeployConfig;
import org.jcjxb.wsn.service.proto.WSNConfig.SensorNodeDeployConfig;
import org.jcjxb.wsn.service.proto.WSNConfig.SourceEventDeployConfig;
import org.jcjxb.wsn.service.sim.SlaveSimConfig;

public abstract class Algorithm {
	
	public static long toReceiveEventInterval = 10L;
	
	public static long eventProcessCycle = 2L;

	protected Map<String, EventHandler> handlerList = new HashMap<String, EventHandler>();

	protected int sourceEventTime = 0;

	protected long eventTime = 0;

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
		Random random = new Random();
		SourceEventDeployConfig sourceConfig = SlaveSimConfig.getInstance().getSimulationConfig().getDeployConfig()
				.getSourceEventDeployConfig();
		DeployConfig deployConfig = SlaveSimConfig.getInstance().getSimulationConfig().getDeployConfig();
		SensorNodeDeployConfig sensorConfig = SlaveSimConfig.getInstance().getSimulationConfig().getDeployConfig()
				.getSensorNodeDeployConfig();
		int soueceNum = 1;
		if (sourceConfig.hasEventNum()) {
			soueceNum = sourceConfig.getEventNum();
		}
		int allSensorNum = SlaveSimConfig.getInstance().getSensorCount();
		int slaveNum = SlaveSimConfig.getInstance().getSlaveCount();
		// Distribute source generation task to all slaves
		for (int i = SlaveSimConfig.getInstance().getHostIndex() % slaveNum; i < soueceNum; i += slaveNum) {
			int x = random.nextInt((int) deployConfig.getWidth());
			int y = random.nextInt((int) deployConfig.getHeight());
			double sourceRadius = sourceConfig.getRadius();
			PositionList positionList = sensorConfig.getPostionList();
			Event.Builder builder = Event.newBuilder();
			builder.setType("ER");
			builder.setStartTime(virtualTime);
			builder.setEventId(nextEventTime());
			builder.setDataSize(sourceConfig.getEventBit());
			// Here a simple way is used to calculate sensors in source event
			// area. A more efficient way should be used if lots of sensors are
			// simulated.
			for (int j = 0; j < positionList.getPostionCount(); ++j) {
				Position pos = positionList.getPostion(j);
				double xDis = x - pos.getX();
				double yDis = y - pos.getY();
				if (xDis * xDis + yDis * yDis < sourceRadius * sourceRadius) {
					builder.addSensorId(j);
				}
			}
			// Put X and Y position data to event data property.
			Position.Builder positionBuilder = Position.newBuilder();
			positionBuilder.setX(x);
			positionBuilder.setY(y);
			builder.setPostion(positionBuilder.build());
			events.add(builder.build());
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

	protected Event generateEGEvent(long startTime) {
		Event.Builder builder = Event.newBuilder();
		builder.setType("EG");
		builder.setStartTime(startTime);
		builder.setSensorEvent(false);
		builder.setEventId(nextEventTime());
		++sourceEventTime;
		return builder.build();
	}

	public interface EventHandler {
		public List<Event> handle(Event event);
	}

	// EG means event generator, which happens on source node
	private class EGEventHandler implements EventHandler {

		@Override
		public List<Event> handle(Event event) {
			long nextVirtualTime = event.getStartTime() + toReceiveEventInterval;
			List<Event> events = generateEREvent(nextVirtualTime);
			// Generate next EG event
			SourceEventDeployConfig sourceConfig = SlaveSimConfig.getInstance().getSimulationConfig().getDeployConfig()
					.getSourceEventDeployConfig();
			if (!sourceConfig.hasTimes() || sourceConfig.getTimes() < 0 || sourceEventTime >= sourceConfig.getTimes()) {
				events.add(generateEGEvent(event.getStartTime() + sourceConfig.getEventInterval()));
			}
			return events;
		}
	}
}
