package org.jcjxb.wsn.service.deploy;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.jcjxb.wsn.common.DeployTool;
import org.jcjxb.wsn.service.proto.BasicDataType.Position;
import org.jcjxb.wsn.service.proto.BasicDataType.PositionList;
import org.jcjxb.wsn.service.proto.WSNConfig.SensorNodeDeployConfig.DeployType;

public class SensorNodeDeploy {

	private static SensorNodeDeploy sensorNodeDeploy = new SensorNodeDeploy();

	private Map<DeployType, Deploy> deployStrategys = new HashMap<DeployType, Deploy>();

	private SensorNodeDeploy() {
		deployStrategys.put(DeployType.RANDOM, new RandomDeploy());
		deployStrategys.put(DeployType.STATIC, new StaticDeploy());
		deployStrategys.put(DeployType.GRID, new GridDeploy());
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

	private class GridDeploy implements Deploy {
		@Override
		public PositionList generate(int num, int width, int height, PositionList positionList) {
			PositionList.Builder builder = PositionList.newBuilder();
			double dis = DeployTool.gridDistance(num, height, width);
			int horCount = (int) ((width - dis) / dis);
			double horOffset = (width - horCount * dis) / 2;
			int verNum = 1;
			int horNum = 0;
			for (int i = 0; i < num; ++i) {
				if (horNum > horCount) {
					horNum = 0;
					++verNum;
				}
				builder.addPostion(Position.newBuilder().setX(horOffset + dis * horNum).setY(verNum * dis).build());
				++horNum;
			}
			return builder.build();
		}
	}
}
