package ch.ess.common.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import net.sf.hibernate.HibernateException;

import org.apache.struts.Globals;
import org.apache.struts.util.LabelValueBean;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.RequestUtils;

/**
 * @author Ralph Schaer
 * @version $Revision: 1.4 $ $Date: 2004/05/22 15:38:03 $
 */
public abstract class Options {

  private List labelValue;
  private MessageResources messages;
  private Locale locale;
  private HttpServletRequest request;

  public Options() {
    labelValue = new ArrayList();
  }

  public List getLabelValue() {
    return labelValue;
  }

  protected void clear() {
    labelValue.clear();
  }

  protected void add(LabelValueBean lvBean) {
    labelValue.add(lvBean);
  }

  protected void addObject(Object bean) {
    labelValue.add(bean);
  }

  protected void sort() {
    Collections.sort(labelValue);
  }

  protected Locale getLocale() {
    if (locale == null) {
      locale = RequestUtils.getUserLocale(request, null);
    }

    return locale;
  }

  protected MessageResources getMessages() {
    if (messages == null) {
      messages = ((MessageResources)request.getAttribute(Globals.MESSAGES_KEY));
    }
    return messages;
  }

  protected String translate(String key) {
    return getMessages().getMessage(getLocale(), key);
  }

  protected HttpServletRequest getRequest() {
    return request;
  }

  public void init(HttpServletRequest req) throws HibernateException {
    this.request = req;

    init();
  }

  public abstract void init() throws HibernateException;

  public Object getKey(int index) {
    LabelValueBean lvBean = (LabelValueBean)labelValue.get(index);
    return lvBean.getValue();
  }

  public Object getValue(int index) {
    LabelValueBean lvBean = (LabelValueBean)labelValue.get(index);
    return lvBean.getLabel();
  }

  public String getTooltip(int index) {
    LabelValueBean lvBean = (LabelValueBean)labelValue.get(index);
    return lvBean.getLabel();
  }

  public int size() {
    if (labelValue != null) {
      return labelValue.size();
    }
    return 0;
  }

}