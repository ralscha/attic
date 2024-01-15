package ch.ess.common.web;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Transaction;

import org.apache.struts.action.ActionForward;

import ch.ess.common.db.HibernateSession;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.4 $ $Date: 2003/11/11 19:17:17 $ 
  */
public abstract class HibernateAction extends BaseAction {

  public ActionForward execute() throws Exception {

    ActionForward forward = null;
    Transaction tx = null;
    try {
      tx = HibernateSession.currentSession().beginTransaction();

      forward = doAction();

      tx.commit();
    } catch (HibernateException e) {
      HibernateSession.rollback(tx);
      throw e;
    } finally {
      HibernateSession.closeSession();
    }

    return forward;
  }

  public abstract ActionForward doAction() throws Exception;

}
