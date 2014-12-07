package ch.rasc.packt.config;

import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.jndi.JndiLocatorDelegate;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import ch.rasc.edsutil.optimizer.WebResourceProcessor;

public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {

		String profile = System.getProperty("spring.profiles.active");
		if (profile == null) {
			try {
				profile = (String) JndiLocatorDelegate.createDefaultResourceRefLocator()
						.lookup("spring.profiles.active");
			}
			catch (NameNotFoundException ne) {
				// do nothing
			}
			catch (NamingException e) {
				throw new ServletException(e);
			}
		}

		WebResourceProcessor processor = new WebResourceProcessor(servletContext,
				!"development".equals(profile));
		processor.setResourceServletPath("/resources/");
		processor.ignoreJsResourceFromReordering("/app/model/menu/Item.js");
		processor.process();

		servletContext.setInitParameter("spring.profiles.default", "production");

		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(false);
		servletContext.addFilter("characterEncodingFilter", characterEncodingFilter)
				.addMappingForUrlPatterns(null, false, "/*");

		super.onStartup(servletContext);
	}

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { ComponentConfig.class, DataConfig.class,
				SecurityConfig.class, WebConfig.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return null;
	}

	@Override
	protected WebApplicationContext createServletApplicationContext() {
		return new GenericWebApplicationContext();
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

}