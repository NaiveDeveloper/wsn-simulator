package org.jcjxb.wsn.service.deploy;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.jcjxb.wsn.service.proto.BasicDataType.Position;
import org.jcjxb.wsn.service.proto.BasicDataType.PositionList;
import org.jcjxb.wsn.service.proto.WSNConfig.SourceEventDeployConfig.DeployType;


public class SourceEventDeploy {
	private static SourceEventDeploy sourceNodeDeploy = new SourceEventDeploy();

	private Map<DeployType, Deploy> deployStrategys = new HashMap<DeployType, Deploy>();

	private SourceEventDeploy() {
		deployStrategys.put(DeployType.RANDOM, new RandomDeploy());
		deployStrategys.put(DeployType.STATIC, new StaticDeploy());
	}

	public static SourceEventDeploy getInstance() {
		return sourceNodeDeploy;
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
			PositionList.Builder builder = PositionList.newBuilder();
			Random random = new Random();
			for (int i = 0; i < num; ++i) {
				builder.addPostion(Position.newBuilder().setX(random.nextInt(width)).setY(random.nextInt(height)).build());
			}
			return builder.build();
		}
	}

	private class StaticDeploy implements Deploy {
		@Override
		public PositionList generate(int num, int width, int height, PositionList positionList) {
			return positionList;
		}
	}
}
