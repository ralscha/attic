package ch.ess.cal.web.preferences;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import ch.ess.cal.Constants;
import ch.ess.cal.common.DispatchAction;
import ch.ess.cal.db.User;
import ch.ess.cal.util.PasswordDigest;
import ch.ess.cal.web.CalUser;


public class PreferencesEditAction extends DispatchAction {

  protected ActionForward store(ActionMapping mapping, ActionForm form, 
                                         HttpServletRequest request, HttpSession session,
                                         ActionMessages messages,
                                         Session sess, Transaction tx)
                                  throws Exception {

    PreferencesForm prefForm = (PreferencesForm)form;    

    CalUser calUser = getUser(request);
    prefForm.getFromForm(calUser);
            
    messages.add(ActionMessages.GLOBAL_MESSAGE, Constants.ACTION_MESSAGE_UPDATE_OK);
    
    if (!GenericValidator.isBlankOrNull(prefForm.getNewPassword())) {
      User user = (User)sess.load(User.class, calUser.getUserId());
      System.out.println(prefForm.getNewPassword());
      user.setPasswordHash(PasswordDigest.getDigestString(prefForm.getNewPassword()));      
    }
    
    tx.commit();
    return mapping.findForward(Constants.FORWARD_SUCCESS); 

  }


  protected ActionForward edit(ActionMapping mapping, ActionForm form, 
                                        HttpServletRequest request, HttpSession session,
                                        ActionMessages messages,
                                        Session sess)
                                 throws Exception {

    
    PreferencesForm systemForm = (PreferencesForm)form;
       
    systemForm.setToForm(getResources(request), getUser(request));

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
