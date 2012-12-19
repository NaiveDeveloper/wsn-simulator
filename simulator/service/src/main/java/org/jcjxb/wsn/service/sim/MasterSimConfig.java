package org.jcjxb.wsn.service.sim;

public class MasterSimConfig extends SimConfig {

	private static MasterSimConfig simConfig = new MasterSimConfig();
	
	public static MasterSimConfig getInstance() {
		return simConfig;
	}
}
