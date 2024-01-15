package ch.ess.struts;

import java.util.*;

import javax.servlet.http.*;

import org.apache.struts.*;

public class StrutsUtil {

  public static Locale retrieveUserLocale(HttpServletRequest request, String locale) {
    Locale userLocale = null;
    HttpSession session = request.getSession();

    if (locale == null) {
      locale = Globals.LOCALE_KEY;
    }

    // Only check session if sessions are enabled
    if (session != null) {
      userLocale = (Locale) session.getAttribute(locale);
    }

    if (userLocale == null) {
      userLocale = request.getLocale();
    }

    return userLocale;
  }

}
