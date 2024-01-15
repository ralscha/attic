package ch.ess.timetracker.web;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Transaction;

import org.apache.struts.util.LabelValueBean;

import ch.ess.common.db.HibernateSession;
import ch.ess.common.web.Options;

public class YearOptions extends Options {

  public void init() throws HibernateException {
    
    Transaction tx = null;
    try {
      tx = HibernateSession.currentSession().beginTransaction();

      Calendar cal = new GregorianCalendar();        
                
      int minYear = cal.get(Calendar.YEAR);
      int maxYear = cal.get(Calendar.YEAR);

      List resultList = HibernateSession.currentSession().find("select min(tt.taskTimeDate), max(tt.taskTimeDate) from TaskTime as tt");
      if (!resultList.isEmpty()) {
        Object[] res = (Object[])resultList.get(0);
        
        if (res[0] != null) {
          Date minDate = (Date)res[0];   
          Calendar c = new GregorianCalendar();
          c.setTime(minDate);
          minYear = c.get(Calendar.YEAR);
        }
        
        if (res[1] != null) {
          Date maxDate = (Date)res[1];
          Calendar c = new GregorianCalendar();
          c.setTime(maxDate);
          maxYear = c.get(Calendar.YEAR);
          
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
