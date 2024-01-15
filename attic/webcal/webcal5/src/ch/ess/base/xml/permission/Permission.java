package ch.ess.base.xml.permission;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Permission {
  private String key;
  private Map<Locale, String> text;
  private String feature;

  public String getKey() {
    return key;
  }

  public void setKey(final String string) {
    key = string;
  }

  public void addText(final String locale, final String txt) {
    if (text == null) {
      text = new HashMap<Locale, String>();
    }
    text.put(new Locale(locale), txt);
  }

  public Map<Locale, String> getText() {
    return text;
  }

  public String getFeature() {
    return feature;
  }

  public void setFeature(String feature) {
    this.feature = feature;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

}
