package in.gov.ap.igrs.datasync.app.mongo;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import in.gov.ap.igrs.datasync.app.utility.IGRSDateUtil;

public enum MongoDBOperation {
	
	INSTANCE;
	
	final Logger logger = Logger.getLogger(this.name());
	

	public List<String> getCollectionData(String url, String dbName, String cName) {

		System.out.println("Started connection to MongoDB to fetch the collections");
		
		System.out.println("URL ::" +url);
		System.out.println("DB Name ::" +dbName);
		System.out.println("Collection Name ::" +cName);
		System.out.println("******************************************************");

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

	public List<String> getPartyDetailsCollectionData(String url, String dbName, String cName, String documentId) {

		System.out.println("Started connection to MongoDB to fetch the collections");
		
		System.out.println("URL ::" +url);
		System.out.println("DB Name ::" +dbName);
		System.out.println("Collection Name ::" +cName);
		System.out.println("******************************************************");

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

	public List<String> getPartyRepresentDetailsCollectionData(String url, String dbName, String cName, String documentId) {

		System.out.println("Started connection to MongoDB to fetch the collections");
		
		System.out.println("URL ::" +url);
		System.out.println("DB Name ::" +dbName);
		System.out.println("Collection Name ::" +cName);
		System.out.println("******************************************************");

		ArrayList<String> mess = new ArrayList<>();
		try (MongoClient mongoClient = MongoClients.create(url)) {
			MongoDatabase database = mongoClient.getDatabase(dbName);
			MongoCollection<Document> list = database.getCollection(cName);
			
			 Bson query = new Document("documentId",new Document("$eq",documentId));

			for (Document doc : list.find(query)) {
				doc.remove("_id");
				doc.remove("$numberLong");
				if(doc.getDate("updatedAt")!=null)
					doc.replace("updatedAt", IGRSDateUtil.INSTANCE.convertToDateStringFromDate(doc.getDate("updatedAt")));
				
				if(doc.getDate("createdAt")!=null)
					doc.replace("createdAt", IGRSDateUtil.INSTANCE.convertToDateStringFromDate(doc.getDate("createdAt")));
				
				mess.add(doc.toJson());
			}
		}
		return mess;

	}
	
	public List<String> getUsersCollectionData(String url, String dbName, String cName, String userId) {

		System.out.println("Started connection to MongoDB to fetch the collections");
		
		System.out.println("URL ::" +url);
		System.out.println("DB Name ::" +dbName);
		System.out.println("Collection Name ::" +cName);
		System.out.println("******************************************************");

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
	public List<String> getPropertyDetailsCollectionData(String url, String dbName, String cName, String applicationId) {

		System.out.println("Started connection to MongoDB to fetch the collections");
		System.out.println("URL ::" +url);
		System.out.println("DB Name ::" +dbName);
		System.out.println("Collection Name ::" +cName);
		System.out.println("******************************************************");

		ArrayList<String> mess = new ArrayList<>();
		try (MongoClient mongoClient = MongoClients.create(url)) {
			MongoDatabase database = mongoClient.getDatabase(dbName);
			MongoCollection<Document> list = database.getCollection(cName);
			
			 Bson query = new Document("applicationId",new Document("$eq",applicationId));
			for (Document doc : list.find(query)) {
				doc.remove("_id");
				doc.remove("$numberLong");
				if(doc.getDate("updatedAt")!=null)
					doc.replace("updatedAt", IGRSDateUtil.INSTANCE.convertToDateStringFromDate(doc.getDate("updatedAt")));
				
				if(doc.getDate("createdAt")!=null)
					doc.replace("createdAt", IGRSDateUtil.INSTANCE.convertToDateStringFromDate(doc.getDate("createdAt")));
				
				mess.add(doc.toJson());
			}
		}
		return mess;
	}
	
	public List<String> getPropertyStructureDetailsCollectionData(String url, String dbName, String cName, String propertyID) {

		System.out.println("Started connection to MongoDB to fetch the collections");
		System.out.println("URL ::" +url);
		System.out.println("DB Name ::" +dbName);
		System.out.println("Collection Name ::" +cName);
		System.out.println("******************************************************");

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
	public List<String> getPartyDetailsCollectionDataFromID(String url, String dbName, String cName, String id) {

		   System.out.println("Started connection to MongoDB to fetch the collections");

		   System.out.println("URL ::" +url);
		   System.out.println("DB Name ::" +dbName);
		   System.out.println("Collection Name ::" +cName);
		   System.out.println("******************************************************");

		   ArrayList<String> mess = new ArrayList<>();
		   try (MongoClient mongoClient = MongoClients.create(url)) {
		      MongoDatabase database = mongoClient.getDatabase(dbName);
		      MongoCollection<Document> list = database.getCollection(cName);

		     // Bson query = new Document("_id",new Document("$eq",id));
		     

		      Bson query = new Document("_id",new Document("$eq",new ObjectId(id)));

		      for (Document doc : list.find(query)) {
		         doc.remove("_id");
		         doc.remove("$numberLong");
		         mess.add(doc.toJson());
		        
		      }
		   }
		   return mess;
		}

}
