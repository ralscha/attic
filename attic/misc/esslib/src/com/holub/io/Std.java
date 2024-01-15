package com.holub.io;
import java.io.*;

import com.holub.asynch.*;

/** Convenience wrappers that hide the complexity of creating
 *	<code>Readers</code> and <code>Writers</code> simply to access standard input and output.
 *  For example, a call to
 *	<PRE>
 *	Std.out().println("hello world");
 *	</PRE>
 *	is identical in function to:
 *	<PRE>
 *	new PrintWriter(System.out, true).println("hello world");
 *	</PRE>
 *	and
 *	<PRE>
 *	String line = Std.in().readLine();
 *	</PRE>
 *	is identical in function to:
 *	<PRE>
 *	line = new BufferedReader(new InputStreamReader(System.in)).readLine();
 *	</PRE>
 *	Equivalent methods provide access to standard error
 *	and a "bit bucket" that just absorbs output without printing it.
 *
 *	<p>All of these methods create Singleton objects. For example, the
 *	same <code>PrintWriter</code> object is always returned from
 *	<code>Std.out()</code>This way you don't incur the overhead of
 *	a <code>new</code> with	each I/O request. These calls are all
 *	thread safe, but do not incur any synchronization overhead.
 *	They are also very small, and will be inlined by the Hotspot JVM.
 *
 *	<table>
 *	<tr><td>6-13-00</td><td>	Modified to initialize all fields at
 *								class-load time to eliminate SMP-related
 *								synchronization problems.
 *	</td></tr>
 *	</table>
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
 * @see com.holub.tools.P
 * @see com.holub.tools.R
 * @see com.holub.tools.E
 * @author Allen I. Holub
 */

public final class Std {
  static {
    new JDK_11_unloading_bug_fix(Std.class);
  } //#bug_fix

  private static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
  private static PrintWriter output = new PrintWriter(System.out, true);
  private static PrintWriter error = new PrintWriter(System.err, true);
  private static PrintWriter bit_bucket = new Bit_bucket();

  /*******************************************************************
   * A private constructor prevents anyone from manufacturing an
   * instance.
   */
  private Std() {}

  /*******************************************************************
   * Get a BufferedReader that wraps System.in
   */
  public static BufferedReader in() {
    return input;
  }

  /*******************************************************************
   * Get a PrintWriter that wraps System.out.
   */
  public static PrintWriter out() {
    return output;
  }

  /*******************************************************************
   * Get a PrintWriter that wraps System.err.
   */

  public static PrintWriter err() {
    return error;
  }

  /*******************************************************************
   * Get an output stream that just discards the characters that are
   * sent to it. This convenience class makes it easy to write methods
   * that are passed a "Writer" to which error messages or status information
   * is logged. You could log output to standard output like this:
   *	<PRE>
   *	x.method( Std.out() );	// pass in the stream to which messages are logged
   *	</PRE>
   *	But you could cause the logged messages to simply disappear
   *	by calling:
   *	<PRE>
   *	x.method( Std.bit_bucket() );	// discard normal logging messages
   *	</PRE>
   */

  public static PrintWriter bit_bucket() {
    return bit_bucket;
  }

  /*******************************************************************
   * The bit-bucket class is a PrintWriter that simply throws
   * all output away. It's useful when you have a method that
   * takes a PrintWriter argument that designates a place
   * to log diagnostics or debug messages, but you want to
   * disable the logging feature. Just pass in a bit bucket
   * instead of the normal writer.
   */

  private final static class Bit_bucket extends PrintWriter {
    public Bit_bucket() {
      super(System.err);
    }

    public void close() {}
    public void flush() {}
    public void print(boolean b) {}
    public void print(char c) {}
    public void print(char[] s) {}
    public void print(double d) {}
    public void print(float f) {}
    public void print(int i) {}
    public void print(long l) {}
    public void print(Object o) {}
    public void print(String s) {}
    public void println() {}
    public void println(boolean b) {}
    public void println(char c) {}
    public void println(char[] s) {}
    public void println(double d) {}
    public void println(float f) {}
    public void println(int i) {}
    public void println(long l) {}
    public void println(Object o) {}
    public void write(char[] buf) {}
    public void write(char[] buf, int off, int len) {}
    public void write(int c) {}
    public void write(String buf) {}
    public void write(String buf, int off, int len) {}
  }

  /**	A small test class. This class reads a line from standard input and
   *	echoes it to standard output and standard error. Run it
   *	with: <blockquote><i>java com.holub.tools.Std\$Test</i></blockquote>
   *	(Don't type in the \ when using a Microsoft-style shell.)
   */

  static public class Test {
    static public void main(String[] args) throws IOException {
      String s;
      while ((s = Std.in().readLine()) != null) {
        if (s.equals("quit"))
          break;

        Std.out().println(s);
        Std.err().println(s);
        Std.bit_bucket().println(s);
      }
    }
  }
}
