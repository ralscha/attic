package ch.ess.cal.admin.web;

import java.util.*;

import javax.servlet.http.*;

import net.sf.hibernate.*;

import org.apache.struts.*;
import org.apache.struts.action.*;
import org.apache.struts.validator.*;

import ch.ess.cal.*;
import ch.ess.cal.common.*;
import ch.ess.cal.db.*;
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

        
    DynaValidatorForm lform = (DynaValidatorForm)form;
        
    String userName = (String)lform.get("userName");
    String password = (String)lform.get("password");
    
    List userList = sess.find("from User as u where u.userName = ?", userName, Hibernate.STRING);
   
    if (userList.size() > 0) {
      User user = (User)userList.get(0);  
          
      if (PasswordDigest.validatePassword(user.getPasswordHash(), password)) {
        
        AdminUser adminUser = AdminUser.createUser(user);
        if (adminUser.hasPermission("admin")) {
          session.setAttribute(Globals.LOCALE_KEY, adminUser.getLocale());
  
          session.setAttribute(Constants.ADMIN_USER_KEY, adminUser);
          session.setAttribute(Constants.ADMIN_USER_SESSION, new AdminUserSessionBinding(adminUser));
          
          return mapping.findForward(Constants.FORWARD_SUCCESS);
        }
      }
    } 
    

    messages.add(ActionErrors.GLOBAL_ERROR, new ActionError("LoginFailed"));
    lform.set("password", null);
    return (new ActionForward(mapping.getInput()));
  }
}