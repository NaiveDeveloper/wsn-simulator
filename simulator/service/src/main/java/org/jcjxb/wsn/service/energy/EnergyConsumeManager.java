package org.jcjxb.wsn.service.energy;

import java.util.HashMap;
import java.util.Map;

import org.jcjxb.wsn.service.proto.WSNConfig.EnergyConsumeConfig.ConsumeType;

public class EnergyConsumeManager {
	private static EnergyConsumeManager manager = new EnergyConsumeManager();

	private Map<ConsumeType, EnergyConsume> energyConsumes = new HashMap<ConsumeType, EnergyConsume>();

	private EnergyConsumeManager() {
		energyConsumes.put(ConsumeType.STATIC, new StaticEnergyConsume());
		energyConsumes.put(ConsumeType.SIMPLE, new SimpleEnergyConsume());
	}

	public static EnergyConsumeManager getInstance() {
		return manager;
	}

	public EnergyConsume getEnergyConsume(ConsumeType name) {
		return energyConsumes.get(name);
	}
}
