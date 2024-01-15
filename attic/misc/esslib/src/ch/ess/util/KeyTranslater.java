package ch.ess.util;

import ch.ess.type.*;

import java.util.*;

import org.apache.struts.util.*;


/**
 * DOCUMENT ME!
 * 
 * @version $Revision: 1.6 $ $Date: 2002/08/14 13:26:53 $
 * @author $Author: sr $
 */
public class KeyTranslater {

  /**
   * DOCUMENT ME!
   * 
   * @param messages DOCUMENT ME!
   * @param locale DOCUMENT ME!
   * @param array DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public static String[] translateKeys(MessageResources messages, Locale locale, String[] array) {

    if (array == null) {

      return null;
    }

    String[] newArray = new String[array.length];

    for (int i = 0; i < array.length; i++) {
      newArray[i] = messages.getMessage(locale, array[i]);
    }

    return newArray;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param messages DOCUMENT ME!
   * @param locale DOCUMENT ME!
   * @param map DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public static Map translateFilterMaps(MessageResources messages, Locale locale, Map map) {

    Map newMap = new HashMap();
    Iterator it = map.keySet().iterator();

    while (it.hasNext()) {

      String key = (String)it.next();
      Object value = map.get(key);
      key = messages.getMessage(locale, key);

      if (value instanceof Type) {

        Type type = (Type)value;
        value = messages.getMessage(locale, type.getKey());
      }
      newMap.put(key, value);
    }

    return newMap;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param rb DOCUMENT ME!
   * @param locale DOCUMENT ME!
   * @param array DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public static String[] translateKeys(ResourceBundle rb, Locale locale, String[] array) {

    if (array == null) {

      return null;
    }

    String[] newArray = new String[array.length];

    for (int i = 0; i < array.length; i++) {
      newArray[i] = rb.getString(array[i]);
    }

    return newArray;
  }
}