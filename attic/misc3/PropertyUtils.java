import java.beans.BeanInfo;
import java.beans.IndexedPropertyDescriptor;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Hashtable;


/**
 * Utility methods for using Java Reflection APIs to facilitate generic
 * property getter and setter operations on Java objects.  Much of this
 * code was originally included in <code>BeanUtils</code>, but has been
 * separated because of the volume of code involved.
 * <p>
 * In general, the objects that are examined and modified using these
 * methods are expected to conform to the property getter and setter method
 * naming conventions described in the JavaBeans Specification (Version 1.0.1).
 * No data type conversions are performed, and there are no usage of any
 * <code>PropertyEditor</code> classes that have been registered, although
 * a convenient way to access the registered classes themselves is included.
 * <p>
 * For the purposes of this class, three formats for referencing a particular
 * property value of a bean are defined, with the layout of an identifying
 * String in parentheses:
 * <ul>
 * <li><strong>Simple (<code>name</code>)</strong> - The specified
 *     <code>name</code> identifies an individual property of a particular
 *     JavaBean.  The name of the actual getter or setter method to be used
 *     is determined using standard JavaBeans instrospection, so that (unless
 *     overridden by a <code>BeanInfo</code> class, a property named "xyz"
 *     will have a getter method named <code>getXyz()</code> or (for boolean
 *     properties only) <code>isXyz()</code>, and a setter method named
 *     <code>setXyz()</code>.</li>
 * <li><strong>Nested (<code>name1.name2.name3</code>)</strong> The first
 *     name element is used to select a property getter, as for simple
 *     references above.  The object returned for this property is then
 *     consulted, using the same approach, for a property getter for a
 *     property named <code>name2</code>, and so on.  The property value that
 *     is ultimately retrieved or modified is the one identified by the
 *     last name element.</li>
 * <li><strong>Indexed (<code>name[index]</code>)</strong> - The underlying
 *     property value is assumed to be an array, or this JavaBean is assumed
 *     to have indexed property getter and setter methods.  The appropriate
 *     (zero-relative) entry in the array is selected.</li>
 * <li><strong>Combined (<code>name1.name2[index].name3</strong> - Various
 *     forms combining nested and indexed references are also supported.</li>
 * </ul>
 *
 * @author Craig R. McClanahan
 * @author Ralph Schaer
 * @author Chris Audley
 * @version $Revision: 1.4 $ $Date: 2000/09/23 23:19:33 $
 */

public final class PropertyUtils {


    // ----------------------------------------------------- Manifest Constants


    /**
     * The delimiter that preceeds the zero-relative subscript for an
     * indexed reference.
     */
    public static final char INDEXED_DELIM = '[';


    /**
     * The delimiter that follows the zero-relative subscript for an
     * indexed reference.
     */
    public static final char INDEXED_DELIM2 = ']';


    /**
     * The delimiter that separates the components of a nested reference.
     */
    public static final char NESTED_DELIM = '.';


    // ------------------------------------------------------- Static Variables


    /**
     * The cache of PropertyDescriptor arrays for beans we have already
     * introspected, keyed by the fully qualified class name of this object.
     */
    private static Hashtable descriptorsCache = new Hashtable();


    // --------------------------------------------------------- Public Methods


    /**
     * Copy property values from the "origin" bean to the "destination" bean
     * for all cases where the property names are the same (even though the
     * actual getter and setter methods might have been customized via
     * <code>BeanInfo</code> classes).  No conversions are performed on the
     * actual property values -- it is assumed that the values retrieved from
     * the origin bean are assignment-compatible with the types expected by
     * the destination bean.
     *
     * @param dest Destination bean whose properties are modified
     * @param orig Origin bean whose properties are retrieved
     *
     * @exception IllegalAccessException if the caller does not have
     *  access to the property accessor method
     * @exception InvocationTargetException if the property accessor method
     *  throws an exception
     * @exception NoSuchMethodException if an accessor method for this
     *  propety cannot be found
     */
    public static void copyProperties(Object dest, Object orig)
	throws IllegalAccessException, InvocationTargetException,
	       NoSuchMethodException {

	PropertyDescriptor origDescriptors[] = getPropertyDescriptors(orig);
	for (int i = 0; i < origDescriptors.length; i++) {
	    String name = origDescriptors[i].getName();
	    if (getPropertyDescriptor(dest, name) != null) {
		Object value = getSimpleProperty(orig, name);
		setSimpleProperty(dest, name, value);
	    }
	}

    }


    /**
     * Return the value of the specified indexed property of the specified
     * bean, with no type conversions.  The zero-relative index of the
     * required value must be included (in square brackets) as a suffix to
     * the property name, or <code>IllegalArgumentException</code> will be
     * thrown.
     *
     * @param bean Bean whose property is to be extracted
     * @param name <code>propertyname[index]</code> of the property value
     *  to be extracted
     *
     * @exception IllegalAccessException if the caller does not have
     *  access to the property accessor method
     * @exception InvocationTargetException if the property accessor method
     *  throws an exception
     * @exception NoSuchMethodException if an accessor method for this
     *  propety cannot be found
     */
    public static Object getIndexedProperty(Object bean, String name)
	throws IllegalAccessException, InvocationTargetException,
	       NoSuchMethodException {

	// Identify the index of the requested individual property
        int delim = name.indexOf(INDEXED_DELIM);
        int delim2 = name.indexOf(INDEXED_DELIM2);
        if ((delim < 0) || (delim2 <= delim))
	    throw new IllegalArgumentException("Invalid indexed property '" +
					       name + "'");
	int index = -1;
	try {
	    String subscript = name.substring(delim + 1, delim2);
	    index = Integer.parseInt(subscript);
	} catch (NumberFormatException e) {
	    throw new IllegalArgumentException("Invalid indexed property '" +
					       name + "'");
	}
	name = name.substring(0, delim);

	// Request the specified indexed property value
	return (getIndexedProperty(bean, name, index));

    }


    /**
     * Return the value of the specified indexed property of the specified
     * bean, with no type conversions.
     *
     * @param bean Bean whose property is to be extracted
     * @param name Simple property name of the property value to be extracted
     * @param index Index of the property value to be extracted
     *
     * @exception IllegalAccessException if the caller does not have
     *  access to the property accessor method
     * @exception InvocationTargetException if the property accessor method
     *  throws an exception
     * @exception NoSuchMethodException if an accessor method for this
     *  propety cannot be found
     */
    public static Object getIndexedProperty(Object bean,
					    String name, int index)
	throws IllegalAccessException, InvocationTargetException,
	       NoSuchMethodException {

	// Retrieve the property descriptor for the specified property
	PropertyDescriptor descriptor =
	    getPropertyDescriptor(bean, name);
	if (descriptor == null)
	    throw new NoSuchMethodException("Unknown property '" +
					    name + "'");

	// Call the indexed getter method if there is one
	if (descriptor instanceof IndexedPropertyDescriptor) {
	    Method readMethod =	((IndexedPropertyDescriptor) descriptor).
		getIndexedReadMethod();
	    if (readMethod != null) {
		Object subscript[] = new Object[1];
		subscript[0] = new Integer(index);
		return (readMethod.invoke(bean, subscript));
	    }
	}

	// Otherwise, the underlying property must be an array
	Method readMethod = descriptor.getReadMethod();
	if (readMethod == null)
	    throw new NoSuchMethodException("Property '" + name +
					    "' has no getter method");

	// Call the property getter and return the value
	Object value = readMethod.invoke(bean, new Object[0]);
	if (!value.getClass().isArray())
	    throw new IllegalArgumentException("Property '" + name +
					       "' is not indexed");
	return (Array.get(value, index));

    }


    /**
     * Return the value of the (possibly nested) property of the specified
     * name, for the specified bean, with no type conversions.
     *
     * @param bean Bean whose property is to be extracted
     * @param name Possibly nested name of the property to be extracted
     *
     * @exception IllegalAccessException if the caller does not have
     *  access to the property accessor method
     * @exception IllegalArgumentException if a nested reference to a
     *  property returns null
     * @exception InvocationTargetException if the property accessor method
     *  throws an exception
     * @exception NoSuchMethodException if an accessor method for this
     *  propety cannot be found
     */
    public static Object getNestedProperty(Object bean, String name)
	throws IllegalAccessException, InvocationTargetException,
	       NoSuchMethodException {

	while (true) {
	    int delim = name.indexOf(NESTED_DELIM);
	    if (delim < 0)
		break;
	    String next = name.substring(0, delim);
	    if (next.indexOf(INDEXED_DELIM) >= 0)
		bean = getIndexedProperty(bean, next);
	    else
		bean = getSimpleProperty(bean, next);
	    if (bean == null)
		throw new IllegalArgumentException
		    ("Null property value for '" +
		     name.substring(0, delim) + "'");
	    name = name.substring(delim + 1);
	}

	if (name.indexOf(INDEXED_DELIM) >= 0)
	    return (getIndexedProperty(bean, name));
	else
	    return (getSimpleProperty(bean, name));

    }


    /**
     * Return the value of the specified property of the specified bean,
     * no matter which property reference format is used, with no
     * type conversions.
     *
     * @param bean Bean whose property is to be extracted
     * @param name Possibly indexed and/or nested name of the property
     *  to be extracted
     *
     * @exception IllegalAccessException if the caller does not have
     *  access to the property accessor method
     * @exception InvocationTargetException if the property accessor method
     *  throws an exception
     * @exception NoSuchMethodException if an accessor method for this
     *  propety cannot be found
     */
    public static Object getProperty(Object bean, String name)
	throws IllegalAccessException, InvocationTargetException,
	       NoSuchMethodException {

	return (getNestedProperty(bean, name));

    }


    /**
     * Retrieve the property descriptor for the specified property of the
     * specified bean, or return <code>null</code> if there is no such
     * descriptor.  This method resolves indexed and nested property
     * references in the same manner as other methods in this class, except
     * that if the last (or only) name element is indexed, the descriptor
     * for the property itself is returned.
     *
     * @param bean Bean for which a property descriptor is requested
     * @param name Possibly indexed and/or nested name of the property for
     *  which a property descriptor is requested
     *
     * @exception IllegalAccessException if the caller does not have
     *  access to the property accessor method
     * @exception IllegalArgumentException if a nested reference to a
     *  property returns null
     * @exception InvocationTargetException if the property accessor method
     *  throws an exception
     * @exception NoSuchMethodException if an accessor method for this
     *  propety cannot be found
     */
    public static PropertyDescriptor getPropertyDescriptor(Object bean,
							   String name)
	throws IllegalAccessException, InvocationTargetException,
	       NoSuchMethodException {

	// Resolve nested references
	while (true) {
	    int period = name.indexOf(".");
	    if (period < 0)
		break;
	    String next = name.substring(0, period);
	    if (next.indexOf("[") >= 0)
		bean = getIndexedProperty(bean, next);
	    else
		bean = getSimpleProperty(bean, next);
	    if (bean == null)
		throw new IllegalArgumentException
		    ("Null property value for '" +
		     name.substring(0, period) + "'");
	    name = name.substring(period + 1);
	}

	// Remove any subscript from the final name value
	int left = name.indexOf("[");
	if (left >= 0)
	    name = name.substring(0, left);

	// Look up and return this property from our cache
	if ((bean == null) || (name == null))
	    return (null);
	PropertyDescriptor descriptors[] = getPropertyDescriptors(bean);
	if (descriptors == null)
	    return (null);
	for (int i = 0; i < descriptors.length; i++) {
	    if (name.equals(descriptors[i].getName()))
		return (descriptors[i]);
	}
	return (null);

    }


    /**
     * Retrieve the property descriptors for the specified bean, introspecting
     * and caching them the first time a particular bean class is encountered.
     *
     * @param bean Bean for which property descriptors are requested
     */
    public static PropertyDescriptor[] getPropertyDescriptors(Object bean) {

	if (bean == null)
	    return (new PropertyDescriptor[0]);

	// Look up any cached descriptors for this bean class
	String beanClassName = bean.getClass().getName();
	PropertyDescriptor descriptors[] =
	    (PropertyDescriptor[]) descriptorsCache.get(beanClassName);
	if (descriptors != null)
	    return (descriptors);

	// Introspect the bean and cache the generated descriptors
	BeanInfo beanInfo = null;
	try {
	    beanInfo = Introspector.getBeanInfo(bean.getClass());
	} catch (IntrospectionException e) {
	    return (new PropertyDescriptor[0]);
	}
	descriptors = beanInfo.getPropertyDescriptors();
	if (descriptors == null)
	    descriptors = new PropertyDescriptor[0];
	descriptorsCache.put(beanClassName, descriptors);
	return (descriptors);

    }


    /**
     * Return the Java Class repesenting the property editor class that has
     * been registered for this property (if any).  This method follows the
     * same name resolution rules used by <code>getPropertyDescriptor()</code>,
     * so if the last element of a name reference is indexed, the property
     * editor for the underlying property's class is returned.
     * <p>
     * Note that <code>null</code> will be returned if there is no property,
     * or if there is no registered property editor class.  Because this
     * return value is ambiguous, you should determine the existence of the
     * property itself by other means.
     *
     * @param bean Bean for which a property descriptor is requested
     * @param name Possibly indexed and/or nested name of the property for
     *  which a property descriptor is requested
     *
     * @exception IllegalAccessException if the caller does not have
     *  access to the property accessor method
     * @exception IllegalArgumentException if a nested reference to a
     *  property returns null
     * @exception InvocationTargetException if the property accessor method
     *  throws an exception
     * @exception NoSuchMethodException if an accessor method for this
     *  propety cannot be found
     */
    public static Class getPropertyEditorClass(Object bean, String name)
	throws IllegalAccessException, InvocationTargetException,
	       NoSuchMethodException {

	PropertyDescriptor descriptor =
	    getPropertyDescriptor(bean, name);
	if (descriptor != null)
	    return (descriptor.getPropertyEditorClass());
	else
	    return (null);

    }


    /**
     * Return the Java Class representing the property type of the specified
     * property, or <code>null</code> if there is no such property for the
     * specified bean.  This method follows the same name resolution rules
     * used by <code>getPropertyDescriptor()</code>, so if the last element
     * of a name reference is indexed, the type of the property itself will
     * be returned.  If the last (or only) element has no property with the
     * specified name, <code>null</code> is returned.
     *
     * @param bean Bean for which a property descriptor is requested
     * @param name Possibly indexed and/or nested name of the property for
     *  which a property descriptor is requested
     *
     * @exception IllegalAccessException if the caller does not have
     *  access to the property accessor method
     * @exception IllegalArgumentException if a nested reference to a
     *  property returns null
     * @exception InvocationTargetException if the property accessor method
     *  throws an exception
     * @exception NoSuchMethodException if an accessor method for this
     *  propety cannot be found
     */
    public static Class getPropertyType(Object bean, String name)
	throws IllegalAccessException, InvocationTargetException,
	       NoSuchMethodException {

	PropertyDescriptor descriptor =
	    getPropertyDescriptor(bean, name);
	if (descriptor == null)
            return (null);
        else if (descriptor instanceof IndexedPropertyDescriptor)
            return (((IndexedPropertyDescriptor) descriptor).
                    getIndexedPropertyType());
        else
	    return (descriptor.getPropertyType());

    }


    /**
     * Return the value of the specified simple property of the specified
     * bean, with no type conversions.
     *
     * @param bean Bean whose property is to be extracted
     * @param name Name of the property to be extracted
     *
     * @exception IllegalAccessException if the caller does not have
     *  access to the property accessor method
     * @exception InvocationTargetException if the property accessor method
     *  throws an exception
     * @exception NoSuchMethodException if an accessor method for this
     *  propety cannot be found
     */
    public static Object getSimpleProperty(Object bean, String name)
	throws IllegalAccessException, InvocationTargetException,
	       NoSuchMethodException {

	// Retrieve the property getter method for the specified property
	PropertyDescriptor descriptor =
	    getPropertyDescriptor(bean, name);
	if (descriptor == null)
	    throw new NoSuchMethodException("Unknown property '" +
					    name + "'");
	Method readMethod = descriptor.getReadMethod();
	if (readMethod == null)
	    throw new NoSuchMethodException("Property '" + name +
					    "' has no getter method");

	// Call the property getter and return the value
	Object value = readMethod.invoke(bean, new Object[0]);
	return (value);

    }


    /**
     * Set the value of the specified indexed property of the specified
     * bean, with no type conversions.  The zero-relative index of the
     * required value must be included (in square brackets) as a suffix to
     * the property name, or <code>IllegalArgumentException</code> will be
     * thrown.
     *
     * @param bean Bean whose property is to be modified
     * @param name <code>propertyname[index]</code> of the property value
     *  to be modified
     * @param value Value to which the specified property element
     *  should be set
     *
     * @exception IllegalAccessException if the caller does not have
     *  access to the property accessor method
     * @exception InvocationTargetException if the property accessor method
     *  throws an exception
     * @exception NoSuchMethodException if an accessor method for this
     *  propety cannot be found
     */
    public static void setIndexedProperty(Object bean, String name,
					  Object value)
	throws IllegalAccessException, InvocationTargetException,
	       NoSuchMethodException {

	// Identify the index of the requested individual property
	int delim = name.indexOf(INDEXED_DELIM);
        int delim2 = name.indexOf(INDEXED_DELIM2);
        if ((delim < 0) || (delim2 <= delim))
	    throw new IllegalArgumentException("Invalid indexed property '" +
					       name + "'");
	int index = -1;
	try {
	    String subscript = name.substring(delim + 1, delim2);
	    index = Integer.parseInt(subscript);
	} catch (NumberFormatException e) {
	    throw new IllegalArgumentException("Invalid indexed property '" +
					       name + "'");
	}
	name = name.substring(0, delim);

	// Set the specified indexed property value
	setIndexedProperty(bean, name, index, value);

    }


    /**
     * Set the value of the specified indexed property of the specified
     * bean, with no type conversions.
     *
     * @param bean Bean whose property is to be set
     * @param name Simple property name of the property value to be set
     * @param index Index of the property value to be set
     * @param value Value to which the indexed property element is to be set
     *
     * @exception IllegalAccessException if the caller does not have
     *  access to the property accessor method
     * @exception InvocationTargetException if the property accessor method
     *  throws an exception
     * @exception NoSuchMethodException if an accessor method for this
     *  propety cannot be found
     */
    public static void setIndexedProperty(Object bean, String name,
					  int index, Object value)
	throws IllegalAccessException, InvocationTargetException,
	       NoSuchMethodException {

	// Retrieve the property descriptor for the specified property
	PropertyDescriptor descriptor =
	    getPropertyDescriptor(bean, name);
	if (descriptor == null)
	    throw new NoSuchMethodException("Unknown property '" +
					    name + "'");

	// Call the indexed setter method if there is one
	if (descriptor instanceof IndexedPropertyDescriptor) {
	    Method writeMethod = ((IndexedPropertyDescriptor) descriptor).
		getIndexedWriteMethod();
	    if (writeMethod != null) {
		Object subscript[] = new Object[2];
		subscript[0] = new Integer(index);
		subscript[1] = value;
		writeMethod.invoke(bean, subscript);
                return;
	    }
	}

	// Otherwise, the underlying property must be an array
	Method readMethod = descriptor.getReadMethod();
	if (readMethod == null)
	    throw new NoSuchMethodException("Property '" + name +
					    "' has no getter method");

	// Call the property getter to get the array
	Object array = readMethod.invoke(bean, new Object[0]);
	if (!array.getClass().isArray())
	    throw new IllegalArgumentException("Property '" + name +
					       "' is not indexed");

	// Modify the specified value
	Array.set(array, index, value);

    }


    /**
     * Set the value of the (possibly nested) property of the specified
     * name, for the specified bean, with no type conversions.
     *
     * @param bean Bean whose property is to be modified
     * @param name Possibly nested name of the property to be modified
     * @param value Value to which the property is to be set
     *
     * @exception IllegalAccessException if the caller does not have
     *  access to the property accessor method
     * @exception IllegalArgumentException if a nested reference to a
     *  property returns null
     * @exception InvocationTargetException if the property accessor method
     *  throws an exception
     * @exception NoSuchMethodException if an accessor method for this
     *  propety cannot be found
     */
    public static void setNestedProperty(Object bean,
					 String name, Object value)
	throws IllegalAccessException, InvocationTargetException,
	       NoSuchMethodException {

	while (true) {
	    int delim = name.indexOf(NESTED_DELIM);
	    if (delim < 0)
		break;
	    String next = name.substring(0, delim);
	    if (next.indexOf(INDEXED_DELIM) >= 0)
		bean = getIndexedProperty(bean, next);
	    else
		bean = getSimpleProperty(bean, next);
	    if (bean == null)
		throw new IllegalArgumentException
		    ("Null property value for '" +
		     name.substring(0, delim) + "'");
	    name = name.substring(delim + 1);
	}

	if (name.indexOf(INDEXED_DELIM) >= 0)
	    setIndexedProperty(bean, name, value);
	else
	    setSimpleProperty(bean, name, value);

    }


    /**
     * Set the value of the specified property of the specified bean,
     * no matter which property reference format is used, with no
     * type conversions.
     *
     * @param bean Bean whose property is to be modified
     * @param name Possibly indexed and/or nested name of the property
     *  to be modified
     * @param value Value to which this property is to be set
     *
     * @exception IllegalAccessException if the caller does not have
     *  access to the property accessor method
     * @exception InvocationTargetException if the property accessor method
     *  throws an exception
     * @exception NoSuchMethodException if an accessor method for this
     *  propety cannot be found
     */
    public static void setProperty(Object bean, String name, Object value)
	throws IllegalAccessException, InvocationTargetException,
	       NoSuchMethodException {

	setNestedProperty(bean, name, value);

    }


    /**
     * Set the value of the specified simple property of the specified bean,
     * with no type conversions.
     *
     * @param bean Bean whose property is to be modified
     * @param name Name of the property to be modified
     * @param value Value to which the property should be set
     *
     * @exception IllegalAccessException if the caller does not have
     *  access to the property accessor method
     * @exception InvocationTargetException if the property accessor method
     *  throws an exception
     * @exception NoSuchMethodException if an accessor method for this
     *  propety cannot be found
     */
    public static void setSimpleProperty(Object bean,
					 String name, Object value)
	throws IllegalAccessException, InvocationTargetException,
	       NoSuchMethodException {

	// Retrieve the property setter method for the specified property
	PropertyDescriptor descriptor =
	    getPropertyDescriptor(bean, name);
	if (descriptor == null)
	    throw new NoSuchMethodException("Unknown property '" +
					    name + "'");
	Method writeMethod = descriptor.getWriteMethod();
	if (writeMethod == null)
	    throw new NoSuchMethodException("Property '" + name +
					    "' has no setter method");

	// Call the property setter method
	Object values[] = new Object[1];
	values[0] = value;
	writeMethod.invoke(bean, values);

    }


}
