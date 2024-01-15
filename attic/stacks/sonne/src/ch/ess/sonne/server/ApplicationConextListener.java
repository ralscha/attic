package ch.ess.sonne.server;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;

public class ApplicationConextListener implements ServletContextListener {

  public static String IMAGE_DIRECTORY;
  
  public void contextInitialized(ServletContextEvent servletContextEvent) {
    
    IMAGE_DIRECTORY = servletContextEvent.getServletContext().getInitParameter("imageDirectory");
    System.out.println(IMAGE_DIRECTORY);
    
    try {
      Scheduler sched = StdSchedulerFactory.getDefaultScheduler();
            
      JobDetail jobDetail = new JobDetail("FetchImageJob", Scheduler.DEFAULT_GROUP,  FetchImageJob.class);
      

      SimpleTrigger trigger = new SimpleTrigger("FetchImageJobTrigger",
          Scheduler.DEFAULT_GROUP, SimpleTrigger.REPEAT_INDEFINITELY, 30 * 60 * 1000L);

      sched.scheduleJob(jobDetail, trigger);
      
      sched.start();
      
    } catch (SchedulerException e) {
      
      e.printStackTrace();
    }


  }

  
  public void contextDestroyed(@SuppressWarnings("unused")
  ServletContextEvent servletContextEvent) {
    try {
      Scheduler sched = StdSchedulerFactory.getDefaultScheduler();

      if (sched != null) {
        sched.shutdown(true);
      }
    } catch (SchedulerException e) {
      e.printStackTrace();
    }

  }
}
