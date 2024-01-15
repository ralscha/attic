package com.holub.asynch;

import java.util.*;

/***********************************************************************
 *	<p>A synchronous notification dispatcher, executes a sequence of
 *  operations sequentially. Allows two sets of linked operations to
 *  be interspersed and effectively executed in paralelly, but without
 *	using multiple threads for this purpose.
 *
 *  <p>This class is built on the JDK 1.2x LinkedList class, which must
 *	be present in the system.
 *
 * <br>
 * <br>
 * <table border=1 cellspacing=0 cellpadding=5><tr><td><font size=-1><i>
 * <center>(c) 2000, Allen I. Holub.</center>
 * <p>
 * This code may not be distributed by yourself except in binary form,
 * incorporated into a java .class file. You may use this code freely
 * for personal purposes, but you may not incorporate it into any
 * commercial product without express permission of Allen I. Holub in
 * writing.
 * </td></tr></table>
 *
 * @author Allen I. Holub
 */

public class Synchronous_dispatcher {
  private LinkedList events = new LinkedList(); // Runnable objects

  /** Add a new handler to the <i>end</i> of the current list of
   *  subscribers.
   */

  public synchronized void add_handler(Runnable handler) {
    events.add(handler);
  }

  /** Add several listeners to the dispatcher, distributing them
   *	as evenly as possible with respect to the current list.
   */

  public synchronized void add_handler(Runnable[] handlers) {
    if (events.size() == 0) {
      for (int i = 0; i < handlers.length;)
        events.add(handlers[i++]);
    } else {
      Object[] larger = events.toArray();
      Object[] smaller = handlers;

      if (larger.length < smaller.length) // swap them
        {
        Object[] tmp = larger;
        larger = smaller;
        smaller = tmp;
      }

      int distribution = larger.length / smaller.length;

      LinkedList new_list = new LinkedList();

      int large_source = 0;
      int small_source = 0;

      // Could use the iterator's add() method instead of
      // building an array, but the current implementation
      // will work even for data structures whose iterators
      // don't support add().

      while (small_source < smaller.length) {
        for (int skip = 0; skip < distribution; ++skip)
          new_list.add(larger[large_source++]);
        new_list.add(smaller[small_source++]);
      }

      events = new_list;
    }
  }

  /*******************************************************************
   * Remove all handlers from the current dispatcher.
   */

  public synchronized void remove_all_handlers() {
    events.clear();
  }

  /** 
   *	Dispatch the actions "iterations" times. Use -1 for "forever."
   *  This function is not synchronized so that the list of events
   *  can be modified while the dispather is running. It makes a clone
   *	of the event list and then executes from the clone on each
   *	iteration through the list of subscribers. Events added to
   *	the list will be executed starting with the next iteration.
   */

  public void dispatch(int iterations) {
    // Dispatch operations. A simple copy-and-dispatch-from-copy
    // strategy is used, here. Eventually, I'll replace this code
    // with a <code>Multicaster</code>.

    if (events.size() > 0)
      while (iterations == -1 || iterations-- > 0) {
        Object[] snapshot;
        synchronized (this) //#snapshot
          {
          snapshot = events.toArray();
        }

        for (int i = 0; i < snapshot.length; ++i) {
          ((Runnable) snapshot[i]).run();
          Thread.yield();
        }
      }
  }

  /** Dispatch actions "iterations" number of times, with an action
   *	dispatched every "interval" milliseconds. Note that the
   *  last action executed takes up the entire time slot, even
   *  if the run() function itself doesn't take "interval"
   *	milliseconds to execute. Also note that the timing will be
   *  irregular if any run() method executes in more than "interval"
   *	milliseconds.
   *
   *  If you want a time interval between iterations, but not
   *  between the operations performed in a single iteration, just
   *  insert a Runnable action that sleeps for a fixed number of
   *	milliseconds.
   *
   * @param iterations`	number of times to loop throug the actions
   *						exectuting them. Use -1 to mean "forever."
   * #param interval		An action is exectuted every "interval"
   *						milliseconds.
   */

  public void metered_dispatch(int iterations, int interval) {
    Alarm timer = new Alarm(interval, Alarm.MULTI_SHOT, false);
    timer.start();

    while (iterations == -1 || --iterations >= 0) {
      Object[] snapshot;
      synchronized (this) {
        snapshot = events.toArray();
      }

      for (int i = 0; i < snapshot.length; ++i) {
        ((Runnable) snapshot[i]).run();
        timer.await();
        timer.start();
      }
    }

    timer.stop();
  }

  static public final class Test {
    // Execute the test with:
    //	java "com.holub.asynch.Synchronous_dispatcher\$Test"
    //

    public static void main(String[] args) {
      Synchronous_dispatcher dispatcher = new Synchronous_dispatcher();

      dispatcher.add_handler(new Runnable() {
        public void run() {
          System.out.print("hello");
        }
      });

      dispatcher.add_handler(new Runnable() {
        public void run() {
          System.out.println(" world");
        }
      });

      dispatcher.dispatch(1);
      dispatcher.metered_dispatch(2, 1000);

      //------------------------------------------------
      // Test two tasks, passed to the dispatcher as arrays
      // of chunks. Should print:
      //			Hello (Bonjour) world (monde)

      Runnable[] first_task = { new Runnable() { public void run() { System.out.print("Hello");
          }
        }, new Runnable() {
          public void run() {
            System.out.print(" World");
          }
        }
      };

      Runnable[] second_task = { new Runnable() { public void run() { System.out.print(" Bonjour");
          }
        }, new Runnable() {
          public void run() {
            System.out.print(" Monde\n");
          }
        }
      };

      dispatcher = new Synchronous_dispatcher();
      dispatcher.add_handler(first_task);
      dispatcher.add_handler(second_task);
      dispatcher.dispatch(1);
    }
  }
}
