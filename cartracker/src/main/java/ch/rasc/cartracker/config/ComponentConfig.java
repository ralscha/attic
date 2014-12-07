package ch.rasc.cartracker.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@ComponentScan(basePackages = { "ch.ralscha.extdirectspring", "ch.rasc.cartracker" })
@EnableScheduling
public class ComponentConfig {
	// nothing here
}