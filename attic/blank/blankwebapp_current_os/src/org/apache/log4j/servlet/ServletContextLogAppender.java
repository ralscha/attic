/*
 * ============================================================================
 *                   The Apache Software License, Version 1.1
 * ============================================================================
 *
 *    Copyright (C) 1999 The Apache Software Foundation. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modifica-
 * tion, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of  source code must  retain the above copyright  notice,
 *    this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * 3. The end-user documentation included with the redistribution, if any, must
 *    include  the following  acknowledgment:  "This product includes  software
 *    developed  by the  Apache Software Foundation  (http://www.apache.org/)."
 *    Alternately, this  acknowledgment may  appear in the software itself,  if
 *    and wherever such third-party acknowledgments normally appear.
 *
 * 4. The names "log4j" and  "Apache Software Foundation"  must not be used to
 *    endorse  or promote  products derived  from this  software without  prior
 *    written permission. For written permission, please contact
 *    apache@apache.org.
 *
 * 5. Products  derived from this software may not  be called "Apache", nor may
 *    "Apache" appear  in their name,  without prior written permission  of the
 *    Apache Software Foundation.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS  FOR A PARTICULAR  PURPOSE ARE  DISCLAIMED.  IN NO  EVENT SHALL  THE
 * APACHE SOFTWARE  FOUNDATION  OR ITS CONTRIBUTORS  BE LIABLE FOR  ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL,  EXEMPLARY, OR CONSEQUENTIAL  DAMAGES (INCLU-
 * DING, BUT NOT LIMITED TO, PROCUREMENT  OF SUBSTITUTE GOODS OR SERVICES; LOSS
 * OF USE, DATA, OR  PROFITS; OR BUSINESS  INTERRUPTION)  HOWEVER CAUSED AND ON
 * ANY  THEORY OF LIABILITY,  WHETHER  IN CONTRACT,  STRICT LIABILITY,  OR TORT
 * (INCLUDING  NEGLIGENCE OR  OTHERWISE) ARISING IN  ANY WAY OUT OF THE  USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * This software  consists of voluntary contributions made  by many individuals
 * on  behalf of the Apache Software  Foundation.  For more  information on the
 * Apache Software Foundation, please see <http://www.apache.org/>.
 *
 */

package org.apache.log4j.servlet;

import javax.servlet.ServletContext;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

/**
 * Appends log messages to the servlet context log.
 * <p>
 * This class is set up by first registering your application's
 * <code>ServletContext</code> object by following instructions in the
 * <code>{@link ServletContextLogAppenderListener}</code> class.
 * </p>
 * <p>
 * After providing the above configuration setup, the following configuration is
 * required to set up this appender in Log4j.xml...
 * 
 * <pre>
 * 
 *  &lt;appender name=&quot;ServletContext&quot; class=&quot;org.apache.log4j.servlet.ServletContextLogAppender&quot;&gt;
 *    &lt;layout.../&gt;
 *  &lt;/appender&gt;
 *  
 * </pre>
 * 
 * <p>
 * You can use this appender like any other. Check the servlet context log for
 * the specified servlet context path to view the log output.
 * </p>
 * 
 * @author Aleksei Valikov
 * @author <a href="mailto:hoju@visi.com">Jacob Kjome </a>
 * @since 1.3
 */
public class ServletContextLogAppender extends AppenderSkeleton {
  /** Map of servlet contexts. */
  private static ServletContext servletContext;

  public static void setServletContexts(ServletContext sc) {
    servletContext = sc;
  }

  /**
   * Activates configured options.
   */
  public void activateOptions() {
    if (servletContext == null) {
      errorHandler.error("Servlet context could not be found.");
    }
  }

  /**
   * Appends a logging event through the servlet context logger.
   * 
   * @param event
   *          logging event to append;
   */
  protected void append(final LoggingEvent event) {

    // If servlet context is not found, signal an error
    if (servletContext == null) {
      errorHandler.error("Servlet context could not be found.");

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