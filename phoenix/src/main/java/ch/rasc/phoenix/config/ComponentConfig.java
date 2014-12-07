package ch.rasc.phoenix.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@ComponentScan(basePackages = { "ch.ralscha.extdirectspring", "ch.rasc.phoenix" })
@EnableScheduling
public class ComponentConfig {
	// nothing here
}