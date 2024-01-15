package ch.rasc.proto.config;

import java.io.IOException;
import java.util.Collections;
import java.util.Locale;

import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.boot.context.embedded.ServletListenerRegistrationBean;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.servlet.LocaleResolver;

import ch.rasc.edsutil.optimizer.WebResourceProcessor;
import ch.rasc.proto.ProtoMain;

@Configuration
public class WebConfig {

	@Bean
	public ch.ralscha.extdirectspring.controller.Configuration configuration() {
		ch.ralscha.extdirectspring.controller.Configuration config = new ch.ralscha.extdirectspring.controller.Configuration();
		config.setExceptionToMessage(Collections.singletonMap(
				AccessDeniedException.class, "accessdenied"));
		return config;
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
				LoggerFactory.getLogger(ProtoMain.class).error("read index.html", e);
			}
		};
	}

	@Bean
	@Profile("compression")
	public EmbeddedServletContainerCustomizer servletContainerCustomizer() {
		return servletContainer -> ((TomcatEmbeddedServletContainerFactory) servletContainer)
				.addConnectorCustomizers(connector -> {
					AbstractHttp11Protocol<?> httpProtocol = (AbstractHttp11Protocol<?>) connector
							.getProtocolHandler();
					httpProtocol.setCompression("on");
					httpProtocol.setCompressionMinSize(512);
					String mimeTypes = httpProtocol.getCompressableMimeTypes();
					String additionalMimeTypes = mimeTypes + ","
							+ MediaType.APPLICATION_JSON_VALUE + ","
							+ "application/javascript,text/css";

					httpProtocol.setCompressableMimeTypes(additionalMimeTypes);
				});
	}

}
