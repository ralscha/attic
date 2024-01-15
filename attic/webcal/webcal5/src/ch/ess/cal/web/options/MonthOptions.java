package ch.ess.cal.web.options;

import java.text.DateFormatSymbols;

import javax.servlet.http.HttpServletRequest;

import ch.ess.base.annotation.option.Option;
import ch.ess.base.web.options.AbstractOptions;

@Option(id="monthOptions")
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
