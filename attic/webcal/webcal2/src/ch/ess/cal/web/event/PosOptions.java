package ch.ess.cal.web.event;

import org.apache.struts.util.LabelValueBean;

import ch.ess.common.web.Options;

/** 
  * @author  Ralph Schaer
  * @version 1.0, 11.10.2003 
  */
public class PosOptions extends Options {

  public void init() {

    getLabelValue().clear();
    getLabelValue().add(new LabelValueBean(getMessage("event.recurrence.first"), "1"));
    getLabelValue().add(new LabelValueBean(getMessage("event.recurrence.second"), "2"));
    getLabelValue().add(new LabelValueBean(getMessage("event.recurrence.third"), "3"));
    getLabelValue().add(new LabelValueBean(getMessage("event.recurrence.fourth"), "4"));
    getLabelValue().add(new LabelValueBean(getMessage("event.recurrence.last"), "5"));
  
  }

}
