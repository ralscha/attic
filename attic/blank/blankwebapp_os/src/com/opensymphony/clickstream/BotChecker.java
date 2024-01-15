package com.opensymphony.clickstream;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.opensymphony.clickstream.config.ConfigLoader;

/**
 * Determines if a request is actually a bot or spider.
 * 
 * @author <a href="plightbo@hotmail.com">Patrick Lightbody </a>
 */
public class BotChecker {
  public static boolean isBot(HttpServletRequest request) {
    String requestURI = request.getRequestURI();
    List agents = ConfigLoader.getInstance().getConfig().getBotAgents();
    List hosts = ConfigLoader.getInstance().getConfig().getBotHosts();

    // if it requested robots.txt, it's a bot

    if (requestURI.indexOf("robots.txt") >= 0) {
      return true;
    }

    String remoteHost = request.getRemoteHost(); // requires a DNS lookup
    if (remoteHost != null) {
      for (Iterator iterator = hosts.iterator(); iterator.hasNext();) {
        String host = (String) iterator.next();
        if (remoteHost.indexOf(host) >= 0) {
          return true;
        }
      }
    }

    String userAgent = request.getHeader("User-Agent");
    if (userAgent != null) {
      for (Iterator iterator = agents.iterator(); iterator.hasNext();) {
        String agent = (String) iterator.next();
        if (userAgent.indexOf(agent) >= 0) {
          return true;
        }
      }
    }

    return false;
  }
}

