package ch.rasc.proto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

import ch.ralscha.extdirectspring.ExtDirectSpring;

@Configuration
@ComponentScan(basePackageClasses = { ExtDirectSpring.class, ProtoMain.class })
@EnableAutoConfiguration
@EnableScheduling
@PropertySource("classpath:/version.properties")
public class ProtoMain extends SpringBootServletInitializer {

	// -Dspring.profiles.active=development
	// -Dspring.profiles.active=compression
	public static void main(String[] args) throws Exception {
		SpringApplication app = new SpringApplication(ProtoMain.class);
		app.run(args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ProtoMain.class);
	}

}