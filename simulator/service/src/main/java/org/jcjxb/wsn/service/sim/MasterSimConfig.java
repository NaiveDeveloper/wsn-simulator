package org.jcjxb.wsn.service.sim;

import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jcjxb.wsn.db.DBOperation;
import org.jcjxb.wsn.db.Log;
import org.jcjxb.wsn.service.proto.SlaveService.EventsDetail;
import org.jcjxb.wsn.service.proto.SlaveService.LVTSync;
import org.jcjxb.wsn.service.proto.WSNConfig.SimulationConfig;

public class MasterSimConfig extends SimConfig {

	private static Logger logger = Logger.getLogger(MasterSimConfig.class);

	private static MasterSimConfig simConfig = new MasterSimConfig();

	private Map<Integer, Boolean> slaveReadyStatus = new HashMap<Integer, Boolean>();

	private int logFlushCycle = 100;

	private String dbHost = "127.0.0.1";

	private String logPath = "/var/tssim/data";

	private DBOperation dbOperation = null;

	private EventsDetail.Builder eventsDetailBuilder = EventsDetail.newBuilder();

	private Log log = null;

	private MasterSimConfig() {
	}

	public static MasterSimConfig getInstance() {
		return simConfig;
	}

	@Override
	public void initSimulation(SimulationConfig simulationConfig) {
		super.initSimulation(simulationConfig);
		log = new Log();
		log.setName(simulationConfig.getName());
		log.setConfig(simulationConfig.toByteArray());
		log.setDate(new Date());
	}

	@Override
	public void clear() {
		super.clear();
		eventsDetailBuilder.clear();
		log = null;
	}

	public synchronized void slaveReady(Integer hostIndex) {
		slaveReadyStatus.put(hostIndex, true);
	}

	public synchronized boolean isAllSlaveReady() {
		return slaveReadyStatus.size() == hostConfig.getSlaveHostCount();
	}

	public int getLogFlushCycle() {
		return logFlushCycle;
	}

	public void setLogFlushCycle(int logFlushCycle) {
		this.logFlushCycle = logFlushCycle;
	}

	public DBOperation getDbOperation() {
		if (dbOperation == null) {
			try {
				setDbHost("127.0.0.1");
			} catch (UnknownHostException e) {
				logger.error("Exception happens", e);
			}
		}
		return dbOperation;
	}

	public void setDbHost(String dbHost) throws UnknownHostException {
		this.dbHost = dbHost;
		dbOperation = new DBOperation(dbHost);
	}

	public String getDbHost() {
		return dbHost;
	}

	public String getLogPath() {
		return logPath;
	}

	public void setLogPath(String logPath) {
		this.logPath = logPath;
	}

	public int getEventsDetailSize() {
		return eventsDetailBuilder.getSyncCount();
	}

	public void addSync(LVTSync sync) {
		eventsDetailBuilder.addSync(sync);
	}

	public EventsDetail buildEventsDetail() {
		EventsDetail eventsDetail = eventsDetailBuilder.build();
		eventsDetailBuilder.clear();
		return eventsDetail;
	}

	public Log getLog() {
		return log;
	}
}
