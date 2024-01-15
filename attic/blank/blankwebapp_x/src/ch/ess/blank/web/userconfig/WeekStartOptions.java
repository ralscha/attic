package ch.ess.blank.web.userconfig;

import java.text.DateFormatSymbols;
import java.util.Calendar;

import org.apache.struts.util.LabelValueBean;

import ch.ess.common.web.Options;

/**
 * @author Ralph Schaer
 * @version $Revision: 1.4 $ $Date: 2004/05/22 15:38:03 $
 */
public class WeekStartOptions extends Options {

  public void init() {
    DateFormatSymbols symbols = new DateFormatSymbols(getLocale());
    String[] weekdays = symbols.getWeekdays();

    getLabelValue().clear();
    getLabelValue().add(new LabelValueBean(weekdays[Calendar.SUNDAY], String.valueOf(Calendar.SUNDAY)));
    getLabelValue().add(new LabelValueBean(weekdays[Calendar.MONDAY], String.valueOf(Calendar.MONDAY)));

  }

}