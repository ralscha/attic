package ch.ess.cal.web;

import org.apache.struts.util.LabelValueBean;

import ch.ess.common.web.Options;

/** 
  * @author  Ralph Schaer
  * @version 1.0, 28.09.2003 
  */
public class YesNoOptions extends Options {

  public void init() {

    getLabelValue().clear();
    getLabelValue().add(new LabelValueBean(getMessage("common.yes"), Boolean.TRUE.toString()));
    getLabelValue().add(new LabelValueBean(getMessage("common.no"), Boolean.FALSE.toString()));
  
  }

}
