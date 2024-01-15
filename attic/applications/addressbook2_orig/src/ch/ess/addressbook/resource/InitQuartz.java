package ch.ess.addressbook.resource;

import java.util.GregorianCalendar;

import javax.servlet.ServletException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;

import ch.ess.common.search.OptimizeJob;

/** 
  * @author  Ralph Schaer
  * @version 1.0, 13.07.2003 
  */

public class InitQuartz implements PlugIn {

  private static final Log LOG = LogFactory.getLog(InitQuartz.class);

  public void destroy() {

    try {
      Scheduler sched = StdSchedulerFactory.getDefaultScheduler();

      if (sched != null) {
        sched.shutdown();
      }

    } catch (Exception e) {
      LOG.error("Quartz Scheduler failed to shutdown cleanly: ", e);
    }

    LOG.debug("Quartz Scheduler successful shutdown.");

  }

  public void init(ActionServlet servlet, ModuleConfig config) throws ServletException {

    LOG.debug("Quartz Initializer Servlet loaded, initializing Scheduler...");

    try {

      Scheduler sched = StdSchedulerFactory.getDefaultScheduler();
      sched.start();

      JobDetail jobDetail = new JobDetail("optimize", Scheduler.DEFAULT_GROUP, OptimizeJob.class);

      java.util.Calendar cal = new GregorianCalendar();
      cal.set(java.util.Calendar.HOUR_OF_DAY, 23);
      cal.set(java.util.Calendar.MINUTE, 30);
      cal.set(java.util.Calendar.SECOND, 0);
      cal.set(java.util.Calendar.MILLISECOND, 0);

      java.util.Date startTime = cal.getTime();

      SimpleTrigger trigger =
        new SimpleTrigger(
          "dailyTrigger",
          Scheduler.DEFAULT_GROUP,
          startTime,
          null,
          SimpleTrigger.REPEAT_INDEFINITELY,
          24 * 60 * 60 * 1000L);

      sched.scheduleJob(jobDetail, trigger);

    } catch (Exception e) {
      LOG.error("Quartz Scheduler failed to initialize: ", e);
      throw new ServletException(e);
    }
  }

}
