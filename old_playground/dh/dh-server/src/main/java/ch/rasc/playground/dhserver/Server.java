package ch.rasc.playground.dhserver;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.HttpEncodingAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = { DispatcherServletAutoConfiguration.class,
		HttpEncodingAutoConfiguration.class, WebMvcAutoConfiguration.class })
public class Server {

	public static void main(String[] args) throws Exception {
		new SpringApplicationBuilder(Server.class).run(args);
	}

	@Bean
	SendServlet sendServlet() {
		return new SendServlet();
	}
}
