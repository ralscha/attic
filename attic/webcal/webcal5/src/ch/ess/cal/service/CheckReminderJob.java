package ch.ess.cal.service;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.logging.LogFactory;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import ch.ess.base.Constants;
import ch.ess.cal.CalUtil;
import ch.ess.cal.dao.EventDao;
import ch.ess.cal.dao.TaskDao;
import ch.ess.cal.model.Event;
import ch.ess.cal.model.Recurrence;
import ch.ess.cal.model.Reminder;

public class CheckReminderJob extends QuartzJobBean {

  private EventDao eventDao;
  private TaskDao taskDao;
  private ReminderMailer reminderMailer;
  private SessionFactory sessionFactory;

  public void setEventDao(final EventDao eventDao) {
    this.eventDao = eventDao;
  }

  public void setTaskDao(final TaskDao taskDao) {
    this.taskDao = taskDao;
  }
  
  public void setReminderMailer(final ReminderMailer reminderMailer) {
    this.reminderMailer = reminderMailer;
  }

  public void setSessionFactory(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  private void fillJobDataMap(JobDataMap dataMap, Integer reminderId) {
    dataMap.put("id", reminderId);
    dataMap.put("reminderMailer", reminderMailer);

  }

  @Override
  public void executeInternal(JobExecutionContext context) {

    Session session = SessionFactoryUtils.getSession(sessionFactory, true);
    session.setFlushMode(FlushMode.MANUAL);

    TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));

    try {

      TimeZone serverTimeZone = TimeZone.getDefault();

      Calendar now = new GregorianCalendar(Constants.UTC_TZ);
      Calendar nowPlus15Min = new GregorianCalendar(Constants.UTC_TZ);

      nowPlus15Min.add(Calendar.MINUTE, 15);

      //Events
      List<Event> events = eventDao.getEventsWithReminders(now.getTimeInMillis());

      for (Event ev : events) {
        if (ev.getRecurrences().isEmpty()) {
          Calendar start = CalUtil.utcLong2UserCalendar(ev.getStartDate(), Constants.UTC_TZ);

          for (Reminder reminder : ev.getReminders()) {

            Calendar tmp = (Calendar)start.clone();
            tmp.add(Calendar.MINUTE, -reminder.getMinutesBefore());

            if (tmp.getTimeInMillis() >= now.getTimeInMillis()
                && tmp.getTimeInMillis() <= nowPlus15Min.getTimeInMillis()) {
              tmp.setTimeZone(serverTimeZone);

              JobDetail jobDetail = new JobDetail(reminder.getId().toString(), "REMINDER", ReminderJob.class);
              fillJobDataMap(jobDetail.getJobDataMap(), reminder.getId());

              Trigger trigger = new SimpleTrigger(reminder.getId().toString(), "REMINDER", tmp.getTime(), null, 0, 0L);
              trigger.setDescription(ev.getSubject());
              try {
                context.getScheduler().deleteJob(reminder.getId().toString(), "REMINDER");
                context.getScheduler().scheduleJob(jobDetail, trigger);
              } catch (SchedulerException e) {
                LogFactory.getLog(getClass()).error("scheduler error", e);
              }

            }

          }
        } else {
          Recurrence recur = ev.getRecurrences().iterator().next();
          for (Reminder reminder : ev.getReminders()) {

            Calendar nowFuture = (Calendar)now.clone();
            nowFuture.add(Calendar.MINUTE, reminder.getMinutesBefore());

            Calendar nowPlusOneDayFuture = (Calendar)nowFuture.clone();
            nowPlusOneDayFuture.add(Calendar.DATE, 1);

            List<Calendar> dates = EventUtil.getDaysBetween(recur, nowFuture, nowPlusOneDayFuture, serverTimeZone);

            if (!dates.isEmpty()) {
              for (Calendar startRecur : dates) {

                Calendar tmp = (Calendar)startRecur.clone();
                tmp.add(Calendar.MINUTE, -reminder.getMinutesBefore());

                if (tmp.getTimeInMillis() >= now.getTimeInMillis()
                    && tmp.getTimeInMillis() <= nowPlus15Min.getTimeInMillis()) {

                  JobDetail jobDetail = new JobDetail(reminder.getId().toString(), "REMINDER", ReminderJob.class);
                  fillJobDataMap(jobDetail.getJobDataMap(), reminder.getId());

                  Trigger trigger = new SimpleTrigger(reminder.getId().toString(), "REMINDER", tmp.getTime(), null, 0,
                      0L);
                  trigger.setDescription(ev.getSubject());
                  try {
                    context.getScheduler().deleteJob(reminder.getId().toString(), "REMINDER");
                    context.getScheduler().scheduleJob(jobDetail, trigger);
                  } catch (SchedulerException e) {
                    LogFactory.getLog(getClass()).error("scheduler error", e);
                  }

                }

              }
            }

          }

        }

      }
      
      
      List<Reminder> taskReminders = taskDao.findRemindersBetween(now.getTimeInMillis(), nowPlus15Min.getTimeInMillis());
      for (Reminder reminder : taskReminders) {
        
        Calendar tmp = CalUtil.utcLong2UserCalendar(reminder.getReminderDate(), serverTimeZone);

        JobDetail jobDetail = new JobDetail(reminder.getId().toString(), "REMINDER", ReminderJob.class);
        fillJobDataMap(jobDetail.getJobDataMap(), reminder.getId());

        Trigger trigger = new SimpleTrigger(reminder.getId().toString(), "REMINDER", tmp.getTime(), null, 0, 0L);
        trigger.setDescription(reminder.getEvent().getSubject());
        try {
          context.getScheduler().deleteJob(reminder.getId().toString(), "REMINDER");
          context.getScheduler().scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
          LogFactory.getLog(getClass()).error("scheduler error", e);
        }

        
      }

    } finally {
      TransactionSynchronizationManager.unbindResource(sessionFactory);
      SessionFactoryUtils.releaseSession(session, sessionFactory);

    }
  }

}
