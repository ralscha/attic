package ch.rasc.golb.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import ch.rasc.golb.config.AppProperties;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private RememberMeServices rememberMeServices;

	@Autowired
	private AppProperties appProperties;

	@Autowired
	private AuthenticationSuccessHandler authenticationSuccessHandler;

	@Autowired
	private Environment environment;

	@Override
	public void configure(WebSecurity web) throws Exception {
		if (this.environment.acceptsProfiles("development")) {
			web.ignoring().antMatchers("/resources/**", "/build/**", "/ext/**",
					"/**/*.js", "/**/*.css", "/bootstrap.json", "/robots.txt",
					"/binary/**", "/sitemap.xml", "/favicon.ico", "/p/*", "/preview/*",
					"/", "/feedback/*", "/feedback_ok.html", "/submitFeedback",
					"/index.html", "/cspreports", "/rss2", "/atom1");
		}
		else {
			web.ignoring().antMatchers("/resources/**", "/app.js", "/app.json",
					"/app.jsonp", "/ext-locale-de.js", "/i18n-en.js", "/robots.txt",
					"/microloader.jsp", "/cache.appcache", "/binary/**", "/feedback/*",
					"/feedback_ok.html", "/submitFeedback", "/sitemap.xml",
					"/favicon.ico", "/p/*", "/preview/*", "/", "/index.html",
					"/cspreports", "/rss2", "/atom1");
		}
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth,
			UserDetailsService userDetailsService, PasswordEncoder passwordEncoder)
			throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http
		  .authorizeRequests()
		    .antMatchers("/admin.html", "/csrf", "/router").permitAll()
		    .antMatchers("/info", "/health").permitAll()
		    .anyRequest().authenticated()
		    .and()
		  .rememberMe()
            .rememberMeServices(this.rememberMeServices)
            .key(this.appProperties.getRemembermeCookieKey())
		    .and()
		  .formLogin()
            .successHandler(this.authenticationSuccessHandler)
            .failureHandler(new JsonAuthFailureHandler())
		    .permitAll()
		    .and()
		  .logout()
            .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
            .deleteCookies("JSESSIONID")
		    .permitAll()
		    .and()
		  .exceptionHandling()
            .authenticationEntryPoint(new Http401UnauthorizedEntryPoint());
		// @formatter:on
	}

}