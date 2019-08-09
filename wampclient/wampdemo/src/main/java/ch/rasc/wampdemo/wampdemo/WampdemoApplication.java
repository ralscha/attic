package ch.rasc.wampdemo.wampdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import ch.rasc.wamp2spring.servlet.EnableServletWamp;

@SpringBootApplication
@EnableScheduling
@EnableServletWamp
public class WampdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(WampdemoApplication.class, args);
	}
}
