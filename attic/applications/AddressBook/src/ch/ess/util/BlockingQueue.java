/*
 * $Header: c:/cvs/pbroker/src/ch/ess/util/BlockingQueue.java,v 1.2 2002/04/02 18:27:20 sr Exp $
 * $Revision: 1.2 $
 * $Date: 2002/04/02 18:27:20 $
 */

package ch.ess.util;

import java.util.*;


/**
 * This is a thread-safe queue that blocks automatically if you
 *      try to dequeue from an empty queue. It's based on a linked list,
 *  so will never fill. (You'll never block on a queue-full condition
 *      because there isn't one.)
 *
 *      <p>
 *      This class uses the <code>LinkedList</code> class, introduced into
 *      the JDK at version 1.2. It will not work with earlier releases.
 *
 * <br><br>
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
public final class BlockingQueue {

  private LinkedList elements = new LinkedList();
  private boolean closed = false;
  private boolean reject_enqueue_requests = false;
  private int waiting_threads = 0;
  private int maximum_size = 0;    // no limit

  /**
   * The BlockingQueue.Exception class is the base class of the
   * other exception classes, provided so that you can catch any
   * queue-related error with a single <code>catch</code> statement.
   */
  public class Exception extends RuntimeException {

    public Exception(String s) {
      super(s);
    }
  }

  /**
   *  The Closed exception is thrown if you try to used an explicitly
   *  closed queue. See {@link #close}.
   */
  public class Closed extends Exception {

    private Closed() {
      super("Tried to access closed BlockingQueue");
    }
  }

  /**
   *  The full exception is thrown if you try to enqueue an item in
   *  a size-limited queue that's full.
   */
  public class Full extends Exception {

    private Full() {
      super("Attempt to enqueue item to full BlockingQueue.");
    }
  }

  /**
   *  Convenience constructor, creates a queue with no upper limit
   *  on the size.
   */
  public BlockingQueue() {
  }

  /**
   *  Convenience constructor, creates a queue with no upper limit
   *  on the size.
   */
  public BlockingQueue(int maximum_size) {
    this.maximum_size = maximum_size;
  }

  /**
   *    Enqueue an object that will remain in the queue until it is
   *   dequeued.
   */
  public final synchronized void enqueue(Object new_element) throws Closed, Full {

    if (closed || reject_enqueue_requests) {
      throw new Closed();
    }

    // Detect a full queue. Queues of size 0 are allowed to grow
    // indefinitely.
    if ((maximum_size != 0) && (elements.size() >= maximum_size)) {
      throw new Full();
    }

    elements.addLast(new_element);
    notify();    //#notify
  }

  /**
   *    Enqueue an object that will remain in the queue for at most
   *    "timeout" milliseconds. The <code>run()</code> method of the
   *    <code>on_removal</code> object is called if the object is
   *    removed in this way.
   *
   *    If a given object is in the queue more than once, then the first
   *    occurrence of the object is removed.
   *
   *  @param new_element        The object to enqueue
   *  @param timeout            The maximum time that the object will spend
   *                                            in the queue (subject to the usual variations
   *                                            that can occur if a higher-priority thread
   *                                            happens to be running when the timeout occurs).
   *  @param on_removal If non-null, the <code>run()</code> method
   *                                            is called if the object is removed due to
   *                                            a timeout. If <code>null</code>, nothing
   *                                            in particular is done when the object is
   *                                            removed.
   */

  //#enqueue.with.timeout
  public final synchronized void enqueue(final Object new_element, final long timeout, final Runnable on_removal) {

    enqueue(new_element);
    new Thread() {

      {
        setDaemon(true);
      }    // instance initializer, effectively

      // a constructor for this object.
      public void run() {

        try {
          boolean found;

          sleep(timeout);

          synchronized (BlockingQueue.this) {
            found = elements.remove(new_element);

            if (found && (elements.size() == 0) && reject_enqueue_requests) {
              close();    // Have just removed final item,
            }
          }

          if (found && (on_removal != null)) {
            on_removal.run();
          }
        } catch (InterruptedException e) {    /* can't happen */
        }
      }
    }.start();
  }

  /**
   *  Convenience method, calls {@link enqueue(Object,long,Runnable)} with
   *  a null <code>on_removal</code> reference.
   */
  public final synchronized void enqueue(final Object new_element, final long timeout) {
    enqueue(new_element, timeout, null);
  }

  /**
   *  Enqueue an item, and thereafter reject any requests to enqueue
   *  additional items. The queue is closed automatically when the
   *  final item is dequeued.
   */
  public final synchronized void enqueue_final_item(Object last) throws Closed {

    enqueue(last);

    reject_enqueue_requests = true;
  }    //#final.end

  /**
   *    Dequeues an element; blocks if the queue is empty
   *    (until something is enqueued). Be careful of nested-monitor
   *  lockout if you call this function. You must ensure that
   *    there's a way to get something into the queue that does
   *  not involve calling a synchronized method of whatever
   *  class is blocked, waiting to dequeue something. A timeout is
   *    not supported because of a potential race condition (see text).
   *  You can {@link Thread#interrupt interrupt} the dequeueing thread
   *  to break it out of a blocked dequeue operation, however.
   *
   *    @param timeout  Time-out value in milliseconds. An
   *                                    <code>ArithmeticException</code> is thrown
   *                                    if this value is greater than a million years or so.
   *                                    Use {@link Semaphore#FOREVER} to wait forever.
   *
   *  @see #enqueue
   *  @see #drain
   *  @see #nonblocking_dequeue
   *
   *    @return the dequeued object or null if the wait timed out and
   *                    nothing was dequeued.
   *
   *  @throws InterruptedException      if interrupted while blocked
   *  @throws Semaphore.Timed_out               if timed out while blocked
   *  @throws BlockingQueue.Closed      on attempt to dequeue from a closed queue.
   */
  public final synchronized Object dequeue(long timeout) throws InterruptedException, Closed, Semaphore.Timed_out {

    long expiration = (timeout == Semaphore.FOREVER) ? Semaphore.FOREVER : System.currentTimeMillis() + timeout;

    if (closed) {
      throw new Closed();
    }

    try {

      // If the queue is empty, wait. I've put the spin lock
      // inside an "if" so that the waiting_threads count doesn't
      // jitter while inside the spin lock. A thread is not
      // considered to be done waiting until it's actually
      // acquired an element or the timeout is exceeded.
      if (elements.size() <= 0) {
        ++waiting_threads;    //#waiting_up

        while (elements.size() <= 0) {
          wait(timeout);    //#wait

          if (System.currentTimeMillis() > expiration) {
            --waiting_threads;

            throw new Semaphore.Timed_out("Timed out waiting to dequeue " + "from BlockingQueue");
          }

          if (closed) {
            --waiting_threads;

            throw new Closed();
          }
        }

        --waiting_threads;    //#waiting_down
      }

      Object head = elements.removeFirst();

      if ((elements.size() == 0) && reject_enqueue_requests) {
        close();    // just removed final item, close the queue.
      }

      return head;
    } catch (NoSuchElementException e) {   // Shouldn't happen    
      throw new Error("Internal error (com.holub.asynch.BlockingQueue)");
    }
  }

  /**
   *  Convenience method, calls {@link dequeue(long)} with a timeout of
   *  Semaphore.FOREVER.
   */
  public final synchronized Object dequeue() throws InterruptedException, Closed, Semaphore.Timed_out {
    return dequeue(Semaphore.FOREVER);
  }

  /**
   *    The is_empty() method is inherently unreliable in a
   *   multithreaded situation. In code like the following,
   *    it's possible for a thread to sneak in after the test but before
   *    the dequeue operation and steal the element you thought you
   *    were dequeueing.
   *    <PRE>
   *    BlockingQueue queue = new BlockingQueue();
   *    //...
   *    if( !some_queue.is_empty() )
   *            some_queue.dequeue();
   *    </PRE>
   *    To do the foregoing reliably, you must synchronize on the
   *    queue as follows:
   *    <PRE>
   *    BlockingQueue queue = new BlockingQueue();
   *    //...
   *    synchronized( queue )
   *    {   if( !some_queue.is_empty() )
   *                    some_queue.dequeue();
   *    }
   *    </PRE>
   *    The same effect can be achieved if the test/dequeue operation
   *    is done inside a synchronized method, and the only way to
   *    add or remove queue elements is from other synchronized
   *    methods.
   */
  public final boolean is_empty() {
    return elements.size() <= 0;
  }

  /**
   *  Return the number of threads waiting for a message on the
   *  current queue. See {@link is_empty} for warnings about
   *  synchronization.
   */
  public final int waiting_threads() {
    return waiting_threads;
  }

  /**
   *  Close the blocking queue. All threads that are blocked
   *  [waiting in dequeue() for items to be enqueued] are released.
   *  The {@link dequeue()} call will throw a {@link BlockingQueue.Closed}
   *  runtime
   *  exception instead of returning normally in this case.
   *  Once a queue is closed, any attempt to enqueue() an item will
   *  also result in a BlockingQueue.Closed exception toss.
   *
   *  The queue is emptied when it's closed, so if the only references
   *  to a given object are those stored on the queue, the object will
   *  become garbage collectible.
   */
  public synchronized void close() {

    closed = true;
    elements = null;

    notifyAll();
  }

  /**
   *  Unit test for the BlockingQueue class.
   */
  public static final class Test {

    private static BlockingQueue queue = new BlockingQueue();
    private boolean timed_out = false;

    public static void main(String[] args) throws InterruptedException {
      new Test();
    }

    public Test() throws InterruptedException {

      // Test the enqueue timeout. Wait two seconds for a
      // dequeue operation that will never happen.
      queue.enqueue("Enqueue this String", 2000,    // two seconds
                    new Runnable() {

        public void run() {

          System.out.println("Enqueue timeout worked.");

          timed_out = true;
        }
      });
      Thread.currentThread().sleep(2500);

      if (!timed_out) {
        System.out.println("Enqueue timeout failed.");
      }

      // Create a thread that enqueues numbers and another
      // that dequeues them
      Thread enqueueing = new Thread() {

        public void run() {

          for (int i = 10; --i >= 0; ) {
            queue.enqueue("" + i);
          }

          queue.enqueue_final_item(null);
        }
      };
      Thread dequeueing = new Thread() {

        public void run() {

          try {
            String s;

            while ((s = (String)queue.dequeue()) != null) {
              System.out.println(s);
            }
          } catch (InterruptedException e) {
            System.out.println("Unexpected InterruptedException");
          }

          boolean close_handled_correctly = false;

          try {
            queue.enqueue(null);
          } catch (BlockingQueue.Closed e) {
            close_handled_correctly = true;
          }

          if (close_handled_correctly) {
            System.out.println("Close handled");
          } else {
            System.out.println("Error: Close failed");
          }
        }
      };

      dequeueing.start();
      enqueueing.start();
    }
  }
}
