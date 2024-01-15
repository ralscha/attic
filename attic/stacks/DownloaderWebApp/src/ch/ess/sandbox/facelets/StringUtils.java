package ch.ess.sandbox.facelets;

// JSE imports
import java.lang.reflect.Method;

/**
 * Utility class that performs miscelaneous String functions.
 * @author Rpbert Swarr
 */
public class StringUtils {

  /**
   * Reformats the String in the form of a proper name
   * with the first letter in upper case and the
   * rest of the String in lower case. 
   * Example 7-10 in the Facelets Shortcut
   * @param aName
   * @return String
   */
  public static String formatName(String aName) {
    StringBuilder sb = new StringBuilder();
    if (aName != null) {
      sb.append(Character.toUpperCase(aName.charAt(0))).append((aName.substring(1, aName.length())).toLowerCase());
    }
    return sb.toString();
  }

  /**
   * Creates a formatted String representation of an object and the
   * values of its properties.  It uses the public get methods of an
   * object to obtain property values.
   * @param obj
   * @return String
   */
  public static String reflectiveToString(Object obj) {
    if (obj == null) {
      throw new IllegalArgumentException("Invalid argument: null");
    }

    Class theClass = obj.getClass();
    StringBuilder sb = new StringBuilder(theClass.getSimpleName() + ": [");
    Method[] methods = theClass.getDeclaredMethods();

    for (int i = 0; i < methods.length; i++) {
      String methodName = methods[i].getName();
      if (methodName.startsWith("get")) {
        String attributeName = (methodName.substring(3, 4)).toLowerCase() + methodName.substring(4);
        try {
          sb.append(attributeName + "=" + methods[i].invoke(obj) + ",");
        } catch (Exception e) {
          continue;
        }
      }
    }
    sb.setCharAt(sb.length() - 1, ']');
    return sb.toString();
  }
}
