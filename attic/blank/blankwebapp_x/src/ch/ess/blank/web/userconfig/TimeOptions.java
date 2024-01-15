package ch.ess.blank.web.userconfig;

import org.apache.struts.util.LabelValueBean;

import ch.ess.common.web.Options;

/**
 * @author Ralph Schaer
 * @version $Revision: 1.4 $ $Date: 2004/05/22 15:38:03 $
 */
public class TimeOptions extends Options {

  public void init() {
    clear();
    add(new LabelValueBean(translate(TimeEnum.MINUTE.getName()), TimeEnum.MINUTE.getName()));
    add(new LabelValueBean(translate(TimeEnum.HOUR.getName()), TimeEnum.HOUR.getName()));
    add(new LabelValueBean(translate(TimeEnum.DAY.getName()), TimeEnum.DAY.getName()));
    add(new LabelValueBean(translate(TimeEnum.WEEK.getName()), TimeEnum.WEEK.getName()));
    add(new LabelValueBean(translate(TimeEnum.MONTH.getName()), TimeEnum.MONTH.getName()));
    add(new LabelValueBean(translate(TimeEnum.YEAR.getName()), TimeEnum.YEAR.getName()));

  }

}