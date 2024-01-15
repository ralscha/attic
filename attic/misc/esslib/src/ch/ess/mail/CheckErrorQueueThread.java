

package ch.ess.mail;




public class CheckErrorQueueThread implements Runnable {


  private MailQueue pool;
  private long timeout;

  public CheckErrorQueueThread(MailQueue p, MailConfiguration config) {
    this.pool = p;
    this.timeout = (config.getErrorQueueCheckIntervalInSec() * 1000);
  }

  public void run() {

    for (;;) {
      while (pool.getErrorQueueSize() <= 0) {
        try {
          Thread.sleep(this.timeout);
        } catch (InterruptedException ie) {
          return;
        }
      }

      try {
        Thread.sleep(this.timeout);
      } catch (InterruptedException ie) {
        return;
      }

      pool.checkErrorQueue();
    }
  }
}
