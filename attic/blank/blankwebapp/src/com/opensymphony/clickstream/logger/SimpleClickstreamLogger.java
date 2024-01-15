/*
 * Created by IntelliJ IDEA.
 * User: plightbo
 * Date: Jun 8, 2002
 * Time: 1:57:13 AM
 */
package com.opensymphony.clickstream.logger;

import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.clickstream.Clickstream;
import com.opensymphony.clickstream.ClickstreamRequest;

/**
 * A simple ClickstreamLogger that outputs the entire clickstream to the OSCore
 * Logger utility.
 * 
 * @author <a href="plightbo@hotmail.com">Patrick Lightbody </a>
 */
public class SimpleClickstreamLogger implements ClickstreamLogger {
  private static final Log LOG = LogFactory.getLog(SimpleClickstreamLogger.class);

  public void log(Clickstream clickstream) {
    if (clickstream == null) {
      return;
    }

    StringBuffer output = new StringBuffer();

    String hostname = clickstream.getHostname();
    HttpSession session = clickstream.getSession();
    String initialReferrer = clickstream.getInitialReferrer();
    Date start = clickstream.getStart();
    Date lastRequest = clickstream.getLastRequest();

    output.append("Clickstream for: " + hostname + "\n");
    output.append("Session ID: " + (session != null ? session.getId() + "" : "") + "\n");
    output.append("Initial Referrer: " + initialReferrer + "\n");
    output.append("Stream started: " + start + "\n");
    output.append("Last request: " + lastRequest + "\n");

    long streamLength = lastRequest.getTime() - start.getTime();

    output.append("Stream length:" + (streamLength > 3600000 ? " " + (streamLength / 3600000) + " hours" : "")
        + (streamLength > 60000 ? " " + ((streamLength / 60000) % 60) + " minutes" : "")
        + (streamLength > 1000 ? " " + ((streamLength / 1000) % 60) + " seconds" : "") + "\n");

    int count = 0;
    for (Iterator iterator = clickstream.getStream().iterator(); iterator.hasNext();) {
      ClickstreamRequest request = (ClickstreamRequest) iterator.next();
      count++;
      output.append(count + ": " + request + (iterator.hasNext() ? "\n" : ""));
    }

    LOG.info(output);
  }
}