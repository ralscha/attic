package ch.ess.addressbook.web;

import java.util.Locale;

import net.sf.hibernate.HibernateException;

import org.apache.struts.util.LabelValueBean;
import org.apache.struts.util.MessageResources;

import ch.ess.common.web.Options;

/** 
  * @author  Ralph Schaer
  * @version 1.0, 13.07.2003 
  */
public class LocaleOptions extends Options {

  public LocaleOptions(Locale locale, MessageResources messages) throws HibernateException {
    super(locale, messages);
  }

  public void init() throws HibernateException {

    getLabelValue().add(new LabelValueBean(getMessage("language.german"), "de"));
    getLabelValue().add(new LabelValueBean(getMessage("language.english"), "en"));

  }

}
