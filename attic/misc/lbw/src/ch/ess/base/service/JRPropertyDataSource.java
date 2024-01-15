package ch.ess.base.service;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JRRewindableDataSource;

import org.apache.commons.beanutils.PropertyUtils;

import com.cc.framework.convert.Converter;

public class JRPropertyDataSource implements JRRewindableDataSource {
  private Iterator iterator;
  protected Object currentRecord;
  private Collection records = null;
  private Map<String, Converter> converterMap = new HashMap<String,Converter>();  

  public JRPropertyDataSource(final Collection collection) {
    this.records = collection;
    this.iterator = records.iterator();
  }

  public boolean next() {
    if (iterator.hasNext()) {
      currentRecord = iterator.next();
      return true;
    }
    return false;
  }

  public Object getFieldValue(JRField field) {
    try {
      Converter converter = converterMap.get(field.getName());
      Object value = getCurrentRowValue(field.getName());
      if (converter == null) {
        return value;
      } 
      
      return converter.getAsString(null, value);
      
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  protected Object getCurrentRowValue(String property) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
    return PropertyUtils.getProperty(currentRecord, property);
  }

  public void moveFirst() {
    if (records != null) {
      iterator = records.iterator();
    }
  }
  
  public void addConverter(String property, Converter converter) {
    converterMap.put(property, converter);
  }
}
