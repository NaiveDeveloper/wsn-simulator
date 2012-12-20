package org.jcjxb.wsn.service.sim;

import java.util.Map;

import org.jcjxb.wsn.service.proto.BasicDataType.Host;

public class MasterTimeLine {
	
	private long globalVirtualTime = 0L;
	
	private int slaveCount = 0;
	
	private Map<Host, Long> localVirtualTime = null;
}
