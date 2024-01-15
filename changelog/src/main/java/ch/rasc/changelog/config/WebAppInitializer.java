package ch.rasc.changelog.config;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

import ch.rasc.changelog.web.MdcFilter;
import ch.rasc.edsutil.optimizer.WebResourceProcessor;

public class WebAppInitializer implements WebApplicationInitializer {

	@SuppressWarnings("resource")
	@Override
	public void onStartup(ServletContext container) {
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.setServletContext(container);
		rootContext.getEnvironment().setDefaultProfiles("production");
		rootContext.register(ComponentConfig.class, DataConfig.class,
				ScheduleConfig.class, SecurityConfig.class, WebConfig.class,
				WebSocketConfig.class);

		container.addListener(new ContextLoaderListener(rootContext));
		container.addListener(HttpSessionEventPublisher.class);

		container
				.addFilter("springSecurityFilterChain",
						new DelegatingFilterProxy("springSecurityFilterChain",
								rootContext))
				.addMappingForUrlPatterns(
						EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD), true,
						"/*");

		container.addFilter("mdcFilter", MdcFilter.class).addMappingForUrlPatterns(
				EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD), true, "/*");

		ServletRegistration.Dynamic dispatcher = container.addServlet("dispatcher",
				new DispatcherServlet(new GenericWebApplicationContext()));
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/");

		MultipartConfigElement config = new MultipartConfigElement(null,
				100 * 1024 * 1024, 100 * 1024 * 1024, 1024 * 1024);
		dispatcher.setMultipartConfig(config);

		WebResourceProcessor processor = new WebResourceProcessor(container,
				rootContext.getEnvironment().acceptsProfiles("production"));
		processor.process();
	}

}