package ch.ess.cal.web.userconfig;

import java.text.DateFormatSymbols;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import ch.ess.cal.web.AbstractOptions;

/**
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2005/05/09 07:46:06 $
 */
public class WeekStartOptions extends AbstractOptions {

  @Override
  public void init(final HttpServletRequest request) {
    DateFormatSymbols symbols = new DateFormatSymbols(getLocale(request));
    String[] weekdays = symbols.getWeekdays();

    clear();
    add(weekdays[Calendar.SUNDAY], String.valueOf(Calendar.SUNDAY));
    add(weekdays[Calendar.MONDAY], String.valueOf(Calendar.MONDAY));

  }

}
