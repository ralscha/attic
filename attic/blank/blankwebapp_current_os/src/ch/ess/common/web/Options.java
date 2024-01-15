package ch.ess.common.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import net.sf.hibernate.HibernateException;

import org.apache.struts.util.MessageResources;

/**
 * @author Ralph Schaer
 * @version $Revision: 1.7 $ $Date: 2004/05/22 12:24:22 $
 */
public abstract class Options {

  private List labelValue = null;
  private MessageResources messages = null;
  private Locale locale = null;
  private HttpServletRequest request = null;

  public Options() {
    labelValue = new ArrayList();
  }

  public List getLabelValue() {
    return labelValue;
  }

  public void setLabelValue(List list) {
    labelValue = list;
  }

  public int getSize() {
    if (labelValue != null) {
      return labelValue.size();
    }
    return 0;
  }

  public void sort() {
    Collections.sort(labelValue);
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

  public void setLocale(Locale locale) {
    this.locale = locale;
  }

  public void setMessages(MessageResources resources) {
    messages = resources;
  }

  public HttpServletRequest getRequest() {
    return request;
  }

  public void setRequest(HttpServletRequest request) {
    this.request = request;
  }

  public abstract void init() throws HibernateException;

}