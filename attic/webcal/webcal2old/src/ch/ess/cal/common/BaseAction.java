package ch.ess.cal.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import org.apache.struts.action.Action;

import ch.ess.cal.Constants;
import ch.ess.cal.admin.web.AdminUser;
import ch.ess.cal.web.CalUser;


public class BaseAction extends Action {

  protected CalUser getUser(HttpServletRequest request) {
    return getUser(request.getSession());
  }

  protected CalUser getUser(HttpSession session) {
    return (CalUser)session.getAttribute(Constants.USER_KEY);
  }
 
 
  protected AdminUser getAdminUser(HttpServletRequest request) {
    return getAdminUser(request.getSession());
  }

  protected AdminUser getAdminUser(HttpSession session) {
    return (AdminUser)session.getAttribute(Constants.ADMIN_USER_KEY);
  }  
    
}