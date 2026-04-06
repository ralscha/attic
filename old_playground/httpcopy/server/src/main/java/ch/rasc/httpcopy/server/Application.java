package ch.rasc.httpcopy.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	Protobuf3HttpMessageConverter protobufHttpMessageConverter() {
		return new Protobuf3HttpMessageConverter();
	}
}
