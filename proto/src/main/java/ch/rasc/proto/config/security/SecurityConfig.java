package ch.rasc.proto.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter.XFrameOptionsMode;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private RememberMeServices rememberMeServices;

	@Value("${spring.security.rememberme.cookie.key}")
	private String rememberMeKey;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth,
			UserDetailsService userDetailsService, PasswordEncoder passwordEncoder)
			throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}

	@Override
	public void configure(WebSecurity builder) throws Exception {
		builder.ignoring().antMatchers("/resources/**", "/app*.css", "/**/favicon.ico",
				"/browserconfig.xml");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http
		  .headers()
		    .contentTypeOptions()
			.xssProtection()
			//.cacheControl()
			.httpStrictTransportSecurity()
			.addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsMode.SAMEORIGIN))
		    .and()
		  .authorizeRequests()
		    .antMatchers("/login*.js", "/i18n*.js", "/passwordreset*.js", "/passwordresetEmail", "/passwordreset.html", "/passwordreset.action").permitAll()
		    .anyRequest().authenticated()
		    .and()
		  .rememberMe()
            .rememberMeServices(this.rememberMeServices)
            .key(this.rememberMeKey)
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
		  .csrf()
		    .disable();
		// @formatter:on
	}

}