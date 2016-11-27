package ch.rasc.eds.starter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

import ch.ralscha.extdirectspring.ExtDirectSpring;
import ch.rasc.edsutil.optimizer.WebResourceProcessor;

@Configuration
@ComponentScan(basePackageClasses = { ExtDirectSpring.class, Application.class })
@EnableAutoConfiguration(exclude = SpringDataWebAutoConfiguration.class)
public class Application extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}

	public static void main(String[] args) throws Exception {
		// -Dspring.profiles.active=development
		new SpringApplicationBuilder().sources(Application.class).run(args);
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

}