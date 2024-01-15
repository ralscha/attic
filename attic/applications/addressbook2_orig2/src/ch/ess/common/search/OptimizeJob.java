package ch.ess.common.search;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.3 $ $Date: 2003/11/11 19:08:33 $ 
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
