package com.ca.mongo;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.LogManager;

import org.bson.Document;
import org.bson.conversions.Bson;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import com.ca.data.KafkaMessage;
import com.ca.data.MemberData;
import com.cr.main.CMain;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.bulk.BulkWriteResult;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.InsertOneModel;
import com.mongodb.client.model.WriteModel;

public class MongoDBOperation {
	
	static Logger logger = Logger.getLogger(MongoDBOperation.class.getName());
	public static void main(String[] args) throws InterruptedException, SQLException, IOException {

		logger.info("Applicatin started");

		BasicConfigurator.configure();
		logger.info("APPLication Started ::*********");
		logger.info("Started");
		InputStream fis;
		try {
			 fis= MongoDBOperation.class.getClassLoader().getResourceAsStream("log4j.properties");
			LogManager.getLogManager().readConfiguration(fis);
		} catch (IOException e1) {
			logger.error("Error occurred ::: ", e1);
		}
		
	
	
	
	Properties initProperties = new Properties();
	initProperties.load(MongoDBOperation.class.getClassLoader().getResourceAsStream("mon.properties"));
	logger.info("Loaded Properties ::" + initProperties);

	}

	public static List<String> getCollectionData(String url, String dbName, String cName) {

		logger.info("Started connection to MongoDB to fetch the collections");
		
		logger.info("URL ::" +url);
		logger.info("DB Name ::" +dbName);
		logger.info("Collection Name ::" +cName);
		logger.info("******************************************************");

		ArrayList<String> mess = new ArrayList<>();
		try (MongoClient mongoClient = MongoClients.create(url)) {
			MongoDatabase database = mongoClient.getDatabase(dbName);
			MongoCollection<Document> list = database.getCollection(cName);

			for (Document doc : list.find()) {
				doc.remove("_id");
				doc.remove("$numberLong");
			
				mess.add(doc.toJson());

			}

		}
		//catch(Exception e) {e.getMessage();}
		return mess;

	}

	public static List<String> getPartyDetailsCollectionData(String url, String dbName, String cName, String documentId) {

		logger.info("Started connection to MongoDB to fetch the collections");
		
		logger.info("URL ::" +url);
		logger.info("DB Name ::" +dbName);
		logger.info("Collection Name ::" +cName);
		logger.info("******************************************************");

		ArrayList<String> mess = new ArrayList<>();
		try (MongoClient mongoClient = MongoClients.create(url)) {
			MongoDatabase database = mongoClient.getDatabase(dbName);
			MongoCollection<Document> list = database.getCollection(cName);
			
			 Bson query = new Document("applicationId",new Document("$eq",documentId));

			for (Document doc : list.find(query)) {
				doc.remove("_id");
				doc.remove("$numberLong");
			
				mess.add(doc.toJson());

			}

		}
		return mess;

	}

	public static List<String> getPartyRepresentDetailsCollectionData(String url, String dbName, String cName, String documentId) {

		logger.info("Started connection to MongoDB to fetch the collections");
		
		logger.info("URL ::" +url);
		logger.info("DB Name ::" +dbName);
		logger.info("Collection Name ::" +cName);
		logger.info("******************************************************");

		ArrayList<String> mess = new ArrayList<>();
		try (MongoClient mongoClient = MongoClients.create(url)) {
			MongoDatabase database = mongoClient.getDatabase(dbName);
			MongoCollection<Document> list = database.getCollection(cName);
			
			 Bson query = new Document("documentId",new Document("$eq",documentId));

			for (Document doc : list.find(query)) {
				doc.remove("_id");
				doc.remove("$numberLong");
			
				mess.add(doc.toJson());

			}

		}
		return mess;

	}
	
	public static List<String> getUsersCollectionData(String url, String dbName, String cName, String userId) {

		logger.info("Started connection to MongoDB to fetch the collections");
		
		logger.info("URL ::" +url);
		logger.info("DB Name ::" +dbName);
		logger.info("Collection Name ::" +cName);
		logger.info("******************************************************");

		ArrayList<String> mess = new ArrayList<>();
		try (MongoClient mongoClient = MongoClients.create(url)) {
			MongoDatabase database = mongoClient.getDatabase(dbName);
			MongoCollection<Document> list = database.getCollection(cName);
			
			 Bson query = new Document("loginId",new Document("$eq",userId));

			for (Document doc : list.find(query)) {
				doc.remove("_id");
				doc.remove("$numberLong");
			
				mess.add(doc.toJson());

			}

		}
		return mess;

	}
	public static List<String> getPropertyDetailsCollectionData(String url, String dbName, String cName, String applicationId) {

		logger.info("Started connection to MongoDB to fetch the collections");
		
		logger.info("URL ::" +url);
		logger.info("DB Name ::" +dbName);
		logger.info("Collection Name ::" +cName);
		logger.info("******************************************************");

		ArrayList<String> mess = new ArrayList<>();
		try (MongoClient mongoClient = MongoClients.create(url)) {
			MongoDatabase database = mongoClient.getDatabase(dbName);
			MongoCollection<Document> list = database.getCollection(cName);
			
			 Bson query = new Document("applicationId",new Document("$eq",applicationId));
			 
			 

			for (Document doc : list.find(query)) {
				doc.remove("_id");
				doc.remove("$numberLong");
			
				mess.add(doc.toJson());

			}

		}
		return mess;

	}
	
	public static List<String> getPropertyStructureDetailsCollectionData(String url, String dbName, String cName, String propertyID) {

		logger.info("Started connection to MongoDB to fetch the collections");
		
		logger.info("URL ::" +url);
		logger.info("DB Name ::" +dbName);
		logger.info("Collection Name ::" +cName);
		logger.info("******************************************************");

		ArrayList<String> mess = new ArrayList<>();
		try (MongoClient mongoClient = MongoClients.create(url)) {
			MongoDatabase database = mongoClient.getDatabase(dbName);
			MongoCollection<Document> list = database.getCollection("property_structure_details");
			getPropertyDetailsCollectionData(url,dbName,cName,propertyID);
			
			 Bson query = new Document("propertyId",new Document("$eq",propertyID));

			for (Document doc : list.find(query)) {
				doc.remove("_id");
				doc.remove("$numberLong");
			
				mess.add(doc.toJson());

			}

		}
		return mess;

	}

	public static void insertDocuments(List<KafkaMessage> message, String topic) {

		try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {

			MongoDatabase database = mongoClient.getDatabase("PDE");

			// get a handle to the "test" collection
			MongoCollection<Document> collection = database.getCollection(topic);
			List<WriteModel<Document>> writeOperations = new ArrayList<WriteModel<Document>>();

			for (KafkaMessage kafkaMessage : message) {
				Gson gson = new GsonBuilder().create();
				logger.info("SSSSS" + kafkaMessage.toString());
				logger.info("CLSSSSS" + kafkaMessage.getClass());
				logger.info("Class type :" + kafkaMessage.getMessage().getClass());
				//List<MemberData> mData = new ArrayList<MemberData>();
				MemberData mdata = gson.fromJson(kafkaMessage.getMessage(), MemberData.class);

				writeOperations.add(new InsertOneModel<Document>(new Document("", mdata.getMembershipID().intValue())
						.append("EntryDate", mdata.getEntryDate()).append("Status", mdata.getStatus())));

			}

			BulkWriteResult bulkWriteResult = collection.bulkWrite(writeOperations);
			logger.info("bulkWriteResult:- " + bulkWriteResult);

		}

	}

}
