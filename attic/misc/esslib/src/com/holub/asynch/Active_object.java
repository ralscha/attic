package com.holub.asynch;

import com.holub.tools.*;

/***********************************************************************
 *	A dispatcher for use in implementing active objects. Create and
 *	start up a dispatcher like this:
 *	<pre>
 *	Active_object dispatcher = new Active_object();
 *	dispatcher.start();
 *	</pre>
 *	Ask the active object to do something for you like this:
 *	<pre>
 *	dispatcher.dispatch
 *	(	new Runnable()
 *		{	public void run()
 *			{	System.out.println("hello world");
 *			}
 *		}
 *	);
 * </pre>
 * When you're done with the dispatcher, close it:
 * <pre>
 * dispatcher.close();
 * </pre>
 * Variations on these themes are also possible. See the main
 * documentation for more details.
 * <br><br>
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

public class Active_object extends Thread {
  private Blocking_queue requests = new Blocking_queue();

  /******************************************************************
   *	Create an active object. The object is a Daemon thread that
   *  waits for {@link #dispatch()} requests, then executes them in
   *	the order that they were enqueued. Since the thread is a 
   *	daemon, it will not keep the process alive.
   */
  public Active_object() {
    setDaemon(true);
  }
  /******************************************************************
   * This fact that this method is
   * public is an artifact of extending Thread. Do not call this method.
   * The dispatcher's event loop is found here.
   */
  public void run() {
    try {
      Runnable the_request;
      while ((the_request = (Runnable) (requests.dequeue())) != null) {
        the_request.run();
        the_request = null;
        yield(); // give other threads a chance to run
      }
    } catch (InterruptedException e) { // Thread was interrupted while blocked on a dequeue,
      // Treat it as a close request and ignore it.
    }
  }

  /*****************************************************************
   *	Cause an operation to be performed on the current active-object's
   *	event-handler thread. Operations are executed serially in the
   *	order received.
   *
   *  @param	operation
   *			A <code>Runnable</code> "command" object that encapsulates
   *			the operation to perform. If <code>operation</code> is
   *			<code>null</code> then the active object will
   *			shut itself down when that request is dequeued (in its
   *			turn). Any operations posted after a <code>null</code>
   *			request is dispatched are not executed.
   *
   *	@throws	Blocking_queue.Closed if you attempt to dispatch
   *			on a closed object.
   *
   *  @see	com.holub.tools.Generic_command
   **/

  public final void dispatch(Runnable operation) {
    requests.enqueue(operation);
  }

  /******************************************************************
   *  Request that the active object execute the <code>execute()</code>
   *  method of the <code>Command</code> object.
   *  Otherwise works like {@link dispatch(Runnable)}.
   */

  public final synchronized void dispatch(final Command action, final Object argument) throws Blocking_queue.Closed {
    dispatch(new Runnable() {
      public void run() {
        action.fire(argument);
      }
    });
  }

  /*****************************************************************
   * Close the active object (render it unwilling to accept new
   * "dispatch" requests.) All pending requests are executed, but
   * new ones are not accepted. This method returns immediately.
   * before the pending requests have been processed
   * <code>Active_object</code> shuts down. You can block until
   * the pending requests are handled by sending the <code>Active_object</code>
   * object a <code>join()</code> message:
   * <pre>
   *		Active_object dispatcher = new Active_object();
   *		//...
   *		dispatcher.close();	// cause it to reject new requests
   *		dispatcher.join();	// wait for existing request to be processed
   * </pre>
   * <p>
   * You can also cause the Active_object to terminate gracefully
   * (without blocking) by calling <code>dispatch(null)</code>.
   * Any requests that are "dispatched" after a <code>dispatch(null)</code>.
   * is issued are silently ignored, however.
   * <p>
   * Attempts to close a closed queue are silently ignored.
   **/

  public final void close() {
    requests.enqueue_final_item(null);
  }
}
