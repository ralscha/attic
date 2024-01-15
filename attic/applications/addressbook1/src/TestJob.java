import org.quartz.*;


public class TestJob implements Job {

  public void execute(JobExecutionContext arg0) throws JobExecutionException {
    System.out.println("go");

  }

}
