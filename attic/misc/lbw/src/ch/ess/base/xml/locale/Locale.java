package ch.ess.base.xml.locale;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Locale {
  private String key;
  private java.util.Locale locale;
  private boolean defaultLocale;
  private Map<java.util.Locale, String> text;

  public String getKey() {
    return key;
  }

  public void setKey(final String key) {
    this.key = key;
    locale = new java.util.Locale(key);
  }

  public java.util.Locale getLocale() {
    return locale;
  }

  public boolean isDefaultLocale() {
    return defaultLocale;
  }

  public void setDefaultLocale(final boolean defaultLocale) {
    this.defaultLocale = defaultLocale;
  }

  public void addText(final String localeStr, final String txt) {
    if (text == null) {
      text = new HashMap<java.util.Locale, String>();
    }
    text.put(new java.util.Locale(localeStr), txt);
  }

  public Map<java.util.Locale, String> getText() {
    return text;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

}