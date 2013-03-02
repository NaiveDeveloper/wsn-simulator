package org.jcjxb.wsn.service.sim;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class MasterTimeLine {

	private static MasterTimeLine timeline = new MasterTimeLine();

	private Map<Integer, Long> localVirtualTime = null;

	private Map<Integer, Boolean> execFlag = null;

	private MasterTimeLine() {
	}

	public static MasterTimeLine getInstance() {
		return timeline;
	}

	public void init() {
		localVirtualTime = new HashMap<Integer, Long>();
		for (int i = 0; i < MasterSimConfig.getInstance().getSlaveCount(); ++i) {
			localVirtualTime.put(i, Long.MAX_VALUE);
		}
		execFlag = new HashMap<Integer, Boolean>();
		for (int i = 0; i < MasterSimConfig.getInstance().getSlaveCount(); ++i) {
			execFlag.put(i, true);
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
		long globalVirtualTime = Long.MAX_VALUE;
		for (long slaveTime : localVirtualTime.values()) {
			if (slaveTime < globalVirtualTime) {
				globalVirtualTime = slaveTime;
			}
		}
		return globalVirtualTime;
	}

	public List<Integer> slavesToExec(long globalVirtualTime) {
		List<Integer> slaves = new ArrayList<Integer>();
		for (Entry<Integer, Long> entry : localVirtualTime.entrySet()) {
			if (entry.getValue() == globalVirtualTime) {
				slaves.add(entry.getKey());
				execFlag.put(entry.getKey(), true);
			}
		}
		return slaves;
	}

	public boolean isRoundEnd() {
		for (boolean flag : execFlag.values()) {
			if (flag) {
				return false;
			}
		}
		return true;
	}
}
