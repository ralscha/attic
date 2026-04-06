package ch.rasc.session;

import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.session.ExpiringSession;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.SessionRepository;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;
import org.springframework.session.web.http.SessionRepositoryFilter;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public SessionRepository<ExpiringSession> sessionRepository() {
		return new MapSessionRepository();
	}

	@Bean
	public SessionRepositoryFilter<ExpiringSession> sessionRepositoryFilter(
			SessionRepository<ExpiringSession> sessionRepository) {
		SessionRepositoryFilter<ExpiringSession> filter = new SessionRepositoryFilter<>(
				sessionRepository);
		filter.setHttpSessionStrategy(new HeaderHttpSessionStrategy());
		return filter;
	}

	@Bean
	public EmbeddedServletContainerCustomizer servletContainerCustomizer() {
		return servletContainer -> ((TomcatEmbeddedServletContainerFactory) servletContainer)
				.addConnectorCustomizers(connector -> {
					AbstractHttp11Protocol<?> httpProtocol = (AbstractHttp11Protocol<?>) connector
							.getProtocolHandler();
					httpProtocol.setCompression("on");
					httpProtocol.setCompressionMinSize(512);
					String[] mimeTypes = httpProtocol.getCompressableMimeTypes();
					String additionalMimeTypes = String.join(",", mimeTypes) + ","
							+ MediaType.APPLICATION_JSON_VALUE;

					httpProtocol.setCompressableMimeType(additionalMimeTypes);
				});
	}
}
