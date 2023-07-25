package in.gov.ap.igrs.datasync.app.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import in.gov.ap.igrs.datasync.app.service.IGRSDataSyncService;


@RestController
public class IGRSDataSyncRestController {
	
	@Autowired
	private IGRSDataSyncService iGRSDataSyncService;
	
	@PostMapping(value = "/dataSyncFromMongoToOracle/{appNumber}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public Object dataSyncFromMongoToOracle(@PathVariable(required = true,value = "appNumber") String appNumber ) throws Exception{
		System.out.println("Inside of dataSyncFromMongoToOracle ::: ");
		Map<String, String> responseObject = iGRSDataSyncService.dataSyncFromMongoToOracle(appNumber);
		System.out.println("End of dataSyncFromMongoToOracle ::: ");
		return ResponseEntity.ok(responseObject);
	}
	@GetMapping(value = "/data/hre", produces = {MediaType.APPLICATION_JSON_VALUE})
	public String dataSync() throws Exception{
		System.out.println("Inside of dataSyncFromMongoToOracle ::: ");
		return "hello, h r u sir?";
	}
	@PostMapping(value = "/dataSyncFromOracleToMongo/{appNumber}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public Object dataSyncFromOracleToMongo(@PathVariable(required = true,value = "appNumber") String appNumber ) throws Exception{
		System.out.println("Inside of dataSyncFromOracleToMongo ::: ");
		Map<String, String> responseObject = iGRSDataSyncService.dataSyncFromOracleToMongo(appNumber);
		System.out.println("End of dataSyncFromMongoToOracle ::: ");
		return ResponseEntity.ok(responseObject);
	}
	@PostMapping(value = "/dataSyncFromMongoToOracleProd/{appNumber}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public Object dataSyncFromMongoToOracleProd(@PathVariable(required = true,value = "appNumber") String appNumber ) throws Exception{
		System.out.println("Inside of dataSyncFromMongoToOracleProd ::: ");
		Map<String, String> responseObject = iGRSDataSyncService.dataSyncFromMongoToOracleProd(appNumber);
		System.out.println("End of dataSyncFromMongoToOracleProd ::: ");
		return ResponseEntity.ok(responseObject);
	}
}
