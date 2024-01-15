package org.apache.struts.example;

import org.apache.struts.action.ValidatingActionForm;

public final class LogonForm implements ValidatingActionForm  {

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

  public String[] validate() {
  }
}
