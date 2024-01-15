package com.holub.io;

import java.io.*;
import java.util.*;

import com.holub.asynch.*;

/***********************************************************************
 * A console "task" that demonstrates how to use the Active_object class.
 * The Console is an OutputStream that multiple threads can use to write
 * to the console. Unlike a normal PrintStream, the current class
 * guarantees that lines print intact. (Characters from one line will
 * not be inserted into another line.)
 *
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

public final class Console extends OutputStream {
  // For this particular singleton, it's sensible to initialize
  // the object during class loading. This practice cleans up the
  // accessor considerably, and also eliminates a host of SMP-related
  // treading problems. This strategy wouldn't make sense
  // if you didn't know enough to create the object at load
  // time or if you wanted to guarantee that creation would
  // be deffered until first use. Neither condition holds here.

  private static Active_object dispatcher = new Active_object();
  private static Console the_console = new Console();
  static Map users = new HashMap(); //#create.map

  static {
    dispatcher.start();
  }

  /******************************************************************
   * A private constructor makes it impossible to create a <code>Console</code>
   * using <code>new</code>. Use <code>System.out()</code> to get
   * a reference to the <code>Console</code>.
   */

  private Console() {
    new JDK_11_unloading_bug_fix(Console.class);
  }

  /******************************************************************
   * The console is a Singleton&#151;only one is permitted to exist.
   *
   * The <code>Console</code> has a private constructor, so you cannot manufacture
   * one with <code>new</code>.
   * Get a reference to the one-and-only instance of the <code>Console</code>
   * by calling <code>Console.out()</code>.
   *
   * @return	a thread-safe <code>OutputStream</code> that you can
   *			wrap with any of the standard java.io decorators.
   *			This output stream buffers characters on a per-thread
   *			basis until a newline, sent by that thread, is encountered.
   *			The Console object then sends the entire line to the
   *			standard output as a single unit.
   */

  public static final Console out() {
    return the_console;
  }

  /*******************************************************************
   * Shut down the Console in an orderly way. The Console uses a
   * daemon thread to do its work, so it's not essential to shut it
   * down explicitly, but you can call <code>shut_down()</code> to
   * kill the thread in situations where you know that no more output
   * is expected. Any characters that have been buffered, but not yet
   * sent to the console, will be lost.
   *
   * <p>You can actually call <code>out()</code> after <code>shut_down()</code>,
   * but it's inefficient to do so.
   */

  public static void shut_down() {
    dispatcher.close();
    dispatcher = null;
    the_console = null;
    users = null;
  }

  /*******************************************************************
   * Overrides the OutputStream write(int) function. Use the
   * inherited functions for all other OutputStream
   * functionality.
   *
   * <p>For a given thread, no output at all is flushed until a
   * newline is encountered. (This behaviour is accomplished using a
   * hash table, indexed by a thread object, that contains a buffer
   * of accumulated characters.) The entire line is flushed as a unit
   * when the newline is encountered.
   *
   * <p>Once the Console is closed, (see {@link #close}), any requests
   * to write characters are silently ignored.
   */

  public void write(int character) throws IOException {
    if (character != 0)
      dispatcher.dispatch(new Handler(character, Thread.currentThread())); //#current.thread
  }

  /*******************************************************************
   * The request object that's sent to the <code>Active_object</code>.
   * All the real work is done here.
   */

  private final class Handler implements Runnable {
    private int character;
    private Object key;

    Handler(int character, Object key) {
      this.character = character;
      this.key = key;
    }

    public void run() {
      List buffer = (List) (users.get(key));

      if (character != '\n') {
        if (buffer == null) // 1st time this thread requested
          {
          buffer = new Vector();
          users.put(key, buffer);
        }
        buffer.add(new int[] { character });
      } else //#run.else
        {
        if (buffer != null) {
          for (Iterator i = ((List) buffer).iterator(); i.hasNext();) {
            int c = ((int[]) (i.next()))[0];
            System.out.print((char) c);
          }
          users.remove(key);
        }
        System.out.print('\n');
      }
    }
  }

  /*******************************************************************
   * Overrides the <code>OutputStream flush()</code> method.
   * All partially-buffered lines are printed. A newline
   * is added automatically to the end of each text string. This method
   * <u>does not</u> block.
   **/

  public void flush() throws IOException {
    Set keys = users.keySet();
    for (Iterator i = keys.iterator(); i.hasNext();)
      dispatcher.dispatch(new Handler('\n', i.next()));
  }

  /*******************************************************************
   * Overrides the <code>OutputStream close()</code> method.
   * Output is flushed (see {@link #flush}). Subsequent output
   * requests are silently ignored.
   **/

  public void close() throws IOException {
    flush();
    dispatcher.close(); // blocks until everything stops.
  }

  /*******************************************************************
   * Convenience method, this method provides a simple way to print
   * a string without having to wrap the Console.out() stream in
   * a <code>DataOutputStream</code>.
   **/

  public void println(final String s) {
    try {
      for (int i = 0; i < s.length(); ++i)
        write(s.charAt(i));
      write('\n');
    } catch (IOException e) { /*ignore it*/
    }
  }

  /*******************************************************************
   * A test class that prints two messages in parallel on two threads,
   * with random sleeps between each character.
   */

  static public final class Test extends Thread {
    private String message;
    private DataOutputStream data = new DataOutputStream(Console.out());

    public Test(String message) {
      this.message = message;
    }

    public void run() {
      try {
        Random indeterminate_time = new Random();
        for (int count = 2; --count >= 0;) {
          for (int i = 0; i < message.length(); ++i) {
            Console.out().write(message.charAt(i));
            sleep(Math.abs(indeterminate_time.nextInt()) % 20);
          }

          Console.out().println("(" + count + ")");
          sleep(Math.abs(indeterminate_time.nextInt()) % 20);

          data.writeChars("[" + count + "]");
          sleep(Math.abs(indeterminate_time.nextInt()) % 20);

          Console.out().write('\n');
        }
      } catch (Exception e) {
        Console.out().println(e.toString());
      }
    }

    static public void main(String[] args) throws Exception {
      Thread one = new Test("THIS MESSAGE IS FROM THREAD ONE");
      Thread two = new Test("this message is from thread two");

      one.start();
      two.start();

      one.join(); // Wait for everything to get enqueued
      two.join();

      Console.out().close(); // wait for everything to be printed
    }
  }
}
