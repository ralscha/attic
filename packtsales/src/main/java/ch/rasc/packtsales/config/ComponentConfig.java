package ch.rasc.packtsales.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "ch.ralscha.extdirectspring", "ch.rasc.packtsales" })
public class ComponentConfig {
	// nothing here
}