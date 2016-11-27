package ch.rasc.e4desk.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = { "ch.ralscha.extdirectspring", "ch.rasc.e4desk.config",
		"ch.rasc.e4desk.schedule", "ch.rasc.e4desk.security", "ch.rasc.e4desk.service",
		"ch.rasc.e4desk.web" })
@PropertySource("classpath:/version.properties")
public class ComponentConfig {
	// nothing here
}