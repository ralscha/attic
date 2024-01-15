package ch.ess.cal.web;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import net.sf.hibernate.HibernateException;
import ch.ess.cal.db.User;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.1 $ $Date: 2004/02/14 16:32:46 $ 
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
      if (ch.ess.cal.Constants.COOKIE_REMEMBER.equals(cookies[i].getName())) {
        loginCookie = cookies[i];
        break;
      }
    }  
    
    return loginCookie;
  }
    
    
  public static Map getChangeRequestParameterMap(HttpServletRequest request) {
    
    Map resultMap = new HashMap();
            
    Enumeration e = request.getParameterNames();
    while (e.hasMoreElements()) {
      String param = (String)e.nextElement();
    
      if (param.startsWith("change.")) {
        String key = param.substring(7);
        if (key.endsWith(".x") || key.endsWith(".y")) {
          key = key.substring(0, key.length()-2);
        }
        
        int pos = key.lastIndexOf(".");
        if (pos != -1) {
          resultMap.put(key.substring(0, pos), key.substring(pos+1));
        } else {
          resultMap.put(key, null);
        }
      
      }
    
    }
    
    return resultMap;
    
    
  }
    
    
}
