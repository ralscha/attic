package ch.rasc.travellog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.codahale.passpol.BreachDatabase;
import com.codahale.passpol.PasswordPolicy;
import com.samskivert.mustache.Mustache;

@Configuration
public class AppConfig {

    @Bean
    Mustache.Compiler mustacheCompiler() {
		return Mustache.compiler();
	}

    @Bean
    PasswordPolicy passwordPolicy() {
		return new PasswordPolicy(BreachDatabase.top100K(), 8, 256);
	}

    @Bean
    PasswordEncoder passwordEncoder() {
		return new Argon2PasswordEncoder(16, 32, 8, 1 << 16, 4);
	}

}
