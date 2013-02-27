package org.jcjxb.wsn.service.partition;

import java.util.HashMap;
import java.util.Map;

import org.jcjxb.wsn.service.proto.BasicDataType.PositionList;
import org.jcjxb.wsn.service.proto.BasicDataType.SensorsOnHostList;
import org.jcjxb.wsn.service.proto.WSNConfig.PartitionConfig.PartitionType;

public class SensorNodePartition {
	private static SensorNodePartition sensorNodePartition = new SensorNodePartition();

	private Map<PartitionType, Partition> partitionStrategys = new HashMap<PartitionType, Partition>();

	private SensorNodePartition() {
		partitionStrategys.put(PartitionType.RANDOM, new RandomPartition());
		partitionStrategys.put(PartitionType.STATIC, new StaticPartition());
	}

	public static SensorNodePartition getInstance() {
		return sensorNodePartition;
	}

	public SensorsOnHostList partition(PartitionType partitionType, int slaveNum, PositionList positionList,
			SensorsOnHostList sensorsOnHostList) {
		return partitionStrategys.get(partitionType).generate(slaveNum, positionList, sensorsOnHostList);
	}

	private interface Partition {

		public SensorsOnHostList generate(int slaveNum, PositionList positionList, SensorsOnHostList sensorsOnHostList);
	}

	private class RandomPartition implements Partition {

		@Override
		public SensorsOnHostList generate(int slaveNum, PositionList positionList, SensorsOnHostList sensorsOnHostList) {
			return null;
		}
	}

	private class StaticPartition implements Partition {

		@Override
		public SensorsOnHostList generate(int slaveNum, PositionList positionList, SensorsOnHostList sensorsOnHostList) {
			return null;
		}
	}
}
