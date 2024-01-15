package ch.ess.base.web.config;

import java.net.InetAddress;
import java.util.Date;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import ch.ess.base.Constants;
import ch.ess.base.Util;
import ch.ess.base.annotation.spring.SpringAction;
import ch.ess.base.annotation.struts.ActionScope;
import ch.ess.base.annotation.struts.StrutsAction;
import ch.ess.base.service.AppConfig;
import ch.ess.base.service.Config;
import ch.ess.base.web.CrumbsUtil;
import ch.ess.cal.WebCalMessageResources;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FWAction;
import com.cc.framework.adapter.struts.FormActionContext;

@SpringAction
@StrutsAction(path = "/config", 
    form = ConfigForm.class, 
    input = "/configedit.jsp", 
    scope = ActionScope.REQUEST, 
    roles = "$admin")
public class ConfigAction extends FWAction {

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

    String dateTimeFormat = appConfig.getStringProperty(AppConfig.DATE_TIME_FORMAT, null);
    configForm.setDateTimeFormat(dateTimeFormat);

    String timeFormat = appConfig.getStringProperty(AppConfig.TIME_FORMAT, null);
    configForm.setTimeFormat(timeFormat);

    String googleApiKey = appConfig.getStringProperty(AppConfig.GOOGLE_API_KEY, null);
    configForm.setGoogleApiKey(googleApiKey);

    // Attributes
    String defaultApp = appConfig.getStringProperty(AppConfig.DEFAULT_APPLICATION, null);
    configForm.setDefaultApp(defaultApp);
    String timeSetting = appConfig.getStringProperty(AppConfig.TIME_SETTING, null);
    configForm.setTimeSetting(timeSetting);
    Boolean newCustomer = Boolean.parseBoolean(appConfig.getStringProperty(AppConfig.NEW_CUSTOMER, "false"));
    configForm.setNewCustomer(String.valueOf(newCustomer));
    
    String defaultHourRate = appConfig.getStringProperty(AppConfig.DEFAULT_HOUR_RATE, "150");
    configForm.setDefaultHourRate(String.valueOf(defaultHourRate));
    
    
    configForm.setLabelCustomerDe(appConfig.getStringProperty(AppConfig.CUSTOMER_DE, "Kunde"));
    configForm.setLabelCustomerEn(appConfig.getStringProperty(AppConfig.CUSTOMER_EN, "Customer"));
    configForm.setLabelProjectDe(appConfig.getStringProperty(AppConfig.PROJECT_DE, "Projekt"));
    configForm.setLabelProjectEn(appConfig.getStringProperty(AppConfig.PROJECT_EN, "Project"));
    configForm.setLabelTaskDe(appConfig.getStringProperty(AppConfig.TASK_DE, "Tätigkeit"));
    configForm.setLabelTaskEn(appConfig.getStringProperty(AppConfig.TASK_EN, "Task"));
    
    Boolean newOrder = Boolean.parseBoolean(appConfig.getStringProperty(AppConfig.NEW_ORDER, "false"));
    configForm.setNewOrder(String.valueOf(newOrder));
    Boolean allowActivity = Boolean.parseBoolean(appConfig.getStringProperty(AppConfig.ALLOW_ACTIVITY, "false"));
    configForm.setAllowActivity(String.valueOf(allowActivity));
    String openCustomerTag = appConfig.getStringProperty(AppConfig.OPEN_CUSTOMER_TAG, "<");
    configForm.setOpenCustomerTag(openCustomerTag);
    String closeCustomerTag = appConfig.getStringProperty(AppConfig.CLOSE_CUSTOMER_TAG, "> ");
    configForm.setCloseCustomerTag(closeCustomerTag);
    
    CrumbsUtil.updateCallStack(this, ctx);
    CrumbsUtil.updateCrumbs(ctx);
    
    ctx.forwardToInput();

  }

  private String getAppName(final FormActionContext ctx) {
	return Util.translate(ctx.request(), "application.title");
  }

  public void mailSend_onClick(final FormActionContext ctx) throws Exception {

    save_onClick(ctx);

    if (!ctx.hasErrors()) {

      SimpleMailMessage mailMessage = new SimpleMailMessage();

      mailMessage.setFrom(appConfig.getStringProperty(AppConfig.MAIL_SENDER));

	  mailMessage.setSubject("Test email from " + getAppName(ctx) + " ["
			+ InetAddress.getLocalHost().getHostName() + " - "
			+ InetAddress.getLocalHost().getHostAddress() + "]");
	  mailMessage.setText("This email has been generated by "
			+ getAppName(ctx) + " for testing purposes.\n\n"
			+ " IP: " + InetAddress.getLocalHost().getHostAddress() + "\n"
			+ " Hostname: " + InetAddress.getLocalHost().getHostName() + "\n"
			+ " Timestamp: " + (new Date()));
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

    String googleApiKey = configForm.getGoogleApiKey();
    appConfig.setProperty(AppConfig.GOOGLE_API_KEY, googleApiKey);
    
    // Attributes
    String defaultApp = configForm.getDefaultApp();
    appConfig.setProperty(AppConfig.DEFAULT_APPLICATION, defaultApp);
    String timeSetting = configForm.getTimeSetting();
    appConfig.setProperty(AppConfig.TIME_SETTING, timeSetting);
    Boolean newCustomer = Boolean.parseBoolean(configForm.getNewCustomer());
    appConfig.setProperty(AppConfig.NEW_CUSTOMER, newCustomer);
    Boolean newOrder = Boolean.parseBoolean(configForm.getNewOrder());
    appConfig.setProperty(AppConfig.NEW_ORDER, newOrder);
    Boolean allowActivity = Boolean.parseBoolean(configForm.getAllowActivity());
    appConfig.setProperty(AppConfig.ALLOW_ACTIVITY, allowActivity);
    String openCustomerTag = configForm.getOpenCustomerTag();
    appConfig.setProperty(AppConfig.OPEN_CUSTOMER_TAG, openCustomerTag);
    String closeCustomerTag = configForm.getCloseCustomerTag();
    appConfig.setProperty(AppConfig.CLOSE_CUSTOMER_TAG, closeCustomerTag);
    String defaultHourRate = configForm.getDefaultHourRate();
    
    
    
    appConfig.setProperty(AppConfig.CUSTOMER_DE, configForm.getLabelCustomerDe());
    appConfig.setProperty(AppConfig.CUSTOMER_EN, configForm.getLabelCustomerEn());
    appConfig.setProperty(AppConfig.PROJECT_DE, configForm.getLabelProjectDe());
    appConfig.setProperty(AppConfig.PROJECT_EN, configForm.getLabelProjectEn());
    appConfig.setProperty(AppConfig.TASK_DE, configForm.getLabelTaskDe());
    appConfig.setProperty(AppConfig.TASK_EN, configForm.getLabelTaskEn());
    
    if(defaultHourRate != null && !defaultHourRate.isEmpty()){
    	appConfig.setProperty(AppConfig.DEFAULT_HOUR_RATE, defaultHourRate);
    }else{
    	appConfig.setProperty(AppConfig.DEFAULT_HOUR_RATE, appConfig.getStringProperty(AppConfig.DEFAULT_HOUR_RATE));
    }
    
    Constants.setDefaultDateFormat(dateFormat, parseDateFormat, dateTimeFormat, timeFormat);

    appConfig.saveAll();
    ((WebCalMessageResources)Util.getMessages(ctx.request())).reload();

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
