package org.jcjxb.wsn.service.algorithm;

import java.util.HashMap;
import java.util.Map;

public class AlgorithmManager {

	private static AlgorithmManager manager = new AlgorithmManager();

	private Map<String, Algorithm> algorithms = new HashMap<String, Algorithm>();

	private AlgorithmManager() {
		algorithms.put("EMPTY", new EmptyAlgorithm());
		algorithms.put("TESTER", new TesterAlgorithm());
		algorithms.put("GPRS", new GPSRAlgorithm());
		algorithms.put("SDVB", new SDVBAlgorithm());
		algorithms.put("LEACH", new LEACHAlgorithm());
		algorithms.put("DIRECT", new DirectAlgorithm());
	}

	public static AlgorithmManager getInstance() {
		return manager;
	}

	public Algorithm getAlgorithm(String name) {
		return algorithms.get(name);
	}
}
