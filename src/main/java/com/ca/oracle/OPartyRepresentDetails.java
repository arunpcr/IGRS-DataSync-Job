package com.ca.oracle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.ca.data.PartyDetails;
import com.ca.data.PartyRepresentDetails;
import com.ca.mongo.MongoDBOperation;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class OPartyRepresentDetails {
	
	final static Logger logger = Logger.getLogger(OPartyRepresentDetails.class.getName());
	
	public static boolean findPartyRepresentDetailsRecordById(String documentId) throws SQLException {
		logger.info("in method documentID::::::::::::::"+documentId);
		Connection con=null;
		boolean result = false;

		logger.info("in findPartyRepresentDetailsRecordById method of OPartyRepresentDetails-PartyRepresentDetail table ");
		con=OracleDBUtil.getConnection();
		 String query = "SELECT * FROM FIRMS_REPRESENTED WHERE id = ?"; 
        
         PreparedStatement pstmt = con.prepareStatement(query);
         pstmt.setString(1, documentId); // Set the ID parameter in the query

         // Execute the query and get the ResultSet
         ResultSet rs = pstmt.executeQuery();
         //result=rs.next();

         // Check if the ResultSet has any data (i.e., if the record exists)
         if (rs.next()) {
            logger.info("Select query PartyRepresentDetail with ID " + documentId + " exists.");
             result=true;
         } else {
             System.out.println("Select query PartyRepresentDetail with ID" + documentId + " does not exist.");
             result=false;  }  
         con.close();
		return result;

	}
	
	public static boolean updateDocumentDetails(PartyRepresentDetails mData, String mongoDBUrl, String dbName) throws SQLException {
		   Connection con=OracleDBUtil.getConnection();
		   logger.info("in updateDocumentDetails of OraclePartyDetails"+ mData.toString());
		   boolean result = true;
		   try {
		      // Update Presentation table with values from party_details collection

		      if (mData.isPresenter() == true) {
		         PreparedStatement pstmt = con.prepareStatement("Update PRESENTATION set P_NAME=?, P_CODE=? where ID = ?");
		         con.setAutoCommit(false);
		         logger.info("IsPresenter for this record id:**************" + mData.isPresenter());
		         logger.info("IsPresenter for this record id:**************" + mData.isPresenter());
		         pstmt.setString(1, mData.getName());
		         List<String> partyDetails = MongoDBOperation.getPartyDetailsCollectionDataFromID(mongoDBUrl, dbName, "party_details", mData.getParentPartyId());

		         logger.info("Size:" + partyDetails.size());
		         int temp = 0;
		         String pCode = "";

		         if(partyDetails.size() > 0) {
		            logger.info(partyDetails.toString());
		            GsonBuilder gsonBuilder = new GsonBuilder();
		            Gson gson = gsonBuilder.create();
		            PartyDetails partyDetails1 = gson.fromJson(partyDetails.get(0), PartyDetails.class);
		            pCode = partyDetails1.getPartyCode();
		         }
		         pstmt.setString(2, pCode);
		         pstmt.setString(3, mData.getDocumentId());
		         result = pstmt.execute();

		         con.commit();
		         pstmt.close();
		         con.close();
		         return result;

		      }
		      logger.info("No.of Presentation table records being updated is: " + result);

		   }
		   catch(SQLException e)
		   {
		      logger.error("UpdateDocumentDetails in OPartyDetails class-SQL",e);
		      result=true;
		   }
		   catch (Exception e) {
		      logger.error("UpdateDocumentDetails in OPartyDetails class-Exception",e);
		      result=true;
		   }
		   con.close();
		   return result;
		}
	
	public static boolean singleInsertPartyRepresentDetails(PartyRepresentDetails mData) throws SQLException {
		
		Connection con=OracleDBUtil.getConnection();

		boolean result = false;

try {
		logger.info("in singleInsertPartyRepresentDetails method of OraclePartyRepresentDetails-FirmsRepresented table ");

			PreparedStatement pstmt = con.prepareStatement("insert into FIRMS_REPRESENTED(ID,ADDRESS1, ADDRESS2, AGE, NAME, PAN_NO, R_CODE, R_NAME) values (?, ?, ?, ?, ?, ?, ?, ?)");
			logger.info("Single Insert started for the PartyRepresentDetails Documents "+mData.toString());
	        con.setAutoCommit(false);
	        logger.info(mData.toString());
	           	pstmt.setString(1, mData.getDocumentId());
	            pstmt.setString(2, mData.getAddress());
	            pstmt.setString(3, mData.getCurrentAddress());
	             pstmt.setInt(4, mData.getAge());
	             pstmt.setString(5, mData.getName());
	             if(mData.getPanNoOrForm60or61().length()>0)
	            {pstmt.setString(6, mData.getPanNoOrForm60or61().substring(0,8));}
	             else
	             {pstmt.setString(6, "");}
	            pstmt.setString(7, mData.getRelationType().substring(0,1));
	            pstmt.setString(8, mData.getRelationName());
	            logger.info("Record with DocumentId is processed now:"+mData.getDocumentId());
	          
	            logger.info("All Batch statements added successfully - FirmsRepresented table");

			result = pstmt.execute();
			con.commit();
			pstmt.close();
			logger.info("Row inserted into FIRMS_REPRESENTED table " + result +"with ID"+mData.getDocumentId());
}catch (SQLException e) {
	logger.error("FirmsRepresented table could not inserted-SQL",e);
	result=true;	
} 
catch (Exception e) {
	logger.error("FirmsRepresented table could not inserted-Exception",e);
	result=true;
}
finally {
	if (con != null) {
		try {
			con.close();
		} catch (Exception e) {
			logger.error("FirmsRepresented table could not inserted-Exception",e);
			result=true;
		}
	}
}
return result;
}
	     
public static boolean singleDeletePartyRepresentDetails(String documentId) throws SQLException {
	
	Connection con=OracleDBUtil.getConnection();
		
		boolean result = false;

		logger.info("in singleDeletePartyRepresentDetails method of OraclePartyRepresentDetails-FirmsRepresented table ");

		PreparedStatement pstmt = con.prepareStatement("DELETE FROM FIRMS_REPRESENTED where ID=?");
		pstmt.setString(1, documentId);
		result=pstmt.execute();
		logger.info("Rows deleted from FIRMS_REPRESENTED: " + result + "with ID"+documentId);
		pstmt.close();
		con.close();
		return result;
	}
	     
			

}
