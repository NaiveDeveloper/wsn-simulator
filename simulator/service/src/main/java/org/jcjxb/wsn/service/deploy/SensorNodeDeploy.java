package org.jcjxb.wsn.service.deploy;

import java.util.HashMap;
import java.util.Map;

import org.jcjxb.wsn.service.proto.BasicDataType.PositionList;
import org.jcjxb.wsn.service.proto.SimulationConfig.SensorNodeDeployConfig.DeployType;

public class SensorNodeDeploy {

	private static SensorNodeDeploy sensorNodeDeploy = new SensorNodeDeploy();

	private Map<DeployType, Deploy> deployStrategys = new HashMap<DeployType, Deploy>();

	private SensorNodeDeploy() {
		deployStrategys.put(DeployType.RANDOM, new RandomDeploy());
		deployStrategys.put(DeployType.STATIC, new StaticDeploy());
	}

	public static SensorNodeDeploy getInstance() {
		return sensorNodeDeploy;
	}
	
	public PositionList deploy(int num, int width, int height, DeployType deployType, PositionList positionList) {
		return deployStrategys.get(deployType).generate(num, width, height, positionList);
	}

	private interface Deploy {

		public PositionList generate(int num, int width, int height, PositionList positionList);
	}

	private class RandomDeploy implements Deploy {

		@Override
		public PositionList generate(int num, int width, int height, PositionList positionList) {

			return null;
		}
	}

	private class StaticDeploy implements Deploy {
		@Override
		public PositionList generate(int num, int width, int height, PositionList positionList) {
			return positionList;
		}
	}
}
