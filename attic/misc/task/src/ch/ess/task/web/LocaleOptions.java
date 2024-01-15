package ch.ess.task.web;

import org.apache.struts.util.LabelValueBean;

import ch.ess.common.web.Options;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.2 $ $Date: 2004/04/05 06:28:58 $ 
  */
public class LocaleOptions extends Options {

  public void init() {

    getLabelValue().add(new LabelValueBean(getMessage("user.language.german"), "de"));
    getLabelValue().add(new LabelValueBean(getMessage("user.language.english"), "en"));

  }

}
