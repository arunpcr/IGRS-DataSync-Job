

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;

import in.gov.ap.igrs.datasync.app.data.DocumentDetails;
import in.gov.ap.igrs.datasync.app.data.PartyDetails;
import in.gov.ap.igrs.datasync.app.data.PartyRepresentDetails;
import in.gov.ap.igrs.datasync.app.data.PropertyDetails;
import in.gov.ap.igrs.datasync.app.data.PropertyStructureDetails;
import in.gov.ap.igrs.datasync.app.data.UserDoc;
import in.gov.ap.igrs.datasync.app.mongo.MongoDBOperation;
import in.gov.ap.igrs.datasync.app.oracle.ODocumentDetails;
import in.gov.ap.igrs.datasync.app.oracle.OPartyDetails;
import in.gov.ap.igrs.datasync.app.oracle.OPartyRepresentDetails;
import in.gov.ap.igrs.datasync.app.oracle.OPropertyDetails;
import in.gov.ap.igrs.datasync.app.oracle.OPropertyStructureDetails;
import in.gov.ap.igrs.datasync.app.oracle.OracleDBUtil;
import in.gov.ap.igrs.datasync.app.service.IGRSDataSyncService;
import in.gov.ap.igrs.datasync.app.utility.CMongoToOracle;

@Service
public class IGRSDataSyncServiceImpl implements IGRSDataSyncService {

	private final Log logger = LogFactory.getLog(this.getClass());
	
	@Value("${mongoDB.url}")
	private String mongoDBUrl;

	@Value("${mongoDB.dbName}")
	private String dbName;

	@Value("${mongoDB.collectionName}")
	private String collectionName;
	
	//@Value("${oracle.jdbc.Url}")
	//private String oraUrl;
	
	@Override
	public Map<String, String> dataSyncFromMongoToOracle(String appId) 
	{ 
		//logger.info(" oraUrl ::::"+oraUrl);
		//OracleDBUtil.setOraUrl(oraUrl);	
			
		logger.info("Inside of dataSyncFromMongoToOracle :::: ");
		logger.info("with new jar deployment****vijaya");
		
		//logger.info("Oracle jdbc url picked is:"+oracleurl);
		GsonBuilder gsonBuilder = new GsonBuilder();
		Map<String, String> responseObject = new HashMap<String, String>();
		List<String> documents;
		try {
			documents = CMongoToOracle.INSTACE.fromMongoDocumentDetails(appId, mongoDBUrl, dbName, collectionName);
			List<DocumentDetails> details = new ArrayList<DocumentDetails>();

			for (String mess : documents) {
				Gson gson = gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
				details.add(gson.fromJson(mess, DocumentDetails.class));
			}

			// for (DocumentDetails dDetails : details) {

			// String documentId = dDetails.getDocumentId();
			if(details.isEmpty())
			{
				responseObject.put("status", "Failer");
				responseObject.put("message", "Data not found for provided document number.");
				return responseObject;
			}
			DocumentDetails ddObj = details.get(0);
			String documentId = ddObj.getDocumentId();
			String status = ddObj.getStatus();
			boolean deleted = ODocumentDetails.singleDeletePresentation(documentId);
			logger.info("value of deleted****" + deleted);
			logger.info("status of the document before syncing****" + status);
			status = "SYNCED";

			if (deleted == false)
				logger.info("Presentation record with ID****" + documentId + "***got deleted successsfully");

			boolean preDeleted = ODocumentDetails.singleDeletePreRegistrationCCA(documentId);

			if (preDeleted == false)
				logger.info("PreRegistrationCCA record with ID****" + documentId + "***got deleted successsfully");

			boolean inserted = ODocumentDetails.singleInsertPresentation(ddObj);

			if (inserted == false) {
				logger.info("Presentation record with ID****" + documentId + "***got inserted successsfully");
			} else if (inserted == true) {
				logger.info("Presentation record with ID****" + documentId + "***could not be inserted");
				status = "FAILED";
			}

			boolean preInserted = ODocumentDetails.singleInsertPreRegistrationCCA(ddObj);
			if (preInserted == false) {
				logger.info("PreRegistationCCA record with ID****" + documentId + "***got inserted successsfully");
			} else if (preInserted == true) {
				logger.info("PreRegistationCCA record with ID****" + documentId + "***could not be inserted");
				status = "FAILED";
			}
			String loginId = null;
			String loginName = null;

			List<String> userDocuments = ODocumentDetails.updateUserDetails(ddObj, mongoDBUrl, dbName);
			List<UserDoc> UserDetails = new ArrayList<UserDoc>();

			for (String userMess : userDocuments) {
				Gson gson = gsonBuilder.create();
				UserDetails.add(gson.fromJson(userMess, UserDoc.class));
			}

			for (UserDoc uds : UserDetails) {
				logger.info("Fetching details of users collection by means of UserDoc object");
				loginId = uds.getLoginId();
				loginName = uds.getLoginName();
			}

			Connection con = OracleDBUtil.getConnection();
			logger.info("in updateOperatorNameAndID details of ODocumentDetails");

			try {
				// Update Pre_Registration_CCA table with values from users collection
				String query1 = "UPDATE PRE_REGISTRATION_CCA SET OPERATOR_NAME=?, OPERATOR_ID=? WHERE ID=?";

				PreparedStatement pstmt = con.prepareStatement(query1);
				con.setAutoCommit(false);
				logger.info("in Updation of Preregistration cca table");
				pstmt.setString(1, loginName);
				pstmt.setString(2, loginId);
				pstmt.setString(3, documentId);
				pstmt.execute();

				con.commit();

				logger.info("PreRegistrationCCA table with" + documentId + "got updated with operatorname and id");

			}

			catch (Exception e) {
				logger.info(e.getMessage());
			}
			con.close();

			logger.info("****************PARTYDETAILS*******************");

			List<String> PartyDetails = MongoDBOperation.INSTANCE.getPartyDetailsCollectionData(mongoDBUrl, dbName, "party_details",
					documentId);
			logger.info("Size:" + PartyDetails.size());
			int temp = 0;
			logger.info(PartyDetails.toString());
			//List<PartyDetails> partyDetails = new ArrayList<PartyDetails>();
			boolean deletePartyDetails = OPartyDetails.singlePartyDetailsDelete(documentId);
			for (String mess : PartyDetails) {
				logger.info("************************Started**************************");
				temp++;
				logger.info("AAAAAAAAAAAAAAAAAAAAAAAAAAAA" + temp);
				logger.info("PartyDetails message:" + mess.toString());

				// Gson gson = new
				// GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").create();
				Gson gson = gsonBuilder.create();
				PartyDetails partyDetails1 = gson.fromJson(mess, PartyDetails.class);
				// boolean deletePartyDetails =
				// OPartyDetails.singlePartyDetailsDelete(documentId);

				if (deletePartyDetails == false)

					logger.info("ExecutantClaimant record with ID****" + documentId + "***got deleted successsfully");

				boolean insertPartyDetails = OPartyDetails.singlePartyDetailsInsert(partyDetails1);

				if (insertPartyDetails == false)

				{
					logger.info("ExecutantClaimant record with ID****" + documentId + "***got inserted successsfully");
				} else if (insertPartyDetails == true) {
					logger.info("ExecutantClaimant record with ID****" + documentId
							+ "***could not be inserted successsfully");
					status = "FAILED";
				}
				// partyDetails.add(gson.fromJson(mess, PartyDetails.class));

				boolean updatePartyDetails = OPartyDetails.updateDocumentDetails(partyDetails1);

				if (updatePartyDetails == false)
					logger.info("Presentation record with ID****" + documentId + "***got updated successsfully");

				logger.info("************************Completed**************************");

			}

			logger.info("****************PARTYREPRESENTDETAILS*******************");
			List<String> PartyRepresentDetails = MongoDBOperation.INSTANCE.getPartyRepresentDetailsCollectionData(mongoDBUrl, dbName,
					"party_represent_detail", documentId);
			logger.info("PartyRepresentDetails size:*********" + PartyRepresentDetails.size());
			//List<PartyRepresentDetails> partyRepresentDetails = new ArrayList<PartyRepresentDetails>();
			boolean deletePartyRepresentDetails = OPartyRepresentDetails.singleDeletePartyRepresentDetails(documentId);

			if (deletePartyRepresentDetails == false)

				logger.info("FirmsRepresented record with ID****" + documentId + "***got deleted successsfully");

			for (String mess : PartyRepresentDetails) {

				System.out.println(mess);
				Gson gson = gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
				PartyRepresentDetails partyRepresentDetails1 = gson.fromJson(mess, PartyRepresentDetails.class);

				// boolean deletePartyRepresentDetails =
				// OPartyRepresentDetails.singleDeletePartyRepresentDetails(documentId);

				boolean insertPartyRepresentDetails = OPartyRepresentDetails
						.singleInsertPartyRepresentDetails(partyRepresentDetails1);

				if (insertPartyRepresentDetails == false)

					logger.info("FirmsRepresented record with ID****" + documentId + "***got inserted successsfully");
				else if (insertPartyRepresentDetails == true) {
					logger.info("FirmsRepresented record with ID****" + documentId
							+ "***could not be inserted successsfully");
					status = "FAILED";
				}
				// partyRepresentDetails.add(gson.fromJson(mess, PartyRepresentDetails.class));
				
				boolean updatePartyRepresentDetails = OPartyRepresentDetails.updateDocumentDetails(partyRepresentDetails1,mongoDBUrl, dbName);

				if (updatePartyRepresentDetails == false)
				   logger.info("Presentation record with ID****" + documentId + "***got updated successsfully");
				else
					logger.info("Presentation record with ID****" + documentId + "***could not be updated successsfully");
				logger.info("************************Completed**************************");

			}
			// logger.info("****************USERDETAILS*******************");
			logger.info("****************PROPERTYDETAILS*******************");
			List<String> PropertyDetails = MongoDBOperation.INSTANCE.getPropertyDetailsCollectionData(mongoDBUrl, dbName,
					"property_details", documentId);
			logger.info("PropertyDetails size***********" + PropertyDetails.size());
			List<PropertyDetails> propertyDetails = new ArrayList<PropertyDetails>();
			boolean deletePropertyDetails = OPropertyDetails.singleDeletePropertyDetails(documentId);
			if (deletePropertyDetails == false)

				logger.info("ScheduleEntry record with ID****" + documentId + "***got deleted successsfully");
			boolean singleDeleteStructureDetails = OPropertyDetails.singleDeleteStructureDetails(documentId);
			if (singleDeleteStructureDetails == false)

				logger.info("StructureDetails record with ID****" + documentId + "***got deleted successsfully");

			boolean deleteAdangalDetails =OPropertyDetails.singleDeleteAdangalDetails(documentId);

			if (deleteAdangalDetails == false)

				logger.info("AdangalDetails record with ID****" + documentId + "***got deleted successsfully");


			for (String mess : PropertyDetails) {
				// Gson gson = new
				// GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").create();
				Gson gson = gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
				PropertyDetails propertyDetails1 = gson.fromJson(mess, PropertyDetails.class);
				propertyDetails.add(propertyDetails1);
				//propertyDetails1.getExtentList()

				// boolean deletePropertyDetails =
				// OPropertyDetails.singleDeletePropertyDetails(propertyDetails1);
				boolean insertAdangalDetails = OPropertyDetails.singleInsertAdangalDetails(propertyDetails1);

				if (insertAdangalDetails == false)

					logger.info("AdangalDetails record with ID****" + documentId + "***got inserted successsfully");
				else if (insertAdangalDetails == true)

				{
					logger.info(
							"AdangalDetails record with ID****" + documentId + "***could not be inserted successsfully");
					status = "FAILED";
				}

				boolean insertPropertyDetails = OPropertyDetails.singleInsertScheduleEntry(propertyDetails1);

				if (insertPropertyDetails == false)

					logger.info("ScheduleEntry record with ID****" + documentId + "***got inserted successsfully");
				else if (insertPropertyDetails == true)

				{
					logger.info(
							"ScheduleEntry record with ID****" + documentId + "***could not be inserted successsfully");
					status = "FAILED";
				}
				boolean singleInsertStructureDetails = OPropertyDetails.singleInsertStructureDetails(propertyDetails1);
				if (singleInsertStructureDetails == false) {
					logger.info("Structuredetails record with ID******" + documentId + "***got inserted successsfully");
				} else if (singleInsertStructureDetails == true) {
					logger.info("Structuredetails record with ID******" + documentId
							+ "***could not inserted successsfully");
					status = "FAILED";
				}

				logger.info("ddsrocode::::::::"+ddObj.getSroCode());
				logger.info("propertyDetails1.getSroCode()::::::::"+propertyDetails1.getSroCode());
				//if(propertyDetails1.getSroCode()==dDetails.getSroCode())
				if(ddObj.getSroCode()==propertyDetails1.getSroCode())
				{
					logger.info("they are equal");
					boolean updatePropertyDetailsY = OPropertyDetails.updateScheduleEntryY(propertyDetails1);
					boolean PreRegistrationCCAY = OPropertyDetails.updatePreRegistrationCCAY(propertyDetails1);
					logger.info("updatePropertyDetailsY:&&&&&&&&&"+updatePropertyDetailsY);
					//boolean updateDocumentDetailsY = ODocumentDetails. 
				}
				//else if(propertyDetails1.getSroCode()!=dDetails.getSroCode())
				else if(ddObj.getSroCode()!=propertyDetails1.getSroCode())
				{logger.info("not they are equal");
				boolean updatePropertyDetailsN = OPropertyDetails.updateScheduleEntryN(propertyDetails1);
				boolean updatePreRegistrationCCAN = OPropertyDetails.updatePreRegistrationCCAN(propertyDetails1);

				logger.info("insertPropertyDetailsN:&&&&&&&&&"+updatePropertyDetailsN);

				}


				

			}
			logger.info("*************PropertyStructureDetails*****************");
			for (int i = 0; i < propertyDetails.size(); i++) {
				String propertyId2 = propertyDetails.get(i).getPropertyId();
				//String applicationId = propertyDetails.get(i).getApplicationId();

				logger.info("************PropertyId****" + propertyId2 + "for record:******" + 1);
				List<String> PropertyStructureDetails = MongoDBOperation.INSTANCE.getPropertyStructureDetailsCollectionData(mongoDBUrl,
						dbName, "property_structure_details", propertyId2);
				logger.info("PropertyStructureDetails size***************" + PropertyStructureDetails.size() + "*****");
				// boolean deletePropertyScheduleEntryDetails =
				// OPropertyStructureDetails.singleDeleteScheduleEntry(propertyId2);
				boolean deletePropertyScheduleEntryDetails = OPropertyStructureDetails
						.singleDeleteScheduleEntry(propertyId2.substring(0, 15));
				if (deletePropertyScheduleEntryDetails == false)

					logger.info("ScheduleEntry record with ID" + propertyId2.substring(0, 15)
					+ "got deleted successsfully");

				// boolean deletePropertyStructureDetails =
				// OPropertyStructureDetails.singleDeleteStructureDetails(propertyId2);
				boolean deletePropertyStructureDetails = OPropertyStructureDetails
						.singleDeleteStructureDetails(propertyId2.substring(0, 15));
				if (deletePropertyStructureDetails == false)

					logger.info("structure_details record with ID****" + propertyId2.substring(0, 15)
					+ "***got deleted successsfully");

				for (String mess : PropertyStructureDetails) {
					logger.info("In message" + mess.toString());

					// Gson gson = new
					// GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").create();
					Gson gson = gsonBuilder.create();
					PropertyStructureDetails propertyStructureDetails = gson.fromJson(mess,
							PropertyStructureDetails.class);

					String propertyId = propertyStructureDetails.getPropertyId().substring(0, 15);
					int seqNo2=propertyDetails.get(i).getSeqNumber();

					logger.info("propertyStructureDetails ::: "+(propertyStructureDetails!=null?propertyStructureDetails:""));
					logger.info("PropertyID fromobject" + propertyId);

					// boolean deletePropertyScheduleEntryDetails =
					// OPropertyStructureDetails.singleDeleteScheduleEntry(propertyId);

					boolean insertPropertyScheduleEntryDetails =  OPropertyStructureDetails.singleUpdateScheduleEntry(propertyStructureDetails, seqNo2, propertyDetails.get(i).getApplicationId());
					if (insertPropertyScheduleEntryDetails == false)
						{logger.info("scheduleentry record with "+propertyDetails.get(i).getApplicationId()+"got updated successfully");
												

						//logger.info("ScheduleEntry record with ID" + propertyId2.substring(0, 15)
						//+ "got inserted successsfully")
}
					else if (insertPropertyScheduleEntryDetails == true)

					{
						logger.info("scheduleentry record with "+propertyDetails.get(i).getApplicationId()+"could not updated successfully");
						//logger.info("ScheduleEntry record with ID" + propertyId2.substring(0, 15)
						//+ "could not be inserted successsfully");
						status = "FAILED";
					}

					// boolean deletePropertyStructureDetails =
					// OPropertyStructureDetails.singleDeleteStructureDetails(propertyStructureDetails);

					boolean insertPropertyStructureDetails = OPropertyStructureDetails.singleUpdateStructureDetails(propertyStructureDetails, seqNo2, propertyDetails.get(i).getApplicationId());


					if (insertPropertyStructureDetails == false)

						logger.info("structure_details record with ID****" + propertyId2.substring(0,15)	+ "***got updated successsfully"+propertyDetails.get(i).getApplicationId());
					else 
						if (insertPropertyStructureDetails == true)

						{	logger.info("structure_details record with ID****" + propertyId2.substring(0,15)	+ "***could not be updated successsfully"+propertyDetails.get(i).getApplicationId());
						status="FAILED";
						}
			
					/*
					 * boolean
					 * updateScheduleEntry=OPropertyStructureDetails.singleUpdateScheduleEntry(
					 * propertyStructureDetails, applicationId); if(updateScheduleEntry==false) {
					 * logger.
					 * info("Successfully updated PropertyStructureDetails-ScheduleEntry table with ID"
					 * +applicationId); } boolean
					 * updateStructureDetails=OPropertyStructureDetails.singleUpdateStructureDetails
					 * (propertyStructureDetails, applicationId); if(updateStructureDetails==false)
					 * { logger.
					 * info("Successfully updated PropertyStructureDetails-StructureDetails table with ID"
					 * +applicationId); }
					 */

				}

			} // end for for-loop
			logger.info("Value of status is*******************" + status);
			Bson query = new Document("documentId", new Document("$eq", documentId));
			Bson update = new Document("$set", new Document("status", status));
			MongoClient mongoClient = MongoClients.create(mongoDBUrl);


			// use test as the database. Use your database here.
			// MongoDatabase db = mongoClient.getDatabase("PDE");
			MongoDatabase db = mongoClient.getDatabase(dbName);
			MongoCollection<Document> coll = db.getCollection("document_details");
			coll.findOneAndUpdate(query, update);
			logger.info("Document with *********" + documentId + "got updated successfully with sync status" + status);
			responseObject.put("status", "Success");
			// }
		} catch (Exception e) {
			logger.error("Error ::: ", e);
			responseObject.put("status", "Failer");
			responseObject.put("message", e.getMessage());
		}
		logger.info("responseObject :::: "+responseObject);
		logger.info("End of dataSyncFromMongoToOracle :::: ");
		return responseObject;
	}


	@Override
	public Map<String, String> dataSyncFromMongoToOracleProd(String appId) 
	{
		logger.info("Inside of dataSyncFromMongoToOracleProd :::: ");
		GsonBuilder gsonBuilder = new GsonBuilder();
		Map<String, String> responseObject = new HashMap<String, String>();
		List<String> documents;
		try {
			documents = CMongoToOracle.INSTACE.fromMongoDocumentDetails(appId, mongoDBUrl, dbName, collectionName);
			List<DocumentDetails> details = new ArrayList<DocumentDetails>();

			for (String mess : documents) {
				Gson gson = gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
				details.add(gson.fromJson(mess, DocumentDetails.class));
			}

			// for (DocumentDetails dDetails : details) {

			// String documentId = dDetails.getDocumentId();
			if(details.isEmpty())
			{
				responseObject.put("status", "Failer");
				responseObject.put("message", "Data not found for provided document number.");
				return responseObject;
			}
			DocumentDetails ddObj = details.get(0);
			String documentId = ddObj.getDocumentId();
			String status = ddObj.getStatus();
			boolean deleted = ODocumentDetails.singleDeletePresentation(documentId);
			logger.info("value of deleted****" + deleted);
			logger.info("status of the document before syncing****" + status);
			status = "SYNCED";

			if (deleted == false)
				logger.info("Presentation record with ID****" + documentId + "***got deleted successsfully");

			boolean preDeleted = ODocumentDetails.singleDeletePreRegistrationCCA(documentId);

			if (preDeleted == false)
				logger.info("PreRegistrationCCA record with ID****" + documentId + "***got deleted successsfully");

			boolean inserted = ODocumentDetails.singleInsertPresentation(ddObj);

			if (inserted == false) {
				logger.info("Presentation record with ID****" + documentId + "***got inserted successsfully");
			} else if (inserted == true) {
				logger.info("Presentation record with ID****" + documentId + "***could not be inserted");
				status = "FAILED";
			}

			boolean preInserted = ODocumentDetails.singleInsertPreRegistrationCCA(ddObj);
			if (preInserted == false) {
				logger.info("PreRegistationCCA record with ID****" + documentId + "***got inserted successsfully");
			} else if (preInserted == true) {
				logger.info("PreRegistationCCA record with ID****" + documentId + "***could not be inserted");
				status = "FAILED";
			}
			String loginId = null;
			String loginName = null;

			List<String> userDocuments = ODocumentDetails.updateUserDetails(ddObj, mongoDBUrl, dbName);
			List<UserDoc> UserDetails = new ArrayList<UserDoc>();

			for (String userMess : userDocuments) {
				Gson gson = gsonBuilder.create();
				UserDetails.add(gson.fromJson(userMess, UserDoc.class));
			}

			for (UserDoc uds : UserDetails) {
				logger.info("Fetching details of users collection by means of UserDoc object");
				loginId = uds.getLoginId();
				loginName = uds.getLoginName();
			}

			Connection con = OracleDBUtil.getConnection();
			logger.info("in updateOperatorNameAndID details of ODocumentDetails");

			try {
				// Update Pre_Registration_CCA table with values from users collection
				String query1 = "UPDATE PRE_REGISTRATION_CCA SET OPERATOR_NAME=?, OPERATOR_ID=? WHERE ID=?";

				PreparedStatement pstmt = con.prepareStatement(query1);
				con.setAutoCommit(false);
				logger.info("in Updation of Preregistration cca table");
				pstmt.setString(1, loginName);
				pstmt.setString(2, loginId);
				pstmt.setString(3, documentId);
				pstmt.execute();

				con.commit();

				logger.info("PreRegistrationCCA table with" + documentId + "got updated with operatorname and id");

			}

			catch (Exception e) {
				logger.info(e.getMessage());
			}
			con.close();

			logger.info("****************PARTYDETAILS*******************");

			List<String> PartyDetails = MongoDBOperation.INSTANCE.getPartyDetailsCollectionData(mongoDBUrl, dbName, "party_details",
					documentId);
			logger.info("Size:" + PartyDetails.size());
			int temp = 0;
			logger.info(PartyDetails.toString());
			//List<PartyDetails> partyDetails = new ArrayList<PartyDetails>();
			boolean deletePartyDetails = OPartyDetails.singlePartyDetailsDelete(documentId);
			for (String mess : PartyDetails) {
				logger.info("************************Started**************************");
				temp++;
				logger.info("AAAAAAAAAAAAAAAAAAAAAAAAAAAA" + temp);
				logger.info("PartyDetails message:" + mess.toString());

				// Gson gson = new
				// GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").create();
				Gson gson = gsonBuilder.create();
				PartyDetails partyDetails1 = gson.fromJson(mess, PartyDetails.class);
				// boolean deletePartyDetails =
				// OPartyDetails.singlePartyDetailsDelete(documentId);

				if (deletePartyDetails == false)

					logger.info("ExecutantClaimant record with ID****" + documentId + "***got deleted successsfully");

				boolean insertPartyDetails = OPartyDetails.singlePartyDetailsInsert(partyDetails1);

				if (insertPartyDetails == false)

				{
					logger.info("ExecutantClaimant record with ID****" + documentId + "***got inserted successsfully");
				} else if (insertPartyDetails == true) {
					logger.info("ExecutantClaimant record with ID****" + documentId
							+ "***could not be inserted successsfully");
					status = "FAILED";
				}
				// partyDetails.add(gson.fromJson(mess, PartyDetails.class));

				boolean updatePartyDetails = OPartyDetails.updateDocumentDetails(partyDetails1);

				if (updatePartyDetails == false)
					logger.info("Presentation record with ID****" + documentId + "***got updated successsfully");

				logger.info("************************Completed**************************");

			}

			logger.info("****************PARTYREPRESENTDETAILS*******************");
			List<String> PartyRepresentDetails = MongoDBOperation.INSTANCE.getPartyRepresentDetailsCollectionData(mongoDBUrl, dbName,
					"party_represent_detail", documentId);
			logger.info("PartyRepresentDetails size:*********" + PartyRepresentDetails.size());
			//List<PartyRepresentDetails> partyRepresentDetails = new ArrayList<PartyRepresentDetails>();
			boolean deletePartyRepresentDetails = OPartyRepresentDetails.singleDeletePartyRepresentDetails(documentId);

			if (deletePartyRepresentDetails == false)

				logger.info("FirmsRepresented record with ID****" + documentId + "***got deleted successsfully");

			for (String mess : PartyRepresentDetails) {

				System.out.println(mess);
				Gson gson = gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
				PartyRepresentDetails partyRepresentDetails1 = gson.fromJson(mess, PartyRepresentDetails.class);

				// boolean deletePartyRepresentDetails =
				// OPartyRepresentDetails.singleDeletePartyRepresentDetails(documentId);

				boolean insertPartyRepresentDetails = OPartyRepresentDetails
						.singleInsertPartyRepresentDetails(partyRepresentDetails1);

				if (insertPartyRepresentDetails == false)

					logger.info("FirmsRepresented record with ID****" + documentId + "***got inserted successsfully");
				else if (insertPartyRepresentDetails == true) {
					logger.info("FirmsRepresented record with ID****" + documentId
							+ "***could not be inserted successsfully");
					status = "FAILED";
				}
				// partyRepresentDetails.add(gson.fromJson(mess, PartyRepresentDetails.class));

			}
			// logger.info("****************USERDETAILS*******************");
			logger.info("****************PROPERTYDETAILS*******************");
			List<String> PropertyDetails = MongoDBOperation.INSTANCE.getPropertyDetailsCollectionData(mongoDBUrl, dbName,
					"property_details", documentId);
			logger.info("PropertyDetails size***********" + PropertyDetails.size());
			List<PropertyDetails> propertyDetails = new ArrayList<PropertyDetails>();
			boolean deletePropertyDetails = OPropertyDetails.singleDeletePropertyDetails(documentId);
			if (deletePropertyDetails == false)

				logger.info("ScheduleEntry record with ID****" + documentId + "***got deleted successsfully");
			boolean singleDeleteStructureDetails = OPropertyDetails.singleDeleteStructureDetails(documentId);
			if (singleDeleteStructureDetails == false)

				logger.info("StructureDetails record with ID****" + documentId + "***got deleted successsfully");

			for (String mess : PropertyDetails) {
				// Gson gson = new
				// GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").create();
				Gson gson = gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
				PropertyDetails propertyDetails1 = gson.fromJson(mess, PropertyDetails.class);
				propertyDetails.add(propertyDetails1);

				// boolean deletePropertyDetails =
				// OPropertyDetails.singleDeletePropertyDetails(propertyDetails1);

				boolean insertPropertyDetails = OPropertyDetails.singleInsertScheduleEntry(propertyDetails1);

				if (insertPropertyDetails == false)

					logger.info("ScheduleEntry record with ID****" + documentId + "***got inserted successsfully");
				else if (insertPropertyDetails == true)

				{
					logger.info(
							"ScheduleEntry record with ID****" + documentId + "***could not be inserted successsfully");
					status = "FAILED";
				}
				boolean singleInsertStructureDetails = OPropertyDetails.singleInsertStructureDetails(propertyDetails1);
				if (singleInsertStructureDetails == false) {
					logger.info("Structuredetails record with ID******" + documentId + "***got inserted successsfully");
				} else if (singleInsertStructureDetails == true) {
					logger.info("Structuredetails record with ID******" + documentId
							+ "***could not inserted successsfully");
					status = "FAILED";
				}

				logger.info("ddsrocode::::::::"+ddObj.getSroCode());
				logger.info("propertyDetails1.getSroCode()::::::::"+propertyDetails1.getSroCode());
				//if(propertyDetails1.getSroCode()==dDetails.getSroCode())
				if(ddObj.getSroCode()==propertyDetails1.getSroCode())
				{
					logger.info("they are equal");
					boolean updatePropertyDetailsY = OPropertyDetails.updateScheduleEntryY(propertyDetails1);
					boolean PreRegistrationCCAY = OPropertyDetails.updatePreRegistrationCCAY(propertyDetails1);
					logger.info("updatePropertyDetailsY:&&&&&&&&&"+updatePropertyDetailsY);
					//boolean updateDocumentDetailsY = ODocumentDetails. 
				}
				//else if(propertyDetails1.getSroCode()!=dDetails.getSroCode())
				else if(ddObj.getSroCode()!=propertyDetails1.getSroCode())
				{logger.info("not they are equal");
				boolean updatePropertyDetailsN = OPropertyDetails.updateScheduleEntryN(propertyDetails1);
				boolean updatePreRegistrationCCAN = OPropertyDetails.updatePreRegistrationCCAN(propertyDetails1);

				logger.info("insertPropertyDetailsN:&&&&&&&&&"+updatePropertyDetailsN);

				}


			}
			logger.info("*************PropertyStructureDetails*****************");
			for (int i = 0; i < propertyDetails.size(); i++) {
				String propertyId2 = propertyDetails.get(i).getPropertyId();
				//String applicationId = propertyDetails.get(i).getApplicationId();

				logger.info("************PropertyId****" + propertyId2 + "for record:******" + 1);
				List<String> PropertyStructureDetails = MongoDBOperation.INSTANCE.getPropertyStructureDetailsCollectionData(mongoDBUrl,
						dbName, "property_structure_details", propertyId2);
				logger.info("PropertyStructureDetails size***************" + PropertyStructureDetails.size() + "*****");
				// boolean deletePropertyScheduleEntryDetails =
				// OPropertyStructureDetails.singleDeleteScheduleEntry(propertyId2);
				boolean deletePropertyScheduleEntryDetails = OPropertyStructureDetails
						.singleDeleteScheduleEntry(propertyId2.substring(0, 15));
				if (deletePropertyScheduleEntryDetails == false)

					logger.info("ScheduleEntry record with ID" + propertyId2.substring(0, 15)
					+ "got deleted successsfully");

				// boolean deletePropertyStructureDetails =
				// OPropertyStructureDetails.singleDeleteStructureDetails(propertyId2);
				boolean deletePropertyStructureDetails = OPropertyStructureDetails
						.singleDeleteStructureDetails(propertyId2.substring(0, 15));
				if (deletePropertyStructureDetails == false)

					logger.info("structure_details record with ID****" + propertyId2.substring(0, 15)
					+ "***got deleted successsfully");

				for (String mess : PropertyStructureDetails) {
					logger.info("In message" + mess.toString());

					// Gson gson = new
					// GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").create();
					Gson gson = gsonBuilder.create();
					PropertyStructureDetails propertyStructureDetails = gson.fromJson(mess,
							PropertyStructureDetails.class);

					String propertyId = propertyStructureDetails.getPropertyId().substring(0, 15);
					int seqNo2=propertyDetails.get(i).getSeqNumber();

					logger.info("propertyStructureDetails ::: "+(propertyStructureDetails!=null?propertyStructureDetails:""));
					logger.info("PropertyID fromobject" + propertyId);

					// boolean deletePropertyScheduleEntryDetails =
					// OPropertyStructureDetails.singleDeleteScheduleEntry(propertyId);

					boolean insertPropertyScheduleEntryDetails =  OPropertyStructureDetails.singleInsertScheduleEntry(propertyStructureDetails, seqNo2);
					if (insertPropertyScheduleEntryDetails == false)

						logger.info("ScheduleEntry record with ID" + propertyId2.substring(0, 15)
						+ "got inserted successsfully");
					else if (insertPropertyScheduleEntryDetails == true)

					{
						logger.info("ScheduleEntry record with ID" + propertyId2.substring(0, 15)
						+ "could not be inserted successsfully");
						status = "FAILED";
					}

					// boolean deletePropertyStructureDetails =
					// OPropertyStructureDetails.singleDeleteStructureDetails(propertyStructureDetails);

					boolean insertPropertyStructureDetails = OPropertyStructureDetails.singleInsertStructureDetails(propertyStructureDetails, seqNo2);


					if (insertPropertyStructureDetails == false)

						logger.info("structure_details record with ID****" + propertyId2.substring(0, 15)
						+ "***got inserted successsfully");
					else if (insertPropertyStructureDetails == true)

					{
						logger.info("structure_details record with ID****" + propertyId2.substring(0, 15)
						+ "***could not be inserted successsfully");
						status = "FAILED";
					}
					/*
					 * boolean
					 * updateScheduleEntry=OPropertyStructureDetails.singleUpdateScheduleEntry(
					 * propertyStructureDetails, applicationId); if(updateScheduleEntry==false) {
					 * logger.
					 * info("Successfully updated PropertyStructureDetails-ScheduleEntry table with ID"
					 * +applicationId); } boolean
					 * updateStructureDetails=OPropertyStructureDetails.singleUpdateStructureDetails
					 * (propertyStructureDetails, applicationId); if(updateStructureDetails==false)
					 * { logger.
					 * info("Successfully updated PropertyStructureDetails-StructureDetails table with ID"
					 * +applicationId); }
					 */

				}

			} // end for for-loop
			logger.info("Value of status is*******************" + status);
			Bson query = new Document("documentId", new Document("$eq", documentId));
			Bson update = new Document("$set", new Document("status", status));
			MongoClient mongoClient = MongoClients.create(mongoDBUrl);


			// use test as the database. Use your database here.
			// MongoDatabase db = mongoClient.getDatabase("PDE");
			MongoDatabase db = mongoClient.getDatabase(dbName);
			MongoCollection<Document> coll = db.getCollection("document_details");
			coll.findOneAndUpdate(query, update);
			logger.info("Document with *********" + documentId + "got updated successfully with sync status" + status);
			responseObject.put("status", "Success");
			// }
		} catch (Exception e) {
			logger.error("Error ::: ", e);
			responseObject.put("status", "Failer");
			responseObject.put("message", e.getMessage());
		}
		logger.info("responseObject :::: "+responseObject);
		logger.info("End of dataSyncFromMongoToOracleProduction :::: ");
		return responseObject;
	}

	public Map<String, String> dataSyncFromOracleToMongo(String appId) {
		String did=appId;
		logger.info("Inside of dataSyncFromOracleToMongo ::::with documentID"+appId);
		Connection con =null;
		PreparedStatement pstmt =null;
		ResultSet rs =null;
		GsonBuilder gsonBuilder = new GsonBuilder();
		Map<String, String> responseObject = new HashMap<String, String>();

		if (collectionName.equals("document_details")) {
			logger.info("DocumentDetails collection");
			try {
				con= OracleDBUtil.getConnection();
				con.setAutoCommit(false);
				String documentId1 = null;

				String sql = "SELECT * FROM PRE_REGISTRATION_CCA WHERE STATUS='P' AND ID=?";
				logger.info("after SQL statement&&&&&&&&&&");
				logger.info("SQL query::::::::::::::::" + sql);
				pstmt=con.prepareStatement(sql);
				pstmt.setString(1, did);
				logger.info("PreparedStatement:::::::::"+pstmt.toString());
				rs=pstmt.executeQuery();
				//logger.info("record details:" + rs.getString(1));
				if(rs==null)
					logger.info("result set is null");
				else
					logger.info("resultset is not null");
				// logger.info("Result set next value:::::"+rs.next());

				while(rs.next()) {
					documentId1 = rs.getString(1);
					logger.info("Document IId from Oracle*********" + documentId1);

					MongoClient mongoClient = MongoClients.create(mongoDBUrl);
					MongoDatabase database = mongoClient.getDatabase(dbName);
					MongoCollection<Document> coll = database.getCollection(collectionName);

					Bson query = new Document("", new Document("$eq", documentId1));
					Bson update = new Document("$set", new Document("status", "RECTIFY"));
					FindOneAndUpdateOptions options = new FindOneAndUpdateOptions();
					options.returnDocument(ReturnDocument.AFTER); 

					Document d1 = coll.findOneAndUpdate(query, update, options);

					//coll.updateMany(query, update, options);
					logger.info("Document with *********" + documentId1
							+ "got updated successfully with sync status RECTIFY");
					responseObject.put("status", "Success");

					logger.info("after update of status field***********" + d1.toJson());
					mongoClient.close();
					con.commit();
					responseObject.put("status", "Success");

				}


			}

			catch (SQLException e) {
				logger.error("Error ::: ", e);
				responseObject.put("status", "Failer");
				responseObject.put("message", e.getMessage());
			}

			finally {
				try {
					rs.close();
					pstmt.close();
					con.close();
				}
				catch(Exception e)
				{logger.info(e);}

			}

		}
		return responseObject;
	}

}
