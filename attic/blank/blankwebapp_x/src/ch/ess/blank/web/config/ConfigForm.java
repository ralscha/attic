package ch.ess.blank.web.config;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

/** 
 * @author  Ralph Schaer
 * @version $Revision: 1.4 $ $Date: 2004/05/22 15:38:03 $
 *  
 * @struts.form name="configForm"
 */
public class ConfigForm extends ValidatorForm {

  private String passwordLen;
  private String mailSmtp;
  private int mailSmtpPort;
  private String mailSmtpUser;
  private String mailSmtpPassword;
  private String mailSender;
  private String errorMailReceiver;
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
   * @struts.validator-arg position="0" key="config.errorMailReceiver"
   */
  public void setErrorMailReceiver(String string) {

    if (!isEqual(errorMailReceiver, string)) {
      dirty = true;
    }

    errorMailReceiver = string;
  }

  /**   
   * @struts.validator type="required,email"
   * @struts.validator-arg position="0" key="config.defaultMailSender"
   */
  public void setMailSender(String string) {

    if (!isEqual(mailSender, string)) {
      dirty = true;
    }

    mailSender = string;
  }

  /**   
   * @struts.validator type="required"
   * @struts.validator-arg position="0" key="config.mailSmtp"
   */
  public void setMailSmtp(String string) {
    if (!isEqual(mailSmtp, string)) {
      dirty = true;
    }

    mailSmtp = string;
  }

  /**   
   * @struts.validator type="required,integer"
   * @struts.validator-arg position="0" key="config.passwordLen"
   */
  public void setPasswordLen(String string) {
    passwordLen = string;
  }

  /**   
   * @struts.validator type="required,integer"
   * @struts.validator-arg position="0" key="config.sessionTimeout"
   */
  public void setSessionTimeout(String string) {
    sessionTimeout = string;
  }

  public int getMailSmtpPort() {
    return mailSmtpPort;
  }

  /**   
   * @struts.validator type="required,integer"
   * @struts.validator-arg position="0" key="config.mailSmtpPort"
   */
  public void setMailSmtpPort(int port) {
    if (mailSmtpPort != port) {
      dirty = true;
    }

    this.mailSmtpPort = port;
  }

  public String getMailSmtpPassword() {
    return mailSmtpPassword;
  }

  public void setMailSmtpPassword(String string) {
    if (GenericValidator.isBlankOrNull(string)) {
      string = null;
    }

    if (!isEqual(mailSmtpPassword, string)) {
      dirty = true;
    }

    this.mailSmtpPassword = string;
  }

  public String getMailSmtpUser() {
    return mailSmtpUser;
  }

  public void setMailSmtpUser(String string) {
    if (GenericValidator.isBlankOrNull(string)) {
      string = null;
    }

    if (!isEqual(mailSmtpUser, string)) {
      dirty = true;
    }

    this.mailSmtpUser = string;
  }

  public boolean isDirty() {
    return dirty;
  }

  protected boolean isEqual(String one, String two) {
    if (one == null) {
      if (two == null) {
        return true;
      }
      return false;

    }

    return one.equals(two);

  }

}