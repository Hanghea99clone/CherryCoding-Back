package clone.cherrycoding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CherryCodingApplication {

	public static void main(String[] args) {
		SpringApplication.run(CherryCodingApplication.class, args);
	}

}
