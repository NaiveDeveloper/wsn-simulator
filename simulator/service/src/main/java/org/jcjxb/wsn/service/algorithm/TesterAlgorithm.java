package org.jcjxb.wsn.service.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.jcjxb.wsn.service.proto.BasicDataType.Event;
import org.jcjxb.wsn.service.sim.SlaveSimConfig;

public class TesterAlgorithm extends Algorithm {

	@Override
	public void initHandlers() {
		handlerList.put("Random", new RandomEventHandler());
	}

	@Override
	public List<Event> start() {
		return generateRandomEvent(1L, 2);
	}

	private class RandomEventHandler implements EventHandler {

		@Override
		public List<Event> handle(Event event) {
			Random random = new Random();
			int sensorNum = SlaveSimConfig.getInstance().getSensorCount();
			int randomNum = random.nextInt(sensorNum);
			if (event.getStartTime() > 1000L) {
				return null;
			}
			return generateRandomEvent(event.getStartTime() + (long) random.nextInt(20) + 1L, randomNum / 5);
		}
	}

	private List<Event> generateRandomEvent(long virtualTime, int sensorNum) {
		List<Event> events = new ArrayList<Event>(1);
		Event.Builder builder = Event.newBuilder();
		builder.setType("Random");
		builder.setStartTime(virtualTime);
		int allSensorNum = SlaveSimConfig.getInstance().getSensorCount();
		int[] randomData = new int[allSensorNum];
		for (int i = 0; i < allSensorNum; ++i) {
			randomData[i] = i;
		}
		Random random = new Random();
		for (int i = 0; i < sensorNum; ++i) {
			int randomIndex = random.nextInt(allSensorNum - i);
			builder.addSensorId(randomData[randomIndex]);
			int temp = randomData[allSensorNum - i - 1];
			randomData[allSensorNum - i - 1] = randomData[randomIndex];
			randomData[randomIndex] = temp;
		}
		events.add(builder.build());
		return events;
	}
}
