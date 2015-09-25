#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.Filter;

import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.filter.CharacterEncodingFilter;

import ch.ralscha.extdirectspring.ExtDirectSpring;
import ch.rasc.edsutil.optimizer.WebResourceProcessor;

@Configuration
@ComponentScan(basePackageClasses = { ExtDirectSpring.class, Application.class })
@EnableAutoConfiguration
public class Application extends SpringBootServletInitializer {

	private static SpringApplicationBuilder configureApp(
			SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return configureApp(application);
	}

	public static void main(String[] args) throws Exception {
		// -Dspring.profiles.active=development
		configureApp(new SpringApplicationBuilder()).run(args);
	}

	@Bean
	public Filter characterEncodingFilter() {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding(StandardCharsets.UTF_8.name());
		characterEncodingFilter.setForceEncoding(false);
		return characterEncodingFilter;
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

				ClassPathResource cpr = new ClassPathResource("index.html");
				String indexHtml = StreamUtils.copyToString(cpr.getInputStream(),
						StandardCharsets.UTF_8);
				indexHtml = indexHtml.replace("application.app_css",
						(String) servletContext.getAttribute("app_css"));
				indexHtml = indexHtml.replace("application.app_js",
						(String) servletContext.getAttribute("app_js"));
				servletContext.setAttribute("index.html", indexHtml);
			}
			catch (IOException e) {
				LoggerFactory.getLogger(Application.class).error("read index.html", e);
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