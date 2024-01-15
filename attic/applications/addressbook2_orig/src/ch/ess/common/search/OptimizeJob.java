package ch.ess.common.search;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/** 
  * @author  Ralph Schaer
  * @version 1.0, 13.07.2003 
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
