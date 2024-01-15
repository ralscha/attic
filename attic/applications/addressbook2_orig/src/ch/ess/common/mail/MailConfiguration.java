package ch.ess.common.mail;

import java.util.HashMap;
import java.util.Map;

/** 
  * @author  Ralph Schaer
  * @version 1.0, 13.07.2003 
  */
public class MailConfiguration {

  private boolean debug = false;
  private int errorQueueCheckIntervalInSec = 60 * 30; // 30 Minuten
  private int errorMessageWaitIntervalInSec = 60 * 60 * 6; // 6 Stunden
  private int maxTries = 12;
  private String smtpServer;
  private boolean sendMail;
  private String test;
  private String greetingsKey;
  private String defaultSender;
  private String bundle;

  private Map classMap = new HashMap();

  public boolean isDebug() {
    return this.debug;
  }

  public void setDebug(boolean debug) {
    this.debug = debug;
  }

  public String getSmtpServer() {
    return this.smtpServer;
  }

  public void setSmtpServer(String smtpServer) {
    this.smtpServer = smtpServer;
  }

  public int getErrorQueueCheckIntervalInSec() {
    return this.errorQueueCheckIntervalInSec;
  }

  public void setErrorQueueCheckIntervalInSec(int errorQueueCheckIntervalInSec) {
    this.errorQueueCheckIntervalInSec = errorQueueCheckIntervalInSec;
  }

  public int getMaxTries() {
    return this.maxTries;
  }

  public void setMaxTries(int maxTries) {
    this.maxTries = maxTries;
  }

  public boolean isSendMail() {
    return this.sendMail;
  }

  public void setSendMail(boolean sendMail) {
    this.sendMail = sendMail;
  }

  public String getTest() {
    return this.test;
  }

  public void setTest(String test) {
    this.test = test;
  }

  public int getErrorMessageWaitIntervalInSec() {
    return this.errorMessageWaitIntervalInSec;
  }

  public void setErrorMessageWaitIntervalInSec(int errorMessageWaitIntervalInSec) {
    this.errorMessageWaitIntervalInSec = errorMessageWaitIntervalInSec;
  }

  public Map getClassMap() {
    return classMap;
  }

  public void setClassMap(Map classMap) {
    this.classMap = classMap;
  }

  public String getGreetingsKey() {
    return greetingsKey;
  }

  public void setGreetingsKey(String greetingsKey) {
    this.greetingsKey = greetingsKey;
  }

  public String getDefaultSender() {
    return defaultSender;
  }

  public void setDefaultSender(String defaultSender) {
    this.defaultSender = defaultSender;
  }

  public String getBundle() {
    return bundle;
  }

  public void setBundle(String string) {
    bundle = string;
  }

}
