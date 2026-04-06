package ch.rasc.session;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.ExpiringSession;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.SessionRepository;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;
import org.springframework.session.web.http.SessionRepositoryFilter;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableScheduling
public class Application extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public SessionRepository<ExpiringSession> sessionRepository() {
		return new MapSessionRepository();
	}

	// @Bean
	// public ServletContextInitializer servletContextInitializer() {
	// return servletContext -> {
	// servletContext.addFilter("sessionFilter", DelegatingFilterProxy.class)
	// .addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), false,
	// "/*");
	// };
	// }

	@Bean
	public SessionRepositoryFilter<ExpiringSession> sessionFilter(
			SessionRepository<ExpiringSession> sessionRepository) {
		SessionRepositoryFilter<ExpiringSession> filter = new SessionRepositoryFilter<>(
				sessionRepository);
		filter.setHttpSessionStrategy(new HeaderHttpSessionStrategy());
		return filter;
	}

	// @Bean
	// public FilterRegistrationBean sessionFilter(SessionRepositoryFilter<Session>
	// sessionRepositoryFilter) {
	// FilterRegistrationBean filterRegBean = new FilterRegistrationBean();
	// filterRegBean.setFilter(sessionRepositoryFilter);
	// return filterRegBean;
	// }

}
