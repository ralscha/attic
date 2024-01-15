package ch.rasc.tttracker;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import ch.rasc.tttracker.config.SecurityConfiguration;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackageClasses = TttStarter.class,
		basePackages = "ch.ralscha.extdirectspring")
@EnableGlobalMethodSecurity(securedEnabled = false, prePostEnabled = true)
public class TttStarter {

	public static void main(String[] args) throws Exception {
		new SpringApplicationBuilder(TttStarter.class, SecurityConfiguration.class)
				.run(args);
	}
}