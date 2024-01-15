/*
 * $Header: c:/cvs/pbroker/src/ch/ess/pbroker/util/CleanSession.java,v 1.2 2002/04/02 18:27:21 sr Exp $
 * $Revision: 1.2 $
 * $Date: 2002/04/02 18:27:21 $
 */

package ch.sr.lottowin.filter;

import java.util.*;

import javax.servlet.http.*;

import org.apache.log4j.*;

import ch.sr.lottowin.*;


/**
 * Class CleanSession
 *
 * @version $Revision: 1.2 $ $Date: 2002/04/02 18:27:21 $
 */
public class CleanSession {

  private static final Logger LOG = Logger.getLogger(CleanSession.class.getName());
  private static Set objectSet = new HashSet();

  static {
    objectSet.add(Constants.USER_KEY);
  }

  //Utility method
  //removes objects in session except app objects
  public static void cleanSession(HttpSession session) {

    try {
      Enumeration e = session.getAttributeNames();
      List attrList = new ArrayList();

      while (e.hasMoreElements()) {
        String key = (String)e.nextElement();

        if (!objectSet.contains(key) &&!key.startsWith("org.")) {
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
