package ch.ess.blank.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import net.sf.hibernate.HibernateException;

import ch.ess.blank.db.User;
import ch.ess.common.Constants;
import ch.ess.common.web.UserSession;

/**
 * @author Ralph Schaer
 * @version $Revision: 1.6 $ $Date: 2004/05/22 16:51:13 $
 */

public class WebUtils {
  public static User getUser(HttpServletRequest request) throws HibernateException {
    UserSession userSession = (UserSession) request.getSession().getAttribute(Constants.USER_SESSION);
    if (userSession != null) {
      return User.load(userSession.getUserId());
    }
    return null;

  }

  public static Cookie lookForLoginCookie(HttpServletRequest request) {
    Cookie[] cookies = request.getCookies();

    if (cookies != null) {
      for (int i = 0; i < cookies.length; i++) {
        if (ch.ess.blank.Constants.COOKIE_REMEMBER.equals(cookies[i].getName())) {
          return cookies[i];
        }
      }
    }

    return null;
  }

}