package org.jcjxb.wsn.service.partition;

import org.jcjxb.wsn.service.proto.BasicDataType.PositionList;
import org.jcjxb.wsn.service.proto.WSNConfig.PartitionConfig;

public class PartitionStrategyManager {

	private static PartitionStrategyManager partitionStrategyManager = new PartitionStrategyManager();

	public static PartitionStrategyManager getInstance() {
		return partitionStrategyManager;
	}

	public void partition(PartitionConfig.Builder builder, PartitionConfig partitionConfig, int slaveNum, PositionList positionList) {
		builder.setSensorsOnHostList(SensorNodePartition.getInstance().partition(partitionConfig.getPartitionType(), slaveNum,
				positionList, partitionConfig.getSensorsOnHostList()));
	}
}
