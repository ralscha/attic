package ch.ess.common.db;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/** 
  * @author  Ralph Schaer
  * @version 1.0, 13.07.2003 
  */
public class HibernateSession {

  private static final Log LOG = LogFactory.getLog(HibernateSession.class);
  public static final ThreadLocal SESSION = new ThreadLocal();

  public static Session currentSession() throws HibernateException {

    Session s = (Session)SESSION.get();
    if (s == null) {
      s = HibernateFactoryManager.getSessionFactory().openSession();
      SESSION.set(s);
    }
    return s;
  }

  public static void closeSession() {
    try {
      Session s = (Session)SESSION.get();
      SESSION.set(null);
      if (s != null) {
        s.close();
      }

    } catch (HibernateException e) {
      LOG.error("HibernateSession:  closeSession", e);
    }

  }

  public static void rollback(Transaction tx) {
    if (tx != null) {
      try {
        tx.rollback();
        closeSession();
      } catch (HibernateException he) {
        LOG.error("HibernateSession: rollback", he);
      }
    }
  }

}