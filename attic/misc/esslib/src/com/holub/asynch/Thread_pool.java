package com.holub.asynch;
import com.holub.tools.*;

/** A generic implementation of a thread pool. Use it like this:
 *
 *	<PRE>
 *	Thread_pool pool = new Thread_pool();
 *	pool.execute
 *	(	new Runnable()
 *		{	public void run()
 *			{	// execute this function on an existing
 *				// thread from the pool.
 *			}
 *		}
 *	);
 *	</PRE>
 *
 * <p>The size of the thread pool can expand automatically to accommodate
 * requests for execution. That is, if a thread is available in
 * the pool, it's used to execute the Runnable object, otherwise
 * a new thread can be created (and added to the pool) to execute the
 * request. A maximum count can be specified to limit the number of
 * threads in the pool, however.
 *
 * <p>Each thread pool also forms a thread group (all threads in the
 * pool are in the group). In practice this means that the security
 * manager controls whether a thread in the pool can access threads
 * in other groups, but it also gives you an easy mechanism to make the
 * entire group a Daemon.
 * 
 * Unlike a <code>ThreadGroup</code>, it's possible to do the equivalent
 * of a <code>join()</code> operations simply by calling <code>join()</code>
 * on the thread pool itself. The {@link #join} method is provided for
 * this purpose. You can also <code>wait()</code> for a thread pool to
 * become idle, but if it's idle when you start to wait, the wait isn't
 * satisfied until the pool becomes active and then quiescent again.
 * {@link join}, on the other hand, returns immediately if the pool
 * is idle.
 *
 * You can remove a thread from the pool by interrupting it. Often the
 * easiest way to do this is to enqueue a request that issues the
 * interrupt:
 * <PRE>
 * some_pool.execute
 * (	new Runnable()
 * 		{	public void run()
 *			{	Thread.currentThread().interrupt();
 * 			}
 *		}
 * );
 * </PRE>
 * The {@link deflate} method removes all threads that
 * were created over and above the initial size, and is usually a better
 * choice than an explicit interrupt, however.
 * <br>
 * <br>
 * <p>History (modifications since book was published)
 * <table>
 * <tr>06/05/00<td></td>Cleaned up constructor code so that all the
 * 						pooled_threads threads are created before
 * 						constructor returns. Fixed a bug in the
 * 						idle-thread counting mechanism.
 * <tr>08/14/02<td></td>Added a toString() method that evaluates to a
 * 						string that represents the pool's current state.
 * 						Note that this method can use the replaceAll method
 * 						of String, new to JDK 1.4. It's commented out
 * 						right now so that the code will work on older
 * 						versions of Java, but you might want to put it
 * 						back in to make the output more readable.
 * <tr>08/14/02<td></td>Removed the version of @{link execute} that took
 * 						a Command argument (rather than a Runnable). It's
 * 						just commented out, so you can put it back in if you
 * 						need it.
 * <tr>08/15/02<td></td>Modified {@link #join} to throw InterruptedException
 * 						if it's interrupted.
 * <tr>08/15/02<td></td>Modified {@link #join()} so that it will return
 * 						immediately if the pool is idle when it's called.
 * <tr>08/15/02<td></td>Eliminated "joiners" field. All threads that
 * 						are waiting on the thread pool are simply notified
 * 						whenever the pool becomes idle. This is a slight
 * 						inefficiency, but simplifies the code.
 * <tr>08/15/02<td></td>Modified code to permit the pool to have an
 * 						initial size of zero.
 * <tr>08/15/02<td></td>Fixed Pooled_thread constructor to eliminate
 * 						a race condition (and also a possible deadlock)
 * 						on pool startup.
 * <tr>08/16/02<td></td>Fixed an (unrported) bug in execute() so that
 * 						we're now guaranteed that newly created threads
 * 						are running before execute returns, Otherwise
 * 						the pool size and idle-thread count could be
 * 						incorrect.
 * <tr>09/17/02<td></td>Modified the join() logic so that the Pooled_thread
 * 						doesn't notify the joiners until all threads are
 * 						idle AND there are no requests waiting to be serviced.
 * <td></td></tr>
 * My thanks (in no particular order) to Scott Willy, Aaron Greenhouse,
 * TK Samy, Patrick Hancke, Dave Yost, Greg Charles, Pavel Lisovin,
 * Omi Traub, Anatoly Pidruchny, Michael Oswall, and Anders Lindell
 * for sending in bug reports and suggestions. (If I've forgotten anybody,
 * I'm sorry.)
 * </table>
 *
 * <table border=1 cellspacing=0 cellpadding=5><tr><td><font size=-1><i>
 * <center>(c) 2002, Allen I. Holub.</center>
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

public final class Thread_pool extends ThreadGroup {
  final Blocking_queue pool = new Blocking_queue();
  private final int initial_size;
  private final int maximum_size;
  int idle_threads = 0;
  int new_threads = 0;
  int pool_size = 0;
  volatile boolean has_closed = false;
  private boolean deflate = false;
  Object creation_lock = new Object();
  private boolean daemon;

  // The pooled_threads_all_running lock assures that all of the
  // threads in the pool are running before the constructor
  // returns. The constructor waits on it, and the last thread
  // added to the pool issues the notify when it detects that
  // the pool is now full [initial_size == pool_size].
  // 
  Object pooled_threads_all_running = new Object();

  private static int group_number = 0;
  static int thread_id = 0;

  /******************************************************************
   * Return a string that shows the pool's current state.
   */
  public synchronized String toString() {
    StringBuffer message = new StringBuffer();
    message.append("pool:");

    message.append(pool.toString());
    // If you're running JDK 1.4, use the following:
    // 		message.append( pool.toString().replaceAll("\\n","\n\t") );

    message.append("\ninitial_size = " + initial_size);
    message.append("\nmaximum_size = " + maximum_size);
    message.append("\nidle_threads = " + idle_threads);
    message.append("\nnew_threads  = " + new_threads);
    message.append("\npool_size    = " + pool_size);
    message.append("\nhas_closed   = " + has_closed);
    message.append("\ndeflate      = " + deflate);
    message.append("\n");
    return message.toString();
  }

  /******************************************************************
   * These are the objects that wait to be activated. They are
   * typically blocked on an empty queue. You post a Runnable
   * object to the queue to release a thread, which will execute
   * the run() method from that object. All Pooled-thread objects
   * will be members of the thread group the comprises the tread
   * pool.
   */

  private final class Pooled_thread extends Thread {
    public Pooled_thread() {
      super(Thread_pool.this, "T" + thread_id++);
    }

    public void run() {
      // The following handshake is performed both
      // by the Thread_pool constructor (when it creates the
      // pool) and by the execute() method (when it increases
      // the pool size during lazy instantiation):
      //
      // The initial synchronized statement blocks
      // until the Thread_pool constructor [or execute()] has
      // started all of the threads in the pool. I'd much
      // rather synchronize on Thread_pool.this than the
      // creation_lock, but synchronization on this
      // doesn't work in a constuctor.
      //
      // The notifyAll() is the second part of the handshake,
      // and notifies the constructor [or execute()] that all
      // of the threads // are not only started, but running,
      // and that the idle_threads and pool_size
      // fields are accurate.

      synchronized (creation_lock) {
        ++idle_threads; // Thread has been started, and is idle
        ++pool_size;

        if (--new_threads <= 0) {
          synchronized (pooled_threads_all_running) {
            pooled_threads_all_running.notifyAll();
          }
        }
      }

      try {
        // The Thread_pool constructor synchronizes on the
        // Thread_pool object that's being constructed until
        // that Thread-pool object is fully constructed. The
        // Pooled_thread objects are both created and started
        // from within that constructor. The following 
        // "synchronized" statement forces the pooled threads
        // to block until initialization is complete and the
        // constructor gives up the lock. This synchronization
        // is essential on a multiple-CPU machine to make sure
        // that the various CPU caches are in synch with each
        // other.

        while (!isInterrupted() && !has_closed) {
          try {
            Runnable command = (Runnable) pool.dequeue();

            synchronized (Thread_pool.this) {
              --idle_threads;
            }

            if (command == null) // sentinal to kill thread.
              break;

            command.run();
          } catch (InterruptedException e) // User-issued stop
            {
            break;
          } catch (Blocking_queue.Closed e) { // Ignore it. The thread pool is shutting down.
          } catch (Exception e) {
            System.err.println(
              "Ignoring exception thrown from " + " user-supplied thread-pool action:\n\t" + e.getMessage());
          } finally {
            synchronized (Thread_pool.this) {
              ++idle_threads;

              // If none of the threads in the pool are
              // doing anything, notify any joined threads.

              if (all_threads_are_idle() && pool.is_empty())
                Thread_pool.this.notifyAll();
            }
          }
        }
      } finally {
        synchronized (Thread_pool.this) {
          synchronized (creation_lock) {
            --idle_threads;
            --pool_size;
          }
        }
      }
    }
  }

  /******************************************************************
   * Convenience method, aids readability.
   */
  boolean all_threads_are_idle() {
    return idle_threads == pool_size;
  }

  /******************************************************************
   *	Create a thread pool with initial_thread_count threads
   *	in it. The pool can expand to contain additional threads
   *  if they are needed.
   *
   *	@param <b>initial_thread_count</b>	The initial thread count.
   *			If the initial count is greater than the maximum, it is
   *			silently truncated to the maximum.
   *  @param <b>maximum_thread_count</b> specifies the maximum number
   *			of threads that can be in the pool. A maximum of 0
   *			indicates that the 	pool will be permitted to grow
   *			indefinitely.
   */
  public Thread_pool(int initial_thread_count, int maximum_thread_count) {
    this(initial_thread_count, maximum_thread_count, false);
  }

  public Thread_pool(int initial_thread_count, int maximum_thread_count, boolean daemon) {
    super("Thread_pool" + group_number++);
    this.daemon = daemon;

    // I'm synchronizing, here, to prevent the Pooled_thread objects
    // that are created below from being able to access the current
    // object until it's fully initialized. They synchronize on
    // "creation_lock" at the top of their run() methods. The wait call
    // prevents the contructor from returning until all the threads
    // are active. [The notify is in the Pooled_threads run() method.]
    // 
    // Note that you can't syncronize on "this" in a constructor
    // so I'm using two roll-your-own locks to implement
    // the required handshake. (In Hotspot, at least, synchronizing
    // on "this" in a constructor is effectively a no-op. It
    // doesn't actually grab the lock.)

    synchronized (pooled_threads_all_running) {
      synchronized (creation_lock) {
        initial_size = initial_thread_count;
        maximum_size = (maximum_thread_count > 0) ? maximum_thread_count : Integer.MAX_VALUE;

        if (initial_thread_count > maximum_size)
          throw new IllegalArgumentException("Thread_pool: initial_thread_count > maximum_size");

        // new_threads is used by the handshake with the newly
        // created threads. Take a look in the Pooled_thread
        // constructor to see how this works
        new_threads = initial_thread_count;

        for (int i = initial_thread_count; --i >= 0;) {
          Pooled_thread pt = new Pooled_thread();
          pt.setDaemon(daemon);
          pt.start();
        }
      }

      if (new_threads > 0) {
        try {
          pooled_threads_all_running.wait();
        } catch (InterruptedException e) {
          throw new Error("Internal error: unexpected InterruptedException");
        }
      }
    }
  }

  /******************************************************************
   *	An efficient way to say <code>Thread_pool(0,0)</code>.
   */

  public Thread_pool() {
    super("Thread_pool" + group_number++);
    this.initial_size = 0;
    this.maximum_size = Integer.MAX_VALUE;
  }

  /******************************************************************
   *  Execute the <code>run()</code> method of the Runnable object on a thread
   *	in the pool. A new thread is created if the pool is
   *	empty and the number of threads in the pool is not at the
   *  maximum.
   *
   * 	@throws Thread_pool.Closed if you try to execute an action
   *			on a pool to which a close() request has been sent.
   */

  public synchronized void execute(Runnable action) throws Closed {
    // You must synchronize on the pool because the Pooled_thread's
    // run method is asynchronously dequeueing elements. If we
    // didn't synchronize, it would be possible for the is_empty()
    // query to return false, and then have a Pooled_thread sneak
    // in and put a thread on the queue (by doing a blocking dequeue).
    // In this scenario, the number of threads in the pool could
    // exceed the maximum specified in the constructor. You'll have
    // to look closely at the pooled-threads run() method to understand
    // why we need two locks. Run needs to get both of the locks
    // we acquire here before it can issue a notify(). It can't get
    // both locks until we both leave the synchronized(this) block and
    // also issue the wait(). This way, we're guaranteed that the
    // notify is issued by run() after the wait() is called here.
    //
    // The InterruptedException that comes from wait is ignored because
    // the only references to pooled_threads_all_running are internal,
    // and an interrupt is never issued internally. Just to be on the
    // safe side, I throw an Error() should the impossible happen.
    //
    // What a mess.

    boolean added_thread = false;

    synchronized (pooled_threads_all_running) {
      synchronized (creation_lock) {
        if (has_closed)
          throw new Closed();

        pool.enqueue(action); // Attach action to the thread

        if (pool_size < maximum_size && pool.waiting_threads() == 0) {
          added_thread = true;
          new_threads = 1;

          Pooled_thread pt = new Pooled_thread();
          pt.setDaemon(daemon);
          pt.start();

        }
      }

      // Don't wait if we haven't added any threads, but the
      // creation_lock must have been released before we can
      // wait, otherwise we'll deadlock.

      if (added_thread)
        try {
          pooled_threads_all_running.wait();
        } catch (InterruptedException e) {
          throw new Error("Internal error: unexpected InterruptedException");
        }
    }
  }

  //	/******************************************************************
  //	 *  A convenience wrapper to execute a Command (rather than a
  //	 *  Runnable) object. Just creates a Runnable that 
  //	 *  calls <code>action.fire(argument)</code> and passes that
  //	 *  Runnable to {@link execute}
  //	 */

  final synchronized public void execute(final Command action, final Object argument) throws Closed {
    execute(new Runnable() {
      public void run() {
        action.fire(argument);
      }
    });
  }

  /******************************************************************
   * Objects of class Thread_pool.Closed are thrown if you try to
   * execute an action on a closed Thread_pool.
   */

  public final class Closed extends RuntimeException {
    Closed() {
      super("Tried to execute operation on a closed Thread_pool");
    }
  }

  /******************************************************************
   *	Kill all the threads waiting in the thread pool, and arrange
   *	for all threads that came out of the pool, but which are working,
   *	to die natural deaths when they're finished with whatever they're
   *	doing. Actions that have been passed to execute() but which
   *	have not been assigned to a thread for execution are discarded.
   *	<p>
   *  No further operations are permitted on a closed pool, though
   *	closing a closed pool is a harmless no-op.
   */

  public synchronized void close() {
    has_closed = true;
    pool.close(); // release all waiting threads
  }

  /******************************************************************
   * Wait for the pool to become idle if it's active.
   * You can also <code>wait()</code> for a thread pool to
   * become idle, but if it's idle when you start to wait, the wait isn't
   * satisfied until the pool becomes active and then quiescent again.
   * The join() method, on the other hand, returns immediately if the
   * pool is idle.
   *
   * In retrospect, "join" is not a great name for this method
   * since the threads are not dead, just idle. I'm reluctant
   * to change the name because I don't want to break existing code.
   * I've * provided a {@link #wait_for_all_threads_to_be_idle} method
   * if you'd like a more sensible name, however.
   *
   * @throws InterruptedException if the waiting thread is 
   * 			interrupted.
   */

  public synchronized void join() throws InterruptedException {
    // If all the threads are currently idle and no actions
    // are queued up waiting for service, then the pool
    // really is idle.

    if (!(all_threads_are_idle() && pool.is_empty()))
      wait();
  }

  /** Same as {@link #join}. Better name.
   */
  public void wait_for_all_threads_to_be_idle() throws InterruptedException {
    join();
  }

  /******************************************************************
   * If the argument is true,
   * discard threads as they finish their operations until the
   * initial thread count passed to the constructor. That is, the
   * number of threads in the pool will not go below the initial
   * count, but if the number of threads in the pool expanded, then
   * the pool size will shrink to the initial size as these extra
   * threads finish whatever they're doing. It's generally best to
   * set this flag before any threads in the pool are activated.
   * If the argument is false (the default behavior of the thread
   * pool), then treads created above the initial count remain
   * in the pool indefinitely.
   * <p>
   * Though you can call deflate at any time to deflate the
   * pool back to its original size, it's generally best to
   * call deflate immediately after creating the thread pool.
   * Otherwise, the deflation state might be surprising when
   * the first few threads run.
   */

  public synchronized void deflate(boolean do_deflate) {
    deflate = do_deflate;
    if (deflate == do_deflate) {
      int excess_threads = pool_size - initial_size;
      while (--excess_threads >= 0)
        pool.enqueue(null);
    }
  }

  /* ============================================================== */

  public static final class Test {
    private static Thread_pool pool = new Thread_pool(10, 10);

    public static void main(String[] args) throws Exception {
      Test test_bed = new Test();

      System.out.println("Thread pool is running");

      test_bed.fire_runnable("hello", pool);
      System.out.println("hello fired");

      pool.join();
      System.out.println("Pool is idle, closing");

      pool.close();
      System.out.println("Pool closed");

      pool = new Thread_pool(); // test lazy instantiation.
      pool.deflate(true);

      System.out.println("\n\nNew thread pool is running:");
      System.out.println(pool.toString());

      test_bed.fire_runnable("lazy instantiation", pool);

      pool.join();
      Thread.sleep(250); // wait a bit for the
      // pool to decay back to 0 size

      test_bed.fire_runnable("lazy instantiation", pool);
      pool.join();
      pool.close();
    }

    // The argument must be final in order for it to be accessed
    // from the inner class.

    private void fire_runnable(final String id, final Thread_pool pool) {
      pool.execute(new Runnable() {
        public void run() {
          System.out.println("-------------Begin " + id);

          // System.out.println( "Pool state:\n" + pool );
          try {
            Thread.sleep(250);
          } catch (InterruptedException e) {}

          System.out.println("-------------End " + id);
        }
      });
    }
  }
}
