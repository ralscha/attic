package ch.ess.cal.web.config;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import ch.ess.cal.Constants;
import ch.ess.cal.service.Config;
import ch.ess.cal.service.impl.AppConfig;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FWAction;
import com.cc.framework.adapter.struts.FormActionContext;

/** 
 * @author sr
 * @version $Revision: 1.2 $ $Date: 2005/05/17 06:04:25 $ 
 * 
 * @struts.action path="/config" name="configForm" input="/configedit.jsp" scope="request" validate="false" roles="$systemconfig"
 * 
 * @spring.bean name="/config" lazy-init="true"
 */
public class ConfigAction extends FWAction {

  private Config appConfig;
  private JavaMailSenderImpl mailSender;

  /** 
   * @spring.property ref="mailSender"
   */
  public void setMailSender(final JavaMailSenderImpl mailSender) {
    this.mailSender = mailSender;
  }

  /**
   * @spring.property reflocal="appConfig"      
   */
  public void setAppConfig(final Config appConfig) {
    this.appConfig = appConfig;
  }

  public void doExecute(final ActionContext ctx) throws Exception {

    ConfigForm configForm = (ConfigForm)ctx.form();

    int len = appConfig.getIntegerProperty(AppConfig.PASSWORD_MINLENGTH, -1);
    configForm.setPasswordLen(String.valueOf(len));

    int sessionTimeout = appConfig.getIntegerProperty(AppConfig.SESSION_TIMEOUT, 30);
    configForm.setSessionTimeout(String.valueOf(sessionTimeout));

    String smtpHost = appConfig.getStringProperty(AppConfig.MAIL_SMTPHOST, null);
    configForm.setMailSmtp(smtpHost);

    int smtpHostPort = appConfig.getIntegerProperty(AppConfig.MAIL_SMTPPORT, 25);
    configForm.setMailSmtpPort(String.valueOf(smtpHostPort));

    String smtpUser = appConfig.getStringProperty(AppConfig.MAIL_SMTPUSER, null);
    configForm.setMailSmtpUser(smtpUser);

    String smtpPassword = appConfig.getStringProperty(AppConfig.MAIL_SMTPPASSWORD, null);
    configForm.setMailSmtpPassword(smtpPassword);

    String defaultSender = appConfig.getStringProperty(AppConfig.MAIL_SENDER, null);
    configForm.setMailSender(defaultSender);

    String dateFormat = appConfig.getStringProperty(AppConfig.DATE_FORMAT, null);
    configForm.setDateFormat(dateFormat);

    String parseDateFormat = appConfig.getStringProperty(AppConfig.PARSE_DATE_FORMAT, null);
    configForm.setParseDateFormat(parseDateFormat);

    String dateTimeFormat = appConfig.getStringProperty(AppConfig.DATE_TIME_FORMAT, null);
    configForm.setDateTimeFormat(dateTimeFormat);

    String timeFormat = appConfig.getStringProperty(AppConfig.TIME_FORMAT, null);
    configForm.setTimeFormat(timeFormat);

    ctx.forwardToInput();

  }

  public void mailSend_onClick(final FormActionContext ctx) throws Exception {

    save_onClick(ctx);

    if (!ctx.hasErrors()) {

      SimpleMailMessage mailMessage = new SimpleMailMessage();

      mailMessage.setFrom(appConfig.getStringProperty(AppConfig.MAIL_SENDER));

      mailMessage.setSubject("Test E-Mail");
      mailMessage.setText("Test E-Mail");
      mailMessage.setTo(appConfig.getStringProperty(AppConfig.MAIL_SENDER));

      mailSender.send(mailMessage);

      ctx.addGlobalMessage("userconfig.sentSuccessful");
      ctx.forwardToInput();
    }

  }

  public void save_onClick(final FormActionContext ctx) throws Exception {

    ConfigForm configForm = (ConfigForm)ctx.form();

    ctx.addErrors(configForm.validate(ctx.mapping(), ctx.request()));

    if (ctx.hasErrors()) {
      ctx.forwardToInput();
      return;
    }

    String smtpHost = configForm.getMailSmtp();
    String defaultSender = configForm.getMailSender();

    int smtpPort = Integer.parseInt(configForm.getMailSmtpPort());
    if (smtpPort == 0) {
      smtpPort = 25;
    }

    String smtpUser = configForm.getMailSmtpUser();
    String smtpPassword = configForm.getMailSmtpPassword();

    appConfig.setProperty(AppConfig.MAIL_SMTPHOST, smtpHost);
    appConfig.setProperty(AppConfig.MAIL_SMTPPORT, smtpPort);

    if (smtpUser != null) {
      appConfig.setProperty(AppConfig.MAIL_SMTPUSER, smtpUser);
    } else {
      appConfig.removeProperty(AppConfig.MAIL_SMTPUSER);
    }

    if (smtpPassword != null) {
      appConfig.setProperty(AppConfig.MAIL_SMTPPASSWORD, smtpPassword);
    } else {
      appConfig.removeProperty(AppConfig.MAIL_SMTPPASSWORD);
    }

    appConfig.setProperty(AppConfig.MAIL_SENDER, defaultSender);

    int len = Integer.parseInt(configForm.getPasswordLen());
    appConfig.setProperty(AppConfig.PASSWORD_MINLENGTH, len);

    int sessionTimeout = Integer.parseInt(configForm.getSessionTimeout());
    appConfig.setProperty(AppConfig.SESSION_TIMEOUT, sessionTimeout);

    String dateFormat = configForm.getDateFormat();
    appConfig.setProperty(AppConfig.DATE_FORMAT, dateFormat);

    String parseDateFormat = configForm.getParseDateFormat();
    appConfig.setProperty(AppConfig.PARSE_DATE_FORMAT, parseDateFormat);

    String dateTimeFormat = configForm.getDateTimeFormat();
    appConfig.setProperty(AppConfig.DATE_TIME_FORMAT, dateTimeFormat);

    String timeFormat = configForm.getTimeFormat();
    appConfig.setProperty(AppConfig.TIME_FORMAT, timeFormat);

    Constants.setDefaultDateFormat(dateFormat, parseDateFormat, dateTimeFormat, timeFormat);

    appConfig.saveAll();

    if (configForm.isDirty()) {
      mailSender.setHost(smtpHost);
      mailSender.setPassword(smtpPassword);
      mailSender.setPort(smtpPort);
      mailSender.setUsername(smtpUser);
    }

    ctx.addGlobalMessage("common.updateOK");

    ctx.forwardToInput();

  }

}
