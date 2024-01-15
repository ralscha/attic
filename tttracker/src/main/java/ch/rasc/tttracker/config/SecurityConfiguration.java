package ch.rasc.tttracker.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth,
			UserDetailsService userDetailsService) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	// @Override
	// @Bean
	// protected UserDetailsService userDetailsService() {
	// return new JpaUserDetailsService(userRepository);
	// }

	// @Override
	// public void configure(WebSecurity builder) throws Exception {
	// builder.ignoring().antMatchers("/resources/**", "/favicon.ico",
	// "/api*.js");
	// }
	//
	// @Override
	// protected void configure(HttpSecurity http) throws Exception {
	//
	// //@formatter:off
	// http
	// .headers()
	// .contentTypeOptions()
	// .xssProtection()
	// .cacheControl()
	// .httpStrictTransportSecurity()
	// .addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsMode.SAMEORIGIN))
	// .and()
	// .authorizeRequests()
	// .anyRequest().permitAll()
	// .and()
	// .logout()
	// .deleteCookies("JSESSIONID")
	// .permitAll()
	// .and()
	// .csrf()
	// .disable();
	// //@formatter:on
	// }

}