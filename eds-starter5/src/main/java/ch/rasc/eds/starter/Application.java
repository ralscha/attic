package ch.rasc.eds.starter;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;

import ch.ralscha.extdirectspring.ExtDirectSpring;
import ch.rasc.eds.starter.entity.User;

@Configuration
@ComponentScan(basePackageClasses = { ExtDirectSpring.class, Application.class })
@EnableAutoConfiguration
@EnableScheduling
@PropertySource("classpath:/version.properties")
@EntityScan(basePackageClasses = { ch.rasc.edsutil.entity.AbstractPersistable.class,
		User.class })
public class Application extends SpringBootServletInitializer {

	// -Dspring.profiles.active=development
	public static void main(String[] args) throws Exception {
		SpringApplication app = new SpringApplication(Application.class);
		app.run(args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}

	@Autowired
	private Environment environment;

	@PostConstruct
	public void showProfile() {
		LoggerFactory.getLogger(Application.class).info("Active Spring Profiles: {}",
				Arrays.toString(this.environment.getActiveProfiles()));
	}

}