package ch.ess.base.web.config;

import org.apache.struts.action.Action;
import org.apache.struts.annotation.ActionScope;
import org.apache.struts.annotation.StrutsAction;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import ch.ess.base.Constants;
import ch.ess.base.service.AppConfig;
import ch.ess.base.service.Config;

/** 
 * @author sr
 * @version $Revision: 1.4 $ $Date: 2005/07/03 11:31:19 $ 
 * 
 * @spring.bean name="/config" lazy-init="true" autowire="byType"
 */
@StrutsAction(path = "/config", 
    form = ConfigForm.class, 
    input = "/configedit.jsp", 
    scope = ActionScope.REQUEST, 
    roles = "$systemconfig")
public class ConfigAction extends Action {

  private Config appConfig;
  private JavaMailSenderImpl mailSender;

  public void setMailSender(final JavaMailSenderImpl mailSender) {
    this.mailSender = mailSender;
  }

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


    Constants.setDefaultDateFormat(dateFormat, parseDateFormat);

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