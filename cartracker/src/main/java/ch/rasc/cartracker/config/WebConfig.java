package ch.rasc.cartracker.config;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewInterceptor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import ch.ralscha.extdirectspring.util.JsonHandler;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.collect.ImmutableMap;

@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {

	@Autowired
	private Environment environment;

	@Autowired
	private EntityManagerFactory entityManagerFactory;

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		OpenEntityManagerInViewInterceptor in = new OpenEntityManagerInViewInterceptor();
		in.setEntityManagerFactory(entityManagerFactory);
		registry.addWebRequestInterceptor(in).addPathPatterns("/router");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		ResourceHandlerRegistration registration = registry.addResourceHandler(
				"/resources/**").addResourceLocations("/resources/");
		if (environment.acceptsProfiles("production")) {
			registration.setCachePeriod(31556926);
		}
	}

	@Bean
	public MultipartResolver multipartResolver() {
		return new CommonsMultipartResolver();
	}

	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
		return objectMapper;
	}

	@Bean
	public ch.ralscha.extdirectspring.controller.Configuration configuration() {
		ch.ralscha.extdirectspring.controller.Configuration config = new ch.ralscha.extdirectspring.controller.Configuration();
		config.setExceptionToMessage(new ImmutableMap.Builder<Class<?>, String>().put(
				AccessDeniedException.class, "accessdenied").build());

		JsonHandler handler = new JsonHandler();
		handler.setMapper(objectMapper());
		config.setJsonHandler(handler);

		return config;
	}

}