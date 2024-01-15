package org.apache.struts.example;

import org.apache.struts.action.ActionForm;

public final class LogonForm implements ActionForm  {

  private String username;

  public LogonForm() {
    username = "";
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    if (username != null)
      this.username = username;
    else
      this.username = "";
  }

}
