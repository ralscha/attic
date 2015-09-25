package ch.rasc.webapp;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE + 10)
class GlobalAuthenticationConfig extends GlobalAuthenticationConfigurerAdapter {

	@Override
	public void init(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("jimi").password("jimispassword")
				.roles("USER", "ADMIN").and().withUser("bob").password("bobspassword")
				.roles("USER");
	}

}