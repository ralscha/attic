package ch.ess.addressbook.web;

import net.sf.hibernate.HibernateException;

import org.apache.struts.util.LabelValueBean;

import ch.ess.common.web.Options;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.5 $ $Date: 2003/11/11 19:02:19 $ 
  */
public class LocaleOptions extends Options {

  public void init() throws HibernateException {

    getLabelValue().add(new LabelValueBean(getMessage("user.language.german"), "de"));
    getLabelValue().add(new LabelValueBean(getMessage("user.language.english"), "en"));

  }

}
