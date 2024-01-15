package ch.ess.base.web.userconfig;

import javax.servlet.http.HttpServletRequest;

import ch.ess.base.enums.TimeEnum;
import ch.ess.base.web.AbstractOptions;

/**
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2005/07/03 04:48:21 $
 */
public class TimeOptions extends AbstractOptions {

  @Override
  public void init(final HttpServletRequest request) {
    clear();
    addTranslate(request, TimeEnum.MINUTE.getKey(), String.valueOf(TimeEnum.MINUTE.getValue()));
    addTranslate(request, TimeEnum.HOUR.getKey(), String.valueOf(TimeEnum.HOUR.getValue()));
    addTranslate(request, TimeEnum.DAY.getKey(), String.valueOf(TimeEnum.DAY.getValue()));
    addTranslate(request, TimeEnum.WEEK.getKey(), String.valueOf(TimeEnum.WEEK.getValue()));
    addTranslate(request, TimeEnum.MONTH.getKey(), String.valueOf(TimeEnum.MONTH.getValue()));
    addTranslate(request, TimeEnum.YEAR.getKey(), String.valueOf(TimeEnum.YEAR.getValue()));
  }

}