package ch.ess.base.web;

import ch.ess.base.Constants;

import com.cc.framework.adapter.RequestContext;
import com.cc.framework.convert.Converter;

public class BigDecimalConverter implements Converter {

  public Object getAsObject(RequestContext ctx, String value) {
    return null;
  }

  public String getAsString(RequestContext ctx, Object value) {
    if (value != null) {
    return Constants.TWO_DECIMAL_FORMAT.format(value);
  }
    return null;
  }

}
