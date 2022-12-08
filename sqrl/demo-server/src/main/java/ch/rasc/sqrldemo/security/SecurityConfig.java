package ch.rasc.sqrldemo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return authentication -> {
			throw new AuthenticationServiceException(
					"Cannot authenticate " + authentication);
		};
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable)
				.authorizeRequests(customizer -> {
					customizer.antMatchers("/sqrl", "/sqrl-nut", "/sqrl-polling/**")
							.permitAll().anyRequest().authenticated();
				}).logout(customizer -> customizer.logoutSuccessHandler(
						new HttpStatusReturningLogoutSuccessHandler()));
	}

}
