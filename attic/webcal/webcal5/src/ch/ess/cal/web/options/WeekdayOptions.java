package ch.ess.cal.web.options;

import java.text.DateFormatSymbols;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import ch.ess.base.Util;
import ch.ess.base.annotation.option.Option;
import ch.ess.base.dao.UserConfigurationDao;
import ch.ess.base.dao.UserDao;
import ch.ess.base.model.User;
import ch.ess.base.service.Config;
import ch.ess.base.service.UserConfig;
import ch.ess.base.web.options.AbstractOptions;


@Option(id = "weekdayOptions")
public class WeekdayOptions extends AbstractOptions {

  private UserDao userDao;
  private UserConfigurationDao userConfigurationDao;

  public void setUserDao(final UserDao userDao) {
    this.userDao = userDao;
  }

  public void setUserConfigurationDao(final UserConfigurationDao userConfigurationDao) {
    this.userConfigurationDao = userConfigurationDao;
  }

  @Override
  public void init(final HttpServletRequest request) {

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
