package ch.ess.hgtracker.web.rangpunkte;

import org.apache.struts.validator.ValidatorForm;

public class RangpunkteListForm extends ValidatorForm {

  private String jahr;
  private String printable;
  private String webLogin;

  public String getJahr() {
    return jahr;
  }

  public void setJahr(String jahr) {
    this.jahr = jahr;
  }

  public String getPrintable() {
    return printable;
  }

  public void setPrintable(String printable) {
    this.printable = printable;
  }

  public String getWebLogin() {
    return webLogin;
  }

  public void setWebLogin(String webLogin) {
    this.webLogin = webLogin;
  }

}
