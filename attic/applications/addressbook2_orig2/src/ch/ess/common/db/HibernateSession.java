package ch.ess.common.db;

import java.util.Collection;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.5 $ $Date: 2003/11/11 19:07:56 $ 
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

  public static Session getSession() throws HibernateException {
    return HibernateFactoryManager.getSessionFactory().openSession();
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

  //Utility methods
  public static int collectionSize(Collection coll) throws HibernateException {
    return ((Integer)currentSession().createFilter(coll, "select count(*)").iterate().next()).intValue();  
  }

}