package ch.ess.addressbook.web.userconfig;

import java.util.Locale;

import net.sf.hibernate.HibernateException;

import org.apache.struts.util.LabelValueBean;
import org.apache.struts.util.MessageResources;

import ch.ess.common.web.Options;

/** 
  * @author  Ralph Schaer
  * @version 1.0, 13.07.2003 
  */
public class TimeOptions extends Options {

  public TimeOptions(Locale locale, MessageResources messages) throws HibernateException {
    super(locale, messages);
    init();
  }

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
