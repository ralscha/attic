package ch.ess.base.web.options;

import java.text.DateFormatSymbols;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import ch.ess.base.annotation.option.Option;

@Option(id = "weekStartOptions")
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