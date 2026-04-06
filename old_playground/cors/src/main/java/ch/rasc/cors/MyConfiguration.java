package ch.rasc.cors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MyConfiguration {

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/router/**")
						.allowedHeaders("x-requested-with", "Content-Type")
						.maxAge(3600) /* default: 1800 */
						.allowedMethods(HttpMethod.POST.name())
						.allowedOrigins("http://localhost:8080");

			}
		};
	}

}