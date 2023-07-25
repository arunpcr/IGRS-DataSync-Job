package in.gov.ap.igrs.datasync.app.oracle;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

//import org.apache.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OracleConfigLoader {
	
	final static Logger logger = LogManager.getLogger(OracleConfigLoader.class.getClass());
	 private static Properties properties;

	    static {
	        loadConfig();
	    }

	    private static void loadConfig() {
	        properties = new Properties();
	        String operatingSystem = System.getProperty("os.name").toLowerCase();

	        if (operatingSystem.contains("win")) 
	        {logger.info("operating System Windows");
	        // Load the first configuration file
	        loadConfigFile("D:\\project-backups\\JARS\\oracle.properties");
	        }
	        else
	        {logger.info("operating System Linux");
	        // Load the first configuration file
	        loadConfigFile("/root/sync/igrs/oracle.properties");
	        loadConfigFile("/home/igrs/DataSync/oracle.properties");
	        }
        
	    }

	    private static void loadConfigFile(String configFile) {
	        FileInputStream fis = null;

	        try {
	            fis = new FileInputStream(configFile);
	            properties.load(fis);
	        } catch (IOException e) {
	            logger.error("Could not load Oracle Properties file",e);
	        } finally {
	            if (fis != null) {
	                try {
	                    fis.close();
	                } catch (IOException e) {
	                	 logger.error("Could not load Oracle Properties file in finally block",e);
	                }
	            }
	        }
	    }

	    public static String getProperty(String key) {
	        return properties.getProperty(key);
	    }

}
