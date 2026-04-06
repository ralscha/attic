package ch.rasc.eds.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import ch.ralscha.extdirectspring.ExtDirectSpring;
import ch.ralscha.extdirectspring.controller.ApiController;

@Configuration
@ComponentScan(basePackageClasses = { ExtDirectSpring.class, Application.class },
		excludeFilters = { @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
				value = ApiController.class) })
@EnableAutoConfiguration(exclude = SpringDataWebAutoConfiguration.class)
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
