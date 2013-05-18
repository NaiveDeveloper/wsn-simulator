package org.jcjxb.wsn.service.energy;

import org.jcjxb.wsn.service.proto.WSNConfig.EnergyConsumeConfig;


public class SimpleEnergyConsume implements EnergyConsume {

	@Override
	public double receive(int bit, double distance, EnergyConsumeConfig config) {
		return config.getReceiver() * bit;
	}

	@Override
	public double send(int bit, double distance, EnergyConsumeConfig config) {
		return config.getTransmitter() * bit + config.getAmplifier() * bit * Math.pow(distance, config.getExponent());
	}

	@Override
	public double sense(int bit, double distance, EnergyConsumeConfig config) {
		return config.getSensor() * bit;
	}
}
