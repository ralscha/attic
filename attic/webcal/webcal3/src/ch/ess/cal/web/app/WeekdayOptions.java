package ch.ess.cal.web.app;

import java.text.DateFormatSymbols;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import ch.ess.cal.Util;
import ch.ess.cal.model.User;
import ch.ess.cal.persistence.UserConfigurationDao;
import ch.ess.cal.persistence.UserDao;
import ch.ess.cal.service.Config;
import ch.ess.cal.service.impl.UserConfig;
import ch.ess.cal.web.AbstractOptions;

/** 
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2005/05/09 07:46:06 $ 
 * 
 * @spring.bean id="weekdayOptions" singleton="false"
 */
public class WeekdayOptions extends AbstractOptions {

  private UserDao userDao;
  private UserConfigurationDao userConfigurationDao;

  /**
   * @spring.property reflocal="userDao"
   */
  public void setUserDao(final UserDao userDao) {
    this.userDao = userDao;
  }

  /**
   * @spring.property reflocal="userConfigurationDao"
   */
  public void setUserConfigurationDao(final UserConfigurationDao userConfigurationDao) {
    this.userConfigurationDao = userConfigurationDao;
  }

  @Override
  public void init(final HttpServletRequest request) {

    clear();
    User user = Util.getUser(request.getSession(), userDao);
    Config config = userConfigurationDao.getUserConfig(user);
    int start = config.getIntegerProperty(UserConfig.FIRST_DAY_OF_WEEK, Calendar.MONDAY);
    DateFormatSymbols symbols = new DateFormatSymbols(getLocale(request));
    String[] weekdays = symbols.getWeekdays();
    for (int i = 0; i < 7; i++) {
      add(weekdays[start], String.valueOf(start));
      start++;
      if (start == 8) {
        start = 1;
      }
    }

  }

}
