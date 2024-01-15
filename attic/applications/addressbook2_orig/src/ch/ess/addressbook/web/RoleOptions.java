package ch.ess.addressbook.web;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import net.sf.hibernate.HibernateException;

import org.apache.struts.util.LabelValueBean;
import org.apache.struts.util.MessageResources;

import ch.ess.addressbook.db.Role;
import ch.ess.common.db.HibernateSession;
import ch.ess.common.web.Options;

/** 
  * @author  Ralph Schaer
  * @version 1.0, 13.07.2003 
  */
public class RoleOptions extends Options {

  public RoleOptions(Locale locale, MessageResources messages) throws HibernateException {
    super(locale, messages);
  }

  public void init() throws HibernateException {
    List resultList = HibernateSession.currentSession().find("from Role as r order by r.name asc");
    for (Iterator it = resultList.iterator(); it.hasNext();) {
      Role r = (Role)it.next();
      getLabelValue().add(new LabelValueBean(r.getName(), r.getId().toString()));
    }

  }

}
