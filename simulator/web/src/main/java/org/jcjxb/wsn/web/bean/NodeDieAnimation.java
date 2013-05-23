package org.jcjxb.wsn.web.bean;

import java.util.ArrayList;
import java.util.List;

public class NodeDieAnimation extends Animation {
	
	private List<Integer> nodes = new ArrayList<Integer>();

	public NodeDieAnimation(long cycle, List<Integer> nodes) {
		super(cycle);
		this.nodes.addAll(nodes);
		this.name = "NodeDie";
	}

	public List<Integer> getNodes() {
		return nodes;
	}
	
	public void addNodes(List<Integer> nodes) {
		this.nodes.addAll(nodes);
	}
}
