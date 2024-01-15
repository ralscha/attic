package ch.ess.cal.admin.web.system;

import java.util.*;

import javax.servlet.http.*;

import net.sf.hibernate.*;

import org.apache.struts.action.*;

import ch.ess.cal.*;
import ch.ess.cal.common.*;
import ch.ess.cal.mail.*;
import ch.ess.cal.resource.*;
import ch.ess.mail.*;


public class SystemEditAction extends DispatchAction {

  protected ActionForward store(ActionMapping mapping, ActionForm form, 
                                         HttpServletRequest request, HttpSession session,
                                         ActionMessages messages,
                                         Session sess, Transaction tx)
                                  throws Exception {

    SystemForm systemForm = (SystemForm)form;    

    
    String lenStr = systemForm.getPasswordLen();        
    String smtpHost = systemForm.getMailSmtp();  
    String mailSender = systemForm.getMailSender();          
    String errorReceiver = systemForm.getErrorMailReceiver();
    
    
    AppConfig.storeProperty(AppConfig.MAIL_SMTPHOST, smtpHost);
    
    AppConfig.storeProperty(AppConfig.MAIL_SENDER, mailSender);
    AppConfig.storeProperty(AppConfig.ERROR_MAIL_RECEIVER, errorReceiver);    
    
    
    int len = Integer.parseInt(lenStr);    
    
    AppConfig.storeProperty(AppConfig.PASSWORD_MINLENGTH, len);
    
        
    //Re Init
    InitLog.init(getServlet().getServletContext());    
    InitMail.init(true);
    
    
    if (request.getParameter("test") != null) {
      Mail.send(AppMailType.TEST, new HashMap());
      messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("testmail.ok"));
    } else {    
      messages.add(ActionMessages.GLOBAL_MESSAGE, Constants.ACTION_MESSAGE_UPDATE_OK);
    }
        
    return mapping.findForward(Constants.FORWARD_SUCCESS); 

  }


  protected ActionForward edit(ActionMapping mapping, ActionForm form, 
                                        HttpServletRequest request, HttpSession session,
                                        ActionMessages messages,
                                        Session sess)
                                 throws Exception {

    
    SystemForm systemForm = (SystemForm)form;
       
    int len = AppConfig.getIntegerProperty(AppConfig.PASSWORD_MINLENGTH, -1);    
    systemForm.setPasswordLen(String.valueOf(len));
           
    String smtpHost = AppConfig.getStringProperty(AppConfig.MAIL_SMTPHOST, null);    
    systemForm.setMailSmtp(smtpHost);
    
    String mailSender = AppConfig.getStringProperty(AppConfig.MAIL_SENDER, null);    
    systemForm.setMailSender(mailSender);
    
    String errorReceiver = AppConfig.getStringProperty(AppConfig.ERROR_MAIL_RECEIVER, null);
    systemForm.setErrorMailReceiver(errorReceiver);


    return mapping.findForward(Constants.FORWARD_SUCCESS);   
    
  }

 



  protected ActionForward delete(ActionMapping mapping, ActionForm form, 
                                          HttpServletRequest request, HttpSession session,
                                          ActionMessages messages,  
                                          Session sess)
                                   throws Exception {
    return null;
  }




  protected ActionForward add(ActionMapping mapping, ActionForm form, 
                                       HttpServletRequest request, HttpSession session,
                                       ActionMessages messages)  
                                throws Exception {
    return null;                                  
  }

}
