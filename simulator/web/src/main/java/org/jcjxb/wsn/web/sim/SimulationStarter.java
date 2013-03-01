package org.jcjxb.wsn.web.sim;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.jcjxb.wsn.rpc.LionRpcController;
import org.jcjxb.wsn.service.agent.MasterServicAgentManager;
import org.jcjxb.wsn.service.proto.WSNConfig.AlgorithmConfig;
import org.jcjxb.wsn.service.proto.WSNConfig.DeployConfig;
import org.jcjxb.wsn.service.proto.WSNConfig.PartitionConfig;
import org.jcjxb.wsn.service.proto.WSNConfig.PartitionConfig.PartitionType;
import org.jcjxb.wsn.service.proto.WSNConfig.SensorConfig;
import org.jcjxb.wsn.service.proto.WSNConfig.SensorNodeDeployConfig;
import org.jcjxb.wsn.service.proto.WSNConfig.SimulationConfig;
import org.jcjxb.wsn.service.proto.WSNConfig.SinkNodeDeployConfig;
import org.jcjxb.wsn.service.proto.WSNConfig.SourceEventDeployConfig;

import com.google.protobuf.RpcController;

public class SimulationStarter {
	
	private static Logger logger = Logger.getLogger(SimulationStarter.class);

	public static void main(String[] args) throws IOException {
		SimulationConfig.Builder simulationBuilder = SimulationConfig.newBuilder();
		
		AlgorithmConfig.Builder algorithmBuilder = AlgorithmConfig.newBuilder();
		algorithmBuilder.setName("DD");
		simulationBuilder.setAlgorithmConfig(algorithmBuilder.build());
		
		PartitionConfig.Builder partitionBuilder = PartitionConfig.newBuilder();
		partitionBuilder.setPartitionType(PartitionType.RANDOM);
		simulationBuilder.setPartitionConfig(partitionBuilder.build());
		
		DeployConfig.Builder deployBuilder = DeployConfig.newBuilder();
		deployBuilder.setWidth(1000);
		deployBuilder.setHeight(1000);
		
		SensorNodeDeployConfig.Builder sensorDeployBuilder = SensorNodeDeployConfig.newBuilder();
		sensorDeployBuilder.setDeployType(SensorNodeDeployConfig.DeployType.RANDOM);
		sensorDeployBuilder.setNodeNum(100);
		SensorConfig.Builder sensorBuilder= SensorConfig.newBuilder();
		sensorBuilder.setEnergy(1000);
		sensorBuilder.setTransmissionRadius(20);
		sensorDeployBuilder.setSensorConfig(sensorBuilder.build());
		deployBuilder.setSensorNodeDeployConfig(sensorDeployBuilder.build());
		
		SinkNodeDeployConfig.Builder sinkDeployBuilder = SinkNodeDeployConfig.newBuilder();
		sinkDeployBuilder.setDeployType(SinkNodeDeployConfig.DeployType.RANDOM);
		sinkDeployBuilder.setNodeNum(1);
		deployBuilder.setSinkNodeDeployConfig(sinkDeployBuilder.build());
		
		SourceEventDeployConfig.Builder sourceDeployBuilder = SourceEventDeployConfig.newBuilder();
		sourceDeployBuilder.setDeployType(SourceEventDeployConfig.DeployType.RANDOM);
		sourceDeployBuilder.setEventInterval(10);
		sourceDeployBuilder.setEventNum(10);
		sourceDeployBuilder.setTimes(-1);
		sourceDeployBuilder.setRegenerate(false);
		sourceDeployBuilder.setRadius(30);
		deployBuilder.setSourceEventDeployConfig(sourceDeployBuilder.build());
		
		simulationBuilder.setDeployConfig(deployBuilder.build());
		
		RpcController controller = new LionRpcController();
		MasterServicAgentManager.getInstance().getServiceAgent("127.0.0.1", 10000).startSimulation(simulationBuilder.build(), controller);
		
		if (controller.failed()) {
			logger.error("Start simulation failed, " + controller.errorText());
			return;
		}
		logger.info("Start simulation successfully");
	}
}
