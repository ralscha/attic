package ch.ess.hgtracker.web.spiel;

import org.apache.struts.validator.ValidatorForm;

public class SpielListForm extends ValidatorForm {

  private String artIdSuche;
  private String printable;
  private String webLogin;
  private String jahr;

  public String getJahr() {
    return jahr;
  }

  public void setJahr(String jahr) {
    this.jahr = jahr;
  }

  public String getPrintable() {
    return printable;
  }

  public void setPrintable(String printing) {
    this.printable = printing;
  }

  public String getWebLogin() {
    return webLogin;
  }

  public void setWebLogin(String webLogin) {
    this.webLogin = webLogin;
  }

  public String getArtIdSuche() {
    return artIdSuche;
  }

  public void setArtIdSuche(String artId) {
    this.artIdSuche = artId;
  }
}
