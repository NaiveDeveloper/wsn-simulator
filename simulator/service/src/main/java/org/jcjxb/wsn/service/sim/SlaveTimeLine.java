package org.jcjxb.wsn.service.sim;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;

import org.jcjxb.wsn.service.proto.BasicDataType.Event;

public class SlaveTimeLine {

	private PriorityBlockingQueue<Event> eventQueue = new PriorityBlockingQueue<Event>(10, new EventComparator());

	public void run(long globalVirtualTime) {
		while (!eventQueue.isEmpty()
				&& eventQueue.peek().getStartTime() == globalVirtualTime) {
			Event event = eventQueue.poll();
			List<Event> eventList = SlaveSimConfig.getInstance().getAlgorithm()
					.generate(event);
		}
	}
	
	private class EventComparator implements Comparator<Event> {

		@Override
		public int compare(Event left, Event right) {
			if(left.getStartTime() < right.getStartTime()) {
				return -1;
			} else if(left.getStartTime() == right.getStartTime()) {
				return left.getPriority().compareTo(right.getPriority());
			} else {
				return 1;
			}
		}
	}
}
