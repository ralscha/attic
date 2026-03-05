package ch.rasc.webauthn.security;

import static ch.rasc.javersdemo.db.tables.AppUser.APP_USER;

import java.util.List;
import java.util.Set;

import org.jooq.DSLContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.webauthn.management.PublicKeyCredentialUserEntityRepository;
import org.springframework.security.web.webauthn.management.UserCredentialRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import ch.rasc.javersdemo.db.tables.records.AppUserRecord;
import ch.rasc.webauthn.AppProperties;

@Configuration
public class SecurityConfig {

  @Bean
  PublicKeyCredentialUserEntityRepository jooqPublicKeyCredentialUserEntityRepository(
      DSLContext dsl) {
    return new JooqPublicKeyCredentialUserEntityRepository(dsl);
  }

  @Bean
  UserCredentialRepository jdbcUserCredentialRepository(DSLContext dsl) {
    return new JooqUserCredentialRepository(dsl);
  }

  @Bean
  UserDetailsService userDetailsService(DSLContext dsl) {
    return username -> {
      AppUserRecord record = dsl.selectFrom(APP_USER)
          .where(APP_USER.USERNAME.eq(username)).fetchOne();

      if (record == null) {
        throw new UsernameNotFoundException("User not found: " + username);
      }

      return new AppUserDetail(record, new SimpleGrantedAuthority("ROLE_USER"));
    };
  }

  @Bean
  SecurityFilterChain filterChain(AppProperties appProperties, HttpSecurity http)
      throws Exception {
    return http
        .csrf(c -> c.disable())
        .cors(Customizer.withDefaults())
        .authorizeHttpRequests(authz -> authz
            .requestMatchers("/secret").authenticated()
            .anyRequest().permitAll())
        .webAuthn(webAuthn -> webAuthn.rpName(appProperties.relyingPartyName())
            .rpId(appProperties.relyingPartyId())
            .allowedOrigins(appProperties.relyingPartyOrigins()))
        .build();
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource(AppProperties appProperties) {
    CorsConfiguration cors = new CorsConfiguration();

    Set<String> origins = appProperties.corsOrigins();
    if (origins == null || origins.isEmpty()) {
      origins = appProperties.relyingPartyOrigins();
    }

    cors.setAllowedOrigins(List.copyOf(origins));
    cors.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    cors.setAllowedHeaders(List.of("*"));
    cors.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", cors);
    return source;
  }

}
