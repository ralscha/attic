package ch.ess.base.web.userconfig;

import java.text.DateFormatSymbols;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import ch.ess.base.web.AbstractOptions;

/**
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2005/07/03 04:48:21 $
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