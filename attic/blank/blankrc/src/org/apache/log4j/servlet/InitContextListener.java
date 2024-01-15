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

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * A servlet context listener for initializing and shutting down Log4j. See
 * <a href="http://jakarta.apache.org/log4j/docs/documentation.html">Log4j
 * documentation</a> for how to use Log4j.
 * <p>
 * This is a <code>ServletContextListener</code> as defined by the servlet 2.3
 * specification.  It gets called immediately before full application startup
 * and immediately before full application shutdown.  Unlike servlets, which
 * may be destroyed at the will of the container at any time during the
 * application lifecycle, a servlet context listener is guaranteed to be
 * called exactly twice within the application's lifecycle.  As such, we can
 * use it to initialize things once at application startup and clean things
 * up at application shutdown.</p>
 * <p>
 * Initialization is described below in the discussion of the various
 * parameters available for configuring this context listener.  In the case of
 * shutdown we are concerned with cleaning up loggers and appenders within the
 * <code>Hierarchy</code> that the current application is using for logging.
 * If we don't do this, there is a chance that, for instance, file appenders
 * won't give up handles to files they are using for logging which would leave
 * them in a locked state until the current JVM is shut down.  This would
 * entail a full shutdown of the application server in order to release locks
 * on log files.  Using this servlet context listener ensures that locks will
 * be released without requiring a full server shutdown.</p>
 * <p>
 * The following needs to be added to the webapp's web.xml file to configure
 * this listener:
 * <blockquote>
 * <pre>
 * &lt;context-param&gt;
 *     &lt;!-- preferred repository selector. &quot;preferred&quot; because
 *              if one is already installed, this choice is ignored. --&gt;
 *     &lt;param-name&gt;log4j-selector&lt;/param-name&gt;
 *     &lt;param-value&gt;
 *     org.apache.log4j.selector.ContextClassLoaderSelector
 *     &lt;/param-value&gt;
 * &lt;/context-param&gt;
 * &lt;context-param&gt;
 *     &lt;!-- relative path to config file within current webapp --&gt;
 *     &lt;param-name&gt;log4j-config&lt;/param-name&gt;
 *     &lt;param-value&gt;WEB-INF/log4j.xml&lt;/param-value&gt;
 * &lt;/context-param&gt;
 * &lt;context-param&gt;
 *     &lt;!-- config file re-reading specified in milliseconds...
 *              Note that if the webapp is served directly from a
 *              .war file and the log4j-log-home path is set to within the
 *              webapp (default), configureAndWatch() cannot be used because
 *              it requires a system file path. In that case, this
 *              param will be ignored.  Set to 0 or don't specify this
 *              param to do a normal configure().  Additionally, the watchdogs
 *              in Log4j-1.2.x have no lifecycle, meaning that they can't be
 *              shut down, even by a call to LogManager.shutdown().  For this
 *              reason, it is recommended that this param not be used or set
 *              to 0. --&gt;
 *     &lt;param-name&gt;log4j-cron&lt;/param-name&gt;
 *     &lt;param-value&gt;0&lt;/param-value&gt;
 * &lt;/context-param&gt;
 * &lt;!-- Below are optional context params for use with a File Appender.
 *          &quot;log4j-log-home&quot; specifies a path to be read from a
 *          log4j xml config file as a system property. The system property
 *          name is dynamically generated and takes on the following pattern:
 *              [context path].log.home
 *          If the app has a path of &quot;/Barracuda&quot;, the system
 *          variable name would be &quot;Barracuda.log.home&quot;.  So,
 *          the FileAppender in log4j.xml would contain a param which looks
 *          like:
 *              &lt;param
 *                name=&quot;File&quot;
 *                value=&quot;
 *                  ${Barracuda.log.home}/arbitraryLogFileName.log&quot; /&gt;
 *          Dynamic generation of a predictable system property name has only
 *          been tested successfully on Tomcat and may not work in other
 *          containers. As such, one may specify the desired system property
 *          name using the "log4j-sysprop-name". If specified, this name will
 *          be used in place of the dynamically generated system property name.
 *          It is recommended that naming pattern match that described above.
 *          If the &quot;log4j-log-home&quot; context param is not specified,
 *          the path associated with the generated system variable defaults to
 *          the WEB-INF/logs directory of the current webapp which is created
 *          if it doesn't exist... unless the webapp is running directly
 *          from a .war file.  In the latter case, this context param
 *          *must* be specified if using a FileAppender.
 *          Note that, if specified, the value is treated as an absolute
 *          system path which is not relative to the webapp. --&gt;
 * &lt;!-- &lt;context-param&gt;
 *     &lt;param-name&gt;log4j-log-home&lt;/param-name&gt;
 *     &lt;param-value&gt;/usr/local/logs/tomcat&lt;/param-value&gt;
 * &lt;/context-param&gt; --&gt;
 * &lt;!-- &lt;context-param&gt;
 *     &lt;param-name&gt;log4j-sysprop-name&lt;/param-name&gt;
 *     &lt;param-value&gt;Barracuda.log.home&lt;/param-value&gt;
 * &lt;/context-param&gt; --&gt;
 *
 * &lt;listener&gt;
 *     &lt;listener-class&gt;
 *      org.apache.log4j.servlet.InitContextListener
 *     &lt;/listener-class&gt;
 * &lt;/listener&gt;
 * </pre>
 * NOTE: Make sure to reference the documentation for each individual selector
 * to see if any configuration is required above an beyond the above.
 * </blockquote>
 * </p>
 * <h4>Below is some more information on each of the configuration properties
 * </h4>
 * <p>
 * <dl>
 * <dt><code>log4j-selector</code></dt>
 * <dd>
 * The <code>log4j-selector</code> init parameter specifies the preferred
 * repository selector for Log4j.  Log4j only uses a single repository
 * selector at a time and if one is already installed, a new one will not
 * be installed except if one has a reference to the original guard object
 * that can unlock the mechanism to re-set the repository selector. We make
 * no attempt to store a handle to the guard object, so once it is set, there
 * is no re-setting possible. The first application to set the repository
 * selector will force that repository selector upon all other applications
 * using a particular instance of log4j.jar.  This is actually fine since
 * any of the available repository selectors should provide a unique logging
 * environment for each application.  Just be ready to use all possible
 * repository selectors.  Of the classes in the {@link org.apache.log4j.selector selector}
 * package, only the {@link org.apache.log4j.selector.ContextJNDISelector} requires
 * any special setup.
 * </dd>
 * <dt><code>log4j-config</code></dt>
 * <dd>
 * The <code>log4j-config</code> init parameter specifies the location of the
 * Log4j configuration file relative to the current webapp.
 * If the <code>log4j-config</code> init parameter is omitted, this class
 * will just let Log4j configure itself since, upon first use of Log4j, if it
 * has not yet been configured, it will search for a config file named
 * log4j.xml or log4j.properties in the classpath. If it can't find one, it
 * falls back to using the <code>BasicConfigurator.configure()</code> to
 * initialize Log4j.
 * </dd>
 * <dt><code>log4j-cron</code></dt>
 * <dd>
 * The <code>log4j-cron</code> init parameter specifies the number of
 * milliseconds to wait in between reads of the config file using
 * <code>configureAndWatch()</code>. If omitted, given a value of 0, or given
 * a value that is other than something that which can be converted to a Java
 * long value a normal <code>configure()</code> is used. Additionally, the watchdogs
 * in Log4j-1.2.x have no lifecycle, meaning that they can't be shut down, even by a
 * call to LogManager.shutdown().  For this
 * reason, it is recommended that this param not be used or set to 0.
 * </dd>
 * <dt><code>log4j-log-home</code></dt>
 * <dd>
 * The <code>log4j-log-home</code> init parameter is optional. It specifies a
 * custom path to a directory meant to contain log files for the current
 * webapp when using a <code>FileAppender</code>. If not specified, it will
 * default to using the location WEB-INF/logs to contain log files. If the
 * directory doesn't exist, it is created. A system parameter is then created
 * in the following format:
 * <blockquote>
 *     <code>[context path].log.home</code>
 * </blockquote>
 * This can be referenced in an xml config file (not sure if it works for a
 * properties config file?) in the following fashion for a webapp with the
 * context path &quot;/Barracuda&quot;:
 * <blockquote>
 *   <code>&lt;param
 *           name=&quot;File&quot;
 *           value=&quot;${Barracuda.log.home}/main.log&quot; /&gt;</code>
 * </blockquote>
 * In this case, we are running in the &quot;Barracuda&quot; context and the
 * &quot;main.log&quot; file will get created in whatever directory path is
 * specified by the system property &quot;Barracuda.log.home&quot;.
 * <p>
 * <strong>Note</strong> that if the webapp is being run directly from a .war
 * file, the automatic creation of the WEB-INF/logs directory and
 * [context path].log.home system property will *not* be performed. In this
 * case, you would have to provide a custom directory path for the this to
 * work. Also note that <code>configureAndWatch()</code> will not be used in
 * the case that the webapp is running directly from a .war file.
 * <code>configure()</code> will be used instead.
 * </p>
 * </dd>
 * <dt><code>log4j-sysprop-name</code></dt>
 * <dd>
 * The <code>log4j-sysprop-name</code> init parameter is optional. The system
 * property name is derived automatically by default. However, this has only
 * been tested successfully on Tomcat which as a very straightforward and
 * predictable naming scheme for the path returned by the
 * &quot;javax.servlet.context.tempdir&quot; context attribute, of which the
 * last directory in the value of that path is used as the [context path] part
 * of the system property name. Since other servers may not use a predictable
 * naming scheme such as this and Tomcat may, at any time, change its naming
 * scheme, this context param was added to make this initialization usable
 * across all servers. If this is used, one needs to make sure that the system
 * property name specified here matches up with that specified in log4.xml.
 * Additionally, it is recommended that one use the same naming scheme
 * mentioned previously ([context path].log.home) to make sure that the name
 * is unique within the current JVM, as system properties are global and we
 * want to avoid clashing with another app using the same system property
 * name.
 * </dd>
 * </dl>
 *
 * @author <a href="mailto:hoju@visi.com">Jacob Kjome</a>
 * @since  1.3
 */
public class InitContextListener implements ServletContextListener {
  /**
   * Application Startup Event
   *
   * @param sce the context event provided by the container
   */
  public void contextInitialized(ServletContextEvent sce) {
    InitShutdownController.initializeLog4j(sce.getServletContext());
  }

  /**
   * Application Shutdown Event
   *
   * @param sce the context event provided by the container
   */
  public void contextDestroyed(ServletContextEvent sce) {
    InitShutdownController.shutdownLog4j(sce.getServletContext());
  }
}