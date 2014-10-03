package ch.rasc.portaldemos.scheduler;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.github.flowersinthesand.portal.App;
import com.github.flowersinthesand.portal.Options;
import com.github.flowersinthesand.portal.atmosphere.AtmosphereModule;

@WebListener
public class SchedulerInitializer implements ServletContextListener {

	private App app;

	@Override
	public void contextInitialized(ServletContextEvent event) {
		app = new App(new Options().url("/sch").packageOf(this), new AtmosphereModule(
				event.getServletContext()));
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		app.close();
	}

}