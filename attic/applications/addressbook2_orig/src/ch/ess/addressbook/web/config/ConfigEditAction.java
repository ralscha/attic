package ch.ess.addressbook.web.config;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.hibernate.Transaction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import ch.ess.addressbook.mail.AppMailType;
import ch.ess.addressbook.mail.InitMail;
import ch.ess.addressbook.resource.AppConfig;
import ch.ess.addressbook.resource.InitLog;
import ch.ess.common.Constants;
import ch.ess.common.mail.Mail;
import ch.ess.common.web.DispatchAction;

/** 
  * @author  Ralph Schaer
  * @version 1.0, 13.07.2003 
  * 
  * @struts.action path="/storeConfig" name="configForm" input=".config.edit" scope="session" validate="true" parameter="store" roles="admin"
  * @struts.action path="/editConfig" name="configForm" input=".config.edit" scope="session" validate="false" parameter="edit" roles="admin"
  * @struts.action-forward name="success" path=".config.edit"
  */
public class ConfigEditAction extends DispatchAction {

  private static final Log LOG = LogFactory.getLog(ConfigEditAction.class);

  protected ActionForward store(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response,
    Transaction tx)
    throws Exception {

    ConfigForm configForm = (ConfigForm)form;

    String smtpHost = configForm.getMailSmtp();
    String mailSender = configForm.getMailSender();
    String errorReceiver = configForm.getErrorMailReceiver();
    String uploadPath = configForm.getUploadPath();

    AppConfig.storeProperty(AppConfig.MAIL_SMTPHOST, smtpHost);

    AppConfig.storeProperty(AppConfig.MAIL_SENDER, mailSender);
    AppConfig.storeProperty(AppConfig.ERROR_MAIL_RECEIVER, errorReceiver);

    int len = Integer.parseInt(configForm.getPasswordLen());
    AppConfig.storeProperty(AppConfig.PASSWORD_MINLENGTH, len);

    AppConfig.storeProperty(AppConfig.UPLOAD_PATH, uploadPath);

    int sessionTimeout = Integer.parseInt(configForm.getSessionTimeout());
    AppConfig.storeProperty(AppConfig.SESSION_TIMEOUT, sessionTimeout);

    if (configForm.isDirty()) {
      
      LOG.info("reinit mail and log");
      
      //ReInit Mail
      InitMail.init(true);
  
      //ReInit Log
      Map substMap = InitLog.getSubstMap();
      substMap.put("${SMTP}", smtpHost);
      substMap.put("${MAILFROM}", mailSender);
      substMap.put("${MAILTO}", errorReceiver);
      InitLog.configure();
      
    }    

    if (request.getParameter("test") != null) {
      Map context = new HashMap();
      context.put("subject", "Test E-Mail");
      context.put("text", "Test E-Mail");
      context.put("recipient", AppConfig.getStringProperty(AppConfig.ERROR_MAIL_RECEIVER));
      Mail.send(AppMailType.TEST, context);

      addOneMessage(request, new ActionMessage("SentSuccessful"));
    } else {
      addOneMessage(request, Constants.ACTION_MESSAGE_UPDATE_OK);
    }
    
   

    return mapping.findForward(Constants.FORWARD_SUCCESS);

  }

  protected ActionForward edit(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response)
    throws Exception {

    ConfigForm configForm = (ConfigForm)form;

    int len = AppConfig.getIntegerProperty(AppConfig.PASSWORD_MINLENGTH, -1);
    configForm.setPasswordLen(String.valueOf(len));

    int sessionTimeout = AppConfig.getIntegerProperty(AppConfig.SESSION_TIMEOUT, 30);
    configForm.setSessionTimeout(String.valueOf(sessionTimeout));

    String smtpHost = AppConfig.getStringProperty(AppConfig.MAIL_SMTPHOST, null);
    configForm.setMailSmtp(smtpHost);

    String mailSender = AppConfig.getStringProperty(AppConfig.MAIL_SENDER, null);
    configForm.setMailSender(mailSender);

    String errorReceiver = AppConfig.getStringProperty(AppConfig.ERROR_MAIL_RECEIVER, null);
    configForm.setErrorMailReceiver(errorReceiver);

    String uploadPath =  AppConfig.getStringProperty(AppConfig.UPLOAD_PATH, null);
    configForm.setUploadPath(uploadPath);

    return mapping.findForward(Constants.FORWARD_SUCCESS);

  }

  protected ActionForward delete(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response)
    throws Exception {
    return null;
  }

  protected ActionForward add(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response)
    throws Exception {
    return null;
  }

}
