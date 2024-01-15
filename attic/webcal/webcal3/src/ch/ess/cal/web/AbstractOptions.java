package ch.ess.cal.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import org.apache.struts.Globals;
import org.apache.struts.util.LabelValueBean;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.RequestUtils;

import com.cc.framework.ui.model.OptionListDataModel;

/**
 * @author sr
 * @version $Revision: 1.2 $ $Date: 2005/05/15 11:05:32 $
 */
public abstract class AbstractOptions implements OptionListDataModel, Serializable {

  private List<Comparable<Object>> labelValue;

  public AbstractOptions() {
    labelValue = new ArrayList<Comparable<Object>>();
  }

  public List<Comparable<Object>> getLabelValue() {
    return labelValue;
  }

  protected void clear() {
    labelValue.clear();
  }

  protected void addTranslate(final HttpServletRequest request, final String label, final String value) {
    add(new LabelValueBean(translate(request, label), value));
  }

  protected void add(final String label, final String value) {
    add(new LabelValueBean(label, value));
  }

  protected void add(final LabelValueBean lvBean) {
    labelValue.add(lvBean);
  }

  protected void addObject(final Comparable bean) {
    labelValue.add(bean);
  }

  protected void sort() {
    Collections.sort(labelValue);
  }

  protected Locale getLocale(final HttpServletRequest request) {
    return RequestUtils.getUserLocale(request, null);
  }

  protected MessageResources getMessages(final HttpServletRequest request) {
    return ((MessageResources)request.getAttribute(Globals.MESSAGES_KEY));
  }

  protected String translate(final HttpServletRequest request, final String key) {
    return getMessages(request).getMessage(getLocale(request), key);
  }

  public abstract void init(final HttpServletRequest request) throws JspException;

  public Object getKey(final int index) {
    LabelValueBean lvBean = (LabelValueBean)labelValue.get(index);
    return lvBean.getValue();
  }

  public Object getValue(final int index) {
    LabelValueBean lvBean = (LabelValueBean)labelValue.get(index);
    return lvBean.getLabel();
  }

  public String getTooltip(final int index) {
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
