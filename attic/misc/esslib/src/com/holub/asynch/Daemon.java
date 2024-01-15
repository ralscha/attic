package com.holub.asynch;
import java.util.*;

/** A generic deamon thread. Execute the `operation` objects
 *	<code>run()</code> method repeatedly until interrupted. Guarantees
 *  that the operation that you provide in the constructor will
 *  not be terminated by a premature JVM shut down, however.
 *	For example, the following code prints the time once
 *	a second. The program won't shut down while the time
 *	is actually being printed, but it can shut down at other
 *	times:
 *	<PRE>
 *		new Daemon
 *		(	new Runnable()	// operation
 *			{	public void run()
 *				{	System.out.println( new Date() );
 *				}
 *			},
 *			new Runnable()	// delay
 *			{	public void run()
 *				{	try
 *					{	Thread.currentThread().sleep(1000);
 *					}
 *					catch(InterruptedException e){}
 *				}
 *			}
 *		).start();
 *	</PRE>
 *	Note that the delay operation runs in parallel to the
 *	operation performed on the thread.
 *
 *  @param operation The operation to perform on the thread
 *	@param delay	 The run() method of this object is called
 *					 at the end of the iteration. It can
 *					 terminate the daemon by calling
 *					 <code>Thread.currentThread().interrupt()</code>.
 */

final class Daemon extends Thread {
  final Runnable operation;
  final Runnable delay;

  public Daemon(Runnable operation, Runnable delay) {
    setDaemon(true);
    this.operation = operation;
    this.delay = delay;
  }

  public void run() {
    while (!isInterrupted()) {
      new Thread() {
        public void run() {
          operation.run();
        }
      }
      .start();

      delay.run();
    }
  }

  //-----------------------------------------------------------------
  public static class Test {
    public static void main(String[] args) throws InterruptedException {
      new Daemon(new Runnable() // operation
      {
        public void run() {
          System.out.println(new Date());
        }
      }, new Runnable() // delay
      {
        public void run() {
          try {
            Thread.sleep(1000);
          } catch (InterruptedException e) {}
        }
      }).start();

      Thread.sleep(5000);
    }
  }
}
