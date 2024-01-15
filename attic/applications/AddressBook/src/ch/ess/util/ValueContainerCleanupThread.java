/*
 * $Header: c:/cvs/pbroker/src/ch/ess/util/ValueContainerCleanupThread.java,v 1.1.1.1 2002/02/26 06:46:55 sr Exp $
 * $Revision: 1.1.1.1 $
 * $Date: 2002/02/26 06:46:55 $
 */

package ch.ess.util;

import java.util.*;


/**
 * Class ValueContainerCleanupThread
 *
 * @version $Revision: 1.1.1.1 $ $Date: 2002/02/26 06:46:55 $
 */
public class ValueContainerCleanupThread implements Runnable {

  private long timeout;

  public ValueContainerCleanupThread(int timeout) {
    this.timeout = (timeout * 1000);
  }

  public void run() {

    for (; ; ) {
      try {
        Thread.sleep(this.timeout);
      } catch (InterruptedException e) {}

      ValueContainer.cleanUp();
    }
  }
}
