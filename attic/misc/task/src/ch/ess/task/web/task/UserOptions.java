package ch.ess.task.web.task;

import java.util.Iterator;
import java.util.List;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Transaction;

import org.apache.struts.util.LabelValueBean;

import ch.ess.common.db.HibernateSession;
import ch.ess.common.web.Options;

/** 
  * @author  Ralph Schaer
  * @version 1.0, 14.09.2003 
  */
public class UserOptions extends Options {

  public void init() throws HibernateException {
    Transaction tx = null;
    try {
      tx = HibernateSession.currentSession().beginTransaction();

      List resultList =
        HibernateSession.currentSession().find(
          "select u.id, u.name, u.firstName from User as u order by u.name, u.firstName");
      for (Iterator it = resultList.iterator(); it.hasNext();) {
        Object[] res = (Object[])it.next();
        getLabelValue().add(new LabelValueBean(res[1] + " " + res[2], res[0].toString()));
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
