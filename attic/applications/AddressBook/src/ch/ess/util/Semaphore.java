/*
 * $Header: c:/cvs/pbroker/src/ch/ess/util/Semaphore.java,v 1.1.1.1 2002/02/26 06:46:55 sr Exp $
 * $Revision: 1.1.1.1 $
 * $Date: 2002/02/26 06:46:55 $
 */

package ch.ess.util;

/**
 * The implementers of the semaphore class can all be used by the
 * Lock_manager (which lets you synchronize several semaphores at once
 * without worrying about common deadlock scenarios.
 * <br>
 * <br>
 * <table border=1 cellspacing=0 cellpadding=5><tr><td><font size=-1><i>
 * <center>(c) 2000, Allen I. Holub.</center>
 * <p>
 * This code may not be distributed by yourself except in binary form,
 * incorporated into a java .class file.
 * </td></tr></table>
 *
 * @author Allen I. Holub
 */
interface Semaphore {

  /**
   *  A useful symbol constant for passing as a timeout value.
   *  Effectively waits forever (actually waits for only
   *  292,271,023 years).
   */
  public static final long FOREVER = Long.MAX_VALUE;

  /**
   * Return a unique integer id for this object
   */
  public int id();

  /**
   * Acquire the semaphore. In the case of a <code>Mutex</code>, for example,
   * you would get ownership. In the case of a <code>Counting_semaphore</code>,
   * you would decrement the count.
   *
   * @return false if the timeout is zero and the lock was not
   *                             acquired. Otherwise returns true.
   *
   * @throws InterruptedException if the wait to acquire the lock was
   *                    interrupted by another thread.
   *
   * @throws Semaphore.Timed_out if the time out interval expires before
   *                    the semaphore has been acquired.
   */
  public boolean acquire(long timeout) throws InterruptedException, Timed_out;

  /**
   * Release the semaphore
   */
  public void release();

  /**
   * Thrown in the event of an expired timeout.
   */
  public static final class Timed_out extends RuntimeException {

    public Timed_out() {
      super("Timed out while waiting to acquire semaphore");
    }

    public Timed_out(String s) {
      super(s);
    }
  }

  /**
   *  Thrown when a thread tries to release a semaphore illegally, e.g.
   *  a Mutex that it has not acquired successfully.
   */
  public static final class Ownership extends RuntimeException {

    public Ownership() {
      super("Calling Thread doesn't own Semaphore");
    }
  }
}
