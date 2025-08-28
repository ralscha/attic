package ch.rasc.travellog.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.context.SecurityContextHolderFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
class SecurityConfig {

	private final AppLogoutSuccessHandler appLogoutSuccessHandler;

	private final AuthHeaderFilter authHeaderFilter;

	public SecurityConfig(AppLogoutSuccessHandler appLogoutSuccessHandler,
			SessionCacheService sessionCacheService) {
		this.appLogoutSuccessHandler = appLogoutSuccessHandler;
		this.authHeaderFilter = new AuthHeaderFilter(sessionCacheService);
	}

	@Bean
	AuthenticationManager authenticationManager() {
		return authentication -> {
			throw new AuthenticationServiceException(
					"Cannot authenticate " + authentication);
		};
	}

	@Scope("prototype")
	@Bean
	MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
		return new MvcRequestMatcher.Builder(introspector);
	}

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http,
                                 MvcRequestMatcher.Builder mvc) throws Exception {
		var pathPrefix = "/be";

	// @formatter:off
    http
    .headers(customizer -> customizer.defaultsDisabled().cacheControl(Customizer.withDefaults()))
    .sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    .csrf(CsrfConfigurer::disable)
  	.logout(customizer -> {
  	  customizer.logoutSuccessHandler(this.appLogoutSuccessHandler);
  	  customizer.logoutUrl(pathPrefix + "/logout");
  	})
    .authorizeHttpRequests(customizer -> {
      customizer.requestMatchers(
			  mvc.pattern(pathPrefix + "/login"),
			  mvc.pattern(pathPrefix + "/signup"),
			  mvc.pattern(pathPrefix + "/logview/*"),
			  mvc.pattern(pathPrefix + "/logview_name/*"),
			  mvc.pattern(pathPrefix + "/confirm-signup"),
			  mvc.pattern(pathPrefix + "/reset-password-request"),
			  mvc.pattern(pathPrefix + "/reset-password"),
			  mvc.pattern(pathPrefix + "/confirm-email-change"),
			  mvc.pattern(pathPrefix + "/client-error"),
			  mvc.pattern(pathPrefix + "/csp-error"))
            .permitAll()
          .requestMatchers(mvc.pattern(pathPrefix + "/admin/**")).hasAuthority("ADMIN")
      	  .anyRequest().authenticated();
    })
    .exceptionHandling(customizer -> customizer.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
    .addFilterAfter(this.authHeaderFilter, SecurityContextHolderFilter.class);
    // @formatter:on
		return http.build();
	}

}
