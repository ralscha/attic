package ch.ess.cal.web.holiday;

import java.util.Iterator;
import java.util.List;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Transaction;

import org.apache.struts.util.LabelValueBean;

import ch.ess.common.db.HibernateSession;
import ch.ess.common.web.Options;

/** 
  * @author  Ralph Schaer
  * @version 1.0, 28.09.2003 
  */
public class CountryOptions extends Options {

  public void init() throws HibernateException {

    Transaction tx = null;
    try {
      tx = HibernateSession.currentSession().beginTransaction();

      List resultList = HibernateSession.currentSession().find("select distinct hol.country from Holiday as hol where hol.country is not null order by hol.country asc");
      for (Iterator it = resultList.iterator(); it.hasNext();) {
        String country = (String)it.next();
        getLabelValue().add(new LabelValueBean(country, country));
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
