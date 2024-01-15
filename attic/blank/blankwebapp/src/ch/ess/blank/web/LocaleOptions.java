package ch.ess.blank.web;

import org.apache.struts.util.LabelValueBean;

import ch.ess.common.web.Options;

/**
 * @author Ralph Schaer
 * @version $Revision: 1.7 $ $Date: 2004/05/22 12:24:29 $
 */
public class LocaleOptions extends Options {

  public void init() {

    getLabelValue().add(new LabelValueBean(getMessage("user.language.german"), "de"));
    getLabelValue().add(new LabelValueBean(getMessage("user.language.english"), "en"));

  }

}