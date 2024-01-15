import java.io.*;
import java.util.*;

import org.quartz.*;
import org.quartz.impl.*;

public class Test {

  public static void main(String[] args) {

      try {

        InputStream is = Test.class.getResourceAsStream("/quartz.properties");
        
        StdSchedulerFactory factory = new StdSchedulerFactory();
        factory.initialize(is);
        is.close();
        
        Scheduler scheduler = factory.getScheduler();
        
        scheduler.start();
        
        JobDetail jobDetail = new JobDetail("myJob", Scheduler.DEFAULT_GROUP, TestJob.class);

        SimpleTrigger trigger = new SimpleTrigger("myTrigger", Scheduler.DEFAULT_GROUP,
                                                  new Date(),
                                                  null,
                                                  1,
                                                  2000);

        scheduler.scheduleJob(jobDetail, trigger);
        
        
        Thread.sleep(10000l);

        scheduler.shutdown(true);
        

      } catch (Exception e) {
        e.printStackTrace();
      }
    
  }
}
