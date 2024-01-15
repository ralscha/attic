package ch.ess.base.web.login;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import ch.ess.base.model.User;

public class LoginCookieUtil {

  private String cookieName;

  public void setCookieName(final String cookieName) {
    this.cookieName = cookieName;
  }

  public Cookie lookForLoginCookie(final HttpServletRequest request) {
    Cookie[] cookies = request.getCookies();

    if (cookies != null) {
      for (Cookie element : cookies) {
        if (cookieName.equals(element.getName())) {
          return element;
        }
      }
    }

    return null;
  }

  public Cookie createNewCookie(final User user) {
    return new Cookie(cookieName, user.getLoginToken());
  }

}