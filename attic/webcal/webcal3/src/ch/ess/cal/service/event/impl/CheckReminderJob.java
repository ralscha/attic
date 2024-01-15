package ch.ess.cal.service.event.impl;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.logging.LogFactory;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.springframework.scheduling.quartz.QuartzJobBean;

import ch.ess.cal.Constants;
import ch.ess.cal.Util;
import ch.ess.cal.model.Event;
import ch.ess.cal.model.Recurrence;
import ch.ess.cal.model.Reminder;
import ch.ess.cal.persistence.EventDao;

/** 
 * @author sr
 * @version $Revision: 1.3 $ $Date: 2005/04/04 11:31:10 $ 
 */
public class CheckReminderJob extends QuartzJobBean {

  private EventDao eventDao;
  private ReminderMailer reminderMailer;

  public void setEventDao(final EventDao eventDao) {
    this.eventDao = eventDao;
  }

  public void setReminderMailer(final ReminderMailer reminderMailer) {
    this.reminderMailer = reminderMailer;
  }

  private void fillJobDataMap(JobDataMap dataMap, Integer reminderId) {
    dataMap.put("id", reminderId);
    dataMap.put("reminderMailer", reminderMailer);

  }

  @Override
  public void executeInternal(JobExecutionContext context) {

    TimeZone serverTimeZone = TimeZone.getDefault();

    Calendar now = new GregorianCalendar(Constants.UTC_TZ);
    Calendar nowPlus15Min = new GregorianCalendar(Constants.UTC_TZ);

    nowPlus15Min.add(Calendar.MINUTE, 15);

    List<Event> events = eventDao.getEventsWithReminders(now.getTimeInMillis());

    for (Event ev : events) {
      if (ev.getRecurrences().isEmpty()) {
        Calendar start = Util.utcLong2UserCalendar(ev.getStartDate(), Constants.UTC_TZ);

        for (Reminder reminder : ev.getReminders()) {

          Calendar tmp = (Calendar)start.clone();
          tmp.add(Calendar.MINUTE, -reminder.getMinutesBefore());

          if (tmp.getTimeInMillis() >= now.getTimeInMillis() && tmp.getTimeInMillis() <= nowPlus15Min.getTimeInMillis()) {
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

          List<Calendar> dates = EventUtil.getDaysBetween(recur.getRfcRule(), recur, nowFuture, nowPlusOneDayFuture,
              serverTimeZone);

          if (!dates.isEmpty()) {
            for (Calendar startRecur : dates) {

              if (!ev.isAllDay()) {
                Calendar startTime = Util.utcLong2UserCalendar(recur.getStartTime().longValue(), serverTimeZone);
                startRecur.set(Calendar.HOUR_OF_DAY, startTime.get(Calendar.HOUR_OF_DAY));
                startRecur.set(Calendar.MINUTE, startTime.get(Calendar.MINUTE));
              }

              Calendar tmp = (Calendar)startRecur.clone();
              tmp.add(Calendar.MINUTE, -reminder.getMinutesBefore());

              if (tmp.getTimeInMillis() >= now.getTimeInMillis()
                  && tmp.getTimeInMillis() <= nowPlus15Min.getTimeInMillis()) {
                tmp.setTimeZone(TimeZone.getDefault());

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
          }

        }

      }

    }

  }

}
