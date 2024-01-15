package ch.ess.common.util;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.1 $ $Date: 2004/01/05 07:58:16 $ 
  */
public class TimedValuesCleanupThread implements Runnable {

  private long timeout;

  public TimedValuesCleanupThread(int timeout) {
    this.timeout = (timeout * 1000);
  }

  public void run() {

    for (;;) {
      try {
        Thread.sleep(this.timeout);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      TimedValues.cleanUp();
    }
  }
}
