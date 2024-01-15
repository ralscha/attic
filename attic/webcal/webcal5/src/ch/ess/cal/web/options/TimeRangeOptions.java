package ch.ess.cal.web.options;

import javax.servlet.http.HttpServletRequest;

import ch.ess.base.annotation.option.Option;
import ch.ess.base.web.options.AbstractOptions;
import ch.ess.cal.enums.TimeRangeEnum;

@Option(id = "timeRangeOptions")
public class TimeRangeOptions extends AbstractOptions {

  @Override
  public void init(final HttpServletRequest request) {
    addTranslate(request, TimeRangeEnum.DAY.getKey(), String.valueOf(TimeRangeEnum.DAY.getValue()));
    addTranslate(request, TimeRangeEnum.WEEK.getKey(), String.valueOf(TimeRangeEnum.WEEK.getValue()));
    addTranslate(request, TimeRangeEnum.MONTH.getKey(), String.valueOf(TimeRangeEnum.MONTH.getValue()));
    addTranslate(request, TimeRangeEnum.QUARTER.getKey(), String.valueOf(TimeRangeEnum.QUARTER.getValue()));
    addTranslate(request, TimeRangeEnum.SEMESTER.getKey(), String.valueOf(TimeRangeEnum.SEMESTER.getValue()));
    addTranslate(request, TimeRangeEnum.YEAR.getKey(), String.valueOf(TimeRangeEnum.YEAR.getValue()));
  }

}
