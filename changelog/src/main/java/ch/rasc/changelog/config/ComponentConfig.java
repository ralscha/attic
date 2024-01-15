package ch.rasc.changelog.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = { "ch.ralscha.extdirectspring", "ch.rasc.changelog" })
@PropertySource("classpath:/version.properties")
public class ComponentConfig {
	// nothing here
}