package ch.ralscha.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

	@Bean
	public MyService myService() {
		return new MyServiceImpl();
	}
}
