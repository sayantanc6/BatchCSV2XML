package dummy;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class BatchCSV2XMLDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(BatchCSV2XMLDemoApplication.class, args);
	}

}
