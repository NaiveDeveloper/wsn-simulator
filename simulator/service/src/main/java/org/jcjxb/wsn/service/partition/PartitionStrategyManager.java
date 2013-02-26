package org.jcjxb.wsn.service.partition;

/*
 * Just do a simple partition now, improve it later
 */
public class PartitionStrategyManager {

	private static PartitionStrategyManager partitionStrategyManager = new PartitionStrategyManager();

	public static PartitionStrategyManager getInstance() {
		return partitionStrategyManager;
	}
	
}
