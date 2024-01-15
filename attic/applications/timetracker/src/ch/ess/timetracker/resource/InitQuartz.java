package ch.ess.timetracker.resource;

import javax.servlet.ServletException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @author Ralph Schaer
 * @version $Revision: 1.1 $ $Date: 2004/01/05 07:58:19 $
 */

public class InitQuartz implements PlugIn {

  private static final Log LOG = LogFactory.getLog(InitQuartz.class);

  public void destroy() {

    try {
      Scheduler sched = StdSchedulerFactory.getDefaultScheduler();      
      if (sched != null) {
        sched.shutdown(true);
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  public void init(ActionServlet servlet, ModuleConfig config) throws ServletException {

    LOG.debug("Quartz Initializer Servlet loaded, initializing Scheduler...");

    try {

      Scheduler sched = StdSchedulerFactory.getDefaultScheduler();
      sched.start();

    } catch (SchedulerException e) {
      LOG.error("Quartz Scheduler failed to initialize: ", e);
      throw new ServletException(e);
    }
  }

}
