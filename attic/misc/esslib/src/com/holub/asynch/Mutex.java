package com.holub.asynch;

// import com.holub.tools.debug.Assert;	// these, but not both.

/** Implementation of a mutual-exclusion semaphore. It can be owned by
 *  only one thread at a time. The thread can acquire it multiple times,
 *  but there must be a release for every acquire.
 * <br>
 * <br>
 *
 * <table border=1 cellspacing=0 cellpadding=5><tr><td><font size=-1><i>
 * <center>(c) 2000, Allen I. Holub.</center>
 * <p>
 * This code may not be distributed by yourself except in binary form,
 * incorporated into a java .class file. You may use this code freely
 * for personal purposes, but you may not incorporate it into any
 * commercial product without
 * the express written permission of Allen I. Holub.
 * </font></i></td></tr></table>
 *
 * @author Allen I. Holub
 */

public final class Mutex implements Semaphore {
  private Thread owner = null; // Owner of mutex, null if nobody
  private int lock_count = 0;

  private final int _id = Lock_manager.new_id();
  public int id() {
    return _id;
  }

  /**
   * Acquire the mutex. The mutex can be acquired multiple times
   * by the same thread, provided that it is released as many
   * times as it is acquired. The calling thread blocks until
   * it has acquired the mutex. (There is no timeout).
   *
   * @param timeout If 0, then the behavior of this function is
   *				  identical to {@link acquire_without_blocking}.
   *				  If <code>timeout</code> is nonzero, then the timeout
   *				  is the the maximum amount of time that you'll wait
   *				  (in milliseconds).
   *				  Use Semaphore.FOREVER to wait forever. 
   *
   * @return false  if the timeout was 0 and you did not acquire the
   *				  lock. True otherwise.
   *
   * @throw InterruptedException if the waiting thread is interrupted
   *				  before the timeout expires.
   *
   * @throw Semaphore.Timed_out if the specified time elapses before the
   *				  mutex on which we are waiting is released.
   *
   * @see #release
   * @see #acquire_without_blocking
   */

  public synchronized boolean acquire(long timeout) throws InterruptedException, Semaphore.Timed_out {
    if (timeout == 0) {
      return acquire_without_blocking();
    } else if (timeout == FOREVER) // wait forever
      {
      while (!acquire_without_blocking()) //#spin_lock
        this.wait(FOREVER);
    } else // wait limited by timeout
      {
      long expiration = System.currentTimeMillis() + timeout;
      while (!acquire_without_blocking()) {
        long time_remaining = expiration - System.currentTimeMillis();
        if (time_remaining <= 0)
          throw new Semaphore.Timed_out("Timed out waiting for Mutex");

        this.wait(time_remaining);
      }
    }
    return true;
  }

  /** A convenience method, effectively waits forever. Actually
   *  waits for 0x7fffffffffffffff milliseconds
   *  (approx. 292,271,023 years), but that's close enough to
   *  forever for me.
   *  @return false if interrupted, otherwise returns true.
   */
  public boolean acquire() {
    try {
      acquire(FOREVER);
    } catch (Exception e) {
      return false;
    }
    return true;
  }

  /**
   * Attempts to acquire the mutex. Returns false (and does not
   * block) if it can't get it. This method does not need to
   * be synchronized because it's always called from a
   * synchronized method.
   *
   * @return true if you get the mutex, false otherwise.
   * @see #release
   * @see #acquire
   */

  private boolean acquire_without_blocking() {
    Thread current = Thread.currentThread();

    if (owner == null) {
      owner = current;
      lock_count = 1;
    } else if (owner == current) {
      ++lock_count;
    }

    return owner == current;
  }

  /**
   * Release the mutex. The mutex has to be released as many times
   * as it was acquired to actually unlock the resource. The mutex
   * must be released by the thread that acquired it.
   *
   * @throws Semaphore.Ownership (a <code>RuntimeException</code>) if a thread
   *		other than the current owner tries to release the mutex.
   *		Also thrown if somebody tries to release a mutex that's
   *		not owned by any thread.
   */

  public synchronized void release() {
    if (owner != Thread.currentThread())
      throw new Semaphore.Ownership();

    if (--lock_count <= 0) {
      owner = null;
      this.notify();
    }
  }
}
