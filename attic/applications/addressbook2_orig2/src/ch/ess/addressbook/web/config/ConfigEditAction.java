package ch.ess.addressbook.web.config;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMessage;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import ch.ess.addressbook.resource.AppConfig;
import ch.ess.addressbook.resource.InitLog;
import ch.ess.common.Constants;
import ch.ess.common.service.mail.MailMessage;
import ch.ess.common.service.mail.MailSender;
import ch.ess.common.web.DispatchAction;
import ch.ess.common.web.WebContext;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.13 $ $Date: 2003/11/12 03:42:19 $ 
  * 
  * @struts.action path="/storeConfig" name="configForm" input=".config.edit" scope="session" validate="true" parameter="store" roles="admin"
  * @struts.action path="/editConfig" name="configForm" input=".config.edit" scope="session" validate="false" parameter="edit" roles="admin"
  * @struts.action-forward name="success" path=".config.edit"
  */
public class ConfigEditAction extends DispatchAction {

  private static final Log LOG = LogFactory.getLog(ConfigEditAction.class);

  protected ActionForward store() throws Exception {

    WebContext ctx = WebContext.currentContext();
    ConfigForm configForm = (ConfigForm)ctx.getForm();
        
    String smtpHost = configForm.getMailSmtp();
    String mailSender = configForm.getMailSender();
    String errorReceiver = configForm.getErrorMailReceiver();

    int smtpPort = configForm.getMailSmtpPort();
    String smtpUser = configForm.getMailSmtpUser();
    String smtpPassword = configForm.getMailSmtpPassword();
    String uploadPath = configForm.getUploadPath();

    AppConfig.storeProperty(AppConfig.MAIL_SMTPHOST, smtpHost);
    AppConfig.storeProperty(AppConfig.MAIL_SMTPPORT, smtpPort);

    if (smtpUser != null) {
      AppConfig.storeProperty(AppConfig.MAIL_SMTPUSER, smtpUser);
    } else {
      AppConfig.removeProperty(AppConfig.MAIL_SMTPUSER);
    }

    if (smtpPassword != null) {
      AppConfig.storeProperty(AppConfig.MAIL_SMTPPASSWORD, smtpPassword);
    } else {
      AppConfig.removeProperty(AppConfig.MAIL_SMTPPASSWORD);
    }

    AppConfig.storeProperty(AppConfig.MAIL_SENDER, mailSender);
    AppConfig.storeProperty(AppConfig.ERROR_MAIL_RECEIVER, errorReceiver);

    int len = Integer.parseInt(configForm.getPasswordLen());
    AppConfig.storeProperty(AppConfig.PASSWORD_MINLENGTH, len);
    
    AppConfig.storeProperty(AppConfig.UPLOAD_PATH, uploadPath);

    int sessionTimeout = Integer.parseInt(configForm.getSessionTimeout());
    AppConfig.storeProperty(AppConfig.SESSION_TIMEOUT, sessionTimeout);

    if (configForm.isDirty()) {
      
      ctx.getTransaction().commit();

      LOG.info("reinit mail and log");
      
      WebApplicationContext wactx = WebApplicationContextUtils.getWebApplicationContext(ctx.getServletContext());
      MailSender sender = (MailSender)wactx.getBean("MailSender");

      sender.reinit(mailSender, smtpHost, smtpPort, smtpUser, smtpPassword);
  
      //ReInit Log
      Map substMap = InitLog.getSubstMap();
      substMap.put("${SMTP}", smtpHost);
      substMap.put("${MAILFROM}", mailSender);
      substMap.put("${MAILTO}", errorReceiver);
      InitLog.configure();
      
    }    

    if (ctx.getRequest().getParameter("test") != null) {

      WebApplicationContext wactx = WebApplicationContextUtils.getWebApplicationContext(ctx.getServletContext());
      MailSender sender = (MailSender)wactx.getBean("MailSender");
    
      MailMessage mm = new MailMessage();
      mm.setSubject("Test E-Mail");
      mm.setText("Test E-Mail");
      mm.addTo(AppConfig.getStringProperty(AppConfig.ERROR_MAIL_RECEIVER));
   
      sender.send(mm);

      addOneMessageRequest(new ActionMessage("userconfig.sentSuccessful"));
    } else {
      addOneMessageRequest(Constants.ACTION_MESSAGE_UPDATE_OK);
    }

    return findForward(Constants.FORWARD_SUCCESS);

  }

  protected ActionForward edit() throws Exception {

    ConfigForm configForm = (ConfigForm)WebContext.currentContext().getForm();

    int len = AppConfig.getIntegerProperty(AppConfig.PASSWORD_MINLENGTH, -1);
    configForm.setPasswordLen(String.valueOf(len));
    
    int sessionTimeout = AppConfig.getIntegerProperty(AppConfig.SESSION_TIMEOUT, 30);
    configForm.setSessionTimeout(String.valueOf(sessionTimeout));

    String smtpHost = AppConfig.getStringProperty(AppConfig.MAIL_SMTPHOST, null);
    configForm.setMailSmtp(smtpHost);

    int smtpHostPort = AppConfig.getIntegerProperty(AppConfig.MAIL_SMTPPORT, 25);
    configForm.setMailSmtpPort(smtpHostPort);

    String smtpUser = AppConfig.getStringProperty(AppConfig.MAIL_SMTPUSER, null);
    configForm.setMailSmtpUser(smtpUser);

    String smtpPassword = AppConfig.getStringProperty(AppConfig.MAIL_SMTPPASSWORD, null);
    configForm.setMailSmtpPassword(smtpPassword);

    String mailSender = AppConfig.getStringProperty(AppConfig.MAIL_SENDER, null);
    configForm.setMailSender(mailSender);

    String errorReceiver = AppConfig.getStringProperty(AppConfig.ERROR_MAIL_RECEIVER, null);
    configForm.setErrorMailReceiver(errorReceiver);

    String uploadPath =  AppConfig.getStringProperty(AppConfig.UPLOAD_PATH, null);
    configForm.setUploadPath(uploadPath);

    return findForward(Constants.FORWARD_SUCCESS);

  }

  protected ActionForward delete() throws Exception {
    return null;
  }

  protected ActionForward add() throws Exception {
    return null;
  }

}
