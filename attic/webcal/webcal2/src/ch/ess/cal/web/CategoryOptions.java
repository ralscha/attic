package ch.ess.cal.web;

import java.util.Iterator;
import java.util.List;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Transaction;

import org.apache.struts.util.LabelValueBean;

import ch.ess.cal.db.Category;
import ch.ess.common.db.HibernateSession;
import ch.ess.common.web.Options;

/** 
  * @author  Ralph Schaer
  * @version 1.0, 04.10.2003 
  */
public class CategoryOptions extends Options {

  public void init() throws HibernateException {

    Transaction tx = null;
    try {
      tx = HibernateSession.currentSession().beginTransaction();

      List resultList = HibernateSession.currentSession().find("from Category");
      for (Iterator it = resultList.iterator(); it.hasNext();) {
        Category cat = (Category)it.next();
        getLabelValue().add(new LabelValueBean((String)cat.getTranslations().get(getLocale()), cat.getId().toString()));
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
