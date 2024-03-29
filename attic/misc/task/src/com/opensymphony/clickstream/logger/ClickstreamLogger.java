/*
 * Created by IntelliJ IDEA.
 * User: plightbo
 * Date: Jun 8, 2002
 * Time: 1:33:36 AM
 */
package com.opensymphony.clickstream.logger;

import com.opensymphony.clickstream.Clickstream;

/**
 * A simple interface that is called when a session is invalidated the clickstream is finished.
 *
 * @author <a href="plightbo@hotmail.com">Patrick Lightbody</a>
 */
public interface ClickstreamLogger {

  /**
   * Initiates logging on a clickstream that just recently finished or was invalidated.
   *
   * @param clickstream the clickstream that has just finished
   */
  void log(Clickstream clickstream);
}
