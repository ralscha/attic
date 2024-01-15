package ch.ess.cal.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import net.sf.hibernate.Session;

import org.apache.struts.util.MessageResources;


public abstract class Options {

  private List labelValue = null;
  private MessageResources messages = null;
  private Session sess = null;
  private Locale locale = null;
  
  public Options(Session sess, Locale locale, MessageResources messages) {
    labelValue = new ArrayList();
    this.messages = messages;
    this.sess = sess;
    this.locale = locale;
  }

  public List getLabelValue() {
    return labelValue;
  }

  public void setLabelValue(List list) {
    labelValue = list;
  }

  public MessageResources getMessages() {
    return messages;
  }
  
  public Locale getLocale() {
    return locale;
  }
    

  public abstract void init();



  public Session getSession() {
    return sess;
  }

}