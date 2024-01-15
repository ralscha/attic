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

package org.apache.log4j.selector;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

import org.apache.log4j.Hierarchy;
import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggerRepository;
import org.apache.log4j.spi.RepositorySelector;
import org.apache.log4j.spi.RootCategory;

/**
 * Log4j Contextual ClassLoader Selector
 *
 * <p>based primarily on Ceki Gülcü's article <h3>Supporting the Log4j
 * <code>RepositorySelector</code> in Servlet Containers</h3> at:
 * http://qos.ch/logging/sc.html</p>
 *
 * <p>By default, the class static <code>RepositorySelector</code> variable
 * of the <code>LogManager</code> class is set to a trivial
 * <code>RepositorySelector</code> implementation which always
 * returns the same logger repository, which also happens to be a
 * <code>Hierarchy</code> instance. In other words, by default log4j will use
 * one hierarchy, the default hierarchy. This behavior can be overridden via
 * the <code>LogManager</code>'s
 * <code>setRepositorySelector(RepositorySelector, Object)</code> method.</p>
 *
 * <p>That is where this class enters the picture.  It can be used to define a
 * custom logger repository.  It makes use of the fact that each webapp runs
 * in its own classloader.  This means we can track hierachies using the
 * webapp classloader as the key to each individual hierarchy.  That is what
 * is meant by "contextual classloader" selector.  Each classloader provides a
 * unique logging context.</p>
 *
 * <p>Of course, this means that this class will only work in containers which
 * provide for separate classloaders, so that is something to keep in mind.
 * This methodology will certainly work in containers such as Tomcat 4/5 and
 * probably a multitude of others.  However, Tomcat 4.x.x and 5.x.x are the only
 * containers currently tested.</p>
 *
 * @author <a href="mailto:hoju@visi.com">Jacob Kjome</a>
 * @since  1.3
 */
public class ContextClassLoaderSelector implements RepositorySelector {
  /**
   * key: current thread's ContextClassLoader,
   * value: Hierarchy instance
   */
  private final Map hierMap;

  /**
   * public no-args constructor
   */
  public ContextClassLoaderSelector() {
    hierMap = Collections.synchronizedMap(new WeakHashMap());
  }

  /**
   * implemented RepositorySelector interface method. The returned
   * value is guaranteed to be non-null.
   *
   * @return the appropriate classloader-keyed Hierarchy/LoggerRepository
   */
  public LoggerRepository getLoggerRepository() {
    ClassLoader cl = Thread.currentThread().getContextClassLoader();
    Hierarchy hierarchy = (Hierarchy)hierMap.get(cl);

    if (hierarchy == null) {
      hierarchy = new Hierarchy(new RootCategory(Level.DEBUG));
      hierMap.put(cl, hierarchy);
    }

    return hierarchy;
  }
}