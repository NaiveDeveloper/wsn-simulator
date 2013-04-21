package org.jcjxb.wsn.service.node;

public class SensorNode extends Node {

	private double x;
	
	private double y;
	
	private double energy;
	
	private int state; // 0 running 1 sleep 2 die

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getEnergy() {
		return energy;
	}

	public void setEnergy(double energy) {
		this.energy = energy;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
}
