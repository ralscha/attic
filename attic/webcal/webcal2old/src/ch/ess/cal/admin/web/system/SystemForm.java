package ch.ess.cal.admin.web.system;

import javax.servlet.http.*;

import org.apache.commons.lang.builder.*;
import org.apache.struts.action.*;
import org.apache.struts.validator.*;

public class SystemForm extends ValidatorForm {

  private String passwordLen;  
  private String mailSmtp;
  private String mailSender;
  private String errorMailReceiver;
  
  

  public void reset(ActionMapping actionMapping, HttpServletRequest request) {
    passwordLen = null;
    mailSmtp = null;
    mailSender = null;
    errorMailReceiver = null;        
  }
  
  
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

  public String getErrorMailReceiver() {
    return errorMailReceiver;
  }

  public String getMailSender() {
    return mailSender;
  }

  public String getMailSmtp() {
    return mailSmtp;
  }


  public String getPasswordLen() {
    return passwordLen;
  }

  public void setErrorMailReceiver(String string) {
    errorMailReceiver = string;
  }

  public void setMailSender(String string) {
    mailSender = string;
  }

  public void setMailSmtp(String string) {
    mailSmtp = string;
  }


  public void setPasswordLen(String string) {
    passwordLen = string;
  }

}
