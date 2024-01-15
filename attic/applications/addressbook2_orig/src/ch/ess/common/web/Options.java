package ch.ess.common.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import net.sf.hibernate.HibernateException;

import org.apache.struts.util.MessageResources;

/** 
  * @author  Ralph Schaer
  * @version 1.0, 13.07.2003 
  */
public abstract class Options {

  private List labelValue = null;
  private MessageResources messages = null;
  private Locale locale = null;

  public Options(Locale locale, MessageResources messages) throws HibernateException {
    labelValue = new ArrayList();
    this.messages = messages;
    this.locale = locale;
    init();
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

  public String getMessage(String key) {
    return messages.getMessage(locale, key);
  }

  public abstract void init() throws HibernateException;

}