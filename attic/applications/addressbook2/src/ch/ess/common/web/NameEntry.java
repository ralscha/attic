package ch.ess.common.web;

import java.io.Serializable;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.1 $ $Date: 2003/12/30 11:10:27 $ 
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
