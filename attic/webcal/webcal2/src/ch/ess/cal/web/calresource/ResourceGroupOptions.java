package ch.ess.cal.web.calresource;

import java.util.Iterator;
import java.util.List;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Transaction;

import org.apache.struts.util.LabelValueBean;

import ch.ess.cal.db.ResourceGroup;
import ch.ess.common.db.HibernateSession;
import ch.ess.common.web.Options;

/** 
  * @author  Ralph Schaer
  * @version 1.1, 14.09.2003 
  */
public class ResourceGroupOptions extends Options {

  public void init() throws HibernateException {

    Transaction tx = null;
    try {
      tx = HibernateSession.currentSession().beginTransaction();

      List resultList = HibernateSession.currentSession().find("from ResourceGroup");
      for (Iterator it = resultList.iterator(); it.hasNext();) {
        ResourceGroup r = (ResourceGroup)it.next();
        getLabelValue().add(new LabelValueBean((String)r.getTranslations().get(getLocale()), r.getId().toString()));
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
