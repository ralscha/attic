package ch.ess.blank.resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.digester.Digester;
import org.apache.log4j.LogManager;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.spi.RepositorySelector;
import org.apache.velocity.app.Velocity;

import ch.ess.blank.Constants;
import ch.ess.blank.resource.text.RuleSet;
import ch.ess.blank.resource.text.TextResources;
import ch.ess.common.db.HibernateFactoryManager;

/** 
 * @author  Ralph Schaer
 * @version $Revision: 1.4 $ $Date: 2004/05/22 15:38:03 $ 
 * 
 * @web.listener 
 */
public class AppContextListener implements ServletContextListener {

  public static final String PARAM_LOG4J_PREF_SELECTOR = "log4j-selector";

  public void contextInitialized(ServletContextEvent sce) {
    ServletContext ctx = sce.getServletContext();
    try {
      HibernateFactoryManager.initLookForDialect(Constants.DATASOURCE_NAME);
      AppConfig.init();

      InitialDataLoad.load(ctx);

      //Log
      ClassLoader savedClassLoader = null;

      try {
        savedClassLoader = Thread.currentThread().getContextClassLoader();
        Thread.currentThread().setContextClassLoader(AppContextListener.class.getClassLoader());
      } catch (SecurityException se) {
        //no action
      }

      try {
        //Attempt to set the repository selector no matter what else happens
        setSelector(ctx);

      } finally {
        if (savedClassLoader != null) {
          Thread.currentThread().setContextClassLoader(savedClassLoader);
        }
      }

      //Velocity
      initVelocity(ctx);

      //Init Text Resources
      initTextResources();
      InitialDataLoad.loadTextResources();

    } catch (Exception e) {
      ctx.log("init", e);
    }

  }

  public void contextDestroyed(ServletContextEvent event) {
    HibernateFactoryManager.destroy();

    ServletContext context = event.getServletContext();
    context.log("Shutting down all loggers and appenders...");
    org.apache.log4j.LogManager.shutdown();
    context.log("Log4j cleaned up.");

  }

  private void initVelocity(ServletContext context) throws Exception {

    Velocity.setApplicationAttribute(ServletContext.class.getName(), context);

    InputStream is = getClass().getResourceAsStream("/velocity.properties");
    Properties props = new Properties();
    props.load(is);
    is.close();
    Velocity.init(props);
  }

  private void initTextResources() {
    InputStream is = null;
    try {
      is = this.getClass().getResourceAsStream("/textresource.xml");
      if (is != null) {
        Digester digester = new Digester();

        digester.addRuleSet(new RuleSet());

        List l = (List)digester.parse(is);
        TextResources.addResource(l);

      }
    } catch (Exception e) {
      e.printStackTrace();

    } finally {
      if (is != null) {
        try {
          is.close();
        } catch (IOException e1) {
          e1.printStackTrace();
        }
      }
    }
  }

  private void setSelector(final ServletContext context) {
    String selector = context.getInitParameter(PARAM_LOG4J_PREF_SELECTOR);

    if (selector == null) {
      LogLog.warn("No preferred selector supplied. Using default repository selector...");
      return;
    }

    try {
      Object guard = new Object();

      Class clazz = Class.forName(selector, true, Thread.currentThread().getContextClassLoader());
      LogManager.setRepositorySelector((RepositorySelector)clazz.newInstance(), guard);
    } catch (ClassNotFoundException cnfe) {
      LogLog.warn("Preferred selector not found. Using default repository selector...");
    } catch (InstantiationException ie) {
      LogLog.warn("Error in instantiation of preferred selector. Using default " + "repository selector...");
    } catch (IllegalAccessException iae) {
      LogLog.warn("Unable to access preferred selector. Using default repository " + "selector...");
    } catch (IllegalArgumentException iae) {
      LogLog.warn("Preferred repository selector not installed because one has already "
          + "exists.  No problem, using existing selector...");
    }
  }

}