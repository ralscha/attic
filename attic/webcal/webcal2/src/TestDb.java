import java.util.Iterator;
import java.util.List;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import ch.ess.cal.Constants;
import ch.ess.cal.db.Event;
import ch.ess.cal.db.Recurrence;
import ch.ess.cal.db.Reminder;
import ch.ess.cal.event.EventUtil;
import ch.ess.common.db.HibernateFactoryManager;
import ch.ess.common.db.HibernateSession;
import ch.ess.common.util.Util;

import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.GregorianCalendar;
import com.ibm.icu.util.TimeZone;


public class TestDb {
  
  public static void main(String[] args) {
    Transaction tx = null;

    
    try {
      
      HibernateFactoryManager.initXML("/hibernatelocal.cfg.xml");

      Session session = HibernateSession.currentSession();
      tx = session.beginTransaction();
      
      

      
      
      /*
      String rule = "FREQ=MONTHLY;INTERVAL=1;BYMONTHDAY=1;WKST=MO;UNTIL=20041001";
      Calendar s = Util.newCalendar(Constants.UTC_TZ, 2003, Calendar.OCTOBER, 1, 23, 0, 0, 0);
      Calendar e = Util.newCalendar(Constants.UTC_TZ, 2003, Calendar.OCTOBER, 31, 22, 0, 0, 0);
      Recurrance r = new Recurrance(rule, s, e);
      List dates = r.getAllMatchingDates();
      */
System.out.println(TimeZone.getDefault());

/*
Iterator io = Event.find();
while (io.hasNext()) {
  Event element = (Event)io.next();
  if (!element.getRecurrences().isEmpty()) {
    Recurrence recur = (Recurrence)element.getRecurrences().iterator().next();
    Calendar start = Util.utcLong2UserCalendar(recur.getPatternStartDate().longValue(), Constants.UTC_TZ);
    System.out.println(Constants.DATE_UTC_FORMAT.format(start));
  }
}
System.exit(0);
*/



      Calendar now = new GregorianCalendar(Constants.UTC_TZ);
      Calendar nowPlus60 = new GregorianCalendar(Constants.UTC_TZ);
      //Calendar now = Util.newCalendar(Constants.UTC_TZ, 2003, Calendar.NOVEMBER, 16, 10,9,0,0);
      //Calendar nowPlus60 = Util.newCalendar(Constants.UTC_TZ, 2003, Calendar.NOVEMBER, 16, 10,9,0,0);
      
      nowPlus60.add(Calendar.MINUTE, 60);
      
      System.out.println(Constants.DATE_UTC_FORMAT.format(now));
      
      List events = Event.getEventsWithReminders(now.getTimeInMillis());
      for (Iterator it = events.iterator(); it.hasNext();) {
        Event ev = (Event)it.next();
        if (ev.getRecurrences().isEmpty()) {
          Calendar start = Util.utcLong2UserCalendar(ev.getStartDate(), Constants.UTC_TZ);
          
          for (Iterator it2 = ev.getReminders().iterator(); it2.hasNext();) {
            Reminder re = (Reminder)it2.next();
            
            Calendar tmp = (Calendar)start.clone();
            tmp.add(Calendar.MINUTE, -re.getMinutesBefore());
            
            if (tmp.getTimeInMillis() >= now.getTimeInMillis() &&
                tmp.getTimeInMillis() <= nowPlus60.getTimeInMillis()) {
              tmp.setTimeZone(TimeZone.getDefault());
              System.out.println(Util.userCalendar2String(tmp, "dd.MM.yyyy HH:mm:ss"));
              System.out.println("send email to: " + re.getEmail());
              System.out.println(ev.getId());
            }
            
            
          }
        } else {
          Recurrence recur = (Recurrence)ev.getRecurrences().iterator().next();
          
          
          
          for (Iterator it2 = ev.getReminders().iterator(); it2.hasNext();) {
            Reminder re = (Reminder)it2.next();
            
            Calendar nowFuture = (Calendar)now.clone();
            nowFuture.add(Calendar.MINUTE, re.getMinutesBefore());
                
            Calendar nowPlusOneDayFuture = (Calendar)nowFuture.clone();
            nowPlusOneDayFuture.add(Calendar.DATE, 1);
                
            List dates = EventUtil.getDaysBetween(recur.getRfcRule(), recur, nowFuture, nowPlusOneDayFuture);
                
            if (!dates.isEmpty()) {
              for (Iterator it3 = dates.iterator(); it3.hasNext();) {
                Calendar startRecur = (Calendar)it3.next();
                
                 
                if (!ev.isAllDay()) {
                  Calendar startTime = Util.utcLong2UserCalendar(recur.getStartTime().longValue(), ev.getTimeZoneObj());
                  startRecur.set(Calendar.HOUR_OF_DAY, startTime.get(Calendar.HOUR_OF_DAY));
                  startRecur.set(Calendar.MINUTE, startTime.get(Calendar.MINUTE));
                }
                
                Calendar tmp = (Calendar)startRecur.clone();
                tmp.add(Calendar.MINUTE, -re.getMinutesBefore());
            
                if (tmp.getTimeInMillis() >= now.getTimeInMillis() &&
                    tmp.getTimeInMillis() <= nowPlus60.getTimeInMillis()) {
                  tmp.setTimeZone(TimeZone.getDefault());
                  System.out.println(Util.userCalendar2String(tmp, "dd.MM.yyyy HH:mm:ss"));
                  System.out.println("send email to: " + re.getEmail());
                  System.out.println(ev.getId());
                }
                            
                                
             }
            }
            
          }
          

          
          
        }
        
        
      }
      
      
      /*
      Criteria crit = session.createCriteria(Event.class);
           
      Calendar start = new GregorianCalendar(2003, Calendar.OCTOBER, 7, 0, 0, 0);
      Calendar end = new GregorianCalendar(2003, Calendar.OCTOBER, 7, 23, 59, 59);    
      Long startMillis = new Long(start.getTimeInMillis());
      Long endMillis = new Long(end.getTimeInMillis());
      
     
      crit.add(Expression.or(
        Expression.and(Expression.ge("startDate", startMillis), Expression.le("startDate", endMillis)),
        Expression.and(Expression.le("startDate", startMillis), Expression.ge("endDate", startMillis))));
       

     
      EventDistribution ed = new EventDistribution(start);       
        
      List l = crit.list();
      for (Iterator it = l.iterator(); it.hasNext();) {
        Event e = (Event)it.next();
        System.out.println(Util.userCalendar2String(Util.utcLong2UserCalendar(e.getStartDate(), TimeZone.getDefault()), "dd.MM.yyyy HH:mm"));
        System.out.println(Util.userCalendar2String(Util.utcLong2UserCalendar(e.getEndDate(), TimeZone.getDefault()), "dd.MM.yyyy HH:mm"));        
        System.out.println("***********");        
        ed.addEvent(e);
      }
            
      ed.compact();
    
      List le = ed.getEvents();
      for (Iterator it = le.iterator(); it.hasNext();) {
        EventDistributionItem edi = (EventDistributionItem)it.next();
        System.out.println(edi);
      
      }

      */
      

      tx.commit();

    } catch (HibernateException e) {
      HibernateSession.rollback(tx);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      HibernateSession.closeSession();
      HibernateFactoryManager.destroy();  
    }
  }

}
