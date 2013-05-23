package org.jcjxb.wsn.web.bean;

import java.util.ArrayList;
import java.util.List;

public class ClusterAnimation extends Animation {

	private List<Cluster> clusters = new ArrayList<Cluster>();

	public ClusterAnimation(long cycle, List<Cluster> clusters) {
		super(cycle);
		this.clusters.addAll(clusters);
		this.name = "Cluster";
	}

	public List<Cluster> getClusters() {
		return clusters;
	}

	public void addClusters(List<Cluster> clusters) {
		this.clusters.addAll(clusters);
	}
}
