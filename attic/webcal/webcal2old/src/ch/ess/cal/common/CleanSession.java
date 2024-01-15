
package ch.ess.cal.common;

import java.util.*;

import javax.servlet.http.*;

import org.apache.commons.logging.*;




public class CleanSession {

  private static final Log logger = LogFactory.getLog(CleanSession.class);  


  public static void cleanSession(HttpSession session, Set excludeSet) {

    try {
      Enumeration e = session.getAttributeNames();
      List attrList = new ArrayList();

      while (e.hasMoreElements()) {

        String key = (String) e.nextElement();

        if (!excludeSet.contains(key) 
            && !key.startsWith("org.") 
            && !key.startsWith("static_")) {
          attrList.add(key);
        }
      }

      for (int i = 0; i < attrList.size(); i++) {

        String key = (String) attrList.get(i);
        System.out.println("REMOVE: " + key);
        session.removeAttribute(key);
      }

    } catch (IllegalStateException ise) {
      logger.error("cleanSession", ise);
    }
  }
}