package ch.ess.cal.xml.textresource;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2005/05/09 07:46:08 $
 */

public class Variable {
  private String name;
  private Map<Locale, String> description;

  public String getName() {
    return name;
  }

  public void setName(final String string) {
    name = string;
  }

  public void addDescription(final String locale, final String text) {
    if (description == null) {
      description = new HashMap<Locale, String>();
    }
    description.put(new Locale(locale), text);
  }

  public Map<Locale, String> getDescription() {
    return description;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

}
