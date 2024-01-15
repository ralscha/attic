package ch.ess.addressbook.web.config;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

/** 
  * @author  Ralph Schaer
  * @version 1.0, 13.07.2003 
  * @struts.form name="configForm"
  */
public class ConfigForm extends ValidatorForm {

  private String passwordLen;
  private String mailSmtp;
  private String mailSender;
  private String errorMailReceiver;
  private String uploadPath;
  private String sessionTimeout;

  private boolean dirty;
  
  public void reset(ActionMapping actionMapping, HttpServletRequest request) {
    dirty = false;
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

  public String getSessionTimeout() {
    return sessionTimeout;
  }


  /**   
   * @struts.validator type="required,email"
   * @struts.validator-args arg0resource="ErrorMailReceiver"
   */
  public void setErrorMailReceiver(String string) {
    
    if (!isEqual(errorMailReceiver, string)) {
      dirty = true;
    } 
    
    errorMailReceiver = string;
  }

  /**   
   * @struts.validator type="required,email"
   * @struts.validator-args arg0resource="DefaultMailSender"
   */
  public void setMailSender(String string) {
    
    if (!isEqual(mailSender, string)) {
      dirty = true;
    } 
        
    mailSender = string;
  }

  /**   
   * @struts.validator type="required"
   * @struts.validator-args arg0resource="MailSmtp"
   */
  public void setMailSmtp(String string) {
    if (!isEqual(mailSmtp, string)) {
      dirty = true;
    }     
    
    mailSmtp = string;
  }

  /**   
   * @struts.validator type="required,integer"
   * @struts.validator-args arg0resource="PasswordLen"
   */
  public void setPasswordLen(String string) {
    passwordLen = string;
  }


  public String getUploadPath() {
    return uploadPath;
  }

  /**   
   * @struts.validator type="required"
   * @struts.validator-args arg0resource="UploadPath"
   */
  public void setUploadPath(String string) {
    uploadPath = string;
  }

  /**   
   * @struts.validator type="required,integer"
   * @struts.validator-args arg0resource="SessionTimeout"
   */
  public void setSessionTimeout(String string) {
    sessionTimeout = string;
  }

  public boolean isDirty() {
    return dirty;
  }

  protected boolean isEqual(String one, String two) {
    if (one == null) {
      if (two == null) {
        return true;
      } else {
        return false;
      }
    }

    return one.equals(two);

  }




}
