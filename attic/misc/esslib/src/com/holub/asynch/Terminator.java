package com.holub.asynch;

/**
 *  Objects of the <code>Terminator</code> class terminate
 *	a thread by sending it an
 *  <code>interrupt()<code> message. One use is to terminate a
 *	wait operation in such a way that you can detect
 *	whether or not the wait timed out:
 *	<pre>
 *	new Thread()
 *	{	public void run()
 *		{	try
 *			{ 	new Terminator( this, 1000 );
 *				synchronized(this){ wait(); }
 *				System.out.println("Notified");
 *			}
 *			catch( InterruptedException e )
 *			{	System.out.println("Timed out");
 *			}
 *		}
 *	}.start();
 *	</pre>
 *	Note that all you have to do is create a new
 *	Terminator. You do not have to keep a reference
 *	to it.
 * <br>
 * <br>
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

public final class Terminator extends Thread {
  private final Thread victim;
  private final long timeout;

  /** Creates an object that terminates the
   *  <code>victim</code> thread by sending it an
   *	interrupt message after <code>timeout</code>
   *  milliseconds have elapsed.
   */

  public Terminator(Thread victim, long timeout) {
    this.victim = victim;
    this.timeout = timeout;
    setDaemon(true);
    setPriority(getThreadGroup().getMaxPriority());
    start();
  }

  /** Do not call.
  */
  public void run() {
    try {
      sleep(timeout);
      victim.interrupt();
    } catch (InterruptedException e) { /*ignore*/
    }
  }
/*
  private static final class Test {
    public static void main(String[] args) {
      new Thread() {
        public void run() {
          try {
            new Terminator(this, 1000);
            synchronized (this) {
              wait();
            }
            System.out.println("Notified");
          } catch (InterruptedException e) {
            System.out.println("Timed out");
          }
        }
      }
      .start();
    }
  }
  */
}
