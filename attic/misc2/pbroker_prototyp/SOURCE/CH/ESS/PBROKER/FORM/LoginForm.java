package ch.ess.pbroker.form;

import org.apache.struts.action.ActionForm;

public final class LoginForm implements ActionForm  {

  private String user = "";
  private String password = "";
  private String logon = "";

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    if (user != null)
      this.user = user;
    else
      this.user = "";
  }

  public void clearPassword() {
    password = "";
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    if (password != null)
      this.password = password;
    else
      this.password = "";
  }

  public String getLogon() {
    return logon;
  }

  public void setLogon(String logon) {
    if (logon != null)
      this.logon = logon;
    else
      this.logon = "";
  }

}
