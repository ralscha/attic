package ch.rasc.pandora.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "ch.rasc.extdirectspring", "ch.rasc.pandora" })
public class ComponentConfig {
	// nothing here
}