package ch.ess.addressbook.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class RememberMeUtil {

  public static Cookie lookForLoginCookie(HttpServletRequest request) {
    Cookie[] cookies = request.getCookies();
    Cookie loginCookie = null;
    for (int i = 0; i < cookies.length; i++) {
      if (ch.ess.addressbook.Constants.COOKIE_REMEMBER.equals(cookies[i].getName())) {
        loginCookie = cookies[i];
        break;
      }
    }  
    
    return loginCookie;
  }
  
}


