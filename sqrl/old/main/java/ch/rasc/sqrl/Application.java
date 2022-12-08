package ch.rasc.sqrl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static final Logger log = LoggerFactory.getLogger("app");

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
