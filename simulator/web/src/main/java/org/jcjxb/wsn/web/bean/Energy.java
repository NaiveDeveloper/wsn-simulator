package org.jcjxb.wsn.web.bean;

public class Energy {

	private int nodeId;
	
	private double eneryLeft;
	
	private boolean die;

	public Energy(int nodeId, double eneryLeft, boolean die) {
		super();
		this.nodeId = nodeId;
		this.eneryLeft = eneryLeft;
		this.die = die;
	}

	public int getNodeId() {
		return nodeId;
	}

	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}

	public double getEneryLeft() {
		return eneryLeft;
	}

	public void setEneryLeft(double eneryLeft) {
		this.eneryLeft = eneryLeft;
	}

	public boolean isDie() {
		return die;
	}

	public void setDie(boolean die) {
		this.die = die;
	}
}
