package ch.rasc.precompressed;

import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.EncodedResourceResolver;

@Configuration
class ResourceConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		String userDir = System.getProperty("user.dir");
		registry.addResourceHandler("/**")
				.addResourceLocations(
						Paths.get(userDir, "client/dist").toUri().toString())
				.resourceChain(true).addResolver(new EncodedResourceResolver());
	}

}
