package org.jcjxb.wsn.service.deploy;

import org.jcjxb.wsn.service.proto.WSNConfig.DeployConfig;
import org.jcjxb.wsn.service.proto.WSNConfig.SensorNodeDeployConfig;
import org.jcjxb.wsn.service.proto.WSNConfig.SinkNodeDeployConfig;

public class DeployStrategyManager {

	private static DeployStrategyManager deployStrategyManager = new DeployStrategyManager();

	public static DeployStrategyManager getInstance() {
		return deployStrategyManager;
	}

	public void deploy(DeployConfig.Builder builder, DeployConfig deplyConfig) {
		DeployConfig.Builder deplyBuilder = DeployConfig.newBuilder();
		deplyBuilder.setWidth(deplyConfig.getWidth());
		deplyBuilder.setHeight(deplyConfig.getHeight());
		
		// Generate sensor node deploy data
		SensorNodeDeployConfig sensorConfig = deplyConfig.getSensorNodeDeployConfig();
		SensorNodeDeployConfig.Builder sensorBuilder = SensorNodeDeployConfig.newBuilder(sensorConfig);
		sensorBuilder.setPostionList(SensorNodeDeploy.getInstance().deploy(sensorConfig.getNodeNum(), (int)deplyConfig.getWidth(),
				(int)deplyConfig.getHeight(), sensorConfig.getDeployType(), sensorConfig.getPostionList()));
		deplyBuilder.setSensorNodeDeployConfig(sensorBuilder.build());
		
		// Generate sink node deploy data
		SinkNodeDeployConfig sinkConfig = deplyConfig.getSinkNodeDeployConfig();
		SinkNodeDeployConfig.Builder sinkBuilder = SinkNodeDeployConfig.newBuilder(sinkConfig);
		sensorBuilder.setPostionList(SinkNodeDeploy.getInstance().deploy(sinkConfig.getNodeNum(), (int)deplyConfig.getWidth(),
				(int)deplyConfig.getHeight(), sinkConfig.getDeployType(), sinkConfig.getPostionList()));
		deplyBuilder.setSinkNodeDeployConfig(sinkBuilder.build());
		
		// TODO
		// Generate source event deploy data
	}
}
