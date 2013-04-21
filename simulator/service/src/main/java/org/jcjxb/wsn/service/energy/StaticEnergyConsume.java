package org.jcjxb.wsn.service.energy;

import org.jcjxb.wsn.service.proto.WSNConfig.EnergyConsumeConfig;

public class StaticEnergyConsume implements EnergyConsume {

	@Override
	public double receive(int bit, double distance, EnergyConsumeConfig config) {
		return config.getTransmitter();
	}

	@Override
	public double send(int bit, double distance, EnergyConsumeConfig config) {
		return config.getReceiver();
	}

	@Override
	public double sense(int bit, double distance, EnergyConsumeConfig config) {
		return config.getSensor();
	}
}
