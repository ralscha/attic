package ch.ess.cal.web.event;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

import ch.ess.cal.Constants;
import ch.ess.cal.Util;

/**
 * @author sr
 */
public class EventListDateObjectFormatter extends Format {

  @Override
  public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
    EventListDateObject dateObject = (EventListDateObject)obj;

    if (dateObject.isAllDay()) {
      return new StringBuffer(Util.userCalendar2String(dateObject.getCalendar(), Constants.getDateFormatPattern()));
    }

    return new StringBuffer(Util.userCalendar2String(dateObject.getCalendar(), Constants.getDateTimeFormatPattern()));
  }

  @Override
  public Object parseObject(String source, ParsePosition pos) {
    //not supported
    return null;
  }

}
