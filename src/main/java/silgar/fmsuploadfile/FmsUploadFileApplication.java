package silgar.fmsuploadfile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("silgar.fmsuploadfile.service")
public class FmsUploadFileApplication {

	public static void main(String[] args) {
		SpringApplication.run(FmsUploadFileApplication.class, args);
	}

}
