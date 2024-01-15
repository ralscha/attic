package ch.ess.base.xml.textresource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2005/07/03 04:48:19 $
 */

public class Resource {
  private String key;
  private Map<String, String> text;
  private List<Variable> variableList;
  private Map<Locale, String> description;

  public String getKey() {
    return key;
  }

  public List<Variable> getVariableList() {
    return variableList;
  }

  public void setKey(final String string) {
    key = string;
  }

  public void setVariableList(final List<Variable> list) {
    variableList = list;
  }

  public void addText(final String locale, final String txt) {
    if (text == null) {
      text = new HashMap<String, String>();
    }
    text.put(locale, txt);
  }

  public Map<String, String> getText() {
    return text;
  }

  public void addVariable(final Variable variable) {
    if (variableList == null) {
      variableList = new ArrayList<Variable>();
    }
    variableList.add(variable);
  }

  public void addDescription(final String locale, final String txt) {
    if (description == null) {
      description = new HashMap<Locale, String>();
    }
    description.put(new Locale(locale), txt);
  }

  public Map<Locale, String> getDescription() {
    return description;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

}