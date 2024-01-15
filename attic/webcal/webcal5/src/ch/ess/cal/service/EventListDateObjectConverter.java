package ch.ess.cal.service;

import ch.ess.base.Constants;
import ch.ess.cal.CalUtil;

import com.cc.framework.adapter.RequestContext;
import com.cc.framework.convert.Converter;

public class EventListDateObjectConverter implements Converter {

  public Object getAsObject(RequestContext ctx, String newValue) {
    return null;
  }

  public String getAsString(RequestContext ctx, Object value) {
    EventListDateObject dateObject = (EventListDateObject)value;
    if (dateObject != null) {
      if (dateObject.isAllDay()) {
        return CalUtil.userCalendar2String(dateObject.getCalendar(), Constants.getDateFormatPattern());
      }
  
      return CalUtil.userCalendar2String(dateObject.getCalendar(), Constants.getDateTimeFormatPattern());
    } 
    return null;    
  }


}
