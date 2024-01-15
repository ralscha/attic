package com.holub.asynch;
import java.util.*;

/** A simple generic spin lock. Use it to wait for some condition to be
 *	true when a notify is received. For example, this code:
 *	<pre>
 *	boolean some_condition = false; // set true by another thread
 *
 *	Spin_lock lock = new Spin_lock();
 *	lock.acquire(	new Spin_lock.Condition()
 * 				 	{	public boolean satisfied()
 * 						{	return some_condition == false;
 *						}
 *					},
 *					1000	// wait at most one second
 *				);
 *	//...
 *	lock.release();
 *	</pre>
 *	has roughly the same effect as:
 *	<pre>
 *	Object lock;
 *
 *	while( some_condition == false )
 *		lock.wait(1000);
 *	//...
 *	lock.notify();
 *	</pre>
 *	The timeout will be reliable if you use the Spin_lock;
 *	it may not be reliable if you use `wait()`.
 * <br><br>
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
 *
 * @author Allen I. Holub
 */

public final class Spin_lock {
  /**	A gang-of-four Strategy object that tells the spin lock the condition
   *	for which we're waiting. Override <code>satisfied()</code> to return
   *	true when the condition for which we're waiting becomes true.
   */

  public interface Condition {
    boolean satisfied();
  }

  /** Block until the condition specified by <code>condition</code> becomes
   *	true <u>and</u> the current Spin_lock is passed a {@link release()} message
   *	after the condition becomes true.
   *
   *	@throws Semaphore.Timed_out	if the timeout expires
   *	@throws InterruptedException if another thread interrupts the timeout
   */
  public synchronized void acquire(Condition condition, long timeout)
    throws Semaphore.Timed_out, InterruptedException {
    long expiration = System.currentTimeMillis() + timeout; //#here
    while (!condition.satisfied()) {
      timeout = expiration - System.currentTimeMillis();
      if (timeout <= 0)
        throw new Semaphore.Timed_out("Spin lock timed out.");
      wait(timeout); //#there
    }
  }

  public synchronized void release() {
    notify();
  }

  /****************************************************************** 
   * Test class, prints "hello world" when executed.
   */

  public static final class Test {
    public static void main(String[] args) throws Exception {
      final Stack stack = new Stack();
      final Spin_lock lock = new Spin_lock();
      new Thread() {
        public void run() {
          try {
            lock.acquire(new Spin_lock.Condition() {
              public boolean satisfied() {
                return !stack.isEmpty();
              }
            }, 4000);

            System.out.println(stack.pop().toString());
          } catch (Exception e) {}
        }
      }
      .start();

      Thread.sleep(500); // give the thread a
      // chance to get started.
      stack.push("hello world");
      lock.release();
    }
  }
}
