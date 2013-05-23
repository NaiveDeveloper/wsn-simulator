package org.jcjxb.wsn.web.bean;

import java.util.ArrayList;
import java.util.List;

public class Cluster {

	private int ch;

	private List<Integer> members = new ArrayList<Integer>();

	public Cluster(int ch, List<Integer> members) {
		super();
		this.ch = ch;
		this.members.addAll(members);
	}

	public int getCh() {
		return ch;
	}

	public void setCh(int ch) {
		this.ch = ch;
	}

	public List<Integer> getMembers() {
		return members;
	}

	public void setMembers(List<Integer> members) {
		this.members = members;
	}
	
	public void addMembers(List<Integer> members) {
		this.members.addAll(members);
	}

	@Override
	public boolean equals(Object obj) {
		if (super.equals(obj)) {
			return true;
		}
		if (obj instanceof Cluster) {
			Cluster cluster = (Cluster) obj;
			return this.ch == cluster.ch;
		}
		return false;
	}
}
