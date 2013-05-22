package org.jcjxb.wsn.web.bean;

import java.util.List;

public class SourceAnimation extends Animation {

	private List<Position> positions;
	
	private double radius;

	public SourceAnimation(long cycle, long eventId, List<Position> positions, double radius) {
		super(cycle, eventId);
		this.positions = positions;
		this.radius = radius;
		this.name = "Source";
	}

	public List<Position> getPositions() {
		return positions;
	}

	public double getRadius() {
		return radius;
	}

	public void addPositions(List<Position> positions) {
		if(this.positions == null) {
			this.positions = positions;
			return;
		}
		this.positions.addAll(positions);
	}	
}
