package in.gov.ap.igrs.datasync.app.oracle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
//import java.util.logging.Logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import in.gov.ap.igrs.datasync.app.data.PartyDetails;

public class OPartyDetails {

	//static Logger logger = Logger.getLogger(OPartyDetails.class.getName());
	final static Logger logger = LogManager.getLogger(OPartyDetails.class.getClass());

	public static boolean singlePartyDetailsInsert(PartyDetails mData) throws SQLException {
		boolean result = false;
		Connection con=OracleDBUtil.getConnection();
		try {
			logger.info("in singlePartyDetailsInsert method of OraclePartyDetails-EXECUTANTS_CLAIMANT table ");

			//PreparedStatement pstmt = con.prepareStatement(
			//"insert into EXECUTANTS_CLAIMANT(ID, NAME, R_CODE, R_NAME, AGE, PAN_NO, AADHAR, EMAIL_ID, PHONE_NO, ADDRESS1, R_CODE, ECNUMBER) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			PreparedStatement pstmt = con.prepareStatement(
					"insert into EXECUTANTS_CLAIMANT(ID, NAME, R_CODE, R_NAME, AGE, PAN_NO, AADHAR, EMAIL_ID, PHONE_NO, ADDRESS1, CODE, EC_NUMBER) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			logger.info("Single Insert started for the Documents from party_details collection " + mData.toString());
			con.setAutoCommit(false);

			pstmt.setString(1, mData.getApplicationId());
			pstmt.setString(2, mData.getName());
			pstmt.setString(3, mData.getRelationType());
			pstmt.setString(4, mData.getRelationName());
			logger.info("RelationName:" + mData.getRelationName());
			pstmt.setInt(5, mData.getAge());
			pstmt.setString(6, mData.getPanNoOrForm60or61());
			logger.info("Aadhar no:" + mData.getAadhaar());
			pstmt.setLong(7, mData.getAadhaar());

			pstmt.setString(8, mData.getEmail());
			logger.info("Phone no:" + mData.getPhone());

			pstmt.setLong(9, mData.getPhone());

			pstmt.setString(10, mData.getAddress());
			pstmt.setString(11, mData.getPartyCode());
			pstmt.setInt(12, mData.getSeqNumber());
			logger.info("All Batch statements added successfully - ExecutantsClaimant table");
			//pstmt.addBatch();

			result = pstmt.execute();
			con.commit();
			logger.info("Row inserted into EXECUTANTS_CLAIMANT table " + result +"with ID"+mData.getApplicationId());
			pstmt.close();

		}
		catch (SQLException e) {
			result=true;
			logger.error("ExecutantClaimant table could not be inserted-SQL",e);
		} 
		catch (Exception e) {
			result=true;
			logger.error("ExecutantClaimant table could not be inserted",e);
		}finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					logger.error("ExecutantClaimant table could not be inserted",e);
				}
			}
		}
		return result;
	}


	public static boolean singlePartyDetailsDelete(String applicationId) throws SQLException {
		Connection con=OracleDBUtil.getConnection();
		boolean result = false;

		logger.info("in singlePartyDetailsDelete method of OraclePartyDetails-EXECUTANTS_CLAIMANT table ");

		PreparedStatement pstmt = con.prepareStatement("DELETE FROM EXECUTANTS_CLAIMANT WHERE ID=?");
		logger.info("Single delete started for the Documents from party_details collection ");
		con.setAutoCommit(false);
		pstmt.setString(1, applicationId);
		result=pstmt.execute();
		logger.info("Delete got executed successfully- single record - ExecutantsClaimant table"+result+"with ID"+applicationId);
		con.commit();
		pstmt.close();
		con.close();
		return result;
	}
	public static boolean updateDocumentDetails(PartyDetails mData) throws SQLException {
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
				pstmt.setString(2, mData.getPartyCode());
				pstmt.setString(3, mData.getApplicationId());
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

}
