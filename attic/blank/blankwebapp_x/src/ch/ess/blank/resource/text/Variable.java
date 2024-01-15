package ch.ess.blank.resource.text;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author Ralph Schaer
 * @version $Revision: 1.4 $ $Date: 2004/05/22 15:38:04 $
 */

public class Variable {
  private String name;
  private Map description;

  public String getName() {
    return name;
  }

  public void setName(String string) {
    name = string;
  }

  public void addDescription(String locale, String text) {
    if (description == null) {
      description = new HashMap();
    }
    description.put(new Locale(locale), text);
  }

  public Map getDescription() {
    return description;
  }

  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

}