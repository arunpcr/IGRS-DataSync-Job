package in.gov.ap.igrs.datasync.app.oracle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
//import java.util.logging.Logger;
import java.util.List;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import in.gov.ap.igrs.datasync.app.data.ConveyedExtent;
import in.gov.ap.igrs.datasync.app.data.ExtentList;
import in.gov.ap.igrs.datasync.app.data.PropertyDetails;
import in.gov.ap.igrs.datasync.app.data.Structure;

public class OPropertyDetails {

	//static Logger logger = Logger.getLogger(OPropertyDetails.class.getName());
	final static Logger logger = LogManager.getLogger(OPropertyDetails.class.getClass());

	public static boolean singleInsertScheduleEntry(PropertyDetails mData) throws SQLException {

		logger.info("%%%in singleInsertScheduleEntry method of OPropertyDetails class%%%%%%%%%%%" );

		Connection con=OracleDBUtil.getConnection();

		logger.info("after Con in singleInsertScheduleEntry method of OraclePropertyDetails-SCHEDULE_ENTRY table ");
		PreparedStatement pstmt = null;
		boolean result = false;
		try {
			//String sqlQuery = "insert into SCHEDULE_ENTRY(ID, LOCAL_BODY_NAME, SCHEDULE_NO, JURISDICTION, HAB_CODE, MKT_VALUE, NATURE_USE, UNIT, VILLAGE_CODE, TOTAL_EXTENT, LOCAL_BODY, SURVEY_NO, EAST, WEST, NORTH, SOUTH, EXTENT, LPMNO) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			String sqlQuery = "insert into SCHEDULE_ENTRY(ID, LOCAL_BODY_NAME, SCHEDULE_NO, JURISDICTION, HAB_CODE, MKT_VALUE, NATURE_USE, UNIT, VILLAGE_CODE, TOTAL_EXTENT, LOCAL_BODY, SURVEY_NO, EAST, WEST, NORTH, SOUTH, EXTENT, LPMNO) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, substr(?,1,74), ?, ?)";


			logger.info("Single Insert started for the Documents from Property_details collection " + mData.toString());
			pstmt = con.prepareStatement(sqlQuery);
			con.setAutoCommit(false);

			pstmt.setString(1, mData.getApplicationId());

			logger.info("Application ID:" + mData.getApplicationId());

			pstmt.setString(2, mData.getLocalBodyName());
			logger.info("LocalBodyName:" + mData.getLocalBodyName());

			pstmt.setInt(3, mData.getSeqNumber());
			pstmt.setInt(4, mData.getSroCode());
			pstmt.setString(5, mData.getHabitationCode());

			pstmt.setInt(6, mData.getMarketValue());
			logger.info("LandUseCode:" + mData.getLandUseCode());
			pstmt.setString(7, mData.getLandUseCode());
			pstmt.setString(8, mData.gettUnits());
			pstmt.setString(9, mData.getVillageCode());
			pstmt.setString(10, mData.gettExtent());
			//pstmt.setString(10, "2");
			if(mData.getLocalBodyCode()==null)
			{pstmt.setString(11,null);}
			else
			{pstmt.setInt(11,  Integer.parseInt(mData.getLocalBodyCode()));}
			pstmt.setString(12, mData.getSurvayNo());
			pstmt.setString(13, mData.getEastBoundry());
			pstmt.setString(14, mData.getWestBoundry());
			pstmt.setString(15, mData.getNorthBoundry());
			pstmt.setString(16,  mData.getSouthBoundry());
			pstmt.setString(17, mData.gettExtent());
			//pstmt.setString(17, "2");
			if(mData.getLpmNo()==null)
			{pstmt.setString(18, null);}
			else
			{pstmt.setString(18, mData.getLpmNo());}
			logger.info("All Batch statements added successfully - ScheduleEntry table");
			//pstmt.addBatch();

			result = pstmt.execute();
			logger.info("Row inserted into SCHEUDLE_ENTRY table " + result +"with ID"+mData.getApplicationId());

			con.commit();
			pstmt.close();
			return result;
		}catch (SQLException e) {
			result=true;
			logger.error("Schedule_entry table could not be inserted-SQL", e);
		} 
		catch (Exception e) {
			result=true;
			logger.error("Schedule_entry table could not be inserted-Exception", e);
		} 
		finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					result=true;
					logger.error("Schedule_entry table could not be inserted-Finally", e);
				} 
			}
		}
		return result;
	}

	public static boolean singleDeletePropertyDetails(String applicationId) throws SQLException {
		Connection con=OracleDBUtil.getConnection();

		boolean result = false;

		logger.info("in singleDeletePropertyDetails method of OraclePropertyDetails-ScheduleEntry table ");

		PreparedStatement pstmt = con.prepareStatement("DELETE FROM schedule_entry where ID=?");
		pstmt.setString(1, applicationId);
		result=pstmt.execute();
		logger.info("Single row deleted from schedule_entry table " + result + "with ID"+applicationId);
		pstmt.close();
		con.close();
		return result;
	}
	public static boolean updateScheduleEntryY(PropertyDetails mData) throws SQLException {
		Connection con=OracleDBUtil.getConnection();
		logger.info("in updateScheduleEntryY of OraclePropertyDetails"+ mData.toString());
		boolean result = true;
		try {
			PreparedStatement pstmt = con.prepareStatement("Update SCHEDULE_ENTRY set JURI_STATUS=? where ID = ? AND SCHEDULE_NO=?");
			con.setAutoCommit(false);
			logger.info("before setting value as Y");
			pstmt.setString(1, "Y");
			logger.info("after setting value as Y");
			pstmt.setString(2, mData.getApplicationId());
			pstmt.setInt(3, mData.getSeqNumber());
			try {
				result = pstmt.execute();
			}
			catch(SQLException e)
			{logger.info(e.getMessage());}


			con.commit();
			pstmt.close();
			return result;


		}
		catch (SQLException e) {
			logger.warn("ScheduleEntry could not be updated with 'Y'-SQLException",e);
		}

		catch (Exception e) {
			logger.warn("ScheduleEntry could not be updated with 'Y'-Exception",e);
		}
		con.close();
		return result;
	}	

	public static boolean updateScheduleEntryN(PropertyDetails mData) throws SQLException {
		Connection con=OracleDBUtil.getConnection();
		logger.info("in updateScheduleEntryN of OraclePropertyDetails"+ mData.toString());
		boolean result = true;
		try {


			PreparedStatement pstmt = con.prepareStatement("Update SCHEDULE_ENTRY set JURI_STATUS=? where ID = ? AND SCHEDULE_NO=?");
			con.setAutoCommit(false);
			pstmt.setString(1, "N");
			pstmt.setString(2, mData.getApplicationId());
			pstmt.setInt(3, mData.getSeqNumber());
			result = pstmt.execute();

			con.commit();
			pstmt.close();
			return result;


		}
		catch(SQLException e)
		{logger.error("ScheduleEntry table could not be updated with 'N'-SQL",e);}
		catch (Exception e) {
			{
				logger.error("ScheduleEntry table could not be updated-Exception with 'N'-Exception",e);
			}
		}
		con.close();
		return result;
	}	

	public static boolean updatePreRegistrationCCAY(PropertyDetails mData) throws SQLException {
		Connection con=OracleDBUtil.getConnection();
		logger.info("in updatePreRegistrationCCA of OraclePropertyDetails"+ mData.toString());
		boolean result = true;
		try {
			PreparedStatement pstmt2 = con.prepareStatement("Update PRE_REGISTRATION_CCA set JURI_STATUS=? where ID = ?");
			con.setAutoCommit(false);
			pstmt2.setString(1, "Y");
			pstmt2.setString(2, mData.getApplicationId());
			//boolean	result2 = pstmt2.execute();
			result = pstmt2.execute();

			con.commit();
			pstmt2.close();
			//return result2;
			return result;


		}
		catch(SQLException e)
		{logger.error("PreRegistrationCCA table could not be updated with 'Y'-SQL",e);}
		catch (Exception e) {
			{logger.error("PreRegistrationCCA table could not be updated with 'Y'-Exception",e);}
		}
		con.close();
		return result;
	}	

	public static boolean updatePreRegistrationCCAN(PropertyDetails mData) throws SQLException {
		Connection con=OracleDBUtil.getConnection();
		logger.info("in updatePreRegistrationCCAN of OraclePropertyDetails"+ mData.toString());
		boolean result = true;
		try {
			PreparedStatement pstmt2 = con.prepareStatement("Update PRE_REGISTRATION_CCA set JURI_STATUS=? where ID = ?");
			con.setAutoCommit(false);
			pstmt2.setString(1, "N");
			pstmt2.setString(2, mData.getApplicationId());
			boolean	result2 = pstmt2.execute();

			con.commit();
			pstmt2.close();
			return result2;


		}
		catch(SQLException e)
		{logger.error("PreRegistrationCCA table could not be updated with 'N'-SQL",e);}
		catch (Exception e) {
			{logger.error("PreRegistrationCCA table could not be updated with 'N'-Exception",e);}
		}
		con.close();
		return result;
	}	


	public static boolean singleInsertStructureDetails(PropertyDetails mData) throws SQLException {
		logger.info("in singleInsertStructureDetails method of OraclePropertyDetails-STRUCTURE_DETAILS table ");

		PreparedStatement pstmt = null;
		boolean result = true;
		Connection con = null;
		String sqlQuery = "insert into STRUCTURE_DETAILS(ID, SCHEDULE_NO) values (?, ?)";

		try {
			con = OracleDBUtil.getConnection();
			logger.info("Bulk Insert started for the Documents " + mData.toString());
			pstmt = con.prepareStatement(sqlQuery);
			con.setAutoCommit(false);
			logger.info("In STRUCTUREDETAILS TABLE");
			pstmt.setString(1, mData.getApplicationId());
			logger.info("Application ID:" + mData.getApplicationId());
			pstmt.setInt(2, mData.getSeqNumber());
			logger.info("All statements added successfully - STRUCTURE_DETAILS table");
			logger.info("COnn" + pstmt.isClosed());
			result = pstmt.execute();
			logger.info("Row inserted into PropertyDetails-Structuredetails table: " + result+ "with ApplicationID"+mData.getApplicationId());
			con.commit();
		} catch (SQLException e) {
			logger.error("ScheduleEntry table could not be inserted-SQL",e);
			result=true;
		} 
		catch (Exception e) {
			logger.error("ScheduleEntry table could not be inserted-Exception",e);
			result=true;
		}finally {
			if (con != null)
				con.close();
			if (pstmt != null)
				pstmt.close();
		}
		return result;
	}

	public static boolean singleDeleteAdangalDetails(String applicationId) throws SQLException {
		Connection con=OracleDBUtil.getConnection();

		boolean result = false;

		logger.info("in singleDeleteAdangalDetails method of OraclePropertyDetails-Adangal table ");

		PreparedStatement pstmt = con.prepareStatement("DELETE FROM PDE_ADANGAL_DETAILS where ID=?");
		pstmt.setString(1, applicationId);
		result=pstmt.execute();
		logger.info("Single row deleted from PDE_ADANGAL_DETAILS  table " + result + "with ID"+applicationId);
		pstmt.close();
		con.close();
		return result;
	}
	public static boolean singleInsertAdangalDetails(PropertyDetails mData) throws SQLException {

		logger.info("%%%in singleInsertAdangalDetails method of OPropertyDetails class%%%%%%%%%%%" + mData);


		Connection con=OracleDBUtil.getConnection();

		logger.info("after Con in singleInsertAdangalDetails method of OraclePropertyDetails-AdangalDetails table ");
		//PreparedStatement pstmt = null;
		boolean result = false;
		try {

			logger.info("Single Insert started AD for the Documents from Property_details collection " + mData.toString());
			String sqlQuery = "insert into PDE_ADANGAL_DETAILS(ID, S_LP_NO, KHATA_NO, VILLAGE_CODE, TOTAL_EXTENT, SCHEDULE_NO, SELLING_EXTENT) values (?, ?, ?, ?, ?, ?, ?)";
			con.setAutoCommit(false);
			PreparedStatement pstmt = con.prepareStatement(sqlQuery);
			logger.info("zero size insert into PDE_ADANGAL_DETAILS where getExtentListSize is greateer than 0");
			
			if (Objects.nonNull(mData.getExtentList()) && mData.getExtentList().size() > 0) {
				logger.info("IN IF condition where list size is greater than 0......");
				for (ExtentList list : mData.getExtentList()) {
					logger.info("ExtentList object:::::::::::::::"+list.toString());
					logger.info("Conveyed extent acers ijaya:"+list.getConveyedExtentAcers());
					logger.info("Total extent acers:"+list.getTotalExtentAcers());
					pstmt.setString(1, mData.getApplicationId());
					logger.info("survey No for this object*****:"+list.getSurvayNo());
					pstmt.setString(2, list.getSurvayNo());
					pstmt.setInt(3, list.getKhataNumber());
					pstmt.setString(4, mData.getVillageCode());

					logger.info("Total Extent from extentlist:"+list.getTotalExtentAcers()+"*((((((("+list.getTotalExtentCents());
					pstmt.setFloat(5, Float.parseFloat(list.getTotalExtentAcers() +"."+list.getTotalExtentCents()));

					logger.info("Conveyed Extent from extentlist:"+list.getConveyedExtentAcers()+"*((((((("+list.getConveyedExtentCents());
					pstmt.setFloat(7, Float.parseFloat(list.getConveyedExtentAcers() +"."+list.getConveyedExtentCents()));
				
					pstmt.setInt(6, mData.getSeqNumber());	    	
						result = pstmt.execute();
					logger.info("Row inserted into AdangalDetails table " + result + "with ID" + mData.getApplicationId());

				}
			}
			//pstmt.setFloat(5, TOTAL_EXTENT_VALUE);

			con.commit();
			pstmt.close();


			logger.info("All Batch statements added successfully - AdangalDetails table");
		}catch (SQLException e) {
			result=true;
			logger.error("AdangalDetails table could not be inserted-SQL", e);
		} 
		catch (Exception e) {
			result=true;
			logger.error("AdangalDetails table could not be inserted-Exception", e);
		} 
		finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					result=true;
					logger.error("AdangalDetails table could not be inserted-Finally", e);
				} 
			}
		}
		return result;
	}


	public static boolean singleDeleteStructureDetails(String applicationId) throws SQLException {
		Connection con=OracleDBUtil.getConnection();

		boolean result = false;

		logger.info("in singleDeleteStructureDetails method of OraclePropertyDetails-StructureDetails table ");

		PreparedStatement pstmt = con.prepareStatement("DELETE FROM structure_details where ID=?");
		pstmt.setString(1, applicationId);
		result=pstmt.execute();
		logger.info("Single row deleted from Structure_Details table " + result + "with ID"+applicationId);
		pstmt.close();
		con.close();
		return result;
	}


}
