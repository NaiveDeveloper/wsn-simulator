package org.jcjxb.wsn.service.sim;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;

import org.jcjxb.wsn.service.proto.BasicDataType.Event;
import org.jcjxb.wsn.service.proto.SlaveService.LVTSync;

public class SlaveTimeLine {

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

	public boolean run(long globalVirtualTime, LVTSync.Builder syncBuilder) {
		if (globalVirtualTime == 0L) {
			List<Event> eventList = SlaveSimConfig.getInstance().getAlgorithm().start();
			return processNewEvents(eventList, syncBuilder);
		} else {
			List<Event> allEventList = new ArrayList<Event>();
			while (!eventQueue.isEmpty() && eventQueue.peek().getStartTime() == globalVirtualTime) {
				Event event = eventQueue.poll();
				List<Event> eventList = SlaveSimConfig.getInstance().getAlgorithm().generate(event);
				if (eventList != null) {
					allEventList.addAll(eventList);
				}
			}
			return processNewEvents(allEventList, syncBuilder);
		}
	}

	private boolean processNewEvents(List<Event> eventList, LVTSync.Builder syncBuilder) {
		// Divide events to slaves
		
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
