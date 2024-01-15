package com.holub.asynch;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 *	An interval timer. Once you "start" it, it runs for a predetermined
 *	amount of time. The timer runs on it's own, relatively high
 *  priority, thread while one or more other threads block, waiting for
 *  the timer to "expire."
 *  <p>	Three sorts of timers are supported:
 *	<dl>
 *	<dt> A "one-shot" timer
 *	<dd>	runs once, then expires. May be started again, but generally
 *			isn't.
 *	<dt> A "multi-shot" timer
 *	<dd>	works like a one-shot timer, but expects to
 *			be started again, so is more efficient in this situation.
 *			Must be stopped explicitly by a call to <code>stop()</code>.
 *	<dt> A "continuous" (oscillator-style) timer
 *	<dd>	runs continuously. Starts up again automatically when
 *			it expires. Must be stopped explicitly.
 *  </dl>
 *	<p>All timers may be restarted (have the time interval set back to
 *	   the original value) while they are running.
 *
 * <p><b>Warnings:</b>
 * <ol>
 * <li>	It's easy to forget to <code>stop()</code> a multi-shot or continuous
 *		timer and create a memory leak as a consequence. 
 *		(The VM will keep the Thread object alive but
 *		suspended, even if there are no external references to it.)
 *		A finalizer is provided to throw an exception in this situation,
 *		but since the finalizer may not be called, that's not a perfect
 *		solution.
 * <li>	Calling {@link java.lang.Thread#wait()} on a timer is possible,
 *		but it only works correctly	for <code>CONTINUOUS</code> timers.
 *		The {@link #await()} method works correctly
 *		for all types of timers, and is easier to use since you don't
 *		have to synchronize first. The only time that using a raw
 *		<code>wait()</code> makes sense if if you're interested in the
 *		InterruptedException, which is silently absorbed by <code>await()</code>.
 * </ol>
 *
 * <table>
 *	<tr>6-14-00<td></td><td>Made the <code>observers</code>, <code>clock</code>, and
 *							<code>notifier</code> references volatile.
 *	</td></tr>
 * </table>
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
 **/

public final class Alarm {
  // A compiler bug (1.1x, 1.2) doesn't permit blank finals.
  int delay;
  private /*final*/
  boolean keeps_process_alive;

  private volatile Alarm.Clock clock = null;
  private boolean is_stopped = false;

  /****************************************************************
   * Notifiers can be added to an Alarm by {@link #addActionListener}.
   * The Notifiers are notified on their own thread by enqueueing a
   * notification request on each clock "tick." If the total time
   * required for notification exceeds the distance between ticks,
   * these request will back up in a queue waiting for service.
   * The `REQUEST_LIMIT` is the maximum number of ticks that can
   * be waiting for service at any given moment. Any additional
   * requests will be silently ignored when the queue fills.
   */

  public static final int REQUEST_LIMIT = 8;

  /****************************************************************
   * A constrained set of operational modes. Legal values are
   * {@link #CONTINUOUS}, {@link #ONE_SHOT}, and {@link #MULTI_SHOT}.
   */

  public static final class Mode {
    Mode() {}
  }

  /****************************************************************
   * CONTINUOUS alarms run continuously (until stopped), sending
   * out notification messages and releasing waiting threads
   * at a regular interval.
   */
  public static final Mode CONTINUOUS = null;

  /****************************************************************
   *	ONE_SHOT alarms run once, then stop automatically. [You do not
   *	have to call stop().] Can be started again manually by calling
   *  start(), but a new internal thread 	must be created to do so.
   */
  public static final Mode ONE_SHOT = new Mode();

  /****************************************************************
   * MULTI_SHOT alarms work just like ONE_SHOT alarms, but they
   * can be restarted more efficiently. (The timer thread for a
   * MULTI_SHOT alarm is not destroyed until you stop() the alarm.)
   * Use a MULTI_SHOT when you know that the one-shot will
   * be restarted at regular intervals and don't mind having an extra
   * thread kicking around in a suspended state.
   */
  public static final Mode MULTI_SHOT = new Mode();

  Mode type;

  //------------------------------------------------------------------

  volatile ActionListener observers = null;
  private volatile Notifier notifier;

  /****************************************************************
   * "Action command" sent to the ActionListeners when the timer
   * has been stopped manually.
   */
  public static final String STOPPED = "stopped";

  /****************************************************************
   * "Action command" sent to the ActionListeners when the timer
   * has expired or "ticked."
   */
  public static final String EXPIRED = "expired";

  /****************************************************************
   * Create a timer that expires after the indicated delay
   *  in milliseconds.
   *
   *  <p> 
   *  Generally, timers must run at high priority to prevent
   *  the waiting threads from starting up immediately on being
   *  notified, thereby messing up the timing. If a high-priority
   *  thread is waiting on a recurring timer, however, the next
   *	"tick" could be delayed by the amount of time the high-priority
   *	thread spends doing whatever it does after being notified.
   *  Alarms run at the highest priority permitted by the thread
   *  group of which they are a member, but spend most of their
   *  time sleeping.
   *
   * @param <b>delay</b> Time to expiration (nominal) in milliseconds.
   * @param <b>type</b>  One of the following symbolic constants:
   *	<table>
   *	<tr><td valign=top>{@link Alarm.CONTINUOUS}</td><td valign=top>
   *									The timer runs continuously.
   *									You must call stop() when you're
   *									done with it.</td></tr>
   *	<tr><td valign=top>{@link Alarm.ONE_SHOT}</td>	<td valign=top>
   *									The timer runs once, then stops
   *									automatically. You do not
   *									have to call stop().</td></tr>
   *	<tr><td valign=top>{@link Alarm.MULTI_SHOT}</td><td valign=top>
   *									Like ONE_SHOT, but can be
   *								    restarted more efficiently.
   *									You must call stop() when you're
   *									done with it.</td></tr>
   *	</table>
   *	If this argument is <code>null</code>, <code>CONTINUOUS</code>
   *  is used.
   *
   * @param keeps_process_alive  By default, the Alarm's
   *					timing-element
   *					thread is a Daemon. This means that
   *					the fact that a Alarm is running won't
   *					keep the process alive. This behavior
   *					can cause problems with some
   *					single-threaded applications that sleep,
   *					doing everything that it does on a clock
   *					tick. Specifying a true value will cause
   *					a running timer to keep the current process
   *					alive; you'll have to {@link #stop()}
   *					the Alarm object [or call system.exit()]
   *					to allow the application to shut down.
   *					Note that an <em>expired</em> <code>ONE_SHOT</code>
   *					or MULTI_SHOT never keeps the process alive.
   **/

  public Alarm(int delay, Mode type, boolean keeps_process_alive) {
    this.delay = delay;
    this.type = (type == null) ? CONTINUOUS : type;
    this.keeps_process_alive = keeps_process_alive;
  }

  /****************************************************************
   * Make a continuous timer that won't keep the process alive
   * simply because its running.
   */

  public Alarm(int delay) {
    this(delay, CONTINUOUS, false);
  }

  /****************************************************************
   *  Start up a timer or restart an expired timer. If the timer is
   *  running, it is set back to the original count without releasing
   *  any of the threads that are waiting for it to expire.
   *  (For example, if you start up a 10-second timer and then restart
   *  it after 5 seconds, the waiting threads won't be notified until
   *  10 seconds after the restart--15 seconds after the original
   *  start time.)
   *  <p>
   *	Starting a running timer causes a new thread to be created.
   **/

  public synchronized void start() {
    if (clock != null) {
      if (type == MULTI_SHOT && clock.has_expired()) {
        clock.restart(); //#restart
        return;
      } else {
        clock.die_silently();
      }
    }

    clock = new Clock();
    clock.setDaemon(!keeps_process_alive);
    clock.start();
    is_stopped = false;
  }

  /****************************************************************
   *	Stops the current timer abruptly, releasing all waiting
   *  threads. The timer can be restarted by calling start().
   *  There's no good way for a thread to determine if it was notified
   *  as the result of a stop() or a normal expiration.
   **/

  public synchronized void stop() {
    if (clock != null)
      clock.interrupt();

    clock = null;
    is_stopped = true;
    notifyAll();
  }

  /****************************************************************
   * It is a bug not to stop() a CONTINUOUS or MULTI_SHOT timer 
   * when you're done with it. This finalizer helps you detect the
   * bug by printing an error message if the timer that is being
   * destroyed is still running, but bear in mind that there's
   * absolutely no guarantee in Java that a finalizer will <em>ever</em>
   * be called, so don't count on this behavior.
   **/

  public void finalize() {
    if (clock != null)
      System.out.println("Alarm was not stopped before being destroyed");
  }

  /****************************************************************
   * A long time (roughly 292,271,023 years) that you can use for
   * the timeout value in {@link await()}.`
   **/

  public static final long FOREVER = Long.MAX_VALUE;

  /****************************************************************
   *	Wait for the timer to expire. Returns immediately in the case
   *	of an expired ONE_SHOT or MULTI_SHOT timer.  Blocks until the
   *	timer expires in all other situations (including any sort of
   *	timer that has not yet been started).
   *
   *  @return false if the method returned because the timer was
   *			stopped, true if the timer simply expired.
   *
   *	@see FOREVER
   **/

  public synchronized boolean await(long timeout) {
    if (clock == null || !clock.has_expired()) {
      try {
        wait(timeout);
      } catch (InterruptedException e) { /*do nothing*/
      }
    }
    return !is_stopped;
  }

  /****************************************************************
   * Same as <code>await(Alarm.FOREVER)</code>
   **/
  public boolean await() {
    return await(FOREVER);
  }

  /****************************************************************
   *	Add a listener that will be notified the next time that the
   *	Alarm goes off. The listeners are notified on
   *	a thread that's created just for that purpose, rather than
   *	being notified from the timer thread. This way the time
   *	spent doing notification doesn't impact the time interval
   *	used by the timer. The "action command" in the ActionEvent
   *	object will be either the String "stopped" or "expired"
   *	(which are also defined in the symbolic constants {@link Alarm.STOPPED}
   *	and {@link Alarm.EXPIRED}), depending on whether this notification
   *  occurred because the timer was stopped manually, or because
   *	it expired in the normal way.
   *  <p>
   *	It's your job to make sure that the total time required to
   *  notify all listeners does not exceed the time between ticks.
   *	Some slop is built into the system, in that up to ticks
   *	will be queued up waiting for service, but if the Alarm gets
   *  more than 8 ticks behind, then the extra ticks are silently
   *  ignored.
   */

  public synchronized void addActionListener(ActionListener observer) {
    observers = AWTEventMulticaster.add(observers, observer);
    if (notifier == null) {
      notifier = new Alarm.Notifier();
      notifier.start();
    }
  }

  /****************************************************************
   * Remove a listener
   */

  public synchronized void removeActionListener(ActionListener observer) {
    observers = AWTEventMulticaster.remove(observers, observer);
    if (observers == null) {
      notifier.interrupt(); // kill the notifier thread.
      notifier = null;
    }
  }

  /****************************************************************
   * The thread that actually notifies other listeners. (Notification
   * is done on a separate thread so as to not impact the Alarm's timing.)
   * Rather than spawn multiple instances of the thread (which can cause
   * problems if the actionPerformed() messages takes longer to execute
   * than the interval between clock ticks), Runnable objects that
   * notify the listeners are queued up in a blocking queue which is
   * serviced by the Notifier thread.
   */

  private final class Notifier extends Thread {
    public Notifier() {
      setDaemon(true);
    }

    private Blocking_queue requests = new Blocking_queue(REQUEST_LIMIT);

    /** Accept a request for this notifier.
     *  @throws Blocking_queue.Full if more than 8 requests
     *			are waiting to be serviced.
     *  @throws Blocking_queue.Closed if an internal error occurs.
     */
    public void accept(Runnable operation) throws Blocking_queue.Full, Blocking_queue.Closed {
      requests.enqueue(operation);
    }

    public void run() {
      try {
        while (!interrupted() && (observers != null)) {
          ((Runnable) (requests.dequeue())).run(); //#dequeue
          yield();
        }
      } catch (InterruptedException e) { /*not an error*/
      }
    }
  }

  /** Sends an ActionEvent indicating that the timer has stopped.
  */
  private final Runnable stopped = new Runnable() {
      public void run() //#run_stopped
  {
      if (observers != null)
        observers.actionPerformed(new ActionEvent(Alarm.this, 0, STOPPED));
    }
  };

  /** Sends an ActionEvent indicating that the timer has expired.
  */
  private final Runnable expired = new Runnable() {
      public void run() //#run_expired
  {
      if (observers != null)
        observers.actionPerformed(new ActionEvent(Alarm.this, 0, EXPIRED));
    }
  };

  /** Sent to us from a Clock on every "tick," queues up a request
   *  to notify listeners. If too many requests are queued, then
   *  ticks will be lost.
   */
  synchronized final void tick() {
    try {
      Alarm.this.notifyAll();
      if (notifier != null)
        notifier.accept(is_stopped ? stopped : expired);
    } catch (Blocking_queue.Full request_queue_full) { // Ignore it.
    }
  }

  /** Sent to us from a Clock when the clock wants to remove all
   *  references to itself.
   */
  synchronized final void delete_me(Clock me) {
    if (clock == me)
      clock = null; //#clock_destroy
  }

  //================================================================
  // Support classes:
  //================================================================

  private final class Clock extends Thread {
    // Note that continuous Alarms don't expire.

    private volatile boolean expired = false;
    private boolean notifications_on = true;

    Clock() {
      setPriority(getThreadGroup().getMaxPriority());
    }

    public void run() {
      while (!isInterrupted()) //#run_loop
        {
        try {
          sleep(delay); // The monitor is not released by
          // sleep() so this call <u>must</u>
          // be outside the synchronized
          // block.

          synchronized (this) //#clock_synch
            {
            if (isInterrupted()) // don't notify waiting
              break; // threads if we've been
            // stopped by the
            // Alarm object.
            expired = true;
            if (notifications_on)
              Alarm.this.tick();

            if (type == MULTI_SHOT) {
              wait(); // suspend		//#clock_suspend
            } else if (type == ONE_SHOT) // Null out the 
              {
              Alarm.this.delete_me(this); // outer-class
              break; // reference to
              // the clock.
            }
          }
        } catch (InterruptedException e) // don't notify the waiting
          {
          break; // threads because an
        } // interrupt is used to
      } // stop the timer.
    }

    public void die_silently() {
      notifications_on = false;
      interrupt();
    }

    public synchronized void restart() {
      expired = false;
      notify(); // resume			//#clock_release
    }

    public boolean has_expired() // CONTINUOUS
    {
      return (type != CONTINUOUS) && expired; // timers never
    } // expire.
  }

  //================================================================
  // Unit test:
  //================================================================
  static public final class Test {
    public static void main(String[] args) throws Exception {
      // A recurring timer, runs until it is stoped manually.

      Alarm timing_element = new Alarm(1000, Alarm.CONTINUOUS, false);
      timing_element.start();

      System.out.println("Print time 3 times, 1-sec. intervals");

      for (int i = 3; --i >= 0;) {
        System.out.println(new Date().toString());
        timing_element.await(Alarm.FOREVER);
      }

      timing_element.stop(); // It is essential to stop the timer
      // manually, Otherwise, the memory
      // for it might never be reclaimed.

      System.out.println("\nOne-shot:\n");

      // A One-shot. Fire it manually. You don't need to stop()
      // it explicitly---it automatically frees up all threads
      // when it expires.

      timing_element = new Alarm(1000, Alarm.ONE_SHOT, false);
      timing_element.start();
      for (int i = 3; --i >= 0;) {
        System.out.println(new Date().toString() + "\r");
        timing_element.await(Alarm.FOREVER);
        timing_element.start();
      }

      System.out.println("");
      System.out.println("Multi-shot:\n");

      // A Multi-shot is much like a one-shot. Fire it manually,
      // but you must stop() it explicitly. The main difference
      // is that a MULTI_SHOT timer doesn't recreate the timer
      // thread when it's restarted. The one-shot timer creates
      // the thread anew every time it's started.

      timing_element = new Alarm(1000, Alarm.MULTI_SHOT, false);
      timing_element.start();
      for (int i = 3; --i >= 0;) {
        System.out.println(new Date().toString() + "\r");
        timing_element.await(Alarm.FOREVER);
        timing_element.start();
      }
      timing_element.stop();

      System.out.println("");
      System.out.println("Notifier\n");

      timing_element = new Alarm(1000); // 1-second continuous
      timing_element.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          System.out.println(new Date().toString() + " (" + e.getActionCommand() + ")");
        }
      });
      timing_element.start();

      System.out.println("Sleeping");
      Thread.sleep(3000);
      System.out.println("Waking");

      timing_element.stop();
      System.exit(0);
    }
  }
}
