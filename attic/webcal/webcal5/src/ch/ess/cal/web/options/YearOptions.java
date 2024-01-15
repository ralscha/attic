package ch.ess.cal.web.options;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.math.IntRange;

import ch.ess.base.annotation.option.Option;
import ch.ess.base.web.options.AbstractOptions;
import ch.ess.cal.dao.EventDao;

@Option(id = "yearOptions")
public class YearOptions extends AbstractOptions {

  private EventDao eventDao;

  public void setEventDao(final EventDao eventDao) {
    this.eventDao = eventDao;
  }

  @Override
  public void init(final HttpServletRequest request) {

    IntRange minMaxYear = eventDao.getMinMaxYear();

    for (int i = minMaxYear.getMinimumInteger(); i <= minMaxYear.getMaximumInteger(); i++) {
      add(String.valueOf(i), String.valueOf(i));
    }

  }

}
