package ch.rasc.packt.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
@ComponentScan(basePackages = { "ch.ralscha.extdirectspring", "ch.rasc.packt.config",
		"ch.rasc.packt.security", "ch.rasc.packt.service", "ch.rasc.packt.web" })
@PropertySource("classpath:/version.properties")
public class ComponentConfig {

	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:messages");
		messageSource.setFallbackToSystemLocale(false);
		return messageSource;
	}

}