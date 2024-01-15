package ch.ess.cal.web.event;

import java.text.DateFormatSymbols;

import net.sf.hibernate.HibernateException;

import org.apache.struts.util.LabelValueBean;

import ch.ess.cal.db.User;
import ch.ess.cal.resource.UserConfig;
import ch.ess.common.web.Options;

import com.ibm.icu.util.Calendar;

public class WeekdayOptions extends Options {

  public void init() throws HibernateException {
    getLabelValue().clear();

    User user = User.find(getRequest().getUserPrincipal().getName());
    UserConfig config = UserConfig.getUserConfig(user);

    int start = config.getIntegerProperty(UserConfig.FIRST_DAY_OF_WEEK, Calendar.MONDAY);

    DateFormatSymbols symbols = new DateFormatSymbols(getLocale());

    String[] weekdays = symbols.getWeekdays();
    
       
    for (int i = 0; i < 7; i++) {
      getLabelValue().add(new LabelValueBean(weekdays[start], String.valueOf(start)));
      start++;
      if (start == 8) {
        start = 1;
      }
    }

  }

}
