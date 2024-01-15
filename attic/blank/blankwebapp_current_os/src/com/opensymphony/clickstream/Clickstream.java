package com.opensymphony.clickstream;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.opensymphony.clickstream.config.ConfigLoader;

/**
 * The actual stream of clicks tracked during a user's navigation through a
 * site.
 * 
 * @author <a href="plightbo@hotmail.com">Patrick Lightbody </a>
 */
public class Clickstream implements Serializable {
  private List clickstream;
  private Map attributes;
  private String hostname;
  private transient HttpSession session;
  private String initialReferrer;
  private Date start;
  private Date lastRequest;
  private boolean bot;
  private int logSize;

  public Clickstream() {
    int logSizeL = ConfigLoader.getInstance().getConfig().getLogSize();
    if (logSizeL > 0) {
      this.logSize = logSizeL;
      clickstream = new ArrayList();
    } else if (logSizeL == -1) {
      this.logSize = Integer.MAX_VALUE;
      clickstream = new ArrayList();
    } else {
      clickstream = null;
    }
    attributes = new HashMap();
    start = new Date();
    lastRequest = new Date();
    bot = false;
  }

  /**
   * Adds a new request to the stream of clicks. The HttpSerlvetRequest is
   * converted to a ClickstreamRequest object and added to the clickstream.
   * 
   * @param request
   *          The serlvet request to be added to the clickstream
   */
  public void addRequest(HttpServletRequest request) {

    if (clickstream == null) {
      return;
    }

    lastRequest = new Date();

    if (hostname == null) {
      hostname = request.getRemoteHost();
      session = request.getSession();
    }

    // if this is the first request in the click stream
    if (clickstream.size() == 0) {
      // setup initial referrer
      if (request.getHeader("REFERER") != null) {
        initialReferrer = request.getHeader("REFERER");
      } else {
        initialReferrer = "";
      }

      // decide whether this is a bot
      bot = BotChecker.isBot(request);
    }

    if (clickstream.size() >= logSize) {
      clickstream.remove(0);
    }

    clickstream.add(new ClickstreamRequest(request, lastRequest));
  }

  /**
   * Gets an attribute for this clickstream.
   * 
   * @param name
   * @return
   */
  public Object getAttribute(String name) {
    return attributes.get(name);
  }

  /**
   * Sets an attribute for this clickstream.
   * 
   * @param name
   * @param value
   */
  public void setAttribute(String name, Object value) {
    attributes.put(name, value);
  }

  /**
   * Returns the host name that this clickstream relates to.
   * 
   * @return the host name that the user clicked through
   */
  public String getHostname() {
    return hostname;
  }

  /**
   * Returns the bot status.
   * 
   * @return true if the client is bot or spider
   */
  public boolean isBot() {
    return bot;
  }

  /**
   * Returns the HttpSession associated with this clickstream.
   * 
   * @return the HttpSession associated with this clickstream
   */
  public HttpSession getSession() {
    return session;
  }

  /**
   * The URL of the initial referer. This is useful for determining how the user
   * entered the site.
   * 
   * @return the URL of the initial referer
   */
  public String getInitialReferrer() {
    return initialReferrer;
  }

  /**
   * Returns the Date when the clickstream began.
   * 
   * @return the Date when the clickstream began
   */
  public Date getStart() {
    return start;
  }

  /**
   * Returns the last Date that the clickstream was modified.
   * 
   * @return the last Date that the clickstream was modified
   */
  public Date getLastRequest() {
    return lastRequest;
  }

  /**
   * Returns the actual List of ClickstreamRequest objects.
   * 
   * @return the actual List of ClickstreamRequest objects
   */
  public List getStream() {
    return clickstream;
  }
}