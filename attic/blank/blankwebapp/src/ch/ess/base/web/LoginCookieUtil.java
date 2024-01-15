package ch.ess.base.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import ch.ess.base.model.User;

/**
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2005/07/03 04:48:20 $
 */

public class LoginCookieUtil {

  private String cookieName;

  public void setCookieName(final String cookieName) {
    this.cookieName = cookieName;
  }

  public Cookie lookForLoginCookie(final HttpServletRequest request) {
    Cookie[] cookies = request.getCookies();

    if (cookies != null) {
      for (int i = 0; i < cookies.length; i++) {
        if (cookieName.equals(cookies[i].getName())) {
          return cookies[i];
        }
      }
    }

    return null;
  }

  public Cookie createNewCookie(final User user) {
    Cookie newCookie = new Cookie(cookieName, user.getLoginToken());
    return newCookie;
  }

}