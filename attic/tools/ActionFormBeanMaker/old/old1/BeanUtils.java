


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;


/**
 * Utility methods for populating JavaBeans properties via reflection.
 *
 * @author Craig R. McClanahan
 * @version $Revision: 1.2 $ $Date: 2000/06/14 19:28:57 $
 */

public final class BeanUtils {


    // --------------------------------------------------------- Public Classes


    /**
     * Return the input string with the first character capitalized.
     *
     * @param name The string to be modified and returned
     */
    public static String capitalize(String name) {

	if ((name == null) || (name.length() < 1))
	    return (name);
	char chars[] = name.toCharArray();
	chars[0] = Character.toUpperCase(chars[0]);
	return new String(chars);

    }


    /**
     * Convert the specified value to an object of the specified class (if
     * possible).  Otherwise, return a String representation of the value.
     *
     * @param value Value to be converted (may be null)
     * @param type Java class to be converted to (must be String or one of
     *  the primitive wrappers)
     */
    public static Object convert(String value, String type) {

	if ("java.lang.String".equals(type)) {
	    if (value == null)
	        return ((String) null);
	    else
	        return (value);
	} else if ("java.lang.Boolean".equals(type) ||
	           "boolean".equals(type)) {
	    return convertBoolean(value);
	} else if ("java.lang.Integer".equals(type) ||
		   "int".equals(type)) {
	    return convertInteger(value);
	} else if ("java.lang.Long".equals(type) ||
		   "long".equals(type)) {
	    return convertLong(value);
	} else if ("java.lang.Float".equals(type) ||
		   "float".equals(type)) {
	    return convertFloat(value);
	} else if ("java.lang.Double".equals(type) ||
		   "double".equals(type)) {
	    return convertDouble(value);
	} else {
	    if (value == null)
	        return ((String) null);
	    else
	        return (value.toString());
	}

    }

   private static Boolean convertBoolean(String value) {
      if (value == null)
	        return (new Boolean(false));
	    else if (value.equalsIgnoreCase("yes"))
	        return (new Boolean(true));
	    else if (value.equalsIgnoreCase("true"))
	        return (new Boolean(true));
	    else if (value.equalsIgnoreCase("on"))
	        return (new Boolean(true));
	    else
	        return (new Boolean(false));
   }

   private static Integer convertInteger(String value) {
    try {
		return (new Integer(value));
	    } catch (NumberFormatException e) {
		return (new Integer(0));
	    }
   }

   private static Long convertLong(String value) {
    try {
		return (new Long(value));
	    } catch (NumberFormatException e) {
		return (new Long(0));
	    }
   }

   private static Float convertFloat(String value) {
      try {
		return (new Float(value));
	    } catch (NumberFormatException e) {
		return (new Float(0.0));
	    }
   }

   private static Double convertDouble(String value) {
    try {
		return (new Double(value));
	    } catch (NumberFormatException e) {
		return (new Double(0.0));
	    }
   }

   public static Object convert(String[] values, Class type) {

    Class componentType = type.getComponentType();
    String ctName = componentType.getName();
    
	if ("java.lang.String".equals(ctName)) {
	    if (values == null)
	        return ((String[]) null);
	    else
	        return (values);
	} 
	
  int len = values.length;

  	
	if ("java.lang.Boolean".equals(ctName)) {
    Boolean[] array = new Boolean[len];
    for (int i = 0; i < len; i++) {
      array[i] = convertBoolean(values[i]);
    }
    return array;
	} else if ("boolean".equals(ctName)) {
    boolean[] array = new boolean[len];
    for (int i = 0; i < len; i++) {
      array[i] = convertBoolean(values[i]).booleanValue();
    }
    return array;
	} else if ("java.lang.Integer".equals(ctName)) {
    Integer[] array = new Integer[len];
    for (int i = 0; i < len; i++) {
      array[i] = convertInteger(values[i]);
    }
    return array;
	} else if ("int".equals(ctName)) {
    int[] array = new int[len];
    for (int i = 0; i < len; i++) {
      array[i] = convertInteger(values[i]).intValue();
    }
    return array;
	} else if ("java.lang.Long".equals(ctName)) {
    Long[] array = new Long[len];
    for (int i = 0; i < len; i++) {
      array[i] = convertLong(values[i]);
    }
    return array;
	} else if ("long".equals(ctName)) {
    long[] array = new long[len];
    for (int i = 0; i < len; i++) {
      array[i] = convertLong(values[i]).longValue();
    }
    return array;
	} else if ("java.lang.Float".equals(ctName)) {
    Float[] array = new Float[len];
    for (int i = 0; i < len; i++) {
      array[i] = convertFloat(values[i]);
    }
    return array;
	} else if ("float".equals(ctName)) {
    float[] array = new float[len];
    for (int i = 0; i < len; i++) {
      array[i] = convertFloat(values[i]).floatValue();
    }
    return array;
	} else if ("java.lang.Double".equals(ctName)) {
    Double[] array = new Double[len];
    for (int i = 0; i < len; i++) {
      array[i] = convertDouble(values[i]);
    }
    return array;
	} else if ("double".equals(ctName)) {
    double[] array = new double[len];
    for (int i = 0; i < len; i++) {
      array[i] = convertDouble(values[i]).doubleValue();
    }
    return array;
	} else {
	    if (values == null)
	        return ((String[]) null);
	    else {
          Object[] array = (Object[])java.lang.reflect.Array.newInstance(componentType, len);
          for (int i = 0; i < len; i++) {
            array[i] = values[i].toString();
          }
	        return array;
	    }
	}

    }


    /**
     * Filter the specified string for characters that are senstive to
     * HTML interpreters, returning the string with these characters replaced
     * by the corresponding character entities.
     *
     * @param value The string to be filtered and returned
     */
    public static String filter(String value) {

	if (value == null)
	    return (null);

	StringBuffer result = new StringBuffer();
	for (int i = 0; i < value.length(); i++) {
	    char ch = value.charAt(i);
	    if (ch == '<')
		result.append("&lt;");
	    else if (ch == '>')
		result.append("&gt;");
	    else if (ch == '&')
		result.append("&amp;");
	    else if (ch == '"')
		result.append("&quot;");
	    else
		result.append(ch);
	}
	return (result.toString());

    }


    /**
     * Populate the properties of the specified JavaBean from the specified
     * HTTP request, based on matching each parameter name against the
     * corresponding JavaBeans "property setter" methods in the bean's class.
     * Suitable conversion is done for argument types as described under
     * <code>convert()</code>.
     * <p>
     * <strong>IMPLEMENTATION NOTE</strong>:  If you have more than one setter
     * for the same property name (with different argument types supported by
     * this logic), the setter method that is actually called will be
     * arbitrarily determined.
     *
     * @param bean The JavaBean whose properties are to be set
     * @param request The HTTP request whose parameters are to be used
     *                to populate bean properties
     *
     * @exception ServletException if an exception is thrown while setting
     *            property values
     */
    public static void populate(Object bean,
				HttpServletRequest request)
	throws ServletException {

	populate(bean, null, null, request);

    }


    /**
     * Populate the properties of the specified JavaBean from the specified
     * HTTP request, based on matching each parameter name (plus an optional
     * prefix and/or suffix) against the corresponding JavaBeans "property
     * setter" methods in the bean's class.  Suitable conversion is done for
     * argument types as described under <code>setProperties()</code>.
     * <p>
     * <strong>IMPLEMENTATION NOTE</strong>:  If you have more than one setter
     * for the same property name (with different argument types supported by
     * this logic), the setter method that is actually called will be
     * arbitrarily determined.
     *
     * @param bean The JavaBean whose properties are to be set
     * @param prefix The prefix (if any) to be prepend to bean property
     *               names when looking for matching parameters
     * @param suffix The suffix (if any) to be appended to bean property
     *               names when looking for matching parameters
     * @param request The HTTP request whose parameters are to be used
     *                to populate bean properties
     *
     * @exception ServletException if an exception is thrown while setting
     *            property values
     */
    public static void populate(Object bean, String prefix, String suffix,
				HttpServletRequest request)
	throws ServletException {

	// Build a list of relevant request parameters from this request
	Hashtable properties = new Hashtable();
	Enumeration names = request.getParameterNames();
	while (names.hasMoreElements()) {
	    String name = (String) names.nextElement();
	    if (prefix != null) {
		if (!name.startsWith(prefix))
		    continue;
		name = name.substring(0, prefix.length());
	    }
	    if (suffix != null) {
		if (!name.endsWith(suffix))
		    continue;
		name = name.substring(0, name.length() - suffix.length());
	    }
	    properties.put(name, request.getParameterValues(name));
	}

	// Set the corresponding properties of our bean
	try {
	    populate(bean, properties);
	} catch (Exception e) {
	    throw new ServletException("BeanUtils.populate", e);
	}

    }


    /**
     * Populate the JavaBeans properties of the specified bean, based on
     * the specified name/value pairs.  This method uses Java reflection APIs
     * to identify corresponding "property setter" method names, and deals
     * with setter arguments of type <code>String</code>, <code>boolean</code>,
     * <code>int</code>, <code>long</code>, <code>float</code>, and
     * <code>double</code>.
     * <p>
     * <strong>IMPLEMENTATION NOTE</strong>:  If you have more than one setter
     * for the same property name (with different argument types supported by
     * this logic), the setter method that is actually called will be
     * arbitrarily determined.
     * <p>
     * FIXME - Deal with array and indexed setters also.
     *
     * @param bean JavaBean whose properties are being populated
     * @param properties Hashtable keyed by property name, with the
     *  corresponding (String or String[]) value(s) to be set
     *
     * @exception Exception if thrown while setting properties
     */
    public static void populate(Object bean, Hashtable properties)
	throws Exception {

	if ((bean == null) || (properties == null))
	    return;

	// Identify the methods supported by our JavaBean
	Method methods[] = null;
	methods = bean.getClass().getMethods();
	Class parameterTypes[] = null;

	// Loop through the property name/value pairs to be set
	Enumeration names = properties.keys();
	while (names.hasMoreElements()) {

	    // Identify the property name, value, and setter method name
	    String name = (String) names.nextElement();
	    
	    Object values = properties.get(name);

	    String setterName = "set" + capitalize(name);

	    // Identify a potential setter method (if there is one)
	    Method setter = null;
	    for (int i = 0; i < methods.length; i++) {
		if (!setterName.equals(methods[i].getName()))
		    continue;
		parameterTypes = methods[i].getParameterTypes();
		if (parameterTypes.length != 1)
		    continue;
		setter = methods[i];
		break;
	    }
	    if (setter == null)
		continue;	// No setter method that takes one argument
	    String parameterType = parameterTypes[0].getName();

	    // Convert the parameter value as required for this setter method
	    Object parameters[] = new Object[1];

	    if (parameterTypes[0].isArray()) {
	      if (values.getClass().isArray()) {
	        parameters[0] = convert((String[])values, parameterTypes[0]);
        } else {
	        parameters[0] = convert(new String[]{(String)values}, parameterTypes[0]);
        }
       
       } else {
        Object value;
        if (values instanceof String[])
	        value = ((String[])values)[0];
        else
	        value = values;

        if (value instanceof String)
	       parameters[0] = convert((String)value, parameterType);
       else
	       parameters[0] = convert(value.toString(), parameterType);
       }


	    // Invoke the setter method
	    setter.invoke(bean, parameters);

	}

    }


    // -------------------------------------------------------- Private Classes


}
