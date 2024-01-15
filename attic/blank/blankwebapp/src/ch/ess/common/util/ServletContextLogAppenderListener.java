package ch.ess.common.util;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ServletContextLogAppenderListener implements ServletContextListener {

  public void contextInitialized(final ServletContextEvent event) {
    final ServletContext servletContext = event.getServletContext();
    ServletContextLogAppender.setServletContext(servletContext);
  }

  public void contextDestroyed(final ServletContextEvent event) {
    ServletContextLogAppender.setServletContext(null);
  }


}