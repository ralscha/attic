package ch.rasc.musicsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import ch.ralscha.extdirectspring.ExtDirectSpring;
import ch.ralscha.extdirectspring.controller.ApiController;

@Configuration
@ComponentScan(basePackageClasses = { ExtDirectSpring.class, Application.class },
		excludeFilters = { @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
				value = ApiController.class) })
@EnableAutoConfiguration
public class Application {

	public static void main(String[] args) {
		// -Dspring.profiles.active=development
		SpringApplication.run(Application.class, args);
	}

}
