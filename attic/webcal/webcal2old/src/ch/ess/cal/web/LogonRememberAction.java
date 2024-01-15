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

public final class LogonRememberAction extends HibernateAction {

  public ActionForward execute(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response,
    ActionMessages messages,
    Session sess)
    throws Exception {

    HttpSession session = request.getSession();

    Cookie[] cookies = request.getCookies();
    Cookie logonCookie = null;
    for (int i = 0; i < cookies.length; i++) {
      if (Constants.COOKIE_REMEMBER.equals(cookies[i].getName())) {
        logonCookie = cookies[i];
        break;
      }
    }
    


    if (logonCookie != null) {
      List userList = sess.find("from User as u where u.logonToken = ?", logonCookie.getValue(), Hibernate.STRING);
      if (userList.size() > 0) {
        
        User user = (User)userList.get(0);
        
        CalUser calUser = CalUser.createUser(user);

        Calendar today = Calendar.getInstance();

        Calendar cal = Calendar.getInstance();
        cal.setTime(user.getLogonTokenTime());
        cal.add(Calendar.SECOND, calUser.getConfig().getIntegerProperty(UserConfig.LOGON_REMEMBER_SECONDS).intValue());

        if (cal.after(today)) {
          
          session.setAttribute(Globals.LOCALE_KEY, calUser.getLocale());

          session.setAttribute(Constants.USER_KEY, calUser);
          session.setAttribute(Constants.USER_SESSION, new CalUserSessionBinding(calUser));

          return mapping.findForward(Constants.FORWARD_SUCCESS);
        } else {
          user.setLogonToken(null);
          user.setLogonTokenTime(null);
          logonCookie.setMaxAge(0);
          response.addCookie(logonCookie);
        }
      }

    }

    return mapping.findForward(Constants.FORWARD_LOGON);

  }

}
