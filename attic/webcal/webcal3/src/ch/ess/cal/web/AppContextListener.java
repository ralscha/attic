package ch.ess.cal.web;

import java.io.InputStream;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.Velocity;

import com.cc.framework.Globals;
import com.cc.framework.ui.painter.PainterFactory;
import com.cc.framework.ui.painter.def2.Def2PainterFactory;
import com.cc.framework.ui.painter.html.HtmlPainterFactory;

/** 
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2005/05/09 07:46:07 $ 
 * 
 * @web.listener 
 */
public class AppContextListener implements ServletContextListener {

  public void contextInitialized(final ServletContextEvent event) {

    Locale.setDefault(Locale.ENGLISH);

    final ServletContext servletContext = event.getServletContext();

    PainterFactory.registerApplicationPainter(servletContext, Def2PainterFactory.instance());
    PainterFactory.registerApplicationPainter(servletContext, HtmlPainterFactory.instance());

    servletContext.setAttribute(Globals.LOCALENAME_KEY, "true");

    try {
      initVelocity(servletContext);
    } catch (Exception e) {
      servletContext.log("contextInitialized", e);
    }

  }

  public void contextDestroyed(final ServletContextEvent event) {
    final ServletContext servletContext = event.getServletContext();

    //release PainterFactories
    PainterFactory.unregisterApplicationPainter(servletContext, Def2PainterFactory.instance());
    PainterFactory.unregisterApplicationPainter(servletContext, HtmlPainterFactory.instance());

    //release JDBC Drivers
    try {
      for (Enumeration<Driver> e = DriverManager.getDrivers(); e.hasMoreElements();) {
        Driver driver = e.nextElement();
        if (driver.getClass().getClassLoader() == getClass().getClassLoader()) {
          DriverManager.deregisterDriver(driver);
        }
      }
    } catch (Throwable throwable) {
      servletContext.log("Failled to cleanup");
      throwable.printStackTrace();
    }

    //release Velocity
    Velocity.setApplicationAttribute(ServletContext.class.getName(), null);

    //release Logging
    LogFactory.release(Thread.currentThread().getContextClassLoader());

  }

  private void initVelocity(final ServletContext context) throws Exception {

    Velocity.setApplicationAttribute(ServletContext.class.getName(), context);

    InputStream is = getClass().getResourceAsStream("/velocity.properties");
    Properties props = new Properties();
    props.load(is);
    is.close();
    Velocity.init(props);
  }

}
