package in.gov.ap.igrs.datasync.app.service;

import java.util.Map;

public interface IGRSDataSyncService {

	Map<String, String> dataSyncFromMongoToOracle(String applicationNumber);
	Map<String, String> dataSyncFromOracleToMongo(String applicationNumber);
	Map<String, String> dataSyncFromMongoToOracleProd(String applicationNumber);	
	
}
