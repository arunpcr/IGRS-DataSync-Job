package com.cr.main;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class CMongoToOracle {

	static Logger logger = Logger.getLogger(CMongoToOracle.class.getName());

	public static List<String> fromMongoDocumentDetails(String url, String dbName, String cName)
			throws InterruptedException, SQLException {
		
		logger.info("Started connection to MongoDB to fetch the collections");

		logger.info("APPLication Started ::*********");

		logger.info("URL of the datbase ::" + url);
		logger.info("DB Name ::" + dbName);
		logger.info("Collection Name ::" + cName);
		logger.info("******************************************************");

		ArrayList<String> mess = new ArrayList<String>();
		try (MongoClient mongoClient = MongoClients.create(url)) {
			MongoDatabase database = mongoClient.getDatabase(dbName);
			MongoCollection<Document> list = database.getCollection(cName);
	
			//Bson statusCheckFilter = Filters.or(Filters.eq("status", "SUBMITTED"),Filters.eq("status", "FAILED"));
			Bson statusCheckFilter = Filters.or(Filters.eq("status", "SUBMITTED"),Filters.eq("status", "FAILED"),Filters.eq("status", "SLOT BOOKED"));
			for (Document doc : list.find(statusCheckFilter)) {
				logger.info("in for-loop after document filter based on status,SUBMITTED or FAILED or SLOT BOOKED ");
				doc.remove("_id");
				doc.remove("$numberLong");

				mess.add(doc.toJson());
				//logger.info("document after filtering based on 'status' 'submitted'"+mess);
				logger.info("document after filtering based on 'status' 'submitted'");

			}

		}
	

		return mess;

	}
	public static List<String> fromMongoDocumentDetails(String id, String url, String dbName, String cName)
			throws InterruptedException, SQLException {
		
		logger.info("Started connection to MongoDB to fetch the collections");

		logger.info("APPLication Started ::*********");

		logger.info("URL of the datbase ::" + url);
		logger.info("DB Name ::" + dbName);
		logger.info("Collection Name ::" + cName);
		logger.info("******************************************************");

		ArrayList<String> mess = new ArrayList<String>();
		try (MongoClient mongoClient = MongoClients.create(url)) {
			MongoDatabase database = mongoClient.getDatabase(dbName);
			MongoCollection<Document> list = database.getCollection(cName);
			

			Bson statusCheckFilter = Filters.eq("documentId", id);

			for (Document doc : list.find(statusCheckFilter)) {
				logger.info("in for-loop after document filter based on documentId submitted*******"+id);
				doc.remove("_id");
				doc.remove("$numberLong");

				mess.add(doc.toJson());
				logger.info("document after filtering based on 'status' 'submitted'"+mess);

			}

		}
			return mess;

	}



}
