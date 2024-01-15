package ch.ess.cal.web;

import javax.servlet.http.*;

import org.apache.commons.lang.builder.*;
import org.apache.struts.action.*;
import org.apache.struts.validator.*;

public class LogonForm extends ValidatorForm {

  private String userName;
  private String password;
  private boolean rememberLogon;
 

  public void reset(ActionMapping actionMapping, HttpServletRequest request) {        
    userName = null;
    password = null;
    rememberLogon = false;
  }

 

  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

  

  public String getUserName() {
    return userName;
  }

  public String getPassword() {
    return password;
  }

  public void setUserName(String string) {
    userName = string;
  }

  public void setPassword(String string) {
    password = string;
  }

  public boolean isRememberLogon() {
    return rememberLogon;
  }

  public void setRememberLogon(boolean b) {
    rememberLogon = b;
  }

}
