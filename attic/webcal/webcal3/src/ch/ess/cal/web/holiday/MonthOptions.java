package ch.ess.cal.web.holiday;

import java.text.DateFormatSymbols;

import javax.servlet.http.HttpServletRequest;

import ch.ess.cal.web.AbstractOptions;

public class MonthOptions extends AbstractOptions {

  @Override
  public void init(final HttpServletRequest request) {
    DateFormatSymbols symbols = new DateFormatSymbols(getLocale(request));

    String[] months = symbols.getMonths();

    for (int i = 0; i < 12; i++) {
      add(months[i], String.valueOf(i));
    }

  }

}
