package ch.ess.util.pool;

import java.util.*;


public class LifeGuardThread implements Runnable {

  private DatabasePool pool;
  private long timeout;

  public LifeGuardThread(long timeoutSecs, DatabasePool p) {
    this.pool = p;
    this.timeout = (timeoutSecs * 1000);
  }

  public void run() {

    for (;;) {

      // do nothing until something is checked out
      while (this.pool.numCheckedOutObjects() <= 0) {
        try {
          Thread.sleep(this.timeout);
        } catch (InterruptedException ie) {}
      }

      // when something is checked out, wait the usertimeout period then act
      try {
        Thread.sleep(this.timeout);
      } catch (InterruptedException iee) {}

      this.pool.checkTimeout();

    }

  }


}
