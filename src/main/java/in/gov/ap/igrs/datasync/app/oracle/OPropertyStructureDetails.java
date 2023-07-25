package in.gov.ap.igrs.datasync.app.oracle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
//import java.util.logging.Logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import in.gov.ap.igrs.datasync.app.data.ConveyedExtent;
import in.gov.ap.igrs.datasync.app.data.PropertyStructureDetails;
import in.gov.ap.igrs.datasync.app.data.Structure;

public class OPropertyStructureDetails {
	
	//final static Logger logger = Logger.getLogger(OPropertyStructureDetails.class.getName());
	final static Logger logger = LogManager.getLogger(OPropertyDetails.class.getClass());
	
	

	public static boolean singleUpdateScheduleEntry(PropertyStructureDetails mData, int seqNo, String applicationId)
	      throws SQLException {
	   logger.info("%%%%%% in singleUpdateScheduleEntry method %%%%%%" + mData);
	   Connection con=OracleDBUtil.getConnection();
	   boolean result = false;
	   String SCHEDULE_NO=Integer.toString(seqNo);
	   try {
	      logger.info("in singleUpdateScheduleEntry of OraclePropertyStructureDetails-PropertyStructureDetails");
	      //PreparedStatement pstmt = con.prepareStatement("UPDATE SCHEDULE_ENTRY set DOOR_NO = ?, PLOT_NO = ?, LAYOUT_NAME = ?, SCHEDULE_NO = ?, WARD_NO = ?, LAYOUT_NO = ?, BLOCK_NO = ?, APT_NAME = ?, FLAT_NO = ?, OLD_HOUSE_NO = ?, TOT_FLOOR = ?, APT_EAST = ?, APT_WEST = ?, APT_NORTH = ?, APT_SOUTH = ?, EXTENT = ? where ID = ?");
	      PreparedStatement pstmt = con.prepareStatement("UPDATE SCHEDULE_ENTRY set DOOR_NO = ?, PLOT_NO = ?, LAYOUT_NAME = ?, SCHEDULE_NO = ?, WARD_NO = ?, LAYOUT_NO = ?, BLOCK_NO = ?, APT_NAME = ?, FLAT_NO = ?, OLD_HOUSE_NO = ?, TOT_FLOOR = ?, APT_EAST = ?, APT_WEST = ?, APT_NORTH = ?, APT_SOUTH = ?, EXTENT = ? where ID = ? AND SCHEDULE_NO=?");
	      //PreparedStatement pstmt = con.prepareStatement("insert into SCHEDULE_ENTRY(ID, DOOR_NO, PLOT_NO, LAYOUT_NAME, SCHEDULE_NO, WARD_NO, LAYOUT_NO, BLOCK_NO, APT_NAME, FLAT_NO, OLD_HOUSE_NO, TOT_FLOOR, APT_EAST, APT_WEST, APT_NORTH, APT_SOUTH, EXTENT) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");


	      logger.info("after preparedstatement");
	      logger.info(mData);
	      con.setAutoCommit(false);
	      //pstmt.setString(1, mData.getPropertyId());
	      logger.info("DoorNo is::::::"+mData.getDoorNo());
	      pstmt.setString(1, mData.getDoorNo());
	      pstmt.setString(2, mData.getPlotNo());
	      pstmt.setString(3, mData.getLayoutName());
	      // should get the mapping from Mongo collection for schedule_no
	      //pstmt.setString(5, "1");
	      logger.info("scheduleno in scheduleEntry table-PropertyStructure&&&&&&:"+SCHEDULE_NO);
	      pstmt.setString(4, SCHEDULE_NO);
	      logger.info("WardNo:::::::"+mData.getWard());
	      pstmt.setInt(5, Integer.parseInt(mData.getWard()));
	      pstmt.setString(6, mData.getLayoutNo());
	      pstmt.setInt(7, Integer.parseInt(mData.getBlock()));
	      pstmt.setString(8, mData.getAppartmentName());
	      if(mData.getFlatNo()==null)
	         pstmt.setString(9, null);
	      else
	         pstmt.setString(9, mData.getFlatNo());
	      pstmt.setString(10, mData.getDoorNo());
	      pstmt.setString(11, mData.getTotalFloors());
	      pstmt.setString(12, mData.getFlatEastBoundry());
	      pstmt.setString(13, mData.getFlatWestBoundry());
	      pstmt.setString(14, mData.getFlatNorthBoundry());
	      pstmt.setString(15, mData.getFlatSouthBoundry());
	      logger.info("Data for Conveyed Extent:"+mData.getConveyedExtent());//Data:[ConveyedExtent [extent=98, unit=Y]]
	      //pstmt.setInt(17, Integer.parseInt(mData.getConveyedExtent().getExtent()));
	         /*if(mData.getConveyedExtent().size()!=0)
	         {pstmt.setInt(17, Integer.parseInt(mData.getConveyedExtent().get(0).getExtent()));
	         }
	         else
	         {pstmt.setInt(17, 0);
	         }*/
	      if(mData.getConveyedExtent()==null || mData.getConveyedExtent().size()==0)

	      {//pstmt.setInt(17, 0);
	         pstmt.setFloat(16, 0);
	      }


	      else if(mData.getConveyedExtent().size()>0)
	      {//pstmt.setInt(17, Integer.parseInt(mData.getConveyedExtent().get(0).getExtent()));
	         List<ConveyedExtent> c=mData.getConveyedExtent();
	         //pstmt.setInt(17, Integer.parseInt(c.get(0).getExtent()));
	         pstmt.setFloat(16, Float.parseFloat(c.get(0).getExtent()));

	         //pstmt.setInt(17, Integer.parseInt(mData.getConveyedExtent().get(0).getExtent()));
	      }
	      /*pstmt.setString(17, mData.getEastBoundry());
	      logger.info("EAST:&&&&&&&&&&&"+mData.getEastBoundry());
	      pstmt.setString(18, mData.getWestBoundry());
	      pstmt.setString(19, mData.getNorthBoundry());
	      pstmt.setString(20, mData.getSouthBoundry());
	      logger.info("SOUTH:&&&&&&&&&&&"+mData.getSouthBoundry());*/
	      pstmt.setString(17, applicationId);
	      pstmt.setString(18, SCHEDULE_NO );

	      logger.info("All Batch statements added successfully - SCHEDULE_ENTRY table");
	      logger.info(pstmt);
	      //pstmt.addBatch();

	      result = pstmt.execute();
	      logger.info("Row inserted into OraclePropertyStructureDetails-ScheduleEntryTable" + result+"with propertyID"+mData.getPropertyId().substring(0,21));
	      con.commit();
	      pstmt.close();
	      return result;

	   }
	   catch (SQLException e) {
	      result=true;
	      logger.error("Schedule_entry table could not be inserted-SQL", e);
	   }
	   catch (Exception e) {
	      result=true;
	      logger.error("Schedule_entry table could not be inserted-Exception", e);
	   }
	   finally {
	      try
	      {
	         if (con != null)
	            con.close();
	      }
	      catch(Exception e){ logger.error("Schedule_entry table could not be inserted-Finally", e);}
	   }
	   return result;
	}         

	

	public static boolean singleUpdateStructureDetails(PropertyStructureDetails mData, int seqNo, String applicationId)throws SQLException {
	      Connection con=OracleDBUtil.getConnection();
	      logger.info("in singleUpdateStructureDetails of OraclePropertyStructureDetails-PropertyStructureDetails");
	      String SCHEDULE_NO=Integer.toString(seqNo);
	      boolean result = true;
	      try {
	         logger.info("PropertyStructureDetails values"+mData.toString());
	         logger.info("Values:"+mData.toString());

	         if (mData.getStructure().size() > 0) {
	            for(Structure structure : mData.getStructure())
	            {
	              // PreparedStatement pstmt = con.prepareStatement("UPDATE STRUCTURE_DETAILS SET FLOOR_NO = ?, STRU_TYPE = ?, UNIT = ?, AGE = ?, PLINTH = ?, SCHEDULE_NO = ? WHERE ID = ?");
	            	 PreparedStatement pstmt = con.prepareStatement("UPDATE STRUCTURE_DETAILS SET FLOOR_NO = ?, STRU_TYPE = ?, UNIT = ?, AGE = ?, PLINTH = ?, SCHEDULE_NO = ? WHERE ID = ? AND SCHEDULE_NO=?");
	               con.setAutoCommit(false);

//	             pstmt.setString(1, mData.getPropertyId().substring(0,15));
	               //If code is given for floorNo, below line should work
	               //pstmt.setString(2, String.valueOf(Integer.parseInt(structure.getFloorNo())));
	               //pstmt.setString(2, String.valueOf(Integer.parseInt(structure.getFloorNo().substring(0,2))));
	               //for floorNo current data ex: floor no-2, only below line will work
	               pstmt.setString(1, structure.getFloorNo().substring(0,2));
	               //String fno=structure.getFloorNo();
	               //if(fno.equals("Floor No-1"))
	               //pstmt.setString(2, "1");
	               //pstmt.setString(2, String.valueOf(Integer.parseInt(structure.getFloorNo())));
	               pstmt.setString(2, structure.getStructureType().substring(0, 2));
	               pstmt.setString(3,structure.getPlinthUnit().substring(0, 1));
	               //pstmt.setInt(5, structure.getAge());
	               pstmt.setInt(4, Integer.parseInt(structure.getAge()));
	               //pstmt.setInt(6, Integer.parseInt(structure.getPlinth().substring(0, 9)));
	               //pstmt.setDouble(6, Integer.parseInt(structure.getPlinth().length()>9?structure.getPlinth().substring(0, 9):structure.getPlinth()));
	               //pstmt.setDouble(6, Integer.parseInt(structure.getPlinth()));
	               if(structure.getPlinth()!=null && structure.getPlinth().trim().length()>0)
	               {
	                  double plinthf=Double.parseDouble(structure.getPlinth());
	                  pstmt.setDouble(5, plinthf);
	                  logger.info("PLINTH in structuredetails table IS &&&&"+structure.getPlinth()+"-PropertyStructure&&&&&&:"+plinthf);
	               }
	               else
	               {
	                  pstmt.setDouble(5, 0);
	                  logger.info("PLINTH in structuredetails table IS &&&&"+structure.getPlinth());
	               }

	               //logger.info("PLINTH in structuredetails table IS &&&&"+structure.getPlinth()+"-PropertyStructure&&&&&&:"+Double.parseDouble(structure.getPlinth()));
	               logger.info("scheduleno in structuredetails table-PropertyStructure&&&&&&:"+SCHEDULE_NO);
	               pstmt.setString(6, SCHEDULE_NO);
	               pstmt.setString(7, applicationId);
	               pstmt.setString(8, SCHEDULE_NO);
	               logger.info("All Batch statements added successfully - STRUCTURE_DETAILS table");
	               result = pstmt.execute();
	               logger.info("Row inserted into PropertyStructureDetails-STRUCTURE_DETAILS:"+ result+"with PropertyID:"+mData.getPropertyId());
	               con.commit();
	               pstmt.close();
	            }

	         } else {
	            PreparedStatement pstmt = con.prepareStatement("insert into STRUCTURE_DETAILS(ID, FLOOR_NO, STRU_TYPE, UNIT, AGE, PLINTH, SCHEDULE_NO) values (?, ?, ?, ?, ?, ?, ?)");
	            con.setAutoCommit(false);

	            pstmt.setString(1, mData.getPropertyId().substring(0,15));

	            pstmt.setString(2, null);
	            pstmt.setString(3, null);
	            pstmt.setString(4, null);
	            pstmt.setInt(5, 0);
	            pstmt.setInt(6, 0);
	            logger.info("scheduleno in structuredetails with NULL table-PropertyStructure&&&&&&:"+SCHEDULE_NO);
	            //pstmt.setString(7, null);
	            pstmt.setString(7, SCHEDULE_NO);
	            logger.info("All Batch statements added successfully - STRUCTURE_DETAILS table");
	            result = pstmt.execute();
	            logger.info("Rows inserted into PropertyStructureDetails-STRUCTURE_DETAILS:"+ result+"with PropertyID:"+mData.getPropertyId());
	            con.commit();
	            pstmt.close();

	         }



	         return result;

	      }
	      catch(SQLException e) {
	         result=true;
	         logger.error("structure_details table could not be inserted-SQL", e);

	      }

	      catch(Exception e) {
	         result=true;
	         //logger.warn(e.getMessage());
	         logger.error("structure_details table could not be inserted-Exception", e);
	         //e.printStackTrace();

	      }
	      finally {

	         if (con != null)
	            con.close();
	      }
	      return result;
	   }
	 
	

	public static boolean singleInsertScheduleEntry(PropertyStructureDetails mData, int seqNo)
			throws SQLException {
		logger.info("%%%%%% in singleInsertScheduleEntry method %%%%%%" + mData);
		Connection con=OracleDBUtil.getConnection();
		boolean result = false;
		String SCHEDULE_NO=Integer.toString(seqNo);
		try {
		logger.info("in singleInsertScheduleEntry of OraclePropertyStructureDetails-PropertyStructureDetails");
		PreparedStatement pstmt = con.prepareStatement("insert into SCHEDULE_ENTRY(ID, DOOR_NO, PLOT_NO, LAYOUT_NAME, SCHEDULE_NO, WARD_NO, LAYOUT_NO, BLOCK_NO, APT_NAME, FLAT_NO, OLD_HOUSE_NO, TOT_FLOOR, APT_EAST, APT_WEST, APT_NORTH, APT_SOUTH, EXTENT, EAST, WEST, NORTH, SOUTH) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		//PreparedStatement pstmt = con.prepareStatement("insert into SCHEDULE_ENTRY(ID, DOOR_NO, PLOT_NO, LAYOUT_NAME, SCHEDULE_NO, WARD_NO, LAYOUT_NO, BLOCK_NO, APT_NAME, FLAT_NO, OLD_HOUSE_NO, TOT_FLOOR, APT_EAST, APT_WEST, APT_NORTH, APT_SOUTH, EXTENT) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			logger.info("after preparedstatement");
			con.setAutoCommit(false);
				pstmt.setString(1, mData.getPropertyId().substring(0,15));
		//pstmt.setString(1, mData.getPropertyId());
				logger.info("PropertyID" + mData.getPropertyId());
				pstmt.setString(2, mData.getDoorNo());
				pstmt.setString(3, mData.getPlotNo());
				pstmt.setString(4, mData.getLayoutName());
				// should get the mapping from Mongo collection for schedule_no
				//pstmt.setString(5, "1");
				logger.info("scheduleno in scheduleEntry table-PropertyStructure&&&&&&:"+SCHEDULE_NO);
				pstmt.setString(5, SCHEDULE_NO);
				pstmt.setInt(6, Integer.parseInt(mData.getWard()));
				logger.info("WARDNO::::"+mData.getWard());
				pstmt.setString(7, mData.getLayoutNo());
				pstmt.setInt(8, Integer.parseInt(mData.getBlock()));
				pstmt.setString(9, mData.getAppartmentName());
				if(mData.getFlatNo()==null)
					pstmt.setString(10, null);	
				else
				pstmt.setString(10, mData.getFlatNo());
				logger.info("DoorNo:::::"+mData.getDoorNo());
				pstmt.setString(11, mData.getDoorNo());
				pstmt.setString(12, mData.getTotalFloors());
				pstmt.setString(13, mData.getFlatEastBoundry());
				pstmt.setString(14, mData.getFlatWestBoundry());
				pstmt.setString(15, mData.getFlatNorthBoundry());
				pstmt.setString(16, mData.getFlatSouthBoundry());
				logger.info("Data for Conveyed Extent:"+mData.getConveyedExtent());//Data:[ConveyedExtent [extent=98, unit=Y]]
				//pstmt.setInt(17, Integer.parseInt(mData.getConveyedExtent().getExtent()));
				/*if(mData.getConveyedExtent().size()!=0)
				{pstmt.setInt(17, Integer.parseInt(mData.getConveyedExtent().get(0).getExtent()));
				}
				else
				{pstmt.setInt(17, 0);
				}*/
				if(mData.getConveyedExtent()==null || mData.getConveyedExtent().size()==0)
			
					{//pstmt.setInt(17, 0);
					pstmt.setFloat(17, 0);
					}
					
				
				else if(mData.getConveyedExtent().size()>0)
				{//pstmt.setInt(17, Integer.parseInt(mData.getConveyedExtent().get(0).getExtent()));
					List<ConveyedExtent> c=mData.getConveyedExtent();
					//pstmt.setInt(17, Integer.parseInt(c.get(0).getExtent()));
					pstmt.setFloat(17, Float.parseFloat(c.get(0).getExtent()));
					
					//pstmt.setInt(17, Integer.parseInt(mData.getConveyedExtent().get(0).getExtent()));
				}
				pstmt.setString(18, mData.getEastBoundry());
				logger.info("EAST:&&&&&&&&&&&"+mData.getEastBoundry());
				pstmt.setString(19, mData.getWestBoundry());
				pstmt.setString(20, mData.getNorthBoundry());
				pstmt.setString(21, mData.getSouthBoundry());
				logger.info("SOUTH:&&&&&&&&&&&"+mData.getSouthBoundry());
					
				logger.info("All Batch statements added successfully - SCHEDULE_ENTRY table");
				//pstmt.addBatch();
			
			result = pstmt.execute();
			logger.info("Row inserted into OraclePropertyStructureDetails-ScheduleEntryTable" + result+"with propertyID"+mData.getPropertyId().substring(0,21));
		con.commit();
		pstmt.close();
		return result;

	}
		 catch (SQLException e) {
			 result=true;
			 logger.error("Schedule_entry table could not be inserted-SQL", e);					
			} 
		 catch (Exception e) {
			 result=true;
			 logger.error("Schedule_entry table could not be inserted-Exception", e);						
			} 
		finally {
try
			{
				if (con != null)
				con.close();
				}
catch(Exception e){ logger.error("Schedule_entry table could not be inserted-Finally", e);}
							}
		return result;
	}
			
		
	
public static boolean singleDeleteScheduleEntry(String propertyId) throws SQLException {
	Connection con=OracleDBUtil.getConnection();
		
		boolean result = true;
		logger.info("in singleDeleteScheduleEntry method of OraclePropertyStructureDetails-ScheduleEntry table ");

		logger.info("in singleDeleteScheduleEntry method of OraclePropertyStructureDetails-ScheduleEntry table ");

		PreparedStatement pstmt = con.prepareStatement("DELETE FROM schedule_entry where ID=?");
		con.setAutoCommit(false);
		if(pstmt!=null)
		logger.info("pstmt is not null"+pstmt);
		////String propertyId1=propertyId.substring(0,15);
		
		pstmt.setString(1, propertyId);
		logger.info("PropertyID is ******"+propertyId);
		//pstmt.setString(2, schedule_no);
		
		result=pstmt.execute();
		logger.info("$$$$$$$result of delete operation"+result);
		if(result==false)
		{logger.info("deleted");}
		else
		{logger.info("couldnot");}
		logger.info("Single row deleted from schedule_entry " + result + "with ID"+propertyId);
		con.commit();
		pstmt.close();
		con.close();
		return result;
	}

	


public static boolean singleDeleteStructureDetails(String propertyId) throws SQLException {
	Connection con=OracleDBUtil.getConnection();
	
	boolean result = true;

	logger.info("in singleDeleteStructureDetails method of OraclePropertyStructureDetails-ScheduleEntry table ");

	PreparedStatement pstmt = con.prepareStatement("DELETE FROM structure_details where ID=?");
	con.setAutoCommit(false);
	pstmt.setString(1, propertyId);
	result=pstmt.execute();
	logger.info("Single row deleted from structure_details " + result + "with ID"+propertyId);
	con.commit();
	pstmt.close();
	con.close();	
	return result;
}

public static boolean singleInsertStructureDetails(PropertyStructureDetails mData, int seqNo)throws SQLException {
	Connection con=OracleDBUtil.getConnection();
	logger.info("in singleInsertStructureDetails of OraclePropertyStructureDetails-PropertyStructureDetails");
String SCHEDULE_NO=Integer.toString(seqNo);
	boolean result = true;
	try {
		logger.info("PropertyStructureDetails values"+mData.toString());
		logger.info("Values:"+mData.toString());
			
			if (mData.getStructure().size() > 0) {
				for(Structure structure : mData.getStructure())
				{
				PreparedStatement pstmt = con.prepareStatement("insert into STRUCTURE_DETAILS(ID, FLOOR_NO, STRU_TYPE, UNIT, AGE, PLINTH, SCHEDULE_NO) values (?, ?, ?, ?, ?, ?, ?)");
				con.setAutoCommit(false);
			
				pstmt.setString(1, mData.getPropertyId().substring(0,15));
				//If code is given for floorNo, below line should work
				//pstmt.setString(2, String.valueOf(Integer.parseInt(structure.getFloorNo())));
				//pstmt.setString(2, String.valueOf(Integer.parseInt(structure.getFloorNo().substring(0,2))));
				//for floorNo current data ex: floor no-2, only below line will work
				pstmt.setString(2, structure.getFloorNo().substring(0,2));
				//String fno=structure.getFloorNo();
				//if(fno.equals("Floor No-1"))
					//pstmt.setString(2, "1");	
				//pstmt.setString(2, String.valueOf(Integer.parseInt(structure.getFloorNo())));
				pstmt.setString(3, structure.getStructureType().substring(0, 2));
				pstmt.setString(4,structure.getPlinthUnit().substring(0, 1));
				//pstmt.setInt(5, structure.getAge());
				pstmt.setInt(5, Integer.parseInt(structure.getAge()));
				//pstmt.setInt(6, Integer.parseInt(structure.getPlinth().substring(0, 9)));
				//pstmt.setDouble(6, Integer.parseInt(structure.getPlinth().length()>9?structure.getPlinth().substring(0, 9):structure.getPlinth()));
				//pstmt.setDouble(6, Integer.parseInt(structure.getPlinth()));
				if(structure.getPlinth()!=null && structure.getPlinth().trim().length()>0)
				{
					double plinthf=Double.parseDouble(structure.getPlinth());
					pstmt.setDouble(6, plinthf);
					logger.info("PLINTH in structuredetails table IS &&&&"+structure.getPlinth()+"-PropertyStructure&&&&&&:"+plinthf);
				}
				else
				{
					pstmt.setDouble(6, 0);
					logger.info("PLINTH in structuredetails table IS &&&&"+structure.getPlinth());
				}
				
				//logger.info("PLINTH in structuredetails table IS &&&&"+structure.getPlinth()+"-PropertyStructure&&&&&&:"+Double.parseDouble(structure.getPlinth()));
				logger.info("scheduleno in structuredetails table-PropertyStructure&&&&&&:"+SCHEDULE_NO);
				pstmt.setString(7, SCHEDULE_NO);
				logger.info("All Batch statements added successfully - STRUCTURE_DETAILS table");
				result = pstmt.execute();
				logger.info("Row inserted into PropertyStructureDetails-STRUCTURE_DETAILS:"+ result+"with PropertyID:"+mData.getPropertyId());
				con.commit();
				pstmt.close();
				}
				
			} else {
				PreparedStatement pstmt = con.prepareStatement("insert into STRUCTURE_DETAILS(ID, FLOOR_NO, STRU_TYPE, UNIT, AGE, PLINTH, SCHEDULE_NO) values (?, ?, ?, ?, ?, ?, ?)");
				con.setAutoCommit(false);
			
				pstmt.setString(1, mData.getPropertyId().substring(0,15));
		
				pstmt.setString(2, null);
				pstmt.setString(3, null);
				pstmt.setString(4, null);
				pstmt.setInt(5, 0);
				pstmt.setInt(6, 0);
				logger.info("scheduleno in structuredetails with NULL table-PropertyStructure&&&&&&:"+SCHEDULE_NO);
				//pstmt.setString(7, null);
				pstmt.setString(7, SCHEDULE_NO);
				logger.info("All Batch statements added successfully - STRUCTURE_DETAILS table");
				result = pstmt.execute();
				logger.info("Rows inserted into PropertyStructureDetails-STRUCTURE_DETAILS:"+ result+"with PropertyID:"+mData.getPropertyId());
				con.commit();
				pstmt.close();
				
			}
			
			
			
			return result;

		}
	catch(SQLException e) {
		 result=true;
		 logger.error("structure_details table could not be inserted-SQL", e);
		
	} 
	
	 catch(Exception e) {
		 result=true;
		//logger.warn(e.getMessage());
		 logger.error("structure_details table could not be inserted-Exception", e);
		//e.printStackTrace();
		
	} 
	finally {

		if (con != null)
			con.close();
			}
	return result;
}

}




