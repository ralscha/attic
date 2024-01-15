package ch.ess.base.web;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.cc.framework.adapter.RequestContext;
import com.cc.framework.convert.Converter;

public class DocumentSizeConverter implements Converter {

  private static final NumberFormat SIZE_FORMAT = new DecimalFormat("#,##0 'KB'");
  
  public Object getAsObject(RequestContext ctx, String newValue) {
    return null;
  }

  public String getAsString(RequestContext ctx, Object value) {
    Integer sizeObj = (Integer)value;    
    if (sizeObj != null) {
      int size = sizeObj / 1024;
      if (size == 0) {
        size = 1;
      }
      return SIZE_FORMAT.format(size);    
    } 
    return null;    
  }


}
