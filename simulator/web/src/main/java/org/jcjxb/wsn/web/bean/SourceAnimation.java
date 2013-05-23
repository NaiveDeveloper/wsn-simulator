package org.jcjxb.wsn.web.bean;

import java.util.ArrayList;
import java.util.List;

public class SourceAnimation extends Animation {

	private List<Position> positions = new ArrayList<Position>();
	
	private double radius;

	public SourceAnimation(long cycle, List<Position> positions, double radius) {
		super(cycle);
		this.positions.addAll(positions);
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
		this.positions.addAll(positions);
	}
}
