package ch.rasc.ab.config;

import javax.validation.Validator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
@ComponentScan(basePackages = { "ch.ralscha.extdirectspring", "ch.rasc.ab" })
public class ComponentConfig {

	@Bean
	public Validator validator() {
		return new LocalValidatorFactoryBean();
	}

}