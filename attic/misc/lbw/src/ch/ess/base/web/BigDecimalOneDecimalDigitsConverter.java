package ch.ess.base.web;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import ch.ess.base.Constants;

import com.cc.framework.adapter.RequestContext;
import com.cc.framework.convert.Converter;

public class BigDecimalOneDecimalDigitsConverter implements Converter {

  public Object getAsObject(RequestContext ctx, String value) {
    if (StringUtils.isNotBlank(value)) {
      return new BigDecimal(value);
    }
    return null;
  }

  public String getAsString(RequestContext ctx, Object value) {
    if (value != null) {
      return Constants.ONE_DECIMAL_FORMAT.format(value);
    }
    return null;
  }

}
