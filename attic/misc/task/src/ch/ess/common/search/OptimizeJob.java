package ch.ess.common.search;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.1 $ $Date: 2003/11/15 10:31:08 $ 
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
