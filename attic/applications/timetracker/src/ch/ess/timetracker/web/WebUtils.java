package ch.ess.timetracker.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import net.sf.hibernate.HibernateException;
import ch.ess.timetracker.db.User;


/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.1 $ $Date: 2004/01/05 07:58:20 $ 
  */

public class WebUtils {
  public static User getUser(HttpServletRequest request) throws HibernateException {
    String userName = request.getUserPrincipal().getName();
    return User.find(userName);
  }
  
  public static Cookie lookForLoginCookie(HttpServletRequest request) {
    Cookie[] cookies = request.getCookies();
    Cookie loginCookie = null;
    for (int i = 0; i < cookies.length; i++) {
      if (ch.ess.timetracker.Constants.COOKIE_REMEMBER.equals(cookies[i].getName())) {
        loginCookie = cookies[i];
        break;
      }
    }  
    
    return loginCookie;
  }
    
}
