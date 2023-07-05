package com.ca.oracle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.cr.main.OracleConfigLoader;

public class OracleDBUtil {

	static Logger logger = Logger.getLogger(OracleDBUtil.class.getName());
	
	public static Connection getConnection() {
		logger.info("In OracleDBUtil method connection. Trying to establish oracle DB Connection");
		Connection con = null;
		logger.info("In OracleDBUtil method connection. Before try block");
		
		try {
			logger.info("In OracleDBUtil method connection,inside firstline of try block");
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			logger.info("Before creating connection object");
			con = DriverManager.getConnection("jdbc:oracle:thin:@apcca-scan.apsdc.ap.gov.in:1521/apcca?user=SYSTEM&password=welcome1");
			logger.info("After creating connection object"+con);
			con.createStatement().execute("alter session set current_schema=preregistration");
			if (con != null) {
				logger.info("Oracle Connection got established");
			} else {
				logger.info("Connection did not got established");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
						logger.error("OracleDBUtil class Connection could not be estalished Exception - SQL", e);
		}
		return con;

	}

}
