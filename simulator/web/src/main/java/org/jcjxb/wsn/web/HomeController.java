package org.jcjxb.wsn.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.jcjxb.wsn.service.proto.WSNConfig.AlgorithmConfig;
import org.jcjxb.wsn.service.proto.WSNConfig.CommandConfig;
import org.jcjxb.wsn.service.proto.WSNConfig.DeployConfig;
import org.jcjxb.wsn.service.proto.WSNConfig.EnergyConsumeConfig;
import org.jcjxb.wsn.service.proto.WSNConfig.PartitionConfig;
import org.jcjxb.wsn.service.proto.WSNConfig.PartitionConfig.PartitionType;
import org.jcjxb.wsn.service.proto.WSNConfig.SimulationConfig;
import org.jcjxb.wsn.service.proto.WSNConfig.EnergyConsumeConfig.ConsumeType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		return "home";
	}

	@RequestMapping(value = "/{path:.*}.jspt", method = RequestMethod.GET)
	public String jsp(@PathVariable("path") String path) {
		return path;
	}

	@RequestMapping(value = "/config", method = RequestMethod.GET)
	public String config() {
		return "config";
	}

	@RequestMapping(value = "/addSimulation", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> addSimulation(InputStream input) {
		Map<String, String> result = new HashMap<String, String>();
		Map<String, Object> reuqetsMap = null;
		// 解析JSON请求为MAP
		try {
			byte[] tempBuffer = new byte[1000];
			ByteBuffer buffer = ByteBuffer.allocate(10000);
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
			int length = -1;
			while ((length = input.read(tempBuffer)) != -1) {
				byteOut.write(tempBuffer, 0, length);
			}
			ObjectMapper mapper = new ObjectMapper();
			TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {
			};
			reuqetsMap = mapper.readValue(byteOut.toByteArray(), typeRef);
		} catch (IOException e) {
			e.printStackTrace();
			result.put("status", "1");
			result.put("errorText", "读取或解析实验配置JSON请求发生错误");
			return result;
		}

		// 生成SimulationConfig对象
		SimulationConfig.Builder simulationConfigbuilder = SimulationConfig.newBuilder();
		try {
			if (!initSimulationConfig(reuqetsMap, simulationConfigbuilder)) {
				result.put("status", "1");
				result.put("errorText", "生成命令请求发生错误");
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("status", "1");
			result.put("errorText", "生成命令请求发生错误");
			return result;
		}
		result.put("status", "0");
		return result;
	}

	@SuppressWarnings({ "rawtypes" })
	private boolean initSimulationConfig(Map<String, Object> reuqetsMap, SimulationConfig.Builder simulationConfigbuilder) {
		simulationConfigbuilder.setName(reuqetsMap.get("name").toString());
		
		// 设置算法选择
		Map algorithmMap = (Map) reuqetsMap.get("algorithmConfig");
		simulationConfigbuilder.setAlgorithmConfig(AlgorithmConfig.newBuilder().setName(algorithmMap.get("name").toString()));
		
		// 设置命令参数
		Map commandConfig = (Map) reuqetsMap.get("commandConfig");
		simulationConfigbuilder.setCommandConfig(CommandConfig.newBuilder().setOutput(
				CommandConfig.Output.valueOf(Integer.parseInt(commandConfig.get("output").toString()))));
		
		// 设置能量消耗模型配置
		Map energyConsumeConfig = (Map) reuqetsMap.get("energyConsumeConfig");
		EnergyConsumeConfig.Builder energyBuider = EnergyConsumeConfig.newBuilder();
		energyBuider.setConsumeType(ConsumeType.valueOf(Integer.parseInt(energyConsumeConfig.get("consumeType").toString())));
		energyBuider.setTransmitter(Double.parseDouble(energyConsumeConfig.get("transmitter").toString()));
		energyBuider.setAmplifier(Double.parseDouble(energyConsumeConfig.get("amplifier").toString()));
		energyBuider.setReceiver(Double.parseDouble(energyConsumeConfig.get("receiver").toString()));
		energyBuider.setExponent(Double.parseDouble(energyConsumeConfig.get("exponent").toString()));
		energyBuider.setSensor(Double.parseDouble(energyConsumeConfig.get("sensor").toString()));
		simulationConfigbuilder.setEnergyConsumeConfig(energyBuider);
		
		// 设置模拟分割模型
		simulationConfigbuilder.setPartitionConfig(PartitionConfig.newBuilder().setPartitionType(PartitionType.RANDOM));
		
		// 设置部署模型
		DeployConfig.Builder deployBuilder = DeployConfig.newBuilder();
		Map deployMap = (Map)reuqetsMap.get("deployConfig");
		
		return true;
	}
}
