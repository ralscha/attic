package ch.ess.base.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import ch.ess.base.Constants;

import com.cc.framework.adapter.RequestContext;
import com.cc.framework.convert.Converter;

public class DateConverter implements Converter {

  public Object getAsObject(RequestContext ctx, String value) {
    return null;
  }

  public String getAsString(RequestContext ctx, Object value) {
    if (value != null) {
    DateFormat df = new SimpleDateFormat(Constants.getDateFormatPattern());
    return df.format(value);
  }
    return null;
  }

}
