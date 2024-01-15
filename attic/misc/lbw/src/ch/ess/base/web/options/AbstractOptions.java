package ch.ess.base.web.options;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import org.apache.struts.util.LabelValueBean;
import org.apache.struts.util.MessageResources;

import ch.ess.base.Util;

import com.cc.framework.ui.model.OptionListDataModel;

public abstract class AbstractOptions implements OptionListDataModel, Serializable {

  private List labelValue;
  private Map<String,Object> tagAttributes;

  public AbstractOptions() {
    labelValue = new ArrayList();
  }

  public List getLabelValue() {
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

  @SuppressWarnings("unchecked")
  protected void add(final LabelValueBean lvBean) {
    labelValue.add(lvBean);
  }

  @SuppressWarnings("unchecked")
  protected void sort() {
    Collections.sort(labelValue);
  }

  protected Locale getLocale(final HttpServletRequest request) {
    return Util.getLocale(request);
  }

  protected MessageResources getMessages(final HttpServletRequest request) {
    return Util.getMessages(request);
  }

  protected String translate(final HttpServletRequest request, final String key) {
    return Util.translate(request, key);
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

  public Map<String, Object> getTagAttributes() {
    return tagAttributes;
  }

  public void setTagAttributes(Map<String, Object> attributes) {
    this.tagAttributes = attributes;
  }

  @SuppressWarnings("unchecked")
  protected void addObject(final Object bean) {
    labelValue.add(bean);
  }
  
  public int size() {
    if (labelValue != null) {
      return labelValue.size();
    }
    return 0;
  }

  public int getSize() {
    return size();
  }
}
