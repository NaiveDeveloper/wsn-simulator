package org.jcjxb.wsn.service.node;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LEACHSensorNode extends SensorNode {

	private boolean head = false;
	
	private Set<Integer> members = new HashSet<Integer>();
	
	private int headId = -1;
	
	private double distanceToHead = Double.MAX_VALUE;
	
	private boolean everHead = false;
	
	private int receiveDataSize = 0;

	public boolean isHead() {
		return head;
	}

	public void setHead(boolean head) {
		this.head = head;
	}

	public int getHeadId() {
		return headId;
	}

	public void setHeadId(int headId) {
		this.headId = headId;
	}
	
	public Set<Integer> getMembers() {
		return members;
	}

	public void setMembers(Set<Integer> members) {
		this.members = members;
	}

	public void addMember(int nodeId) {
		members.add(nodeId);
	}
	
	public void addAllMember(List<Integer> nodeList) {
		members.addAll(nodeList);
	}
	
	public void clear() {
		this.head = false;
		this.members.clear();
		this.headId = -1;
		this.everHead = false;
		this.distanceToHead = Double.MAX_VALUE;
		this.receiveDataSize = 0;
	}

	public double getDistanceToHead() {
		return distanceToHead;
	}

	public void setDistanceToHead(double distanceToHead) {
		this.distanceToHead = distanceToHead;
	}

	public boolean isEverHead() {
		return everHead;
	}

	public void setEverHead(boolean everHead) {
		this.everHead = everHead;
	}

	public int getReceiveDataSize() {
		return receiveDataSize;
	}

	public void setReceiveDataSize(int receiveDataSize) {
		this.receiveDataSize = receiveDataSize;
	}
}
