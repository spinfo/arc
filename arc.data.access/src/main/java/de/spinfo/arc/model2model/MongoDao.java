package de.spinfo.arc.model2model;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class MongoDao {

	MongoClient mongo = null;

	public DBCollection connectAndGetCollection(String name) {
		DBCollection wuColl = null;
		try {
			if (mongo == null)
				mongo = new MongoClient("localhost", 27017);
			DB db = mongo.getDB("arc");
			wuColl = db.getCollection(name);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		return wuColl;
	}

	public void close() {
		if (mongo != null) {
			mongo.close();
			mongo = null;
		}
	}
}
