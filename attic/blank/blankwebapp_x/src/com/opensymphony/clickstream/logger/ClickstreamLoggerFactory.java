/*
 * Created by IntelliJ IDEA.
 * User: plightbo
 * Date: Jun 8, 2002
 * Time: 1:33:46 AM
 */
package com.opensymphony.clickstream.logger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.clickstream.config.ConfigLoader;

/**
 * Simple factory that creates ClickstreamLogger instances.
 * 
 * @author <a href="plightbo@hotmail.com">Patrick Lightbody </a>
 */
public class ClickstreamLoggerFactory {
  private static final Log LOG = LogFactory.getLog(ClickstreamLoggerFactory.class);

  /**
   * Returns a new logging instance.
   * 
   * @return a new logging instance
   */
  public static ClickstreamLogger getLogger() {
    String loggerClass = ConfigLoader.getInstance().getConfig().getLoggerClass();
    try {
      return (ClickstreamLogger) Class.forName(loggerClass).newInstance();
    } catch (Exception e) {
      LOG.fatal("Error finding a suitable ClickstreamLogger", e);
      throw new RuntimeException(e.getMessage());
    }
  }
}