package ch.ess.cal.web.config;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionMapping;

import ch.ess.cal.Util;

import com.cc.framework.adapter.struts.FWValidatorForm;

/** 
 * @author sr
 * @version $Revision: 1.2 $ $Date: 2005/05/17 06:04:25 $
 *  
 * @struts.form name="configForm"
 */
public class ConfigForm extends FWValidatorForm {

  private String passwordLen;
  private String mailSmtp;
  private String mailSmtpPort;
  private String mailSmtpUser;
  private String mailSmtpPassword;
  private String mailSender;
  private String sessionTimeout;
  private String dateFormat;
  private String parseDateFormat;
  private String dateTimeFormat;
  private String timeFormat;

  private boolean dirty;

  @Override
  public void reset(final ActionMapping actionMapping, final HttpServletRequest request) {
    dirty = false;
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
   * @struts.validator-arg position="0" key="config.defaultMailSender"
   */
  public void setMailSender(final String string) {

    if (!Util.isEqual(mailSender, string)) {
      dirty = true;
    }

    mailSender = string;
  }

  /**   
   * @struts.validator type="required"
   * @struts.validator-arg position="0" key="config.mailSmtp"
   */
  public void setMailSmtp(final String string) {
    if (!Util.isEqual(mailSmtp, string)) {
      dirty = true;
    }

    mailSmtp = string;
  }

  /**   
   * @struts.validator type="required,integer,positive"
   * @struts.validator-arg position="0" key="config.passwordLen"
   */
  public void setPasswordLen(final String string) {
    passwordLen = string;
  }

  /**   
   * @struts.validator type="required,integer,positive"
   * @struts.validator-arg position="0" key="config.sessionTimeout"
   */
  public void setSessionTimeout(final String string) {
    sessionTimeout = string;
  }

  public String getMailSmtpPort() {
    return mailSmtpPort;
  }

  /**   
   * @struts.validator type="required,integer,positive"
   * @struts.validator-arg position="0" key="config.mailSmtpPort"
   */
  public void setMailSmtpPort(final String port) {
    if (!Util.isEqual(mailSmtpPort, port)) {
      dirty = true;
    }

    this.mailSmtpPort = port;
  }

  public String getDateFormat() {
    return dateFormat;
  }

  /**   
   * @struts.validator type="required"
   * @struts.validator-arg position="0" key="config.dateFormat"
   */
  public void setDateFormat(String dateFormat) {
    this.dateFormat = dateFormat;
  }

  public String getDateTimeFormat() {
    return dateTimeFormat;
  }

  /**   
   * @struts.validator type="required"
   * @struts.validator-arg position="0" key="config.dateTimeFormat"
   */
  public void setDateTimeFormat(String dateTimeFormat) {
    this.dateTimeFormat = dateTimeFormat;
  }

  public String getParseDateFormat() {
    return parseDateFormat;
  }

  /**   
   * @struts.validator type="required"
   * @struts.validator-arg position="0" key="config.parseDateFormat"
   */
  public void setParseDateFormat(String parseDateFormat) {
    this.parseDateFormat = parseDateFormat;
  }

  public String getTimeFormat() {
    return timeFormat;
  }

  /**   
   * @struts.validator type="required"
   * @struts.validator-arg position="0" key="config.timeFormat"
   */
  public void setTimeFormat(String timeFormat) {
    this.timeFormat = timeFormat;
  }

  public String getMailSmtpPassword() {
    return mailSmtpPassword;
  }

  public void setMailSmtpPassword(final String string) {

    String tmpStr = string;

    if (GenericValidator.isBlankOrNull(tmpStr)) {
      tmpStr = null;
    }

    if (!Util.isEqual(mailSmtpPassword, tmpStr)) {
      dirty = true;
    }

    this.mailSmtpPassword = tmpStr;
  }

  public String getMailSmtpUser() {
    return mailSmtpUser;
  }

  public void setMailSmtpUser(final String string) {

    String tmpStr = string;

    if (GenericValidator.isBlankOrNull(tmpStr)) {
      tmpStr = null;
    }

    if (!Util.isEqual(mailSmtpUser, tmpStr)) {
      dirty = true;
    }

    this.mailSmtpUser = tmpStr;
  }

  public boolean isDirty() {
    return dirty;
  }

}
