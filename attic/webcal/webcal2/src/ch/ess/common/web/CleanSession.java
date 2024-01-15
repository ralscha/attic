package ch.ess.common.web;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.1 $ $Date: 2004/02/14 16:32:50 $  
  */
public class CleanSession {

  private static final Log LOG = LogFactory.getLog(CleanSession.class);

  public static void cleanSession(HttpSession session, Set excludeSet) {

    try {
      Enumeration e = session.getAttributeNames();
      List attrList = new ArrayList();

      while (e.hasMoreElements()) {

        String key = (String)e.nextElement();

        if (!excludeSet.contains(key)
          && !key.startsWith("org.")
          && !key.startsWith("javax.")
          && !key.startsWith("static_")) {
          attrList.add(key);
        }
      }

      for (int i = 0; i < attrList.size(); i++) {

        String key = (String)attrList.get(i);
        session.removeAttribute(key);
      }

    } catch (IllegalStateException ise) {
      LOG.error("cleanSession", ise);
    }
  }
}