package ch.ess.common.web;

import java.io.Serializable;

/**
 * @author Ralph Schaer
 * @version $Revision: 1.4 $ $Date: 2004/05/22 15:38:03 $
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

  public void setLanguage(String string) {
    language = string;
  }

  public void setName(String string) {
    name = string;
  }

  public String getLocale() {
    return locale;
  }

  public void setLocale(String string) {
    locale = string;
  }

}