package ch.ess.cal.web.userconfig;

import org.apache.struts.util.LabelValueBean;

import ch.ess.common.web.Options;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.1 $ $Date: 2004/02/14 16:32:53 $ 
  */
public class TimeOptions extends Options {

  public void init() {
    getLabelValue().clear();
    getLabelValue().add(new LabelValueBean(getMessage(TimeEnum.MINUTE.getName()), TimeEnum.MINUTE.getName()));
    getLabelValue().add(new LabelValueBean(getMessage(TimeEnum.HOUR.getName()), TimeEnum.HOUR.getName()));
    getLabelValue().add(new LabelValueBean(getMessage(TimeEnum.DAY.getName()), TimeEnum.DAY.getName()));
    getLabelValue().add(new LabelValueBean(getMessage(TimeEnum.WEEK.getName()), TimeEnum.WEEK.getName()));
    getLabelValue().add(new LabelValueBean(getMessage(TimeEnum.MONTH.getName()), TimeEnum.MONTH.getName()));
    getLabelValue().add(new LabelValueBean(getMessage(TimeEnum.YEAR.getName()), TimeEnum.YEAR.getName()));

  }

}
