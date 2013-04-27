package org.jcjxb.wsn.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Log {

	private String id;

	private String name;

	private byte[] config;

	private String eventDetailDir;

	private List<String> eventDetailFiles = new ArrayList<String>();

	private byte[] result;
	
	private int state; // 0 running 1 finished 2 error
	
	private Date date;
	
	private static Map<Integer, String> statusMap = new HashMap<Integer, String>();
	
	{
		statusMap.put(0, "正在运行");
		statusMap.put(1, "成功运行");
		statusMap.put(2, "运行失败");
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getConfig() {
		return config;
	}

	public void setConfig(byte[] config) {
		this.config = config;
	}

	public String getEventDetailDir() {
		return eventDetailDir;
	}

	public void setEventDetailDir(String eventDetailDir) {
		this.eventDetailDir = eventDetailDir;
	}

	public List<String> getEventDetailFiles() {
		return eventDetailFiles;
	}

	public void setEventDetailFiles(List<String> eventDetailFiles) {
		this.eventDetailFiles = eventDetailFiles;
	}

	public byte[] getResult() {
		return result;
	}

	public void setResult(byte[] result) {
		this.result = result;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
	
	public void addFile(String file) {
		eventDetailFiles.add(file);
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public static Map<Integer, String> getStatusMap() {
		return statusMap;
	}
}
