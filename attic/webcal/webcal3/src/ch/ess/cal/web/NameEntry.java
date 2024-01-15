package ch.ess.cal.web;

import java.io.Serializable;

/**
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2005/05/09 07:46:07 $
 */
public class NameEntry implements Serializable {

  private String language;
  private String name;
  private String locale;

  public String getLanguage() {
    return language;
  }

  public String getName() {
    return name;
  }

  public void setLanguage(final String string) {
    language = string;
  }

  public void setName(final String string) {
    name = string;
  }

  public String getLocale() {
    return locale;
  }

  public void setLocale(final String string) {
    locale = string;
  }

}
