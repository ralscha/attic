package ch.ess.timetracker.web;

import java.text.DateFormatSymbols;

import org.apache.struts.util.LabelValueBean;

import ch.ess.common.web.Options;

public class MonthOptions extends Options {

  public void init() {
    getLabelValue().clear();

    DateFormatSymbols symbols = new DateFormatSymbols(getLocale());

    String[] months = symbols.getMonths();

    for (int i = 0; i < 12; i++) {
      getLabelValue().add(new LabelValueBean(months[i], String.valueOf(i)));
    }

  }

}
