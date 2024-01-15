
package lottowin.resource;

import lottowin.db.*;
import com.objectmatter.bsf.*;
import java.util.*;
import org.log4j.*;

public abstract class DBResourceBundle extends ResourceBundle {

  private Hashtable lookup;

  public DBResourceBundle(String locale) {
    lookup = new Hashtable();

    Database db = AppConfig.getDatabase();

    OQuery query = new OQuery(Message.class);
    query.add(locale, "language");
    Message[] sr = (Message[]) query.execute(db);
    if (sr != null) {
      for (int i = 0; i < sr.length; i++) {
        lookup.put(sr[i].getLkey(), sr[i].getMsg());
      }
    }
    AppConfig.closeDatabase();
  }

  public Object handleGetObject(String key) {
    Object obj = lookup.get(key);
    return obj;
  }

  /**
    * Implementation of ResourceBundle.getKeys.
    */
  public Enumeration getKeys() {
    Enumeration result = null;
    if (parent != null) {
      final Enumeration myKeys = lookup.keys();
      final Enumeration parentKeys = parent.getKeys();

      result = new Enumeration() {
                 public boolean hasMoreElements() {
                 if (temp == null)
                     nextElement();
                   return temp != null;
                 }

                 public Object nextElement() {
                   Object returnVal = temp;
                 if (myKeys.hasMoreElements())
                     temp = myKeys.nextElement();
                 else {
                     temp = null;
                   while (temp == null && parentKeys.hasMoreElements()) {
                       temp = parentKeys.nextElement();
                     if (lookup.containsKey(temp))
                         temp = null;
                     }
                   }
                   return returnVal;
                 }

                 Object temp = null;
               };
    } else {
      result = lookup.keys();
    }
    return result;
  }
}
