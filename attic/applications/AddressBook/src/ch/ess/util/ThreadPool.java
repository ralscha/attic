/*
 * $Header: c:/cvs/pbroker/src/ch/ess/util/ThreadPool.java,v 1.2 2002/04/02 18:27:21 sr Exp $
 * $Revision: 1.2 $
 * $Date: 2002/04/02 18:27:21 $
 */

package ch.ess.util;

/**
 * A generic implementation of a thread pool. Use it like this:
 *
 *      <PRE>
 *      ThreadPool pool = new ThreadPool();
 *      pool.execute
 *      (       new Runnable()
 *              {       public void run()
 *                      {       // execute this function on an existing
 *                              // thread from the pool.
 *                      }
 *              }
 *      );
 *      </PRE>
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
 * of a <code>join()</code> operations simply by calling <code>wait()</code>
 * on the thread pool itself. The following code blocks until all the
 * threads in the thread pool are idle:
 *
 * <PRE>
 *      ThreadPool pool = new ThreadPool();
 *  //...
 *      try
 *      {       synchronized(pool){ pool.wait(); }
 *  }
 *  catch( InterruptedException exception )
 *      {       // error action goes here
 *  }
 * </PRE>
 *  For convenience, the foregoing code is implemented as a {@link #join}
 *  method.
 *
 *  You can remove a thread from the pool by interrupting it. Often the
 *      easiest way to do this is to enqueue a request that issues the
 *      interrupt:
 *      <PRE>
 *      some_pool.execute
 *      (       new Runnable()
 *               {       public void run()
 *                      {       interrupt();    // Remove this thread from the pool
 *                      }
 *              }
 *      );
 *      </PRE>
 *      The {@link deflate} method removes all threads that
 *      were created over and above the initial size, and is usually a better
 *      choice than an explicit interrupt, however.
 * <br>
 * <br>
 * <p>History (modifications since book was published)
 * <table>
 * <tr>06/05/00<td></td>Cleaned up constructor code so that all the
 *                                              PooledThreads threads are created before
 *                                              constructor returns. Fixed a bug in the
 *                                              idle-thread counting mechanism.
 *      <td></td></tr>
 * </table>
 *
 * <table border=1 cellspacing=0 cellpadding=5><tr><td><font size=-1><i>
 * <center>(c) 2000, Allen I. Holub.</center>
 * <p>
 * This code may not be distributed by yourself except in binary form,
 * incorporated into a java .class file.
 * </font></i></td></tr></table>
 *
 *
 * @author Allen I. Holub
 */
import org.apache.log4j.*;


/**
 * Class ThreadPool
 *
 * @version $Revision: 1.2 $ $Date: 2002/04/02 18:27:21 $
 */
public final class ThreadPool extends ThreadGroup {

  private static final Logger LOG = Logger.getLogger(ThreadPool.class.getName());
  private final BlockingQueue pool = new BlockingQueue();
  private /*final*/ int initial_size = 0;
  private /*final*/ int maximum_size = 0;
  private volatile int idle_threads = 0;
  private volatile int pool_size = 0;
  private boolean has_closed = false;
  private boolean deflate = false;
  private int joiners = 0;
  private Object PooledThreads_all_running = new Object();
  private static int group_number = 0;
  private static int thread_id = 0;

  /**
   * These are the objects that wait to be activated. They are
   * typically blocked on an empty queue. You post a Runnable
   * object to the queue to release a thread, which will execute
   * the run() method from that object. All Pooled-thread objects
   * will be members of the thread group the comprises the tread
   * pool.
   */
  private final class PooledThread extends Thread {

    public PooledThread() {
      super(ThreadPool.this, "T" + thread_id);
    }

    public void run() {

      synchronized (ThreadPool.this) {
        ++idle_threads;
        ++pool_size;

        if ((PooledThreads_all_running != null) && (initial_size == pool_size)) {
          synchronized (PooledThreads_all_running) {
            PooledThreads_all_running.notify();
          }
        }
      }

      try {

        // The ThreadPool constructor synchronizes on the
        // ThreadPool object that's being constructed until
        // that Thread-pool object is fully constructed. The
        // PooledThread objects are both created and started
        // from within that constructor. The following
        // "synchronized" statement forces the pooled threads
        // to block until initialization is complete and the
        // constructor gives up the lock. This synchronization
        // is essential on a multiple-CPU machine to make sure
        // that the various CPU caches are in synch with each
        // other.
        while (!isInterrupted() && !has_closed) {
          try {
            Runnable command = (Runnable)pool.dequeue();

            synchronized (ThreadPool.this) {
              --idle_threads;
            }

            command.run();
          } catch (InterruptedException e) {    // Stop request
            break;
          } catch (BlockingQueue.Closed e) {    // Ignore it. The thread pool is shutting down.
          } catch (Exception e) {
            LOG.error("Ignoring exception thrown from " + " user-supplied thread-pool action:", e);
          } finally {
            synchronized (ThreadPool.this) {
              ++idle_threads;

              // If none of the threads in the pool are
              // doing anything, and some thread has
              // join()ed the current thread pool,
              // notify the joined threads.
              if (joiners > 0) {
                if (idle_threads == pool_size) {
                  ThreadPool.this.notify();
                }
              }
            }
          }
        }
      } finally {
        synchronized (ThreadPool.this) {
          --idle_threads;
          --pool_size;
        }
      }
    }
  }

  /**
   *    Create a thread pool with initial_thread_count threads
   *    in it. The pool can expand to contain additional threads
   *   if they are needed.
   *
   *    @param <b>initial_thread_count</b>      The initial thread count.
   *                    If the initial count is greater than the maximum, it is
   *                    silently truncated to the maximum.
   *   @param <b>maximum_thread_count</b> specifies the maximum number
   *                    of threads that can be in the pool. A maximum of 0
   *                    indicates that the      pool will be permitted to grow
   *                    indefinitely.
   */
  public ThreadPool(int initial_thread_count, int maximum_thread_count) {

    super("ThreadPool" + group_number++);

    // I'm synchronizing, here, to prevent the PooledThread objects
    // that are created below from being able to access the current
    // object until it's fully initialized. They synchronize on
    // "this" at the top of their run() methods. The wait call
    // prevents the contructor from returning until all the threads
    // are active. [The notify is in the PooledThreads run() method.]
    synchronized (this) {
      initial_size = initial_thread_count;
      maximum_size = (maximum_thread_count > 0) ? maximum_thread_count : Integer.MAX_VALUE;

      if (initial_thread_count > maximum_size) {
        throw new IllegalArgumentException("ThreadPool: initial_thread_count > maximum_size");
      }

      for (int i = initial_thread_count; --i >= 0; ) {
        new PooledThread().start();
      }
    }

    synchronized (PooledThreads_all_running) {
      try {
        PooledThreads_all_running.wait();

        PooledThreads_all_running = null;
      } catch (InterruptedException e) {}
    }
  }

  /**
   *    Create a dynamic Thread pool as if you had used
   *    <code>ThreadPool(0, true);</code>
   */
  public ThreadPool() {

    super("ThreadPool" + group_number++);

    this.maximum_size = 0;
  }

  /**
   *   Execute the <code>run()</code> method of the Runnable object on a thread
   *    in the pool. A new thread is created if the pool is
   *    empty and the number of threads in the pool is not at the
   *   maximum.
   *
   *    @throws ThreadPool.Closed if you try to execute an action
   *                    on a pool to which a close() request has been sent.
   */
  public synchronized void execute(Runnable action) throws Closed {

    // You must synchronize on the pool because the PooledThread's
    // run method is asynchronously dequeueing elements. If we
    // didn't synchronize, it would be possible for the is_empty()
    // query to return false, and then have a PooledThread sneak
    // in and put a thread on the queue (by doing a blocking dequeue).
    // In this scenario, the number of threads in the pool could
    // exceed the maximum specified in the constructor. The
    // test for pool_size < maximum_size is outside the synchronized
    // block because I didn't want to incur the overhead of
    // synchronization unnecessarily. This means that I could
    // occasionally create an extra thread unnecessarily, but
    // the pool size will never exceed the maximum.
    if (has_closed) {
      throw new Closed();
    }

    if ((pool_size < maximum_size) && (pool.waiting_threads() == 0)) {   //#check_1    
      synchronized (pool) {   //#check_2      
        if ((pool_size < maximum_size) && (pool.waiting_threads() == 0)) {
          new PooledThread().start();    // Add thread to pool
        }
      }
    }

    pool.enqueue((Object)action);    // Attach action to the thread
  }

  /**
   *   Execute the <code>execute()</code> method of the <code>Command</code> object
   *   on a thread in the pool. A new thread is created if the pool is
   *    empty and the number of threads in the pool is not at the
   *   maximum. Note that the argument's value at the point of execution
   *    is what's used. It can change between the time that <code>execute()</code> is
   *    called and the time whin the action is actually executed.
   *
   *    @throws ThreadPool.Closed if you try to execute an action
   *                    on a pool to which a close() request has been sent.
   */
  public final synchronized void execute(final Command action, final Object argument) throws Closed {

    execute(new Runnable() {

      public void run() {
        action.execute(argument);
      }
    });
  }

  /**
   *  Objects of class ThreadPool.Closed are thrown if you try to
   *  execute an action on a closed ThreadPool.
   */
  public final class Closed extends RuntimeException {

    Closed() {
      super("Tried to execute operation on a closed ThreadPool");
    }
  }

  /**
   *    Kill all the threads waiting in the thread pool, and arrange
   *    for all threads that came out of the pool, but which are working,
   *    to die natural deaths when they're finished with whatever they're
   *    doing. Actions that have been passed to execute() but which
   *    have not been assigned to a thread for execution are discarded.
   *    <p>
   *   No further operations are permitted on a closed pool, though
   *    closing a closed pool is a harmless no-op.
   */
  public synchronized void close() {

    has_closed = true;

    pool.close();    // release all waiting threads
  }

  /**
   *  Wait for the pool to become idle. This method is a convenience
   *  wrapper for the following code:
   *  <PRE>
   *    ThreadPool pool = new ThreadPool();
   *   //...
   *    try
   *    {       synchronized(pool){ pool.wait(); }
   *   }
   *   catch( InterruptedException exception )
   *    {       // eat it.
   *   }
   *  </PRE>
   */
  public synchronized void join() {

    try {
      ++joiners;

      wait();

      --joiners;
    } catch (InterruptedException exception) {    // eat it
    }
  }

  /**
   *  If the argument is true,
   *  discard threads as they finish their operations until the
   *  initial thread count passed to the constructor. That is, the
   *  number of threads in the pool will not go below the initial
   *  count, but if the number of threads in the pool expanded, then
   *  the pool size will shrink to the initial size as these extra
   *  threads finish whatever they're doing. It's generally best to
   *  set this flag before any threads in the pool are activated.
   *  If the argument is false (the default behavior of the thread
   *  pool), then treads created above the initial count remain
   *  in the pool indefinitely.
   */
  public synchronized void deflate(boolean do_deflate) {

    if (deflate = do_deflate) {
      while (idle_threads > initial_size) {

        // Terminate the pooled thread by having
        // run (which runs on the pooled thread)
        // issue an interrupt. You could also terminate
        // the thread with some sort of "sentinel"
        // strategy. For example, you could enqueue
        // "null," and then rewrite the PooledThread's
        // run() method to recognize that as a terminate
        // request. It seemed like interrupting the
        // thread was a more generic solution, however,
        // since this option is open to users of the
        // thread pool as well.
        pool.enqueue(new Runnable() {

          public void run() {
            Thread.currentThread().interrupt();
          }
        });
      }
    }
  }

  /* ============================================================== */

  /**
   * Class Test
   *
   * @version $Revision: 1.2 $ $Date: 2002/04/02 18:27:21 $
   */
  public static final class Test {

    private static ThreadPool pool = new ThreadPool(10, 10);

    public static void main(String[] args) {

      Test test_bed = new Test();

      test_bed.fire_runnable("hello");
      System.out.println("hello fired");
      test_bed.fire_command("world");
      System.out.println("world fired, now waiting for idle pool");
      pool.join();
      System.out.println("Pool is idle, closing");
      pool.close();
      System.out.println("Pool closed");
    }

    // The argument must be final in order for it to be accessed
    // from the inner class.
    private void fire_runnable(final String id) {

      pool.execute(new Runnable() {

        public void run() {

          System.out.println("Starting " + id);

          try {
            Thread.currentThread().sleep(250);
          } catch (InterruptedException e) {}

          System.out.println("Stoping " + id);
        }
      });
    }

    private void fire_command(String id) {

      pool.execute(new Command() {

        public void execute(Object id) {

          System.out.println("Starting " + id);

          try {
            Thread.currentThread().sleep(250);
          } catch (InterruptedException e) {}

          System.out.println("Stoping " + id);
        }
      }, "world");
    }
  }
}
