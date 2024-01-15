package com.holub.asynch;
import java.util.*;

/**
 *	This reader/writer lock prevents reads from occurring while writes
 *	are in progress and vice versa, and also prevents multiple writes from
 *	happening simultaneously. Multiple read operations can run in parallel,
 *  however. Reads take priority over writes, so any read operations
 *  that are pending while a write is in progress will execute before
 *  any subsequent writes execute. Writes are guaranteed to execute in
 *  the order that they were requested&#151;the oldest request is processed
 *	first.
 *	<p>
 *  You should use the lock as follows:
 *	<pre>
 *	public class Data_structure_or_resource
 *	{
 *		Reader_writer lock = new Reader_writer();
 *
 *		public void access( ) throws InterruptedException
 *		{<b>    lock.request_read();
 *			try
 *			{</b>
 *				    // do the read/access operation here.<b>
 *			}
 *			finally
 *			{	lock.read_accomplished();
 *			}</b>
 *		}
 *
 *		public void modify( ) throws InterruptedException
 *		{<b>	lock.request_write();
 *			try 
 *			{</b>
 *				    // do the write/modify operation here.<b>
 *			}
 *			finally
 *			{	lock.write_accomplished();
 *			}</b>
 *		}
 *	}
 *	</pre>
 *
 * <p>The current implementation of `Reader_writer` doesn't support
 *		timeouts.
 *
 * <font size=-1>
 * This implementation is based on the one in Doug Lea's <i>Concurrent
 * Programming in Java</i> (Reading: Addison Wesley, 1997, pp. 300-303),
 * I've simplified the code (and cleaned it up) and added the nonblocking
 * acquisition methods.
 * I've also made the lock a stand-alone class rather than
 * a base class from which you have to derive. You might also want to
 * look at the very different implementation of the reader/writer lock
 * in Scott Oaks and Henry Wong's <i>Java Threads</i>
 * (Sebastopol [Calif.]: O'Reilly, 1997, pp. 180--187).
 * </font>
 *
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
 *
 * @author Allen I. Holub
 */

public final class Reader_writer {
  private int active_readers; // = 0
  private int waiting_readers; // = 0
  private int active_writers; // = 0

  /******************************************************************
   * I keep a linked list of writers waiting for access so that I
   * can release them in the order that the requests were received.
   * The size of this list is effectively the "waiting writers" count.
   *
   * Note that the monitor of the Reader_writer object itself is
   * used to lock out readers while writes are in progress, thus
   * there's no need for a separate "reader_lock."
   */
  private final LinkedList writer_locks = new LinkedList();

  /******************************************************************
   * Request the read lock. Block until a read operation can be
   * performed safely.
   * This call must be followed by a call to <code>read_accomplished()</code>
   * when the read operation completes.
   */
  public synchronized void request_read() throws InterruptedException {
    if (active_writers == 0 && writer_locks.size() == 0)
      ++active_readers;
    else {
      ++waiting_readers;
      wait(); //#read_block

      // The waiting_readers count is decremented in
      // notify_readers() when the waiting reader is
      // reactivated.
    }
  }

  /******************************************************************
   * This version of <code>read()</code> requests read access,
   * returns true if you get it. If it returns false, you may not
   * safely read from the guarded resource. If it returns true, you
   * should do the read, then call <code>read_accomplished</code> in
   * the normal way. Here's an example:
   *	<pre>
   *	public void read()
   *	{	if( lock.request_immediate_read() )
   *		{	try
   *			{
   *				// do the read operation here
   *			}
   *			finally
   *			{	lock.read_accomplished();
   *			}
   *		}
   *		else
   *			// couldn't read safely.
   *	}
   *	</pre>
   */
  public synchronized boolean request_immediate_read() {
    if (active_writers == 0 && writer_locks.size() == 0) {
      ++active_readers;
      return true;
    }
    return false;
  }
  /******************************************************************
   * Release the lock. You must call this method when you're done
   * with the read operation.
   */
  public synchronized void read_accomplished() {
    if (--active_readers == 0)
      notify_writers();
  }
  /******************************************************************
   * Request the write lock. Block until a write operation can be
   * performed safely. Write requests are guaranteed to be
   * executed in the order received. Pending read requests take
   * precedence over all write requests.
   * This call must be followed by a call to <code>write_accomplished()</code>
   * when the write operation completes.
   */
  public void request_write() throws InterruptedException {
    // If this method was synchronized, there'd be a nested-monitor
    // lockout problem: We have to acquire the lock for "this" in
    // order to modify the fields, but that lock must be released
    // before we start waiting for a safe time to do the writing.
    // If request_write() were synchronized, we'd be holding
    // the monitor on the Reader_writer lock object while we were
    // waiting. Since the only way to be released from the wait is
    // for someone to call either read_accomplished()
    // or write_accomplished() (both of which are synchronized),
    // there would be no way for the wait to terminate.

    Object lock = new Object(); //#lock_create
    synchronized (lock) {
      synchronized (this) {
        boolean okay_to_write = writer_locks.size() == 0 && active_readers == 0 && active_writers == 0;
        if (okay_to_write) {
          ++active_writers;
          return; // the "return" jumps over the "wait" call
        }

        writer_locks.addLast(lock); // Note that the
        // active_writers count
        // is incremented in
        // notify_writers() when
        // this writer gets control
      }
      lock.wait();
    }
  }
  /******************************************************************
   * This version of the write request returns false immediately (without
   * blocking) if any read or write operations are in progress and
   * a write isn't safe; otherwise, it returns true and acquires the
   * resource. Use it like this:
   *	<pre>
   *	public void write()
   *	{	if( lock.request_immediate_write() )
   *		{	try
   *			{
   *				// do the write operation here
   *			}
   *			finally
   *			{	lock.write_accomplished();
   *			}
   *		}
   *		else
   *			// couldn't write safely.
   *	}
   */
  synchronized public boolean request_immediate_write() {
    if (writer_locks.size() == 0 && active_readers == 0 && active_writers == 0) {
      ++active_writers;
      return true;
    }
    return false;
  }
  /******************************************************************
   * Release the lock. You must call this method when you're done
   * with the read operation.
   */
  public synchronized void write_accomplished() {
    // The logic here is more complicated than it appears.
    // If readers have priority, you'll  notify them. As they
    // finish up, they'll call read_accomplished(), one at
    // a time. When they're all done, read_accomplished() will
    // notify the next writer. If no readers are waiting, then
    // just notify the writer directly.

    --active_writers;
    if (waiting_readers > 0) // priority to waiting readers
      notify_readers();
    else
      notify_writers();
  }
  /******************************************************************
   * Notify all the threads that have been waiting to read.
   */
  private void notify_readers() // must be accessed from a
  { //	synchronized method
    active_readers += waiting_readers;
    waiting_readers = 0;
    notifyAll();
  }
  /******************************************************************
   * Notify the writing thread that has been waiting the longest.
   */
  private void notify_writers() // must be accessed from a
  { //	synchronized method
    if (writer_locks.size() > 0) {
      Object oldest = writer_locks.removeFirst();
      ++active_writers;
      synchronized (oldest) {
        oldest.notify();
      }
    }
  }

  /*******************************************************************
   * The <code>Test</code> class is a unit test for the other
   * code in the current file. Run the test with:
   * <pre>
   * java com.holub.asynch.Reader_writer\$Test
   * </pre>
   * (the backslash isn't required with windows boxes),
   * and don't include this class file in your final distribution.
   * Though the output could vary in trivial ways depending on
   * system timing. The read/write order should be exactly the
   * same as in the following sample:
   * <pre>
   *	Starting w/0
   *	                w/0 writing
   *	Starting r/1
   *	Starting w/1
   *	Starting w/2
   *	Starting r/2
   *	Starting r/3
   *	                w/0 done
   *	Stopping w/0
   *	                r/1 reading
   *	                r/2 reading
   *	                r/3 reading
   *	                r/1 done
   *	Stopping r/1
   *	                r/2 done
   *	                r/3 done
   *	Stopping r/2
   *	Stopping r/3
   *	                w/1 writing
   *	                w/1 done
   *	Stopping w/1
   *	                w/2 writing
   *	                w/2 done
   *	Stopping w/2
     * </pre>
   */
  public static class Test {
    Resource resource = new Resource();

    /**
     * The Resource class simulates a simple locked resource.
     * The read operation simply pauses for .1 seconds.
     * The write operation (which is typically higher overhead)
     * pauses for .5 seconds. Note that the use of <code>try...finally</code>
     * is not critical in the current test, but it's good style
     * to always release the lock in a <code>finally</code> block
     * in real code.
     */
    static class Resource {
      Reader_writer lock = new Reader_writer();

      public void read(String reader) {
        try {
          lock.request_read();
          System.out.println("\t\t" + reader + " reading");
          try {
            Thread.sleep(100);
          } catch (InterruptedException e) {}
          System.out.println("\t\t" + reader + " done");
        } catch (InterruptedException exception) {
          System.out.println("Unexpected interrupt");
        } finally {
          lock.read_accomplished();
        }
      }

      public void write(String writer) {
        try {
          lock.request_write();
          System.out.println("\t\t" + writer + " writing");
          try {
            Thread.sleep(500);
          } catch (InterruptedException e) {}
          System.out.println("\t\t" + writer + " done");
        } catch (InterruptedException exception) {
          System.out.println("Unexpected interrupt");
        } finally {
          lock.write_accomplished();
        }
      }

      public boolean read_if_possible() {
        if (lock.request_immediate_read()) {
          // in the real world, you'd do the read here
          lock.read_accomplished();
          return true;
        }
        return false;
      }

      public boolean write_if_possible() {
        if (lock.request_immediate_write()) {
          // in the real world, you'd do the write here
          lock.write_accomplished();
          return true;
        }
        return false;
      }
    }
    /**
     * A simple reader thread. Just reads from the resource, passing
     * it a unique string id.
     */
    class Reader extends Thread {
      private String name;
      Reader(String name) {
        this.name = name;
      }
      public void run() {
        System.out.println("Starting " + name);
        resource.read(name);
        System.out.println("Stopping " + name);
      }
    }
    /**
     * A simple writer thread. Just writes to the resource, passing
     * it a unique string id.
     */
    class Writer extends Thread {
      private String name;
      Writer(String name) {
        this.name = name;
      }
      public void run() {
        System.out.println("Starting " + name);
        resource.write(name);
        System.out.println("Stopping " + name);
      }
    }

    /**
     * Test by creating several readers and writers. The initial
     * write operation (w/0) should complete before the first read (r/1)
     * runs. Since readers have priority, r/2 and r/3 should run before
     * w/1, and r/1, r/2, and r/3 should all run in parallel.
     * When all three reads complete, w/1 and w/2 should execute
     * sequentially in that order.
     */

    public Test() {
      if (!resource.read_if_possible())
        System.out.println("Immediate read request failed");
      if (!resource.write_if_possible())
        System.out.println("Immediate write request failed");

      new Writer("w/0").start();
      new Reader("r/1").start();
      new Writer("w/1").start();
      new Writer("w/2").start();
      new Reader("r/2").start();
      new Reader("r/3").start();
    }
/*
    static public void main(String[] args) {
      //Test t = new Test();
    }
    */
  }
}
