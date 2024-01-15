package com.opensymphony.clickstream;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.opensymphony.clickstream.logger.ClickstreamLoggerFactory;

/**
 * The listener that keeps track of all clickstreams in the container as well
 * as the creating new Clickstream objects and initiating logging when the
 * clickstream dies (session has been invalidated).
 *
 * @author <a href="plightbo@hotmail.com">Patrick Lightbody</a>
 * @web.listener 
 */
public class ClickstreamListener implements ServletContextListener, HttpSessionListener {

  private Map clickstreams = new HashMap();

  public void contextInitialized(ServletContextEvent sce) {
    sce.getServletContext().setAttribute("clickstreams", clickstreams);
  }

  public void contextDestroyed(ServletContextEvent sce) {
    sce.getServletContext().setAttribute("clickstreams", null);
  }

  public void sessionCreated(HttpSessionEvent hse) {
    HttpSession session = hse.getSession();
    Clickstream clickstream = new Clickstream();
    session.setAttribute("clickstream", clickstream);
    clickstreams.put(session.getId(), clickstream);
  }

  public void sessionDestroyed(HttpSessionEvent hse) {
    HttpSession session = hse.getSession();
    Clickstream stream = (Clickstream) clickstreams.get(session.getId());

    try {
      ClickstreamLoggerFactory.getLogger().log(stream);
    } catch (Exception e) {
      hse.getSession().getServletContext().log("sessionDestroyed", e);
    } finally {
      clickstreams.remove(session.getId());
    }
  }
}