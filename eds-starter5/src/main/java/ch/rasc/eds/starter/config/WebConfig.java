package ch.rasc.eds.starter.config;

import java.io.IOException;
import java.util.Collections;
import java.util.Locale;

import javax.servlet.Filter;

import org.slf4j.LoggerFactory;
import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.boot.context.embedded.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.servlet.LocaleResolver;

import com.fasterxml.jackson.databind.ObjectMapper;

import ch.ralscha.extdirectspring.util.JsonHandler;
import ch.rasc.eds.starter.Application;
import ch.rasc.eds.starter.web.MdcFilter;
import ch.rasc.edsutil.optimizer.WebResourceProcessor;

@Configuration
public class WebConfig {

	@Bean
	public ch.ralscha.extdirectspring.controller.Configuration configuration() {
		ch.ralscha.extdirectspring.controller.Configuration config = new ch.ralscha.extdirectspring.controller.Configuration();
		config.setExceptionToMessage(
				Collections.singletonMap(AccessDeniedException.class, "accessdenied"));
		config.setMaxRetries(0);				
		return config;
	}

	@Bean
	public JsonHandler jsonHandler(ObjectMapper objectMapper) {
		JsonHandler jh = new JsonHandler();
		jh.setMapper(objectMapper);
		return jh;
	}

	@Bean
	public Filter mdcFilter() {
		return new MdcFilter();
	}

	@Bean
	public LocaleResolver localeResolver() {
		AppLocaleResolver resolver = new AppLocaleResolver();
		resolver.setDefaultLocale(Locale.ENGLISH);
		return resolver;
	}

	@Bean
	public ServletListenerRegistrationBean<HttpSessionEventPublisher> sessionListener() {
		return new ServletListenerRegistrationBean<>(new HttpSessionEventPublisher());
	}

	@Bean
	public ServletContextInitializer servletContextInitializer(
			final Environment environment) {
		return servletContext -> {
			try {
				boolean isDefaultProfileActive = environment.acceptsProfiles("default");
				WebResourceProcessor processor = new WebResourceProcessor(servletContext,
						isDefaultProfileActive);
				processor.process();
			}
			catch (IOException e) {
				LoggerFactory.getLogger(Application.class).error("read index.html", e);
			}
		};
	}

}
