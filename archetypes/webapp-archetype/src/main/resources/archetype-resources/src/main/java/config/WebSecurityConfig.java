#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.config;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		  .authorizeRequests()
		    .antMatchers("/", "/index.html").permitAll()
		    .anyRequest().authenticated()
		  .and()
		    .formLogin().permitAll()
		  .and()
		    .logout().deleteCookies("JSESSIONID").permitAll()
		  .and()
		    .rememberMe();
	}

}