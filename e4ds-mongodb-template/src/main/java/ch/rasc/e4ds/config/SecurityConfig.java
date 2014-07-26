package ch.rasc.e4ds.config;

import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.authentication.encoding.PasswordEncoder;

@Configuration
@ImportResource("classpath:ch/rasc/e4ds/config/security.xml")
public class SecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		org.jasypt.springsecurity3.authentication.encoding.PasswordEncoder pe = new org.jasypt.springsecurity3.authentication.encoding.PasswordEncoder();
		pe.setPasswordEncryptor(new StrongPasswordEncryptor());
		return pe;
	}

}
