package org.jcjxb.wsn.web.bean;

public class Animation {
	
	private long cycle;
	
	protected String name;

	public Animation(long cycle) {
		super();
		this.cycle = cycle;
	}

	public long getCycle() {
		return cycle;
	}

	public String getName() {
		return name;
	}
}
