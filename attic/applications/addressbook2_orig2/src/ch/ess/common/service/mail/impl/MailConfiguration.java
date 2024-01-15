package ch.ess.common.service.mail.impl;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.2 $ $Date: 2003/11/11 19:10:52 $ 
  */
public class MailConfiguration {
  
  public static final String DEFAULT_PROTOCOL = "smtp";
  
  private String host;
  private String user;
  private String password;  
  private String protocol = DEFAULT_PROTOCOL;
  private boolean debug;
  private int port;
  private String defaultSender;
  
  public String getDefaultSender() {
    return defaultSender;
  }

  public void setDefaultSender(String defaultSender) {
    this.defaultSender = defaultSender;
  }

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public boolean isDebug() {
    return debug;
  }

  public void setDebug(boolean debug) {
    this.debug = debug;
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getProtocol() {
    return protocol;
  }

  public void setProtocol(String protocol) {
    this.protocol = protocol;
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }
  
}
