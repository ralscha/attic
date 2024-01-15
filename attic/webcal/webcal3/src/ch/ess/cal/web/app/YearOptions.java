package ch.ess.cal.web.app;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.math.IntRange;

import ch.ess.cal.persistence.EventDao;
import ch.ess.cal.web.AbstractOptions;

/** 
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2005/05/09 07:46:06 $ 
 * 
 * @spring.bean name="yearOptions" singleton="false" 
 */
public class YearOptions extends AbstractOptions {

  private EventDao eventDao;

  /**
   * @spring.property reflocal="eventDao"
   */
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
