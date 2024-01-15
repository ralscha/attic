package ch.ess.addressbook.web;

import java.util.Iterator;
import java.util.List;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Transaction;

import org.apache.struts.util.LabelValueBean;

import ch.ess.addressbook.db.Role;
import ch.ess.common.db.HibernateSession;
import ch.ess.common.web.Options;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.5 $ $Date: 2003/11/11 19:02:19 $ 
  */
public class RoleOptions extends Options {

  public void init() throws HibernateException {

    Transaction tx = null;
    try {
      tx = HibernateSession.currentSession().beginTransaction();

      List resultList = HibernateSession.currentSession().find("from Role as r order by r.name asc");
      for (Iterator it = resultList.iterator(); it.hasNext();) {
        Role r = (Role)it.next();
        getLabelValue().add(new LabelValueBean(r.getName(), r.getId().toString()));
      }
      tx.commit();
    } catch (HibernateException e) {
      HibernateSession.rollback(tx);
      throw e;
    } finally {
      HibernateSession.closeSession();
    }
  }

}
