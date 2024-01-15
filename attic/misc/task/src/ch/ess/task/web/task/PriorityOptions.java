package ch.ess.task.web.task;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Transaction;

import org.apache.struts.util.LabelValueBean;

import ch.ess.common.db.HibernateSession;
import ch.ess.common.web.Options;
import ch.ess.task.db.Priority;

/** 
  * @author  Ralph Schaer
  * @version 1.0, 14.09.2003 
  */
public class PriorityOptions extends Options {


  public void init() throws HibernateException {
    
    Transaction tx = null;
    try {
      tx = HibernateSession.currentSession().beginTransaction();
    
      Map sortedMap = new TreeMap();
  
      List resultList = HibernateSession.currentSession().find("from Priority as p");
      for (Iterator it = resultList.iterator(); it.hasNext();) {
        Priority p = (Priority)it.next();
        sortedMap.put(p.getTranslations().get(getLocale()), p);
      }
  
      Iterator it = sortedMap.entrySet().iterator();
      while (it.hasNext()) {
        Map.Entry entry = (Map.Entry)it.next();
        getLabelValue().add(new LabelValueBean((String)entry.getKey(), ((Priority)entry.getValue()).getId().toString()));
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
