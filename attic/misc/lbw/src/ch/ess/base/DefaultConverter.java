package ch.ess.base;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.beanutils.ConvertUtils;

import com.cc.framework.adapter.RequestContext;
import com.cc.framework.convert.Converter;

public class DefaultConverter implements Converter {

  private Class targetType;
  
  DefaultConverter(Class targetType) {
    this.targetType = targetType;
  }
  
  public Object getAsObject(RequestContext ctx, String value) {
    
    if (targetType == Date.class) {
      String pattern = Constants.getDateFormatPattern();
      if (pattern == null) {
        pattern = "dd.MM.yyyy";
      }
      DateFormat dateFormater = new SimpleDateFormat(pattern);
      try {
        return dateFormater.parse(value);
      } catch (ParseException e) {        
        e.printStackTrace();
      }      
      
    } else {
      
      org.apache.commons.beanutils.Converter converter = ConvertUtils.lookup(targetType);
      if (converter != null) {
        return converter.convert(targetType, value);
      }
    }
    
    return value;    
  }

  public String getAsString(RequestContext ctx, Object value) {
    if (value == null) {
      return null;
    } else if (value instanceof Date) {
      String pattern = Constants.getDateFormatPattern();
      if (pattern == null) {
        pattern = "dd.MM.yyyy";
      }
      DateFormat dateFormater = new SimpleDateFormat(pattern);
      return dateFormater.format(value);
    }
    return value.toString();

  }

}
