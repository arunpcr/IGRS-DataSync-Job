package in.gov.ap.igrs.datasync.app.utility;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import in.gov.ap.igrs.datasync.app.mongo.MongoDBOperation;

public enum CMongoToOracle {

	INSTACE;
	
	private final Log logger = LogFactory.getLog(this.getClass());

	/*public List<String> fromMongoDocumentDetails(String url, String dbName, String cName)
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
			

			
		return mess;

	}}*/
	public List<String> fromMongoDocumentDetails(String id, String url, String dbName, String cName)
			throws InterruptedException, SQLException {
		
		logger.info(" IIIIIIIIIIIDDDDDDDDDDDDDDStarted connection to MongoDB to fetch the collections");

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
				logger.info("in for-loop document matching with documentId "+doc.toJson());
				doc.remove("_id");
				doc.remove("$numberLong");
				if(doc.getDate("stampPurchaseDate")!=null)
					doc.replace("stampPurchaseDate", IGRSDateUtil.INSTANCE.convertToDateStringFromDate(doc.getDate("stampPurchaseDate")));
				
				if(doc.getDate("updatedAt")!=null)
					doc.replace("updatedAt", IGRSDateUtil.INSTANCE.convertToDateStringFromDate(doc.getDate("updatedAt")));
				
				if(doc.getDate("executionDate")!=null)
					doc.replace("executionDate", IGRSDateUtil.INSTANCE.convertToDateStringFromDate(doc.getDate("executionDate")));
				
				if(doc.getDate("createdAt")!=null)
					doc.replace("createdAt", IGRSDateUtil.INSTANCE.convertToDateStringFromDate(doc.getDate("createdAt")));
				mess.add(doc.toJson());
				//logger.info("document after filtering based on 'status' 'submitted'"+mess);

			}

		}
	

		return mess;

	}
	
}
