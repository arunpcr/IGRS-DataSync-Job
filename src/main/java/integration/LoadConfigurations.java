package integration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class LoadConfigurations {
	
	static Logger logger = Logger.getLogger(LoadConfigurations.class.getName());

	public static Properties loadConfigurations(String fileName) {
		File file = new File(fileName);
		logger.info("File that has been loaded:"+file);
		Properties props = new Properties();
		try {
			props.load(new FileInputStream(file));
			logger.info("File that has been loaded successfully without any issues:"+file);

		}

		catch (IOException e) {
			e.printStackTrace();
			logger.warn(e.getMessage());
		}
		return props;
	}

}
