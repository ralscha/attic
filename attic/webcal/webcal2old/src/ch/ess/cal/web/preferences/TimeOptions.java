package ch.ess.cal.web.preferences;

import java.util.Locale;

import net.sf.hibernate.Session;

import org.apache.struts.util.LabelValueBean;
import org.apache.struts.util.MessageResources;

import ch.ess.cal.common.Options;

public class TimeOptions extends Options {


  public TimeOptions(Session sess, Locale locale, MessageResources messages) {
    super(sess, locale, messages);
    init();
  }

  public void init() {
    
    getLabelValue().add(new LabelValueBean(TimeEnum.MINUTE.getName(), TimeEnum.MINUTE.getName()));
    getLabelValue().add(new LabelValueBean(TimeEnum.HOUR.getName(), TimeEnum.HOUR.getName()));
    getLabelValue().add(new LabelValueBean(TimeEnum.DAY.getName(), TimeEnum.DAY.getName()));
    getLabelValue().add(new LabelValueBean(TimeEnum.WEEK.getName(), TimeEnum.WEEK.getName()));
    
  }

}
