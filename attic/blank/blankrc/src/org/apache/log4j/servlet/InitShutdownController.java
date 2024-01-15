/*
 * Copyright 1999,2004 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.log4j.servlet;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.spi.RepositorySelector;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * Used to initialize and cleanup Log4j in a servlet environment.
 * <p>See {@link InitContextListener} for web.xml configuration instructions.</p>
 * <p>Besides basic webapp lifecycle control when used with InitContextListener
 * and {@link InitServlet}, this class can be used by any class at runtime to
 * control initialization and shutdown of Log4j loggers and appenders.</p>
 *
 * @see http://nagoya.apache.org/wiki/apachewiki.cgi?Log4JProjectPages/AppContainerLogging
 * @author <a href="mailto:hoju@visi.com">Jacob Kjome</a>
 * @author Andreas Werner
 * @since  1.3
 */
public class InitShutdownController {
  /**
   * preferred repository selector config param.
   * Maps to web.xml &lt;context-param&gt; with
   * &lt;param-name&gt;log4j-selector&lt;/param-name&gt;
   */
  public static final String PARAM_LOG4J_PREF_SELECTOR = "log4j-selector";

  /**
   * relative path to config file within current webapp config param.
   * Maps to web.xml &lt;context-param&gt; with
   * &lt;param-name&gt;log4j-config&lt;/param-name&gt;
   */
  public static final String PARAM_LOG4J_CONFIG_PATH = "log4j-config";

  /**
   * config file re-reading specified in milliseconds config param.
   * Maps to web.xml &lt;context-param&gt; with
   * &lt;param-name&gt;log4j-cron&lt;/param-name&gt;
   */
  public static final String PARAM_LOG4J_WATCH_INTERVAL = "log4j-cron";

  /**
   * path to be read from a log4j xml config file as a system property
   * config param. Maps to web.xml &lt;context-param&gt; with
   * &lt;param-name&gt;log4j-log-home&lt;/param-name&gt;
   */
  public static final String PARAM_LOG4J_LOG_HOME = "log4j-log-home";

  /**
   * system property to be used in a log4j xml config file config param.
   * Maps to web.xml &lt;context-param&gt; with
   * &lt;param-name&gt;log4j-sysprop-name&lt;/param-name&gt;
   */
  public static final String PARAM_LOG4J_SYSPROP_NAME = "log4j-sysprop-name";

  /**
   * default path to write log files if using a file appender
   */
  private static final String DEFAULT_LOG_HOME = "WEB-INF" + File.separator + "logs";

  /**
   * Log4j specific cleanup.  Shuts down all loggers and appenders.
   *
   * @param context the current servlet context
   */
  public static void shutdownLog4j(final ServletContext context) {
    //shutdown this webapp's logger repository
    context.log("Cleaning up Log4j resources for context: " + context.getServletContextName() + "...");
    context.log("Shutting down all loggers and appenders...");
    org.apache.log4j.LogManager.shutdown();
    context.log("Log4j cleaned up.");
  }

  /**
   * Log4j specific initialization.  Sets up log4j for the current
   * servlet context. Installs a custom repository selector if one hasn't
   * already been installed.
   *
   * @param context the current servlet context
   */
  public static void initializeLog4j(final ServletContext context) {
    ClassLoader savedClassLoader = null;

    try {
      savedClassLoader = Thread.currentThread().getContextClassLoader();

      //attempt to set the thread context class loader to the current class
      //loader to avoid ClassCastExceptions when this library is in a parent
      //class loader (along with log4j.jar) and a webapp includes log4j.jar
      //in WEB-INF/lib, but not this library (assuming they aren't both in
      //log4j.jar).
      //based on the following article...
      //http://www-106.ibm.com/developerworks/websphere/library/techarticles/0310_searle/searle.html
      Thread.currentThread().setContextClassLoader(InitShutdownController.class.getClassLoader());
    } catch (SecurityException se) {
      //oh, well, we tried.  Security is turned on and we aren't allowed to
      //tamper with the thread context class loader.  You're on your own!
    }

    try {
      //Attempt to set the repository selector no matter what else happens
      setSelector(context);

      String configPath = getLog4jConfigPathFromContext(context);

      if ((configPath != null) && (configPath.length() >= 1)) {
        setLog4jLogHome(context);

        if (context.getRealPath("/") != null) {
          configureLog4jFromFile(configPath, context);
        } else {
          configureLog4jFromURL(configPath, context);
        }
      } else {
        if (configPath == null) {
          LogLog.error("Missing log4j-config servlet parameter missing.");
        } else {
          LogLog.error("Zero length Log4j config file path given.");
        }

        displayConfigNotFoundMessage();
      }
    } finally {
      if (savedClassLoader != null) {
        //reset the thread context class loader to the original saved class
        //loader now that our purpose (avoiding class cast exceptions) has been
        //served
        Thread.currentThread().setContextClassLoader(savedClassLoader);
      }
    }
  }

  /**
   * Determines the path to log4j config file from the servlet context param
   * 'logj4-config'.  The path is assumed to be relative to the current context.
   * If set, the path is normalized with any preceeding slash removed as the
   * other methods expect the path to be formatted this way.
   *
   * @param context The servlet context
   * @return the path to the log4j config file within the context or null if
   *         the 'log4j-config' context param is not set.
   */
  private static String getLog4jConfigPathFromContext(final ServletContext context) {
    String configPath = context.getInitParameter(PARAM_LOG4J_CONFIG_PATH);

    if (configPath != null) {
      if (configPath.startsWith("/")) {
        configPath = (configPath.length() > 1) ? configPath.substring(1) : "";
      }
    }

    return configPath;
  }

  /**
   * Determines the log4j home to use. If the context parameter
   * 'log4j-log-home' is set, this is used as the environment variable.
   * Otherwise the the default 'WEB-INF/logs' is used.
   *
   * @param context the current servlet context
   */
  private static void setLog4jLogHome(final ServletContext context) {
    // set up log path System property
    String logHome = context.getInitParameter(PARAM_LOG4J_LOG_HOME);

    if (logHome != null) {
      // set up custom log path system property
      setFileAppenderSystemProperty(logHome, context);
    } else {
      String contextPath = context.getRealPath("/");

      if (contextPath != null) {
        //no log path specified in web.xml. Setting to default within current
        //context path
        logHome = contextPath + DEFAULT_LOG_HOME;
        setFileAppenderSystemProperty(logHome, context);
      }
    }
  }

  /**
   * Configures log4j via url
   *
   * @param configPath the location of the log4j configuration file
   *        relative to the context path
   * @param context the current servlet context
   */
  private static void configureLog4jFromURL(final String configPath, final ServletContext context) {
    //The webapp is deployed from a .war file, not directly off the file system
    //so we *cannot* do File IO. Note that we *won't* be able to use
    //configureAndWatch() here because that requires an absolute system file
    //path.
    boolean isXMLConfigFile = (configPath.endsWith(".xml")) ? true : false;

    URL log4jURL = null;

    try {
      log4jURL = context.getResource("/" + configPath);
    } catch (MalformedURLException murle) {
      //ignore...we check for null later      
    }

    //Now let's check if the given configPath actually exists.
    if (log4jURL != null) {
      context.log("Configuring Log4j from URL at path: /" + configPath);

      if (isXMLConfigFile) {
        try {
          DOMConfigurator.configure(log4jURL);

          //catch (javax.xml.parsers.FactoryConfigurationError fce) {}
        } catch (Exception e) {
          //report errors to server logs
          LogLog.error(e.getMessage());
        }
      } else {
        Properties log4jProps = new Properties();

        try {
          log4jProps.load(log4jURL.openStream());
          PropertyConfigurator.configure(log4jProps);

          //catch (java.io.IOException ioe) {}
        } catch (Exception e) {
          //report errors to server logs
          LogLog.error(e.getMessage());
        }
      }
    } else {
      //The given configPath does not exist. So, let's just let Log4j look for
      //the default files (log4j.properties or log4j.xml) on its own.
      displayConfigNotFoundMessage();
    }
  }

  /**
   * Configures log4j from a File
   *
   * @param systemConfigPath the fully qualified system path location of the
   *        log4j configuration file
   * @param context the current servlet context
   */
  private static void configureLog4jFromFile(final String configPath, final ServletContext context) {
    //The webapp is deployed directly off the filesystem, not from a .war file
    //so we *can* do File IO. This means we can use configureAndWatch() to
    //re-read the the config file at defined intervals.
    boolean isXMLConfigFile = (configPath.endsWith(".xml")) ? true : false;

    String contextPath = context.getRealPath("/");
    String systemConfigPath = configPath.replace('/', File.separatorChar);
    File log4jFile = new File(contextPath + systemConfigPath);

    //Now let's check if the given configPath actually exists.
    if (log4jFile.canRead()) {
      log4jFile = null;

      long timerIntervalVal = getTimerIntervalFromContext(context);
      context.log("Configuring Log4j from File: " + contextPath + systemConfigPath);

      if (timerIntervalVal > 0) {
        context.log("Configuring Log4j with watch interval: " + timerIntervalVal + "ms");

        if (isXMLConfigFile) {
          DOMConfigurator.configureAndWatch(contextPath + systemConfigPath, timerIntervalVal);
        } else {
          PropertyConfigurator.configureAndWatch(contextPath + systemConfigPath, timerIntervalVal);
        }
      } else {
        if (isXMLConfigFile) {
          DOMConfigurator.configure(contextPath + systemConfigPath);
        } else {
          PropertyConfigurator.configure(contextPath + systemConfigPath);
        }
      }
    } else {
      // The given configPath does not exist. So, let's just
      // let Log4j look for the default files (
      // log4j.properties or log4j.xml) on its own.
      displayConfigNotFoundMessage();
    }
  }

  /**
   * Retrieves the timer interval from the servlet context.
   *
   * @param context the current servlet context
   */
  private static long getTimerIntervalFromContext(final ServletContext context) {
    String timerInterval = context.getInitParameter(PARAM_LOG4J_WATCH_INTERVAL);
    long timerIntervalVal = 0L;

    if (timerInterval != null) {
      try {
        timerIntervalVal = Integer.valueOf(timerInterval).longValue();
      } catch (NumberFormatException nfe) {
        //ignore...we just won't use configureAndWatch if there is no valid int        
      }
    }

    return timerIntervalVal;
  }

  /**
   * standard configuration not found message
   */
  private static void displayConfigNotFoundMessage() {
    LogLog.warn("No Log4j configuration file found at given path. " + "Falling back to Log4j auto-configuration.");
  }

  /**
   * sets the [webapp].log.home system property for use with a file appender.
   *
   * @param logHome the path to a logging directory
   * @param context the current servlet context
   */
  private static void setFileAppenderSystemProperty(final String logHome, final ServletContext context) {
    String logHomePropName = null;
    String customPropName = context.getInitParameter(PARAM_LOG4J_SYSPROP_NAME);
    File logHomeDir = new File(logHome);

    if (logHomeDir.exists() || logHomeDir.mkdirs()) {
      if (customPropName != null) {
        logHomePropName = customPropName;
      } else {
        logHomePropName = getContextPath(context) + ".log.home";
      }

      context.log("Setting system property [ " + logHomePropName + " ] to [ " + logHome + " ]");
      System.setProperty(logHomePropName, logHome);
    }
  }

  /**
   * Retrieves the context path of the web application from the servlet context.
   *
   * @param context the current servlet context
   * @return the derived context path, guaranteed non-null
   */
  private static String getContextPath(final ServletContext context) {
    //old way to determine context path
    //String tempdir = "" +
    //context.getAttribute("javax.servlet.context.tempdir");
    //int lastSlash = tempdir.lastIndexOf(File.separator);
    //if ((tempdir.length() - 1) > lastSlash) {
    //  logHomePropName = tempdir.substring(lastSlash + 1) + ".log.home";
    //}
    String contextPath = "";

    try {
      //use a more standard way to obtain the context path name
      //which should work across all servers. The tmpdir technique
      //(above) depends upon the naming scheme that Tomcat uses.
      String path = context.getResource("/").getPath();

      //first remove trailing slash, then take what's left over
      //which should be the context path less the preceeding
      //slash such as "MyContext"
      contextPath = path.substring(0, path.lastIndexOf("/"));
      contextPath = contextPath.substring(contextPath.lastIndexOf("/") + 1);
    } catch (Exception e) {
      //no action
    }

    return contextPath;
  }

  /**
   * Do idempotent initialization of the the logger repository.  Only one
   * repository selector may be set during runtime of log4j.  Therefore, this
   * method is only a guarantee that a repository selector will have been set.
   * It does not guarantee that your preferred selector will be used.  If
   * some other code sets the selector first, that is the selector that all
   * applications using Log4j will use, assuming log4j is running in a shared
   * class loader.
   *
   * @param context the current servlet context
   */
  private static void setSelector(final ServletContext context) {
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