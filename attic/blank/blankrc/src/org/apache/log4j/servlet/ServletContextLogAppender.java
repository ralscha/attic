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

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

/**
 * Appends log messages to the servlet context log.
 * <p>This class is set up by first registering your application's
 * <code>ServletContext</code> object by following instructions
 * in the <code>{@link ServletContextLogAppenderListener}</code> class.</p>
 * <p>After providing the above configuration setup, the following
 * configuration is required to set up this appender in Log4j.xml...
 * <pre>
 * &lt;appender name=&quot;ServletContext&quot; class=&quot;org.apache.log4j.servlet.ServletContextLogAppender&quot;&gt;
 *   &lt;param name=&quot;servletContextPath&quot; value=&quot;/MyContext&quot; /&gt;
 *   &lt;layout.../&gt;
 * &lt;/appender&gt;
 * </pre>
 * Replace &quot;/MyContext&quot; with your own context path.</p>
 * <p>After that, you can use this appender like any other.  Check the
 * servlet context log for the specified servlet context path to view
 * the log output.</p>
 *
 * @author Aleksei Valikov
 * @author <a href="mailto:hoju@visi.com">Jacob Kjome</a>
 * @since  1.3
 */
public class ServletContextLogAppender extends AppenderSkeleton {
  /** Map of servlet contexts. */
  private static Map servletContexts = Collections.synchronizedMap(new HashMap());

  /** Path of the servlet context of this appender instance. */
  protected String servletContextPath;

  /** Servlet context of this appender. */
  protected ServletContext servletContext;

  /**
   * Provides access to the statically stored servlet context map
   *
   * @return the cached map servlet contexts
   */
  public static Map servletContexts() {
    return servletContexts;
  }

  /**
   * Returns servlet context path of this appender.
   *
   * @return Servlet context path of this appender.
   */
  public String getServletContextPath() {
    return servletContextPath;
  }

  /**
   * Sets servlet context path of this appender.
   *
   * @param servletContextPath path of the servlet context of this appender.
   */
  public void setServletContextPath(final String servletContextPath) {
    this.servletContextPath = servletContextPath;
  }

  /**
   * Activates configured options.
   */
  public void activateOptions() {
    servletContext = (ServletContext)servletContexts.get(servletContextPath);

    if (servletContext == null) {
      errorHandler.error("Servlet context [" + servletContextPath + "] could not be found.");
    }
  }

  /**
   * Appends a logging event through the servlet context logger.
   *
   * @param event logging event to append;
   */
  protected void append(final LoggingEvent event) {
    // If servlet context is undefined
    // Try loading servlet context
    if (servletContext == null) {
      servletContext = (ServletContext)servletContexts.get(servletContextPath);
    }

    // If servlet context is not found, signal an error
    if (servletContext == null) {
      errorHandler.error("Servlet context [" + servletContextPath + "] could not be found.");

      return;
    }

    // Output log message
    servletContext.log(layout.format(event));

    if (layout.ignoresThrowable()) {
      String[] s = event.getThrowableStrRep();

      if (s != null) {
        int len = s.length;

        for (int i = 0; i < len; i++) {
          servletContext.log(s[i]);
        }
      }
    }

    return;
  }

  /**
   * Flags that layout is required.
   *
   * @return <code>true</code>.
   */
  public boolean requiresLayout() {
    return true;
  }

  /**
   * Should close the appender - does nothing.
   */
  public void close() {
    //no action
  }
}