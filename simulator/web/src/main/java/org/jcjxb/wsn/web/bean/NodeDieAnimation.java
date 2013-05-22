package org.jcjxb.wsn.web.bean;

import java.util.List;

public class NodeDieAnimation extends Animation {
	
	private List<Integer> nodes;

	public NodeDieAnimation(long cycle, long eventId, List<Integer> nodes) {
		super(cycle, eventId);
		this.nodes = nodes;
		this.name = "NodeDie";
	}

	public List<Integer> getNodes() {
		return nodes;
	}
	
	public void addNodes(List<Integer> nodes) {
		if(this.nodes == null) {
			this.nodes = nodes;
			return;
		}
		this.nodes.addAll(nodes);
	}
}
