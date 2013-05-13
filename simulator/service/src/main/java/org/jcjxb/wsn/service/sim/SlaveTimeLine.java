package org.jcjxb.wsn.service.sim;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.PriorityBlockingQueue;

import org.apache.log4j.Logger;
import org.jcjxb.wsn.common.Status;
import org.jcjxb.wsn.rpc.LionRpcController;
import org.jcjxb.wsn.service.agent.SlaveServiceAgentManager;
import org.jcjxb.wsn.service.proto.BasicDataType.Empty;
import org.jcjxb.wsn.service.proto.BasicDataType.Event;
import org.jcjxb.wsn.service.proto.SlaveService.EventsRequest;
import org.jcjxb.wsn.service.proto.SlaveService.LVTSync;
import org.jcjxb.wsn.service.proto.SlaveService.LVTSync.Update;

import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcController;

public class SlaveTimeLine {

	private static Logger logger = Logger.getLogger(SlaveTimeLine.class);

	private static SlaveTimeLine timeline = new SlaveTimeLine();

	private PriorityBlockingQueue<Event> eventQueue = null;

	private SlaveTimeLine() {
	}

	public static SlaveTimeLine getInstance() {
		return timeline;
	}

	public void init() {
		eventQueue = new PriorityBlockingQueue<Event>(10, new EventComparator());
	}

	public void clear() {
		eventQueue = null;
	}

	public void addEvents(List<Event> events) {
		eventQueue.addAll(events);
	}

	public boolean run(long globalVirtualTime, LVTSync.Builder syncBuilder) {
		logger.info(String.format("Event num in queue is %d", eventQueue.size()));
		if (globalVirtualTime == 0L) {
			List<Event> eventList = SlaveSimConfig.getInstance().getAlgorithm().start();
			logger.info("Global virtual time is 0L, call algorithm start method, return events num "
					+ (eventList == null ? 0 : eventList.size()));
			return processNewEvents(eventList, syncBuilder);
		} else {
			List<Event> allEventList = new ArrayList<Event>();
			int processEventNum = 0;
			while (!eventQueue.isEmpty() && eventQueue.peek().getStartTime() == globalVirtualTime) {
				Event event = eventQueue.poll();
				List<Event> eventList = SlaveSimConfig.getInstance().getAlgorithm().generate(event);
				if (eventList != null) {
					allEventList.addAll(eventList);
				}
				// Add processed event to LVTSync
				if(SlaveSimConfig.getInstance().outputDetail()) {
					syncBuilder.addProcessedEvent(event);
				}
				++processEventNum;
			}
			logger.info(String.format("Processed num in cycle %d is %d", globalVirtualTime, processEventNum));
			return processNewEvents(allEventList, syncBuilder);
		}
	}

	private boolean processNewEvents(List<Event> eventList, LVTSync.Builder syncBuilder) {
		// Divide events to slaves
		Map<Integer, Long> timeSync = new HashMap<Integer, Long>();
		Map<Integer, List<Event>> eventsForSlaves = new HashMap<Integer, List<Event>>();

		// Divide one event to slaves
		if (eventList != null) {
			for (Event event : eventList) {
				Map<Integer, Event.Builder> eventForSlaves = dispatchEvent(event);
				for (Entry<Integer, Event.Builder> entry : eventForSlaves.entrySet()) {
					Integer slaveId = entry.getKey();
					Event.Builder eventBuilder = entry.getValue();
					Long virtualTime = timeSync.get(slaveId);
					if (virtualTime == null || timeSync.get(slaveId) < eventBuilder.getStartTime()) {
						timeSync.put(slaveId, eventBuilder.getStartTime());
					}
					List<Event> events = eventsForSlaves.get(slaveId);
					if (events == null) {
						List<Event> tempList = new ArrayList<Event>();
						tempList.add(eventBuilder.build());
						eventsForSlaves.put(slaveId, tempList);
					} else {
						events.add(eventBuilder.build());
					}
				}
			}
		}

		Integer ownSlaveId = SlaveSimConfig.getInstance().getSlaveId();

		// Put events to local queue or remote queue
		timeSync.remove(ownSlaveId);
		List<Event> eventsForLocal = eventsForSlaves.remove(ownSlaveId);
		if (eventsForLocal != null) {
			eventQueue.addAll(eventsForLocal);
		}

		// Send events to remote queue
		if (!sendEvents(eventsForSlaves)) {
			return false;
		}

		// Build LVTSync
		syncBuilder.setSlaveId(ownSlaveId);
		Event event = eventQueue.peek();
		if (event == null) {
			syncBuilder.setLocalTime(Long.MAX_VALUE);
		} else {
			syncBuilder.setLocalTime(event.getStartTime());
		}
		for (Entry<Integer, Long> sync : timeSync.entrySet()) {
			Update.Builder builder = Update.newBuilder();
			builder.setSlaveId(sync.getKey());
			builder.setLocalTime(sync.getValue());
			syncBuilder.addUpdate(builder.build());
		}
		return true;
	}

	private Map<Integer, Event.Builder> dispatchEvent(Event event) {
		Map<Integer, Event.Builder> eventForSlaves = new HashMap<Integer, Event.Builder>();
		if (!event.getSensorEvent()) {
			Event.Builder builder = Event.newBuilder(event);
			eventForSlaves.put(SlaveSimConfig.getInstance().getSlaveId(), builder);
			return eventForSlaves;
		}
		List<Integer> sensors = event.getSensorIdList();
		for (Integer sensorId : sensors) {
			Integer slaveId = SlaveSimConfig.getInstance().getSlaveIdWithSensorId(sensorId);
			Event.Builder builder = eventForSlaves.get(slaveId);
			if (builder == null) {
				builder = Event.newBuilder(event);
				builder.clearSensorId();
				builder.addSensorId(sensorId);
				eventForSlaves.put(slaveId, builder);
			} else {
				builder.addSensorId(sensorId);
			}
		}
		return eventForSlaves;
	}

	private boolean sendEvents(Map<Integer, List<Event>> eventsForSlaves) {
		if (eventsForSlaves == null || eventsForSlaves.size() == 0) {
			return true;
		}
		final CountDownLatch latch = new CountDownLatch(eventsForSlaves.size());
		final Status status = new Status(true);
		for (Entry<Integer, List<Event>> entry : eventsForSlaves.entrySet()) {
			final RpcController localController = new LionRpcController();
			final int salveId = entry.getKey();
			final EventsRequest.Builder builder = EventsRequest.newBuilder();
			builder.addAllEvent(entry.getValue());
			SlaveServiceAgentManager.getInstance().getServiceAgent(salveId, false)
					.sendEvents(builder.build(), localController, new RpcCallback<Empty>() {
						@Override
						public void run(Empty parameter) {
							if (localController.failed()) {
								logger.error(String.format("Send events to slave [%d] failed, error message [%s]", salveId,
										localController.errorText()));
								status.setFlag(false);
							}
							latch.countDown();
						}
					});
		}
		// Wait all slaves to finish
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
			status.setFlag(false);
		}
		if (!status.isFlag()) {
			return false;
		}
		return true;
	}

	private class EventComparator implements Comparator<Event> {

		@Override
		public int compare(Event left, Event right) {
			if (left.getStartTime() < right.getStartTime()) {
				return -1;
			} else if (left.getStartTime() == right.getStartTime()) {
				return left.getPriority().compareTo(right.getPriority());
			} else {
				return 1;
			}
		}
	}
}
