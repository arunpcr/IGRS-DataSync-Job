package com.cr.main;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;

import com.ca.data.DocumentDetails;
import com.ca.data.PartyDetails;
import com.ca.data.PartyRepresentDetails;
import com.ca.data.PropertyDetails;
import com.ca.data.PropertyStructureDetails;
import com.ca.data.UserDoc;
import com.ca.mongo.MongoDBOperation;
import com.ca.oracle.ODocumentDetails;
import com.ca.oracle.OPartyDetails;
import com.ca.oracle.OPartyRepresentDetails;
import com.ca.oracle.OPropertyDetails;
import com.ca.oracle.OPropertyStructureDetails;
import com.ca.oracle.OracleDBUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class CMain {

	static Logger logger = Logger.getLogger(CMain.class.getName());

	public static void main(String[] args) throws InterruptedException, SQLException, IOException {

		logger.info("Applicatin started Mongo to Oracle");
		logger.info("7777&&&&&&&&&&&&&&&&&&&***************");

		BasicConfigurator.configure();
		logger.info("APPLication Started ::*********");
		logger.info("Started");

		String syncType = args[0];// MongotoOracle, mongotokafka, kafkatoOracle,OracletoMongo
		logger.info("SyncType*******" + syncType);

		String configFilePath = args[1];

		InputStream input = new FileInputStream(configFilePath);

		Properties prop = new Properties();
		prop.load(input);
		
		String[] collections = ((String) prop.get("collections")).split(",");
		String url = prop.getProperty("mongo.url");
		String dbName = prop.getProperty("mongo.database");
		logger.info("Collection name********" + collections);

		if (syncType.equalsIgnoreCase("MongotoOracle")) {
			logger.info("in mongo to oracle");

			for (String cName : collections) {

				if (cName.equals("document_details")) {

					List<String> documents;

					documents = CMongoToOracle.fromMongoDocumentDetails(url, dbName, cName);
					List<DocumentDetails> details = new ArrayList<DocumentDetails>();

					for (String mess : documents) {

						Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").create();
						details.add(gson.fromJson(mess, DocumentDetails.class));
						
						
					}

					for (DocumentDetails dDetails : details) {

						String documentId = dDetails.getDocumentId();
						String status = dDetails.getStatus();
						logger.info("Status from the collection******"+status);
						status="SYNCED";
						boolean deleted = ODocumentDetails.singleDeletePresentation(dDetails.getDocumentId());
						logger.info("value of deleted****" + deleted);

						if (deleted == false)
							logger.info(
									"Presentation record with ID****" + documentId + "***got deleted successsfully");

						boolean preDeleted = ODocumentDetails.singleDeletePreRegistrationCCA(dDetails.getDocumentId());

						if (preDeleted == false)
							logger.info("PreRegistrationCCA record with ID****" + documentId
									+ "***got deleted successsfully");

						boolean inserted = ODocumentDetails.singleInsertPresentation(dDetails);

						if (inserted == false)
							{logger.info("Presentation record with ID****" + documentId + "***got inserted successsfully");}
						else if (inserted == true)
						{
						logger.info("Presentation record with ID****" + documentId + "***could not be inserted");
						status="FAILED";						
						}

						boolean preInserted = ODocumentDetails.singleInsertPreRegistrationCCA(dDetails);

						if (preInserted == false)
						{logger.info("PreRegistationCCA record with ID****" + documentId+ "***got inserted successsfully");}
						else if (preInserted == true)
						{logger.info("PreRegistationCCA record with ID****" + documentId+ "***could not be inserted");
						status="FAILED";}
						
						String loginId =null;
						String loginName=null;
						
						List<String> userDocuments = ODocumentDetails.updateUserDetails(dDetails,url,dbName);
						
						List<UserDoc> UserDetails = new ArrayList<UserDoc>();

						for (String userMess : userDocuments) {

							Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").create();

							UserDetails.add(gson.fromJson(userMess, UserDoc.class));

						}

						for (UserDoc uds : UserDetails) {
logger.info("Fetching details of users collection by means of UserDoc object");
							loginId = uds.getLoginId();
							loginName = uds.getLoginName();
						}
						
						Connection con=OracleDBUtil.getConnection();
						logger.info("in updateOperatorNameAndID details of ODocumentDetails");
						
						try {

							String query1 = "UPDATE PRE_REGISTRATION_CCA SET OPERATOR_NAME=?, OPERATOR_ID=? WHERE ID=?";

									PreparedStatement pstmt = con.prepareStatement(query1);
									con.setAutoCommit(false);
									logger.info("in Updation of Preregistration cca table");
									pstmt.setString(1, loginName);
									pstmt.setString(2, loginId);
									pstmt.setString(3, documentId);
									pstmt.execute();
									
									con.commit();
									
													
									logger.info("PreRegistrationCCA table with"+documentId+"got updated with operatorname and id");
							
							}
							
						 catch (Exception e) {
								logger.info(e.getMessage());
						}
						con.close();

						logger.info("****************PARTYDETAILS*******************");

						List<String> PartyDetails = MongoDBOperation.getPartyDetailsCollectionData(url, dbName,
								"party_details", documentId);
						logger.info("Size:"+PartyDetails.size());
						int temp=0;
						logger.info(PartyDetails.toString());
						//List<PartyDetails> partyDetails = new ArrayList<PartyDetails>();
						boolean deletePartyDetails = OPartyDetails.singlePartyDetailsDelete(documentId);
						for (String mess : PartyDetails) {
							logger.info("************************Started**************************");
							temp++;
							logger.info("AAAAAAAAAAAAAAAAAAAAAAAAAAAA"+temp);
							logger.info("PartyDetails message:"+mess.toString());

							Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").create();

							PartyDetails partyDetails1 = gson.fromJson(mess, PartyDetails.class);

							if (deletePartyDetails == false)

								logger.info("ExecutantClaimant record with ID****" + documentId
										+ "***got deleted successsfully");

							boolean insertPartyDetails = OPartyDetails.singlePartyDetailsInsert(partyDetails1);

							if (insertPartyDetails == false)

								{logger.info("ExecutantClaimant record with ID****" + documentId+ "***got inserted successsfully");}
							else if (insertPartyDetails == true)
								{logger.info("ExecutantClaimant record with ID****" + documentId+ "***could not be inserted successsfully");
								status="FAILED";
								}

							boolean updatePartyDetails = OPartyDetails.updateDocumentDetails(partyDetails1);

							if (updatePartyDetails == false)
								logger.info("Presentation record with ID****" + documentId
										+ "***got updated successsfully");
							
							logger.info("************************Completed**************************");

						}

						logger.info("****************PARTYREPRESENTDETAILS*******************");
							List<String> PartyRepresentDetails = MongoDBOperation.getPartyRepresentDetailsCollectionData(
									url, dbName, "party_represent_detail", documentId);
						logger.info("PartyRepresentDetails size:*********"+PartyRepresentDetails.size());
						List<PartyRepresentDetails> partyRepresentDetails = new ArrayList<PartyRepresentDetails>();
						boolean deletePartyRepresentDetails = OPartyRepresentDetails
								.singleDeletePartyRepresentDetails(documentId);
						
						if (deletePartyRepresentDetails == false)

							logger.info("FirmsRepresented record with ID****" + documentId
									+ "***got deleted successsfully");

						for (String mess : PartyRepresentDetails) {

							Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").create();

							PartyRepresentDetails partyRepresentDetails1 = gson.fromJson(mess,
									PartyRepresentDetails.class);

									boolean insertPartyRepresentDetails = OPartyRepresentDetails
									.singleInsertPartyRepresentDetails(partyRepresentDetails1);

							if (insertPartyRepresentDetails == false)

								logger.info("FirmsRepresented record with ID****" + documentId
										+ "***got inserted successsfully");
							else if (insertPartyRepresentDetails == true)
							{logger.info("FirmsRepresented record with ID****" + documentId	+ "***could not be inserted successsfully");
							status="FAILED";
							}

						}
						logger.info("****************PROPERTYDETAILS*******************");
						List<String> PropertyDetails = MongoDBOperation.getPropertyDetailsCollectionData(url, dbName,
								"property_details", documentId);
						logger.info("PropertyDetails size***********"+PropertyDetails.size());
						
						List<PropertyDetails> propertyDetails = new ArrayList<PropertyDetails>();
						boolean deletePropertyDetails = OPropertyDetails.singleDeletePropertyDetails(documentId);
						if (deletePropertyDetails == false)

							logger.info("ScheduleEntry record with ID****" + documentId
									+ "***got deleted successsfully");
						boolean singleDeleteStructureDetails=OPropertyDetails.singleDeleteStructureDetails(documentId);
						if (singleDeleteStructureDetails == false)

							logger.info("StructureDetails record with ID****" + documentId
									+ "***got deleted successsfully");

						for (String mess : PropertyDetails) {

							Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").create();

							PropertyDetails propertyDetails1 = gson.fromJson(mess, PropertyDetails.class);

							propertyDetails.add(propertyDetails1);
							
							
							boolean insertPropertyDetails = OPropertyDetails.singleInsertScheduleEntry(propertyDetails1);

							if (insertPropertyDetails == false)

								logger.info("ScheduleEntry record with ID****" + documentId
										+ "***got inserted successsfully");
							else if (insertPropertyDetails == true)

								{logger.info("ScheduleEntry record with ID****" + documentId+ "***could not be inserted successsfully");
							status="FAILED";
								}
							
													
							logger.info("ddsrocode::::::::"+dDetails.getSroCode());
							logger.info("propertyDetails1.getSroCode()::::::::"+propertyDetails1.getSroCode());
							//if(propertyDetails1.getSroCode()==dDetails.getSroCode())
							if(dDetails.getSroCode()==propertyDetails1.getSroCode())
								{
								logger.info("they are equal");
							boolean updatePropertyDetailsY = OPropertyDetails.updateScheduleEntryY(propertyDetails1);
							boolean PreRegistrationCCAY = OPropertyDetails.updatePreRegistrationCCAY(propertyDetails1);
							logger.info("updatePropertyDetailsY:&&&&&&&&&"+updatePropertyDetailsY);
							//boolean updateDocumentDetailsY = ODocumentDetails. 
								}
							//else if(propertyDetails1.getSroCode()!=dDetails.getSroCode())
							else if(dDetails.getSroCode()!=propertyDetails1.getSroCode())
								{logger.info("not they are equal");
							boolean updatePropertyDetailsN = OPropertyDetails.updateScheduleEntryN(propertyDetails1);
							boolean updatePreRegistrationCCAN = OPropertyDetails.updatePreRegistrationCCAN(propertyDetails1);
							
							logger.info("insertPropertyDetailsN:&&&&&&&&&"+updatePropertyDetailsN);
								}
							
							boolean singleInsertStructureDetails=OPropertyDetails.singleInsertStructureDetails(propertyDetails1);
							if(singleInsertStructureDetails==false)
							{
								logger.info("Structuredetails record with ID******"+ documentId+ "***got inserted successsfully");
							}
							else if(singleInsertStructureDetails==true)
							{
								logger.info("Structuredetails record with ID******"+ documentId+ "***could not inserted successsfully");
								status="FAILED";
							}
							
							

						}
						logger.info("*************PropertyStructureDetails*****************");
						for (int i = 0; i < propertyDetails.size(); i++) {
							String propertyId2 = propertyDetails.get(i).getPropertyId();
							String applicationId= propertyDetails.get(i).getApplicationId();
							//int =propertyDetails.get(i).getSeqNumber();
							
							logger.info("************PropertyId****"+ propertyId2+"for record:******"+1 );
							List<String> PropertyStructureDetails = MongoDBOperation.getPropertyStructureDetailsCollectionData(url, dbName,"property_structure_details", propertyId2);
							logger.info(
									"PropertyStructureDetails size***************" + PropertyStructureDetails.size()+"*****");
							boolean deletePropertyScheduleEntryDetails = OPropertyStructureDetails.singleDeleteScheduleEntry(propertyId2.substring(0,15));
							  if (deletePropertyScheduleEntryDetails == false)
								  
									 logger.info("ScheduleEntry record with ID" + propertyId2.substring(0,15) +	  "got deleted successsfully");
									  
									
						boolean deletePropertyStructureDetails = OPropertyStructureDetails.singleDeleteStructureDetails(propertyId2.substring(0,15));
							if (deletePropertyStructureDetails == false)

								logger.info("structure_details record with ID****" + propertyId2.substring(0,15)+ "***got deleted successsfully");


							for (String mess : PropertyStructureDetails) {
								logger.info("In message" + mess.toString());

								Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").create();
								PropertyStructureDetails propertyStructureDetails = gson.fromJson(mess,PropertyStructureDetails.class);

								String propertyId = propertyStructureDetails.getPropertyId().substring(0,15);
								
								int seqNo2=propertyDetails.get(i).getSeqNumber();
								logger.info("PropertyID fromobject" + propertyId);
								if (propertyStructureDetails == null) {
									logger.info("propertyStructureDetails is null");
								} else {
									logger.info("propertyStructureDetails is not null");
								}

								

										  boolean insertPropertyScheduleEntryDetails =  OPropertyStructureDetails.singleInsertScheduleEntry(propertyStructureDetails, seqNo2);
										  
										  if (insertPropertyScheduleEntryDetails == false)
										  
										  logger.info("ScheduleEntry record with ID" + propertyId2.substring(0,15) +  "got inserted successsfully");
										  else  if (insertPropertyScheduleEntryDetails == true)
											  
										  { logger.info("ScheduleEntry record with ID" + propertyId2.substring(0,15) +  "could not be inserted successsfully");
										  status="FAILED";
										  }
										  
									 

									boolean insertPropertyStructureDetails = OPropertyStructureDetails.singleInsertStructureDetails(propertyStructureDetails,seqNo2);

								if (insertPropertyStructureDetails == false)

									logger.info("structure_details record with ID****" + propertyId2.substring(0,15)	+ "***got inserted successsfully");
								else 
									if (insertPropertyStructureDetails == true)

									{	logger.info("structure_details record with ID****" + propertyId2.substring(0,15)	+ "***could not be inserted successsfully");
									status="FAILED";
									}
						
					
							}

						} // end for for-loop
						
						System.out.println("Value of status is*******************"+status);
						Bson query = new Document("documentId", new Document("$eq", documentId));
						Bson update = new Document("$set", new Document("status", status));
						MongoClient mongoClient = MongoClients.create(url);

						MongoDatabase db = mongoClient.getDatabase(dbName);
						MongoCollection<Document> coll = db.getCollection("document_details");
						coll.findOneAndUpdate(query, update);
						logger.info(
								"Document with *********" + documentId + "got updated successfully with sync status"+status);

					}

				}
			}
		} else if (syncType.equalsIgnoreCase("OracleToMongo")) {

			for (String cName1 : collections) {

				if (cName1.equals("document_details")) {
					logger.info("DocumentDetails collection");

					Connection con = OracleDBUtil.getConnection();
					con.setAutoCommit(false);

					String documentId1= null;

				try(Statement st = con.createStatement()) {
						//Statement st = con.createStatement();
						logger.info("After try statement");
						String query1 = "SELECT * FROM PRE_REGISTRATION_CCA WHERE STATUS='P'";
						ResultSet rs = st.executeQuery(query1);	
													
						while(rs.next())
						{
						    logger.info("in while loop of rs");
							
					documentId1= rs.getString(1);
						
							//logger.info("Document Id from Oracle*********" + documentId1);

							MongoClient mongoClient = MongoClients.create(url);
							MongoDatabase database = mongoClient.getDatabase(dbName);
							MongoCollection<Document> coll = database.getCollection("document_details");

							Bson query = new Document("documentId", new Document("$eq", documentId1));
							Bson update = new Document("$set", new Document("status", "RECTIFY"));

					Document d1=coll.findOneAndUpdate(query, update);
							
							logger.info("after update of status field***********"+d1.toJson());

							FindIterable<Document> cursor = coll.find();
							

							for (Document d : cursor) {
							//	logger.info("Document*****"+d);
							}
														//mongoClient.close();
							con.commit();

						}//while close
						 

					} catch (SQLException e) {
						e.printStackTrace();
					}
					finally
					{
						if(con!=null)
						{con.close();}
					}
				}//if close of "document_details" collection
				// end of check for document_details collection
			}//for close in collections of OracleToMongo
		}// if close of OracleToMongo
	}//main method close

}