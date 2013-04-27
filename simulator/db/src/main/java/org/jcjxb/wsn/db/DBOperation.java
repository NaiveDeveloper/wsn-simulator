package org.jcjxb.wsn.db;

import java.net.UnknownHostException;
import java.util.List;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

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

	public List<Log> query(String query, int page, int pageSize) {
		Query queryObj = new Query();
		if (query != null && !"".equals(query)) {
			Criteria criteria = Criteria.where("name").regex(String.format("^.*%s.*$", query));
			queryObj.addCriteria(criteria);
		}
		queryObj.limit(pageSize);
		queryObj.skip(page * pageSize);
		return operations.find(queryObj, Log.class);
	}

	public long count(String query) {
		Query queryObj = new Query();
		if (query != null && !"".equals(query)) {
			Criteria criteria = Criteria.where("name").regex(String.format("^.*%s.*$", query));
			queryObj.addCriteria(criteria);
		}
		return operations.count(queryObj, Log.class);
	}
	
	public Log queryById(String id) {
		return operations.findById(id, Log.class);
	}
	
	public void deleteById(String id) {
		Query queryObj = new Query(Criteria.where("id").is(id));
		operations.remove(queryObj, Log.class);
	}
}
