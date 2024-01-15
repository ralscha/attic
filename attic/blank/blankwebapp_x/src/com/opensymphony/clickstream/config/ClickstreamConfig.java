/*
 * Created by IntelliJ IDEA.
 * User: plightbo
 * Date: Jun 8, 2002
 * Time: 1:37:24 AM
 */
package com.opensymphony.clickstream.config;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.validator.GenericValidator;

/**
 * Clickstream configuration data.
 * 
 * @author <a href="plightbo@hotmail.com">Patrick Lightbody </a>
 */
public class ClickstreamConfig {
  private String loggerClass;
  private List botAgents = new ArrayList();
  private List botHosts = new ArrayList();
  private int logSize = -1;

  public String getLoggerClass() {
    return loggerClass;
  }

  public void setLoggerClass(String loggerClass) {
    this.loggerClass = loggerClass;
  }

  public void addBotAgent(String agent) {
    botAgents.add(agent);
  }

  public void addBotHost(String host) {
    botHosts.add(host);
  }

  public List getBotAgents() {
    return botAgents;
  }

  public List getBotHosts() {
    return botHosts;
  }

  public int getLogSize() {
    return logSize;
  }

  public void setLogSize(String string) {
    if (GenericValidator.isInt(string)) {
      logSize = Integer.parseInt(string);
    } else {
      logSize = -1;
    }

  }
}