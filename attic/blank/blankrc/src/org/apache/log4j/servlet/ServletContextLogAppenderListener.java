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

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


/**
 * This class maintains list of contexts in {@link ServletContextLogAppender}.
 * <p>A container-managed registration of contexts would obviate the need
 * for this class, but since there is no container-management at this time,
 * this class is the only way to register the servlet context for your
 * application in order for the <code>ServletContextLogAppender</code>
 * to be able to log to your application's context log.</p>
 * <p>To use this class, the following must be added to the web.xml (and
 * make sure that it is added before the
 * <code>{@link InitContextListener}</code> if you use that class for Log4j
 * initialization)...
 * <pre>
 * &lt;listener&gt;
 *   &lt;listener-class&gt;
 *   org.apache.log4j.servlet.ServletContextLogAppenderListener
 *   &lt;/listener-class&gt;
 * &lt;/listener&gt;
 * </pre></p>
 * <p>See the <code>ServletContextLogAppender</code> for further required
 * configuration information.</p>
 *
 * @author Aleksei Valikov
 * @author <a href="mailto:hoju@visi.com">Jacob Kjome</a>
 * @since  1.3
 */
public class ServletContextLogAppenderListener
  implements ServletContextListener {
  /**
   * Receives a notification of the context initialization event.
   * @param event context event.
   */
  public void contextInitialized(final ServletContextEvent event) {
    // Add context to map of servlet contexts
    final ServletContext servletContext = event.getServletContext();
    ServletContextLogAppender.servletContexts().put(
      contextPath(servletContext), servletContext);
  }

  /**
   * Receives a notification of the context destruction event.
   * @param event context event.
   */
  public void contextDestroyed(final ServletContextEvent event) {
    // Removes context from the map of servlet contexts
    final ServletContext servletContext = event.getServletContext();
    ServletContextLogAppender.servletContexts().remove(
      contextPath(servletContext));
  }

  /**
   * utility method to obtain the servlet context path such as
   * &quot;/MyContext&quot;.
   *
   * @param servletContext a servlet context object
   * @return a string representing the servlet context path
   */
  private static String contextPath(ServletContext servletContext) {
    String contextPath = "";

    try {
      String path = servletContext.getResource("/").getPath();

      //first remove trailing slash, then take what's left over
      //which should be the context path such as "/MyContext"
      contextPath = path.substring(0, path.lastIndexOf("/"));
      contextPath = contextPath.substring(contextPath.lastIndexOf("/"));
    } catch (Exception e) {
      //no action
    }

    return contextPath;
  }
}
