package ch.ess.hgtracker.web.login;

import org.apache.struts.validator.ValidatorForm;

public class LoginForm extends ValidatorForm {

  private String passwort;
  private String benutzername;

  public String getBenutzername() {
    return benutzername;
  }

  public void setBenutzername(String benutzername) {
    this.benutzername = benutzername;
  }

  public String getPasswort() {
    return passwort;
  }

  public void setPasswort(String passwort) {
    this.passwort = passwort;
  }
}
