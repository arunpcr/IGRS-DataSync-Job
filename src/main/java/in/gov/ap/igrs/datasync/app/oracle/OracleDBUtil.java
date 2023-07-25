package in.gov.ap.igrs.datasync.app.oracle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OracleDBUtil {
	
		final static Logger logger = LogManager.getLogger(OracleDBUtil.class.getClass());
		private static String oraUrl;
		
		public static void setOraUrl(String oraUrl) {
			OracleDBUtil.oraUrl = oraUrl;
		}
		
		public static Connection getConnection() {
		logger.info("In OracleDBUtil method connection. Trying to establish oracle DB Connection");
		//logger.info("Oracle JDBC url picked is :::::"+oral);
		Connection con = null;
		logger.info(" before try block in getConnection method");
		//String dbUrl= OracleConfigLoader.getProperty("dbUrl"); 
		

		try {
			logger.info("In try block getConnection method");			
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			logger.info("After DriverManager.registerDriver method");
			//logger.info("oracle url picked is"+oraUrl);
			//con=DriverManager.getConnection(oraUrl);
			//current syn service is pointing to this db in stage environment
			con = DriverManager.getConnection("jdbc:oracle:thin:@10.10.120.125:1521/hocca?user=system&password=welcome1");
			//in local environment pointing to below ip(sync service)
			//con = DriverManager.getConnection("jdbc:oracle:thin:@117.250.201.44:1521/hocca?user=system&password=welcome1");
			//new db change created by pramod garu(in sync currently pointing to production-july15)
			//con = DriverManager.getConnection("jdbc:oracle:thin:@10.252.114.207:1521/aptest?user=system&password=welcome333");
			//new db pointed by ID based sync-pramod garu-july 21
			//con = DriverManager.getConnection("jdbc:oracle:thin:@10.96.47.241:1521/aptest?user=system&password=welcome123");
			//logger.info("after vijaya");
			//con = DriverManager.getConnection("jdbc:oracle:thin:@10.252.114.207:1521/aptest?user=system&password=welcome333");
			//con = DriverManager.getConnection("oracleurl");
			//earlier sync service was pointing to this database
			//con = DriverManager.getConnection("jdbc:oracle:thin:@10.252.114.207:1521/hocca?user=system&password=welcome1");
			//con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/ORCL2?user=c##vijsuvarna&password=abcd12345");
			con.createStatement().execute("alter session set current_schema=preregistration");
			
			logger.info("After calling DriverManager.getConnection method");
			if (con != null) {
				logger.info("Oracle Connection got established");
			} else {
				logger.info("Connection did not got established");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("Oracle connection could not be established-SQL",e);
					}
		return con;
	}
	
	
	}


