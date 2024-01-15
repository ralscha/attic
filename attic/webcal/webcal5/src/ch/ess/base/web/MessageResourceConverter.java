package ch.ess.base.web;

import java.util.Locale;

import org.apache.struts.util.MessageResources;

import ch.ess.base.Util;

import com.cc.framework.adapter.RequestContext;
import com.cc.framework.convert.Converter;

public class MessageResourceConverter implements Converter {

  public Object getAsObject(RequestContext ctx, String value) {
    return value;
  }

  public String getAsString(RequestContext ctx, Object value) {
    Locale locale = ctx.getLocale();
    MessageResources messages = Util.getMessages(ctx.request());
    
    if (value != null){
      return messages.getMessage(locale, (String)value);    
    }
    return "";    
  }
}
