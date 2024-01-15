package com.holub.asynch;

import java.util.*;

import com.holub.io.*;

/**
 * A set of condition variables that can be tested as a single unit.
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
 * <table>
 * <tr>6-13-00<td></td>	Replaced "locks" Vector with a ArrayList
 *						and modified various methods to make
 * 						it work
 * <td></td></tr>
 * </table>
 *
 * @author Allen I. Holub
 */

public final class Condition_set implements Semaphore {
  private final int _id = Lock_manager.new_id();
  public int id() {
    return _id;
  }

  public static class Mode {
    Mode() {}
  }
  public static final Mode ALL = null;
  public static final Mode ANY = new Mode();

  private Collection locks = new ArrayList();
  private Mode acquisition_mode = ALL;

  /** Create a condition set whose {@link acquire} method uses the
   *	specified acquisition mode.
   *	@param acquisition_mode. If <code>Condition_set.ALL</code>, then
   *			{@link acquire} blocks until all condition variables in
   *			the set are in the true state.<br>
   *			If <code>Condition_set.ANY</code>, then
   *			{@link acquire} blocks until any of the condition variables in
   *			the set are in the true state.
   */
  public Condition_set(Mode acquisition_mode) {
    this.acquisition_mode = acquisition_mode;
  }

  /** A convenience method, works like 
   *	<code>Condition( Condition_set.ALL )</code>.
   */

  public Condition_set() {}

  /** Return a Condition object that's a member of the current set.
   *  You can not insert Condition objects into a set, rather you
   *  must ask the Condition_set to manufacture them for you by using
   *	this method.
   *	@param is_true if <code>true</code>, the created Condition object
   *			will be in the true state.
   */

  synchronized Condition element(boolean is_true) {
    Lockable_condition member = new Lockable_condition(is_true);
    locks.add(member);
    return member;
  }

  /** Block until either all or any of the members of the condition
   *	set are true. The behavior (all vs. any) is controlled by the
   *	constructor argument.
   */

  public boolean acquire(long timeout) throws InterruptedException, Semaphore.Timed_out {
    return (acquisition_mode == ALL) ? acquire_all(timeout) : acquire_any(timeout);
  }

  /** Blocks until <u>all</u> of the condition variables in the <code>Condition_set</code>
   *	become
   *	true. If all of them are already true when this method is called,
   *	then return immediately. The condition set,  and all Condition
   *	objects that the condition set creates, will be locked while
   *  this test is going on.
   *	@param timeout blocks for, at most, this many milliseconds. Must be a nonzero
   *					positive number.
   *	@throws InterruptedException is an {@link Thread#interrupt}
   *			message is sent to the current thread.
   *	@throws Semaphore.Timed_out if the method times out.
   *	@throws Java.lang.IllegalArgumentException if the timeout argument is <= 0.
   */

  public boolean acquire_all(long timeout) throws InterruptedException, Semaphore.Timed_out {
    if (timeout <= 0)
      throw new IllegalArgumentException("timeout <= 0 in Condition_set.acquire_all()");

    long expiration = System.currentTimeMillis() + timeout;

    waiting : while (true) {
      Lock_manager.acquire_multiple(locks, timeout);
      synchronized (this) {
        for (Iterator i = locks.iterator(); i.hasNext();) {
          if (!((Condition) i.next()).wait_for_true(0)) {
            timeout = expiration - System.currentTimeMillis();

            if (timeout <= 0)
              throw new Semaphore.Timed_out("Timed out waiting for Condition set");

            continue waiting;
          }
        }
      }
      break;
    }
    return true;
  }

  /** Blocks until <u>any</u> of the condition variables in the <code>Condition_set</code>
   *	become
   *	true. If any of them are already true when this method is called,
   *	then returns immediately.
   *	@param timeout blocks for, at most, this many milliseconds. The timeout
   *			may be zero if you want to check without blocking.
   *	@throws InterruptedException is an {@link Thread#interrupt}
   *			message is sent to the current thread.
   *	@throws Semaphore.Timed_out if the method times out.
   */

  public synchronized boolean acquire_any(long timeout) throws InterruptedException, Semaphore.Timed_out {
    for (Iterator i = locks.iterator(); i.hasNext();)
      if (((Condition) i.next()).is_true())
        return true;

    if (timeout == 0)
      return false;

    wait(timeout); // wait for one to become true

    for (Iterator i = locks.iterator(); i.hasNext();)
      if (((Condition) i.next()).is_true())
        return true;

    throw new Semaphore.Timed_out(); // none of them were true, must
    // have timed out.
  }

  boolean modified;

  /** Releases (sets to "false") all conditions variables in the set
   */
  public synchronized void release() {
    for (Iterator i = locks.iterator(); i.hasNext();)
       ((Lockable_condition) i.next()).release();
  }

  /**	A private class that extends a standard condition variable
   *  to support locking on a Condition_set. This way you cannot
   *	modify the state of a condition_set while a test operation
   *	is in progress.
   */
  private final class Lockable_condition extends Condition {
    public Lockable_condition(boolean is_true) {
      super(is_true);
    }

    public void set_false() {
      synchronized (Condition_set.this) {
        super.set_false();
      }
    }
    public void set_true() {
      synchronized (Condition_set.this) {
        super.set_true();
        Condition_set.this.modified = true;
        Condition_set.this.notifyAll();
      }
    }
  }

  /** A simple test class. Should print:
   *	<pre>
   *	% java com.holub.asynch.Condition_set\$Test
   *
   *	setting a
   *	setting b
   *	resetting b
   *	setting c
   *	setting b
   *	Got 'em all
   *	setting f
   *	Got one as it's set
   *	Got one initially
   *  </pre>
   */
  public static final class Test {
    static Condition_set conditions = new Condition_set();
    static Condition a = conditions.element(false);
    static Condition b = conditions.element(false);
    static Condition c = conditions.element(false);

    static Condition_set or_set = new Condition_set(Condition_set.ANY);
    static Condition d = or_set.element(false);
    static Condition e = or_set.element(false);
    static Condition f = or_set.element(false);

    public static void main(String[] s) throws Exception {
      new Thread() // ALL
      {
        public void run() {
          try {
            sleep(100);
          } catch (InterruptedException e) {}
          Std.out().println("setting a");
          a.set_true();
          Std.out().println("setting b");
          b.set_true();

          // give acquire multiple a chance to get the
          // two that we just set--it will block waiting
          // for c--then change the state of b back to false

          try {
            sleep(100);
          } catch (InterruptedException e) {}
          Std.out().println("resetting b");
          b.set_false();

          Std.out().println("setting c");
          c.set_true();
          Std.out().println("setting b");
          b.set_true();
        }
      }
      .start();

      conditions.acquire(1000 * 5);
      System.out.println("Got 'em all");

      //--------------------------------------------------

      new Thread() // ANY
      {
        public void run() {
          try {
            sleep(1000);
          } catch (InterruptedException e) {}
          Std.out().println("setting f");
          f.set_true();
        }
      }
      .start();

      or_set.acquire_any(1000 * 5);
      System.out.println("Got one as it's set");

      or_set.acquire_any(1000 * 5);
      System.out.println("Got one initially");
    }
  }
}

// Implementation note. It's been pointed out to me that I could
// implement a condition set using a semaphore, where a true
// condition was indicated by acquiring the semaphore.
// (You couldn't wait on individual conditions in this case---
// only on the set as a whole.) I'll leave this implementation
// as an "exercise for the reader."
