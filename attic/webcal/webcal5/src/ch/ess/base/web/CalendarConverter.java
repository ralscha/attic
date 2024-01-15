package ch.ess.base.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import ch.ess.base.Constants;

import com.cc.framework.adapter.RequestContext;
import com.cc.framework.convert.Converter;

public class CalendarConverter implements Converter {

  public Object getAsObject(RequestContext ctx, String value) {
    return null;
  }

  public String getAsString(RequestContext ctx, Object value) {
    if (value != null){
      DateFormat df = new SimpleDateFormat(Constants.getDateTimeFormatPattern());
      return df.format(((Calendar)value).getTime());
    }
    return "";
  }

}
