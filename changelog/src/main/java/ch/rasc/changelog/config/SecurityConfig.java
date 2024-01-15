package ch.rasc.changelog.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter.XFrameOptionsMode;

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
		return new StandardPasswordEncoder();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(this.userDetailsService)
				.passwordEncoder(passwordEncoder());
	}

	@Override
	public void configure(WebSecurity builder) throws Exception {
		builder.ignoring().antMatchers("/resources/**", "/favicon.ico");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		JdbcTokenRepositoryImpl tokenRepo = new JdbcTokenRepositoryImpl();
		tokenRepo.setDataSource(this.dataSource);
		tokenRepo.setCreateTableOnStartup(false);
		tokenRepo.afterPropertiesSet();

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
		    .antMatchers("/i18n*", "/login*", "/app/ux/window/Notification.js").permitAll()
			.anyRequest().authenticated()
		    .and()
	      .formLogin()
	        .loginPage("/login.html")
	        .defaultSuccessUrl("/index.html", true)
	        .permitAll()
		    .and()
		  .logout()
		    .deleteCookies("JSESSIONID")
		    .permitAll()
		    .and()
		  .rememberMe()
		    .tokenRepository(tokenRepo)
		    .and()
		  .csrf()
		    .disable();
		//@formatter:on
	}

}
