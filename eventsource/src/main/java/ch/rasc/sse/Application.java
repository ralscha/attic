package ch.rasc.sse;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import ch.rasc.sse.simple.Html5RocksExample;
import ch.rasc.sse.simple.LongPollingServlet;
import ch.rasc.sse.simple.SimpleServlet;
import ch.rasc.sse.simple.StreamingServlet;

@SpringBootApplication
@EnableScheduling
public class Application {

	public static void main(String[] args) throws Exception {
		new SpringApplicationBuilder(Application.class).run(args);
	}

	@Bean
	ServletRegistrationBean simplestreaming() {
		return new ServletRegistrationBean(new StreamingServlet(), "/simplestreaming");
	}

	@Bean
	ServletRegistrationBean sse() {
		return new ServletRegistrationBean(new Html5RocksExample(), "/sse");
	}

	@Bean
	ServletRegistrationBean simplelong() {
		return new ServletRegistrationBean(new LongPollingServlet(), "/simplelong");
	}

	@Bean
	ServletRegistrationBean simpleservlet() {
		return new ServletRegistrationBean(new SimpleServlet(), "/simpleservlet");
	}

	// @Bean
	// public SSEventMessageConverter sseventMessageConverter() {
	// return new SSEventMessageConverter();
	// }

}