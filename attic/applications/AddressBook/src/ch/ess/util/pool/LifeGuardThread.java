/*
 * $Header: c:/cvs/pbroker/src/ch/ess/util/pool/LifeGuardThread.java,v 1.1.1.1 2002/02/26 06:46:55 sr Exp $
 * $Revision: 1.1.1.1 $
 * $Date: 2002/02/26 06:46:55 $
 */

package ch.ess.util.pool;

import java.util.*;


/**
 * Class LifeGuardThread
 *
 * @version $Revision: 1.1.1.1 $ $Date: 2002/02/26 06:46:55 $
 */
public class LifeGuardThread implements Runnable {

  private DatabasePool pool;
  private long timeout;

  public LifeGuardThread(long timeoutSecs, DatabasePool p) {
    this.pool = p;
    this.timeout = (timeoutSecs * 1000);
  }

  public void run() {

    for (; ; ) {

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
