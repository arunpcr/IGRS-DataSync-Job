package integration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;

public class MongoDBConnecton {

	static Logger logger = Logger.getLogger(MongoDBConnecton.class.getName());
	public static MongoDatabase establishConnections(String dbName) {
		MongoDatabase database = null;
		MongoClient db = null;

		try {
			db = new MongoClient("localhost", 27017);

			database = db.getDatabase(dbName);
			
			logger.info("In MongoDBConnecton"+ database+ "is not null");

		} catch (Exception e) {

			logger.info("Connection establishment failed in MongoDBConnecton");
			logger.warn(e.getMessage());
		}
		return database;

	}
	
	public static void extracted(String dbName) {
		logger.info("in extracted method of MongoDBConnection class");
		MongoDatabase mongoDataBase;
		mongoDataBase = MongoDBConnecton.establishConnections(dbName);
		logger.info("MongoDatabase is not null");
		Map<String, ArrayList<String>> m = new HashMap<String, ArrayList<String>>();
		MongoIterable<String> list = mongoDataBase.listCollectionNames();
		for (String name : list) {
			ArrayList<String> mess = new ArrayList<>();
			MongoCollection<Document> document = mongoDataBase.getCollection(name);
			for (Document doc : document.find()) {
				doc.remove("_id");
				doc.remove("$numberLong");
				mess.add(doc.toJson());
			}
			m.put(name, mess);

		}
		
		for (Entry<String, ArrayList<String>> entry : m.entrySet()) {
			logger.info(entry.getKey()); 
			logger.info("In MongoDBConnection:"+entry.getKey());
			logger.info(entry.getValue());
			logger.info("In MongoDBConnection:"+entry.getValue());
			 
		}
	}

}
