package org.jcjxb.wsn.db;

import java.net.UnknownHostException;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.Mongo;

public class DBOperation {

	private MongoOperations operations = null;

	public DBOperation(String ip) throws UnknownHostException {
		operations = new MongoTemplate(new Mongo(ip), "tssim");
	}

	public boolean saveLog(Log log) {
		try {
			operations.save(log, "log");
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
