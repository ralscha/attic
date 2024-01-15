package ch.rasc.changelog.migration;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

//@Configuration
//@ComponentScan(basePackages = { "ch.rasc.changelog" })
//@Import(value = DataConfig.class)
public class Config {

	// @Bean
	public PasswordEncoder passwordEncoder() {
		return new StandardPasswordEncoder();
	}

}
