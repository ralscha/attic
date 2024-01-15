package ch.ess.timetracker.web;

import java.util.Iterator;
import java.util.List;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Transaction;

import org.apache.struts.util.LabelValueBean;

import ch.ess.common.db.HibernateSession;
import ch.ess.common.web.Options;
import ch.ess.timetracker.db.User;

/**
 * @author Ralph Schaer
 * @version $Revision: 1.1 $ $Date: 2004/01/05 07:58:19 $
 */
public class UserOptions extends Options {

  public void init() throws HibernateException {

    Transaction tx = null;
    try {
      tx = HibernateSession.currentSession().beginTransaction();

      List resultList = HibernateSession.currentSession().find("from User");

      for (Iterator it = resultList.iterator(); it.hasNext(); ) {
        User u = (User) it.next();
        getLabelValue().add(
            new LabelValueBean(u.getName() + " " + u.getFirstName(), u.getId().toString()));
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
