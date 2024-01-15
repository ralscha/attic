package ch.ess.common.search;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author Ralph Schaer
 * @version $Revision: 1.2 $ $Date: 2004/05/22 16:51:16 $
 */
public class OptimizeJob implements Job {

  public void execute(JobExecutionContext context) throws JobExecutionException {

    try {
      SearchEngine.updateIndex(new OptimizeRunnable());
    } catch (InterruptedException e) {
      throw new JobExecutionException(e, false);
    }

  }

}