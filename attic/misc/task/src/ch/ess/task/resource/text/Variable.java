package ch.ess.task.resource.text;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.1 $ $Date: 2003/11/15 10:33:28 $ 
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
