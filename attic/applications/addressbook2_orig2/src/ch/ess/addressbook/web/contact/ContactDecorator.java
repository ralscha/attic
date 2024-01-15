package ch.ess.addressbook.web.contact;

import java.net.MalformedURLException;

import net.sf.hibernate.HibernateException;
import ch.ess.common.web.ListDecorator;


public class ContactDecorator extends ListDecorator {

  public String getEdit() throws MalformedURLException {
    return getEditString("Contact");
  }

  public String getDelete() throws MalformedURLException, HibernateException {        
    return getDeleteString("Contact", "lastName");
  }

}


