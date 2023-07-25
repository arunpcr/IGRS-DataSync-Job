package in.gov.ap.igrs.datasync.app.oracle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
//import java.util.logging.Logger;
//import java.util.logging.logger.info;

import org.apache.log4j.lf5.LogLevel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import in.gov.ap.igrs.datasync.app.data.DocumentDetails;

public class ODocumentDetails {

	//final static Logger logger = Logger.getLogger(ODocumentDetails.class.getName());
	final static Logger logger = LogManager.getLogger(ODocumentDetails.class.getClass());

	public static boolean singleInsertPresentation(DocumentDetails mData) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt =null;
		boolean result = true;
		try {
			con = OracleDBUtil.getConnection();
			logger.info("Before inserting data into Presentation table from DocumentDetails collection" + con.isClosed());

			logger.info("In singleInsertPresentation method of OracleDocumentDetails-Presentation table ");

			pstmt = con.prepareStatement(
					"insert into PRESENTATION(ID, E_DATE, P_DATE, TRAN_MAJ_CODE, TRAN_MIN_CODE, MISC_DESC, FINAL_TAXABLE_VALUE, SR_CODE, MKT_VAL_CHK, RF_PAYABLE, SD_PAYABLE, TD_PAYABLE, BOOK_NO, PRE_REG_CCA_ID) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			logger.info("Bulk Insert started for the Documents " + mData.toString());
			con.setAutoCommit(false);

			logger.info(mData.toString());
			pstmt.setString(1, mData.getDocumentId());
			logger.info("Object ExecutionData:" + mData.getExecutionDate());
			if(mData.getExecutionDate()==null)
				pstmt.setDate(2, null);
			else
				pstmt.setDate(2, mData.getExecutionDate());

			if(mData.getStampPurchaseDate()==null)
				pstmt.setDate(3, null);
			else
				pstmt.setDate(3, mData.getStampPurchaseDate());

			pstmt.setString(4, mData.getDocumentSubType().getTRAN_MAJ_CODE());
			pstmt.setString(5, mData.getDocumentSubType().getTRAN_MIN_CODE());
			// java.sql.BatchUpdateException: ORA-12899: value too large for column
			// "PREREGISTRATION"."PRESENTATION"."MISC_DESC" (actual: 104, maximum: 75)
			String tranDesc = mData.getDocumentSubType().getTRAN_DESC();
			String splitMsg[] = tranDesc.split("\\[");
			pstmt.setString(6, splitMsg[0]);
			pstmt.setInt(7, mData.getAmount());
			pstmt.setInt(8, mData.getSroCode());
			logger.info("TMarketValue:" + mData.getTmarketValue());
			logger.info("TMarketValue:" + mData.getTmarketValue());
			// as tmarketvalue field is not present in documentdetails collection I am
			// setting it to '0'
			/*if (mData.getTmarketValue() == 0) {
				pstmt.setInt(9, 0);
			} else {
				pstmt.setInt(9, mData.getTmarketValue());
			}*/
			if (mData.getTmarketValue() == 0) {
				pstmt.setString(9, "0");
			} else {
				pstmt.setString(9, Double.toString(mData.getTmarketValue()).substring(0,1));
			}

			logger.info("DutyfeeData object loaded properly:" + mData.getDutyFeeData());
			if (mData.getDutyFeeData() != null) {
				pstmt.setInt(10, mData.getDutyFeeData().getRf_p());
				pstmt.setInt(11, mData.getDutyFeeData().getSd_p());
				pstmt.setInt(12, mData.getDutyFeeData().getTd_p());
			} else {
				pstmt.setInt(10, 0);
				pstmt.setInt(11, 0);
				pstmt.setInt(12, 0);
			}
			pstmt.setInt(13, 1);
			// pstmt.setString(14, mData.getDocumentId().substring(0, 14));
			pstmt.setString(14, mData.getDocumentId().substring(0, 14));

			//pstmt.addBatch();
			logger.info("All Batch statements added successfully - Presentation table");

			result = pstmt.execute();
			logger.info("Row inserted into PRESENTATION table " + result + "with ID" + mData.getDocumentId());
			con.commit();
		} catch (SQLException e) {
			result=true;
			logger.error("Presentation table data could not be inserted - SQLException",e);
					} 
		catch (Exception e) {
			result=true;
			logger.error("Presentation table data could not be inserted - Exception",e);
					}
		finally {
			if(pstmt!=null) {
				try {pstmt.close();}
				catch (Exception e) {
					logger.error("Presentation table data could not be inserted - Finallyblock",e);
				}}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					logger.error("Presentation table data could not be inserted - Finallyblock",e);
				}
			}
		
		return result;
	}}

	public static boolean singleDeletePresentation(String documentId) throws SQLException {
		Connection con=null;
		PreparedStatement pstmt =null;
		boolean result=true;
		logger.info("In singleDeletePresentation method of OracleDocumentDetails-Presentation table ");
		try {
		con=OracleDBUtil.getConnection();
		//con.setAutoCommit(false);
		 pstmt = con.prepareStatement("DELETE from PRESENTATION where ID=?");
		pstmt.setString(1, documentId);
		result= pstmt.execute();
		logger.info("Presentation record with****"+documentId+"******* got deleted successfully");
		//con.commit();
		}
		catch(Exception e)
		{logger.info("Presentation table could not be deleted",e);
		
		}
		finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					logger.error("PRE_REGISTRATION_CCA table could not be deleted - Finallyblock",e);
				}
			}
		}
		return result;

			}

	public static boolean singleInsertPreRegistrationCCA(DocumentDetails mData) {
		logger.info("in singleInsertPreRegistrationCCA method of OracleDocumentDetails-PRE_REGISTRATION_CCA table ");
		Connection con=OracleDBUtil.getConnection();
		boolean result = true;
		try {
			PreparedStatement pstmt = con.prepareStatement(
					"insert into PRE_REGISTRATION_CCA(ID, SRO_LOCATION, TRANS_MAJOR_CODE, TRANS_MINOR_CODE, REGD_DISTRICT, STATUS, BOOK_NO, DOC_LOCK, REG_AT, REG_AT_CODE, DOC_PROCESS_TYPE, DOC_PROCESS_CODE ) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			con.setAutoCommit(false);
			logger.info("Insert started for the Documents " + mData.toString());

			pstmt.setString(1, mData.getDocumentId());
			pstmt.setInt(2, mData.getSroCode());
			pstmt.setString(3, mData.getDocumentSubType().getTRAN_MAJ_CODE());
			pstmt.setString(4, mData.getDocumentSubType().getTRAN_MIN_CODE());
			logger.info("District************" + mData.getDistrict());
			pstmt.setInt(5, mData.getDistCode());
			// pstmt.setString(6, "P");
			pstmt.setString(6, null);
			pstmt.setInt(7, 1);
			pstmt.setString(8, "Y");
			logger.info("Registration with Code value is :::::::::"+mData.getRegWithCode());
			if(mData.getRegWithCode()!=null && mData.getRegWithCode().length()>10)
			{pstmt.setString(9, mData.getRegWithCode().substring(0,10));}
			else
				if(mData.getRegWithCode()!=null && mData.getRegWithCode().length()<10)
				{pstmt.setString(9, mData.getRegWithCode());}
				else
					if(mData.getRegWithCode()==null)
					{pstmt.setString(9, null);}
			logger.info("Registration with value Value is :::::::::"+mData.getRegWithValue());
			if(mData.getRegWithValue()!=null && mData.getRegWithValue().length()>10)
			{pstmt.setString(10, mData.getRegWithValue().substring(0, 10));}
			else
				if(mData.getRegWithValue()!=null && mData.getRegWithValue().length()<10)
				{pstmt.setString(10, mData.getRegWithValue());}
			if(mData.getRegWithValue()==null)
			{pstmt.setString(10, null);}
			logger.info("Document Process type value is :::::::::"+mData.getDocProcessType());
			if(mData.getDocProcessType()!=null && mData.getDocProcessType().length()>10)
			{pstmt.setString(11, mData.getDocProcessType().substring(0,10));}
			else
				if(mData.getDocProcessType()!=null && mData.getDocProcessType().length()<10)
				{pstmt.setString(11, mData.getDocProcessType());}
				else
					if(mData.getDocProcessType()==null)
					{pstmt.setString(11, null);}
			logger.info("Document Process code value is :::::::::"+mData.getDocProcessCode());
			if(mData.getDocProcessCode()!=null && mData.getDocProcessCode().length()>2)      
			{pstmt.setString(12, mData.getDocProcessCode().substring(0,2));}
			else 
				if(mData.getDocProcessCode()!=null && mData.getDocProcessCode().length()<=2)      
				{pstmt.setString(12, mData.getDocProcessCode());}
				else
					if(mData.getDocProcessCode()==null)      
					{pstmt.setString(12, null);}
			//pstmt.addBatch();
			logger.info("All Batch statements added successfully - PRE_REGISTRATION_CCA table");
			//pstmt.clearParameters();

			result = pstmt.execute();
			logger.info("Row inserted into PRE_REGISTRATION_CCA table " + result + "with ID" + mData.getDocumentId());
			pstmt.close();
			con.commit();
		} catch (SQLException e) {
			result=true;
			//e.printStackTrace();
			logger.error("Precould not be inserted SQL exception",e);
		} 
		catch(Exception e)
		{
			result=true;
			logger.error("Precould not be inserted",e);

		}finally {
			if (con != null) {
				try {

					con.close();
				} catch (Exception e) {
					//logger.info(e.getMessage());
					logger.error("Precould not be inserted Finally",e);
				}
			}
		}
		return result;
	}

	public static boolean singleDeletePreRegistrationCCA(String documentId) throws SQLException {
		Connection con=null;
		PreparedStatement pstmt =null;
		boolean result = true;
try
{
		logger.info("in singleDeletePreRegistrationCCA method of OracleDocumentDetails-PRE_REGISTRATION_CCA table ");
		con=OracleDBUtil.getConnection();
		//con.setAutoCommit(false);
		pstmt = con.prepareStatement("DELETE from PRE_REGISTRATION_CCA where ID=?");
		pstmt.setString(1,documentId );
		result = pstmt.execute();
		//con.commit();
		logger.info("Delete got executed successfully-singlerecord -PRE_REGISTRATION_CCA table" + result + "with ID"+ documentId);
}
catch(Exception e)
{logger.error("PRE_REGISTRATION_CCA table could not be deleted",e);}
	
finally {
	if (con != null) {
		try {
			con.close();
		} catch (Exception e) {
			logger.error("PRE_REGISTRATION_CCA table could not be deleted - Finallyblock",e);
		}
	}
}
return result;

	}


	public static List<String> updateUserDetails(DocumentDetails mData, String url, String dbName) throws SQLException {

		logger.info("in Oracle User document detaild update ");
		//boolean result = true;
		String userId=mData.getUserId();
		logger.info("UserId from Document Details:"+userId);
		String cName="users";
		MongoClient mongoClient = MongoClients.create(url); 
		MongoDatabase database = mongoClient.getDatabase(dbName);
		MongoCollection<Document> list = database.getCollection(cName);

		List<String> mess = new ArrayList<String>();
		Bson query = new Document("loginId",new Document("$eq",userId));

		for (Document doc : list.find(query)) {
			doc.remove("_id");
			doc.remove("$numberLong");
			//logger.info("Document from user collection:"+doc.toJson());
			mess.add(doc.toJson());
		}
		return mess;
	}


}



