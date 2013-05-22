package org.jcjxb.wsn.web.bean;

public class Animation {
	
	private long cycle;
	
	private long eventId;
	
	protected String name;

	public Animation(long cycle, long eventId) {
		super();
		this.cycle = cycle;
		this.eventId = eventId;
	}

	public long getCycle() {
		return cycle;
	}

	public long getEventId() {
		return eventId;
	}

	public String getName() {
		return name;
	}
}
