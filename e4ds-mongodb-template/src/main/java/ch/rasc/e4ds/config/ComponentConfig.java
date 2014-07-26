package ch.rasc.e4ds.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = { "ch.ralscha.extdirectspring", "ch.rasc.e4ds" })
@PropertySource({ "version.properties" })
public class ComponentConfig {
	// nothing here
}