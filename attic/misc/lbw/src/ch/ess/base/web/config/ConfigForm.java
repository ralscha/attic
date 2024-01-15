package ch.ess.base.web.config;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionMapping;

import ch.ess.base.annotation.struts.Validator;
import ch.ess.base.annotation.struts.ValidatorField;

import com.cc.framework.adapter.struts.FWValidatorForm;

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
  private String tabset = "general";

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

  @ValidatorField(key = "config.defaultMailSender", required = true, validators = @Validator(name = "email"))
  public void setMailSender(final String string) {

    if (!StringUtils.equals(mailSender, string)) {
      dirty = true;
    }

    mailSender = string;
  }

  @ValidatorField(key = "config.mailSmtp", required = true)
  public void setMailSmtp(final String string) {
    if (!StringUtils.equals(mailSmtp, string)) {
      dirty = true;
    }

    mailSmtp = string;
  }

  @ValidatorField(key = "config.passwordLen", required = true, 
                  validators = {@Validator(name = "integer"), @Validator(name = "positive")})
  public void setPasswordLen(final String string) {
    passwordLen = string;
  }

  @ValidatorField(key = "config.sessionTimeout", required = true, 
                  validators = {@Validator(name = "integer"), @Validator(name = "positive")})
  public void setSessionTimeout(final String string) {
    sessionTimeout = string;
  }

  public String getMailSmtpPort() {
    return mailSmtpPort;
  }

  @ValidatorField(key = "config.mailSmtpPort", required = true, 
                  validators = {@Validator(name = "integer"), @Validator(name = "positive")})
  public void setMailSmtpPort(final String port) {
    if (!StringUtils.equals(mailSmtpPort, port)) {
      dirty = true;
    }

    this.mailSmtpPort = port;
  }

  public String getDateFormat() {
    return dateFormat;
  }

  @ValidatorField(key = "config.dateFormat", required = true)
  public void setDateFormat(String dateFormat) {
    this.dateFormat = dateFormat;
  }

  public String getDateTimeFormat() {
    return dateTimeFormat;
  }

  @ValidatorField(key="config.dateTimeFormat", required=true)
  public void setDateTimeFormat(String dateTimeFormat) {
    this.dateTimeFormat = dateTimeFormat;
  }

  public String getParseDateFormat() {
    return parseDateFormat;
  }

  @ValidatorField(key = "config.parseDateFormat", required = true)
  public void setParseDateFormat(String parseDateFormat) {
    this.parseDateFormat = parseDateFormat;
  }

  public String getTimeFormat() {
    return timeFormat;
  }


  @ValidatorField(key="config.timeFormat", required=true)
  public void setTimeFormat(String timeFormat) {
    this.timeFormat = timeFormat;
  }

  public String getMailSmtpPassword() {
    return mailSmtpPassword;
  }

  public void setMailSmtpPassword(final String string) {

    String tmpStr = string;

    if (StringUtils.isBlank(tmpStr)) {
      tmpStr = null;
    }

    if (!StringUtils.equals(mailSmtpPassword, tmpStr)) {
      dirty = true;
    }

    this.mailSmtpPassword = tmpStr;
  }

  public String getMailSmtpUser() {
    return mailSmtpUser;
  }

  public void setMailSmtpUser(final String string) {

    String tmpStr = string;

    if (StringUtils.isBlank(tmpStr)) {
      tmpStr = null;
    }

    if (!StringUtils.equals(mailSmtpUser, tmpStr)) {
      dirty = true;
    }

    this.mailSmtpUser = tmpStr;
  }

  public boolean isDirty() {
    return dirty;
  }

  public String getTabset() {
    return tabset;
  }

  public void setTabset(String tabset) {
    this.tabset = tabset;
  }

}
