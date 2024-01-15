package ch.ess.cal.web;

import java.util.*;

import javax.servlet.http.*;

import net.sf.hibernate.*;

import org.apache.struts.*;
import org.apache.struts.action.*;

import ch.ess.cal.*;
import ch.ess.cal.common.*;
import ch.ess.cal.db.*;
import ch.ess.cal.resource.*;
import ch.ess.cal.util.*;

public final class LogonAction extends HibernateAction {

  public ActionForward execute(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response,
    ActionMessages messages,
    Session sess)
    throws Exception {

    HttpSession session = request.getSession();

    LogonForm lform = (LogonForm)form;
    
    List userList = sess.find("from User as u where u.userName = ?", lform.getUserName(), Hibernate.STRING);
   
    if (userList.size() > 0) {
      User user = (User)userList.get(0);  
                          
      if (PasswordDigest.validatePassword(user.getPasswordHash(), lform.getPassword())) {
        
        CalUser calUser = CalUser.createUser(user);

        session.setAttribute(Globals.LOCALE_KEY, calUser.getLocale());
    
        session.setAttribute(Constants.USER_KEY, calUser);
        session.setAttribute(Constants.USER_SESSION, new CalUserSessionBinding(calUser));

        
        if (lform.isRememberLogon()) {
          user.setLogonToken(PasswordDigest.getLogonToken(user));
          user.setLogonTokenTime(new Date());
                    
          Cookie newCookie = new Cookie(Constants.COOKIE_REMEMBER, user.getLogonToken());
          newCookie.setMaxAge(calUser.getConfig().getIntegerProperty(UserConfig.LOGON_REMEMBER_SECONDS).intValue());
          response.addCookie(newCookie);
          
        } else {
          user.setLogonToken(null);
          user.setLogonTokenTime(null);
        }
        return mapping.findForward(Constants.FORWARD_SUCCESS);
      }
    } 
    

    messages.add(ActionErrors.GLOBAL_ERROR, new ActionError("LoginFailed"));
    lform.setPassword(null);
    return (new ActionForward(mapping.getInput()));
  }

}
