package in.gov.ap.igrs.datasync.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "in.gov.ap.igrs.datasync.*" })
public class IgrsDataSyncIntegrationApp {

	public static void main(String[] args) {
		SpringApplication.run(IgrsDataSyncIntegrationApp.class, args);
	}

}
