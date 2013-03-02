package org.jcjxb.wsn.service.sim;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class MasterTimeLine {

	private static MasterTimeLine timeline = new MasterTimeLine();

	private long globalVirtualTime = 0L;

	private Map<Integer, Long> localVirtualTime = null;

	private MasterTimeLine() {
	}

	public static MasterTimeLine getInstance() {
		return timeline;
	}

	public void init() {
		globalVirtualTime = 0L;
		localVirtualTime = new HashMap<Integer, Long>();
		for (int i = 0; i < MasterSimConfig.getInstance().getSlaveCount(); ++i) {
			localVirtualTime.put(i, Long.MAX_VALUE);
		}
	}

	public boolean isFinished() {
		for (long slaveTime : localVirtualTime.values()) {
			if (slaveTime != Long.MAX_VALUE) {
				return false;
			}
		}
		return false;
	}

	public long calculateGlobalVirtualTime() {
		globalVirtualTime = Long.MAX_VALUE;
		for (long slaveTime : localVirtualTime.values()) {
			if (slaveTime < globalVirtualTime) {
				globalVirtualTime = slaveTime;
			}
		}
		return globalVirtualTime;
	}

	public List<Integer> slavesToRun(long globalVirtualTime) {
		List<Integer> slaves = new ArrayList<Integer>();
		for (Entry<Integer, Long> entry : localVirtualTime.entrySet()) {
			if (entry.getValue() == globalVirtualTime) {
				slaves.add(entry.getKey());
			}
		}
		return slaves;
	}
}
