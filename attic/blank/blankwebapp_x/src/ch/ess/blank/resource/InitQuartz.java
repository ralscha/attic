package ch.ess.blank.resource;

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
 * @version $Revision: 1.4 $ $Date: 2004/05/22 15:38:03 $
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
      LOG.error("Quartz Scheduler failed to shutdown cleanly: ", e);
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