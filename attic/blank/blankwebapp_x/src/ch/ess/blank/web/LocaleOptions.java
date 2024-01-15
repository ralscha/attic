package ch.ess.blank.web;

import org.apache.struts.util.LabelValueBean;

import ch.ess.common.web.Options;

/**
 * @author Ralph Schaer
 * @version $Revision: 1.4 $ $Date: 2004/05/22 15:38:03 $
 */
public class LocaleOptions extends Options {

  public void init() {

    add(new LabelValueBean(translate("user.language.german"), "de"));
    add(new LabelValueBean(translate("user.language.english"), "en"));

  }

}