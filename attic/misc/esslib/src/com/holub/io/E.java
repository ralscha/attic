package com.holub.io;

/** A convenience wrapper around Std.err(). All of the following do the
 *	same thing:
 *	<pre>
 *	Std.err().println("hello world");
 *	P.rintln_err("hello world");
 *	E.rror("hello world");
 *	</pre>
 *
 *	@see Std
 *	@see P
 */

public class E {
  public static final void rror(String s) {
    Std.err().println(s);
  }
}
