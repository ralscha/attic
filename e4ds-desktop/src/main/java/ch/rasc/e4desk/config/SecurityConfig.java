package ch.rasc.e4desk.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter.XFrameOptionsMode;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig {

	@Configuration
	@Order(1)
	public static class CachableWebSecurityConfigurationAdapter
			extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			//@formatter:off
			http
				.antMatcher("/i18n*")
				.headers()
					.disable()
				.authorizeRequests()
					.antMatchers("/i18n*").permitAll()
					.and()
				.csrf()
					.disable();
			//@formatter:on
		}
	}

	@Configuration
	public static class DefaultWebSecurityConfigurerAdapter
			extends WebSecurityConfigurerAdapter {

		@Autowired
		private UserDetailsService userDetailsService;

		@Autowired
		private DataSource dataSource;

		@Bean
		public PasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
		}

		@Autowired
		public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(this.userDetailsService)
					.passwordEncoder(passwordEncoder());
		}

		@Override
		public void configure(WebSecurity builder) throws Exception {
			builder.ignoring().antMatchers("/resources/**", "/favicon.ico", "/api*.js");
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
			    .antMatchers("/login*", "/app/ux/window/Notification.js").permitAll()
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

}
