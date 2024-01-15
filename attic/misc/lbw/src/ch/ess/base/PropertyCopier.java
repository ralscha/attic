package ch.ess.base;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.PropertyUtils;

import com.cc.framework.convert.Converter;
import com.cc.framework.convert.ConverterException;

public class PropertyCopier {

  private boolean disabled = false;
  private Set<String> excludeProperties = new HashSet<String>();
  private Map<String, String> propertyOrigDestMapping = new HashMap<String, String>();
  private Map<String, Converter> propertyConverter = new HashMap<String, Converter>();
  private Map<Class, Converter> classConverter = new HashMap<Class, Converter>();
  
  public void disable() {
    this.disabled = true;
  }

  public void addExcludeProperty(String propertyName) {
    excludeProperties.add(propertyName);
  }

  public void addOrigDestMapping(String sourcePropertyName, String destinationPropertyName) {
    propertyOrigDestMapping.put(sourcePropertyName, destinationPropertyName);
  }

  public void addPropertyConverter(String propertyName, Converter converter) {
    propertyConverter.put(propertyName, converter);
  }

  public void addClassConverter(Class clazz, Converter converter) {
    classConverter.put(clazz, converter);
  }

  public void copyProperties(Object source, Object destination) {

    if (disabled) {
       return;
    }
    
    try {
      PropertyDescriptor sourceDescriptors[] = PropertyUtils.getPropertyDescriptors(source);

      for (int i = 0; i < sourceDescriptors.length; i++) {
        String sourceName = sourceDescriptors[i].getName();

        if ("class".equals(sourceName)) {
          continue; // No point in trying to set an object's class
        }

        if (excludeProperties.contains(sourceName)) {
          continue;
        }

        String destName = propertyOrigDestMapping.get(sourceName);
        if (destName == null) {
          destName = sourceName;
        }

        if (PropertyUtils.isReadable(source, sourceName) && PropertyUtils.isWriteable(destination, destName)) {
          try {
            Object value = PropertyUtils.getSimpleProperty(source, sourceName);
            copyProperty(destination, destName, value);
          } catch (NoSuchMethodException e) {
            e.printStackTrace();
            // Should not happen
          }
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void copyProperty(Object target, String propName, Object value) throws IllegalAccessException,
      InvocationTargetException {

    Class type = null; // Java type of target property

    //Calculate the target property type
    if (target instanceof DynaBean) {
      DynaClass dynaClass = ((DynaBean)target).getDynaClass();
      DynaProperty dynaProperty = dynaClass.getDynaProperty(propName);
      if (dynaProperty == null) {
        return; // Skip this property setter
      }
      type = dynaProperty.getType();
    } else {
      PropertyDescriptor descriptor = null;
      try {
        descriptor = PropertyUtils.getPropertyDescriptor(target, propName);
        if (descriptor == null) {
          return; // Skip this property setter
        }
      } catch (NoSuchMethodException e) {
        return; // Skip this property setter
      }
      type = descriptor.getPropertyType();
      if (type == null) {
        return;
      }
    }

    //to String
    if (type == String.class) {
      Converter converter = propertyConverter.get(propName);
      if (converter == null) {
        converter = classConverter.get(type);
      }
      if (converter == null) {
        converter = new DefaultConverter(type);
      }
      try {
        String strValue = converter.getAsString(null, value);

        PropertyUtils.setSimpleProperty(target, propName, strValue);
      } catch (NoSuchMethodException e) {
        throw new InvocationTargetException(e, "Cannot set " + propName);
      } catch (ConverterException e) {
        throw new InvocationTargetException(e, "Cannot converter " + propName);
      }

    } else if (value instanceof String) { //from String

      Converter converter = propertyConverter.get(propName);
      if (converter == null) {
        converter = classConverter.get(type);
      }
      if (converter == null) {
        converter = new DefaultConverter(type);
      }

      try {
        Object convValue = converter.getAsObject(null, (String)value);

        PropertyUtils.setSimpleProperty(target, propName, convValue);
      } catch (NoSuchMethodException e) {
        throw new InvocationTargetException(e, "Cannot set " + propName);
      } catch (ConverterException e) {
        throw new InvocationTargetException(e, "Cannot converter " + propName);
      }
    } else if (target instanceof DynaBean) {
      try {
        //copy one to one. unless there is a converter, then to string
        Converter converter = propertyConverter.get(propName);
        Object val = null;
        if (converter != null) {
          val = converter.getAsString(null, value);
        } else {
          val = value;
        }

        PropertyUtils.setSimpleProperty(target, propName, val);
      } catch (NoSuchMethodException e) {
        throw new InvocationTargetException(e, "Cannot set " + propName);
      } catch (ConverterException e) {
        throw new InvocationTargetException(e, "Cannot converter " + propName);
      }
    } else {
      try {
        PropertyUtils.setSimpleProperty(target, propName, value);
      } catch (NoSuchMethodException e) {
        throw new InvocationTargetException(e, "Cannot set " + propName);
      } 
    }

    //    Converter converter = getConvertUtils().lookup(type);
    //    if (converter != null) {
    //      value = converter.convert(type, value);
    //    }
    //    try {
    //      PropertyUtils.setSimpleProperty(target, propName, value);
    //    } catch (NoSuchMethodException e) {
    //      throw new InvocationTargetException(e, "Cannot set " + propName);
    //    }

  }
}
