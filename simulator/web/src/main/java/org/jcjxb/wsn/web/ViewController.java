package org.jcjxb.wsn.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jcjxb.wsn.db.DBOperation;
import org.jcjxb.wsn.db.Log;
import org.jcjxb.wsn.service.proto.BasicDataType;
import org.jcjxb.wsn.service.proto.BasicDataType.PositionList;
import org.jcjxb.wsn.service.proto.SlaveService.SimulationResult;
import org.jcjxb.wsn.service.proto.SlaveService.SimulationResult.EnergyData;
import org.jcjxb.wsn.service.proto.WSNConfig.DeployConfig;
import org.jcjxb.wsn.service.proto.WSNConfig.SimulationConfig;
import org.jcjxb.wsn.web.bean.Energy;
import org.jcjxb.wsn.web.bean.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.protobuf.InvalidProtocolBufferException;

@Controller
public class ViewController {

	private static Logger logger = Logger.getLogger(ViewController.class);

	@Autowired
	private DBOperation dbOperation;

	@RequestMapping(value = "/simData", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> simData(String id, String type) {
		logger.debug(String.format("View simulation %s data of %s", type, id));
		Map<String, Object> result = new HashMap<String, Object>();
		Log log = dbOperation.queryById(id);
		if ("location".equalsIgnoreCase(type)) {
			SimulationConfig simulationConfig = null;
			try {
				simulationConfig = SimulationConfig.parseFrom(log.getConfig());
			} catch (InvalidProtocolBufferException e) {
				logger.error("Exception happens", e);
				result.put("errorMsg", "解析实验配置数据出错");
				return result;
			}
			DeployConfig deplyConfig = simulationConfig.getDeployConfig();
			result.put("sinkLocation", convertPositionList(deplyConfig.getSinkNodeDeployConfig().getPostionList()));
			result.put("sensorLocation", convertPositionList(deplyConfig.getSensorNodeDeployConfig().getPostionList()));
			result.put("width", (int) deplyConfig.getWidth());
			result.put("height", (int) deplyConfig.getHeight());
		} else if ("energy".equalsIgnoreCase(type)) {
			SimulationResult simulationResult = null;
			SimulationConfig simulationConfig = null;
			try {
				simulationResult = SimulationResult.parseFrom(log.getResult());
				simulationConfig = SimulationConfig.parseFrom(log.getConfig());
			} catch (InvalidProtocolBufferException e) {
				logger.error("Exception happens", e);
				result.put("errorMsg", "解析实验结果或配置数据出错");
				return result;
			}
			result.put("eneryList", convertEnergyData(simulationResult.getEnergyDataList()));
			result.put("initialEnergy", simulationConfig.getDeployConfig().getSensorNodeDeployConfig().getSensorConfig().getEnergy());
		}
		return result;
	}

	@RequestMapping(value = "/simConfig", method = RequestMethod.GET)
	public ResponseEntity<String> simConfig(String id) {
		HttpHeaders headers = new HttpHeaders();  
        headers.set("content-type", "text/html;charset=UTF-8");
		Log log = dbOperation.queryById(id);
		SimulationConfig simulationConfig = null;
		try {
			simulationConfig = SimulationConfig.parseFrom(log.getConfig());
		} catch (InvalidProtocolBufferException e) {
			logger.error("Exception happens", e);
			return new ResponseEntity<String>("解析配置信息出错", headers, HttpStatus.OK);
		}

		// Show name clearly
		String name = simulationConfig.getName();
		System.out.println(name);
		SimulationConfig.Builder builder = SimulationConfig.newBuilder(simulationConfig);
		builder.clearName();
		simulationConfig = builder.build();
		String result = simulationConfig.toString();
		result = result.replaceAll("\n", "<br/>");
		result += "name: " + name;
		
		return new ResponseEntity<String>(result, headers, HttpStatus.OK);
	}

	private List<Position> convertPositionList(PositionList positionList) {
		List<Position> result = new ArrayList<Position>();
		for (BasicDataType.Position pos : positionList.getPostionList()) {
			result.add(new Position((int) pos.getX(), (int) pos.getY()));
		}
		return result;
	}

	private List<Energy> convertEnergyData(List<EnergyData> energyDataList) {
		List<Energy> result = new ArrayList<Energy>();
		for (EnergyData energyData : energyDataList) {
			result.add(new Energy(energyData.getNodeId(), energyData.getEneryLeft(), energyData.getDie()));
		}
		return result;
	}
}
