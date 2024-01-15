
import java.lang.reflect.*;
import java.beans.*;

public class ReflectionUtil {

	public static Method getPropertyReader(Object bean, String property) throws IntrospectionException {
		Class beanClass = bean.getClass();
		BeanInfo beanInfo = Introspector.getBeanInfo(beanClass);
		PropertyDescriptor descriptors[] = beanInfo.getPropertyDescriptors();
	
		int stop = descriptors.length;
		for (int i = 0; i < stop; ++i) {
			PropertyDescriptor descriptor = descriptors[i];
			if (descriptor.getName().equals(property)) {
				return descriptor.getReadMethod();
			}
		}
    return null;
	}

	public static Method getPropertyWriter(Object bean, String property) throws IntrospectionException {
		Class beanClass = bean.getClass();
		BeanInfo beanInfo = Introspector.getBeanInfo(beanClass);
		PropertyDescriptor descriptors[] = beanInfo.getPropertyDescriptors();
	
		int stop = descriptors.length;
		for (int i = 0; i < stop; ++i) {
			PropertyDescriptor descriptor = descriptors[i];
			if (descriptor.getName().equals(property)) {
				return descriptor.getWriteMethod();
			}
		}
    return null;
	}

	public static Object readProperty (Object bean, Method reader) throws IllegalAccessException, IntrospectionException, InvocationTargetException {
    Object result = reader.invoke(bean, null);
    return result;
	}


	public static Method getPropertyReader(Class beanClass, String property,
                          	Class returnType) throws IntrospectionException{

		BeanInfo beanInfo = Introspector.getBeanInfo(beanClass);
		PropertyDescriptor descriptors[] = beanInfo.getPropertyDescriptors();
		int stop = descriptors.length;
		for (int i = 0; i < stop; ++i) {
			PropertyDescriptor descriptor = descriptors[i];
			if (descriptor.getName().equals(property) &&
    				(descriptor.getPropertyType() == returnType)) {
				return descriptor.getReadMethod();
			}
		}
    return null;
	}

	private Method getIndexedReader (Class beanClass, String property,
                                 	Class returnType) throws IntrospectionException {

		BeanInfo beanInfo = Introspector.getBeanInfo(beanClass);
		PropertyDescriptor descriptors[] = beanInfo.getPropertyDescriptors();
		int stop = descriptors.length;
		for (int i = 0; i < stop; ++i) {
			PropertyDescriptor descriptor = descriptors[i];
			if (descriptor instanceof IndexedPropertyDescriptor &&
    				descriptor.getName().equals(property)) {
				IndexedPropertyDescriptor ipd = (IndexedPropertyDescriptor)descriptor;
				if (ipd.getIndexedPropertyType() == returnType) {
					return ipd.getIndexedReadMethod();
				}
			}
		}
    return null;
  }
}
