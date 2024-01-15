package ch.ess.cal.service.event.impl;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;

public class ReminderJob implements Job {

  public void execute(JobExecutionContext context) {

    JobDataMap dataMap = context.getJobDetail().getJobDataMap();
    Integer reminderId = (Integer)dataMap.get("id");
    ReminderMailer reminderMailer = (ReminderMailer)dataMap.get("reminderMailer");

    reminderMailer.send(reminderId);

  }

}
