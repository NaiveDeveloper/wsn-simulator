package org.jcjxb.wsn.service.energy;

import org.jcjxb.wsn.service.proto.WSNConfig.EnergyConsumeConfig;

public interface EnergyConsume {

	double receive(int bit, double distance, EnergyConsumeConfig config);

	double send(int bit, double distance, EnergyConsumeConfig config);
	
	double sense(int bit, double distance, EnergyConsumeConfig config);
}
