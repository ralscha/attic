package ch.ess.cal.event;

import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;

import org.apache.struts.util.MessageResources;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.Scheduler;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import ch.ess.cal.Constants;
import ch.ess.cal.db.Event;
import ch.ess.cal.db.Recurrence;
import ch.ess.cal.db.Reminder;
import ch.ess.common.db.HibernateSession;
import ch.ess.common.util.Util;

import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.GregorianCalendar;
import com.ibm.icu.util.TimeZone;

public class CheckReminderJob implements Job {

  public void execute(JobExecutionContext context) {
        
    JobDataMap dataMap = context.getJobDetail().getJobDataMap();
    MessageResources resources = (MessageResources)dataMap.get("resources");
    ServletContext servletContext = (ServletContext)dataMap.get("servletContext");

    
    Transaction tx = null;
    Session session = null;
    try {

      Scheduler sched = StdSchedulerFactory.getDefaultScheduler();
      
      session = HibernateSession.getSession();
      tx = session.beginTransaction();

      Calendar now = new GregorianCalendar(Constants.UTC_TZ);
      Calendar nowPlus60 = new GregorianCalendar(Constants.UTC_TZ);

      nowPlus60.add(Calendar.MINUTE, 15);

      List events = Event.getEventsWithReminders(now.getTimeInMillis());
      for (Iterator it = events.iterator(); it.hasNext();) {
        Event ev = (Event)it.next();
        if (ev.getRecurrences().isEmpty()) {
          Calendar start = Util.utcLong2UserCalendar(ev.getStartDate(), Constants.UTC_TZ);

          for (Iterator it2 = ev.getReminders().iterator(); it2.hasNext();) {
            Reminder re = (Reminder)it2.next();

            Calendar tmp = (Calendar)start.clone();
            tmp.add(Calendar.MINUTE, -re.getMinutesBefore());

            if (tmp.getTimeInMillis() >= now.getTimeInMillis()
              && tmp.getTimeInMillis() <= nowPlus60.getTimeInMillis()) {
              tmp.setTimeZone(TimeZone.getDefault());
              System.out.println(Util.userCalendar2String(tmp, "dd.MM.yyyy HH:mm:ss"));
              System.out.println("send email to: " + re.getEmail());
              System.out.println(ev.getId());
              
              
              JobDetail jobDetail = new JobDetail(re.getId().toString(), "REMINDER", ReminderJob.class);      
              jobDetail.getJobDataMap().put("resources", resources);
              jobDetail.getJobDataMap().put("servletContext", servletContext);
              jobDetail.getJobDataMap().put("id", re.getId());
              
              Trigger trigger = new SimpleTrigger(re.getId().toString(),
                                          "REMINDER",
                                          tmp.getTime(),
                                          null,
                                          0,
                                          0L);
              sched.deleteJob(re.getId().toString(), "REMINDER");
              sched.scheduleJob(jobDetail, trigger);              
              
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
                  Calendar startTime =
                    Util.utcLong2UserCalendar(recur.getStartTime().longValue(), ev.getTimeZoneObj());
                  startRecur.set(Calendar.HOUR_OF_DAY, startTime.get(Calendar.HOUR_OF_DAY));
                  startRecur.set(Calendar.MINUTE, startTime.get(Calendar.MINUTE));
                }

                Calendar tmp = (Calendar)startRecur.clone();
                tmp.add(Calendar.MINUTE, -re.getMinutesBefore());

                if (tmp.getTimeInMillis() >= now.getTimeInMillis()
                  && tmp.getTimeInMillis() <= nowPlus60.getTimeInMillis()) {
                  tmp.setTimeZone(TimeZone.getDefault());
                  System.out.println(Util.userCalendar2String(tmp, "dd.MM.yyyy HH:mm:ss"));
                  System.out.println("send email to: " + re.getEmail());
                  System.out.println(ev.getId());
                  
                  JobDetail jobDetail = new JobDetail(re.getId().toString(), "REMINDER", ReminderJob.class);      
                  jobDetail.getJobDataMap().put("resources", resources);
                  jobDetail.getJobDataMap().put("id", re.getId());

                  Trigger trigger = new SimpleTrigger(re.getId().toString(),
                                               "REMINDER",
                                               tmp.getTime(),
                                               null,
                                               0,
                                               0L);
                  sched.deleteJob(re.getId().toString(), "REMINDER");
                  sched.scheduleJob(jobDetail, trigger);                     
                  
                }

              }
            }

          }

        }
        
      }
      tx.commit();

    } catch (HibernateException e) {
      HibernateSession.rollback(tx);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (session != null) {
          session.close();
        }
      } catch (HibernateException e1) {
        e1.printStackTrace();
      }      
    }

  }

}
