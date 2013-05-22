package org.jcjxb.wsn.web.bean;

import java.util.List;

public class ClusterAnimation extends Animation {
	
	private int ch;
	
	private List<Integer> members;

	public ClusterAnimation(long cycle, long eventId, int ch, List<Integer> members) {
		super(cycle, eventId);
		this.ch = ch;
		this.members = members;
		this.name = "Cluster";
	}

	public int getCh() {
		return ch;
	}

	public List<Integer> getMembers() {
		return members;
	}
	
	public void addMembers(List<Integer> members) {
		if(this.members == null) {
			this.members = members;
			return;
		}
		this.members.addAll(members);
	}
}
