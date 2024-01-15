package ch.ess.blank.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import net.sf.hibernate.HibernateException;
import ch.ess.blank.db.User;

/**
 * @author Ralph Schaer
 * @version $Revision: 1.4 $ $Date: 2004/05/22 15:38:03 $
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
      if (ch.ess.blank.Constants.COOKIE_REMEMBER.equals(cookies[i].getName())) {
        loginCookie = cookies[i];
        break;
      }
    }

    return loginCookie;
  }

}