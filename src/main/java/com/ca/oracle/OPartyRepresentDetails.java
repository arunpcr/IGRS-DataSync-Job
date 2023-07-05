package com.ca.oracle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import com.ca.data.PartyRepresentDetails;

public class OPartyRepresentDetails {
	
	final static Logger logger = Logger.getLogger(OPartyRepresentDetails.class.getName());
	
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
