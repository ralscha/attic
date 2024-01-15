package ch.ess.cal.web;

import java.util.Iterator;
import java.util.Set;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Transaction;

import org.apache.struts.util.LabelValueBean;

import ch.ess.cal.db.Department;
import ch.ess.cal.db.User;
import ch.ess.common.db.HibernateSession;
import ch.ess.common.web.Options;

/** 
  * @author  Ralph Schaer
  * @version 1.0, 08.10.2003
  */
public class DepartmentOptions extends Options {

  public void init() throws HibernateException {


    Transaction tx = null;
    try {
      tx = HibernateSession.currentSession().beginTransaction();

      User user = User.find(getRequest().getUserPrincipal().getName());
      
      Set departmentSet = user.getDepartments();
      
      for (Iterator it = departmentSet.iterator(); it.hasNext();) {
        Department d = (Department)it.next();
        getLabelValue().add(new LabelValueBean((String)d.getTranslations().get(getLocale()), d.getId().toString()));
      }
      sort();
      tx.commit();
    } catch (HibernateException e) {
      HibernateSession.rollback(tx);
      throw e;
    } finally {
      HibernateSession.closeSession();
    }
  }

}
