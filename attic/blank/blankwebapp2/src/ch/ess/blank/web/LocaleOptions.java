package ch.ess.blank.web;

import org.apache.struts.util.LabelValueBean;

import ch.ess.common.web.Options;

/**
 * @author Ralph Schaer
 * @version $Revision: 1.2 $ $Date: 2004/05/22 16:51:13 $
 */
public class LocaleOptions extends Options {

  public void init() {

    getLabelValue().add(new LabelValueBean(getMessage("user.language.german"), "de"));
    getLabelValue().add(new LabelValueBean(getMessage("user.language.english"), "en"));

  }

}