package ch.ess.cal.web;

import java.util.List;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Transaction;

import org.apache.struts.util.LabelValueBean;

import ch.ess.cal.Constants;
import ch.ess.common.db.HibernateSession;
import ch.ess.common.web.Options;

import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.GregorianCalendar;

public class YearOptions extends Options {

  public void init() throws HibernateException {
    
    Transaction tx = null;
    try {
      tx = HibernateSession.currentSession().beginTransaction();

      Calendar cal = new GregorianCalendar(Constants.UTC_TZ);        
                
      int minYear = cal.get(Calendar.YEAR);
      int maxYear = cal.get(Calendar.YEAR);

      List resultList = HibernateSession.currentSession().find("select min(e.startDate), max(e.startDate), min(e.endDate), max(e.endDate) from Event as e");
      if (!resultList.isEmpty()) {
        Object[] res = (Object[])resultList.get(0);
        
        if (res[0] != null) {
          cal.setTimeInMillis(((Long)res[0]).longValue());
          minYear = cal.get(Calendar.YEAR);          
        }                
        if (res[2] != null) {
          cal.setTimeInMillis(((Long)res[2]).longValue());
          
          int y = cal.get(Calendar.YEAR);
          if (y < minYear) {
            minYear = y;
          }          
        }
        
        if (res[1] != null) {
          cal.setTimeInMillis(((Long)res[1]).longValue());
          maxYear = cal.get(Calendar.YEAR);
        }
        if (res[3] != null) {
          cal.setTimeInMillis(((Long)res[3]).longValue());
          int y = cal.get(Calendar.YEAR);
          if (y > maxYear) {
            maxYear = y;
          }
        }
        
      }
      
      getLabelValue().clear();
      for (int i = minYear; i <= maxYear; i++) {
        getLabelValue().add(new LabelValueBean(String.valueOf(i), String.valueOf(i)));
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
