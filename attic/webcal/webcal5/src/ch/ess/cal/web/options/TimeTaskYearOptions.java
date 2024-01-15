package ch.ess.cal.web.options;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import ch.ess.base.annotation.option.Option;
import ch.ess.base.web.options.AbstractOptions;
import ch.ess.cal.dao.TimeDao;

@Option(id = "timeYearOptions")
public class TimeTaskYearOptions extends AbstractOptions {

  private TimeDao timeDao;

  public void setTimeDao(TimeDao timeDao) {
    this.timeDao = timeDao;
  }

  @Override
  public void init(final HttpServletRequest request) {

    List<Integer> yearList = timeDao.getYears();

    for (Iterator<Integer> it = yearList.iterator(); it.hasNext();) {
      Integer year = it.next();
      add(year.toString(), year.toString());
    }

  }

}