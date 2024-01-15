package ch.rasc.ab.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private DataSource dataSource;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void configure(WebSecurity builder) throws Exception {
		builder.ignoring().antMatchers("/favicon.ico");
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(this.userDetailsService)
				.passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		//@formatter:off
		http
		.headers()
		    .contentTypeOptions()
		    .xssProtection()
		    .cacheControl()
		    .httpStrictTransportSecurity()
		    .addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsMode.SAMEORIGIN))
		    .and()
		  .authorizeRequests()
			.anyRequest().permitAll()
		    .and()
	      .formLogin()
	        .loginPage("/index.html")
	        .permitAll()
		    .and()
		  .logout()
		    .deleteCookies("JSESSIONID")
		    .permitAll()
		    .and()
		  .rememberMe()
		    .and()
		  .csrf()
		    .disable();
		//@formatter:on
	}

}
