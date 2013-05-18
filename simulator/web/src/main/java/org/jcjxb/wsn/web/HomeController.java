package org.jcjxb.wsn.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.jcjxb.wsn.db.DBOperation;
import org.jcjxb.wsn.db.Log;
import org.jcjxb.wsn.rpc.LionRpcController;
import org.jcjxb.wsn.service.agent.MasterServicAgentManager;
import org.jcjxb.wsn.service.proto.WSNConfig.AlgorithmConfig;
import org.jcjxb.wsn.service.proto.WSNConfig.CommandConfig;
import org.jcjxb.wsn.service.proto.WSNConfig.DeployConfig;
import org.jcjxb.wsn.service.proto.WSNConfig.EnergyConsumeConfig;
import org.jcjxb.wsn.service.proto.WSNConfig.EnergyConsumeConfig.ConsumeType;
import org.jcjxb.wsn.service.proto.WSNConfig.LeachConfig;
import org.jcjxb.wsn.service.proto.WSNConfig.PartitionConfig;
import org.jcjxb.wsn.service.proto.WSNConfig.PartitionConfig.PartitionType;
import org.jcjxb.wsn.service.proto.WSNConfig.SensorConfig;
import org.jcjxb.wsn.service.proto.WSNConfig.SensorNodeDeployConfig;
import org.jcjxb.wsn.service.proto.WSNConfig.SimulationConfig;
import org.jcjxb.wsn.service.proto.WSNConfig.SinkNodeDeployConfig;
import org.jcjxb.wsn.service.proto.WSNConfig.SourceEventDeployConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.protobuf.RpcController;

@Controller
public class HomeController {

	private static Logger logger = Logger.getLogger(HomeController.class);

	private static int pageSize = 8;

	@Autowired
	private DBOperation dbOperation;

	@RequestMapping(value = { "/", "/home" }, method = RequestMethod.GET)
	public String home(@RequestParam(required = false, defaultValue = "") String query,
			@RequestParam(required = false, defaultValue = "0") int page, ModelMap modelMap) {
		logger.debug(String.format("Query keyword is %s visiting page %d", query, page));
		long count = dbOperation.count(query);
		long pageNum = (count + (pageSize - 1)) / pageSize;
		List<Log> logs = dbOperation.query(query, page, pageSize);
		modelMap.put("logs", logs);
		modelMap.put("pageNum", pageNum);
		modelMap.put("page", page);
		modelMap.put("pageSize", pageSize);
		modelMap.put("query", query);
		modelMap.put("statusMap", Log.getStatusMap());
		return "home";
	}

	@RequestMapping(value = { "/delete" }, method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(required = false, defaultValue = "") String query,
			@RequestParam(required = false, defaultValue = "0") int page, String id) {
		logger.debug(String.format("Delete log with id %s", id));
		dbOperation.deleteById(id);
		ModelMap mmap = new ModelMap();
		mmap.put("query", query);
		mmap.put("page", page);
		return new ModelAndView("redirect:/home", mmap);
	}

	@RequestMapping(value = { "/view" }, method = RequestMethod.GET)
	public String view(String id, ModelMap modelMap) {
		Log log = dbOperation.queryById(id);
		modelMap.put("id", id);
		return "view";
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

		SimulationConfig simulationConfig = simulationConfigbuilder.build();
		if (Level.DEBUG.isGreaterOrEqual(logger.getEffectiveLevel())) {
			logger.debug(simulationConfig.toString());
		}

		RpcController controller = new LionRpcController();
		MasterServicAgentManager.getInstance().getServiceAgent("127.0.0.1", 10000).startSimulation(simulationConfig, controller);

		if (controller.failed()) {
			result.put("status", "1");
			result.put("errorText", "Master服务器返回信息：" + controller.errorText());
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
		Map deployMap = (Map) reuqetsMap.get("deployConfig");
		deployBuilder.setWidth(Double.parseDouble(deployMap.get("width").toString()));
		deployBuilder.setHeight(Double.parseDouble(deployMap.get("height").toString()));

		Map sensorNodeMap = (Map) deployMap.get("sensorNodeDeployConfig");
		SensorNodeDeployConfig.Builder sensorDeployBuilder = SensorNodeDeployConfig.newBuilder();
		sensorDeployBuilder.setDeployType(SensorNodeDeployConfig.DeployType.valueOf(Integer.parseInt(sensorNodeMap.get("deployType")
				.toString())));
		sensorDeployBuilder.setNodeNum(Integer.parseInt(sensorNodeMap.get("nodeNum").toString()));
		SensorConfig.Builder sensorBuilder = SensorConfig.newBuilder();
		sensorBuilder.setEnergy(Double.parseDouble(sensorNodeMap.get("energy").toString()));
		sensorBuilder.setTransmissionRadius(Double.parseDouble(sensorNodeMap.get("transmissionRadius").toString()));
		sensorDeployBuilder.setSensorConfig(sensorBuilder);
		deployBuilder.setSensorNodeDeployConfig(sensorDeployBuilder);

		Map sinkNodeMap = (Map) deployMap.get("sinkNodeDeployConfig");
		SinkNodeDeployConfig.Builder sinkDeployBuilder = SinkNodeDeployConfig.newBuilder();
		sinkDeployBuilder
				.setDeployType(SinkNodeDeployConfig.DeployType.valueOf(Integer.parseInt(sinkNodeMap.get("deployType").toString())));
		sinkDeployBuilder.setNodeNum(Integer.parseInt(sinkNodeMap.get("nodeNum").toString()));
		deployBuilder.setSinkNodeDeployConfig(sinkDeployBuilder);

		Map sourceNodeMap = (Map) deployMap.get("sourceEventDeployConfig");
		SourceEventDeployConfig.Builder sourceDeployBuilder = SourceEventDeployConfig.newBuilder();
		sourceDeployBuilder.setDeployType(SourceEventDeployConfig.DeployType.valueOf(Integer.parseInt(sourceNodeMap.get("deployType")
				.toString())));
		sourceDeployBuilder.setRadius(Double.parseDouble(sourceNodeMap.get("radius").toString()));
		sourceDeployBuilder.setEventNum(Integer.parseInt(sourceNodeMap.get("eventNum").toString()));
		sourceDeployBuilder.setEventInterval(Integer.parseInt(sourceNodeMap.get("eventInterval").toString()));
		sourceDeployBuilder.setTimes(Integer.parseInt(sourceNodeMap.get("times").toString()));
		sourceDeployBuilder.setEventBit(Integer.parseInt(sourceNodeMap.get("eventBit").toString()));
		deployBuilder.setSourceEventDeployConfig(sourceDeployBuilder);

		simulationConfigbuilder.setDeployConfig(deployBuilder);

		// 设置算相关的专有参数
		String algorithmName = simulationConfigbuilder.getAlgorithmConfig().getName();
		if ("LEACH".endsWith(algorithmName)) {
			Map leachConfigMap = (Map) algorithmMap.get("leachConfig");
			LeachConfig.Builder leachConfigBuilder = LeachConfig.newBuilder();
			leachConfigBuilder.setClusterNum(Integer.parseInt(leachConfigMap.get("clusterNum").toString()));
			leachConfigBuilder.setClusterBuiltCycle(Integer.parseInt(leachConfigMap.get("clusterBuiltCycle").toString()));
			leachConfigBuilder.setDataSubmitTimes(Integer.parseInt(leachConfigMap.get("dataSubmitTimes").toString()));
			simulationConfigbuilder.getAlgorithmConfigBuilder().setLeachConfig(leachConfigBuilder);
		}
		return true;
	}
}
