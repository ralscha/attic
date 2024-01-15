package com.holub.asynch;

/**
 *	This class provides a workaround for a bug in the JDK 1.1 VM that
 *	unloads classes too aggressively. The problem is that if the only
 *	reference to an object is held in a static member of the object, the
 *	class is subject to unloading and the static member will be discarded.
 *	This behavior causes a lot of grief when you're implementing a
 *	Singleton. Use it like this:
 *
 *	<pre>
 *	class Singleton
 *	{	private Singleton()
 *		{	new JDK_11_unloading_bug_fix(Singleton.class);
 *		}
 *		// ...
 *	}
 *	</pre>
 *  In either event, once the "JDK_11_unloading_bug_fix" object is
 *	created, the class (and its static fields) won't be unloaded for
 *	the life of the program.
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

public class JDK_11_unloading_bug_fix {
  public JDK_11_unloading_bug_fix(final Class the_class) {
    if (System.getProperty("java.version").startsWith("1.1")) {
      Thread t = new Thread() {
        public void run() {
          //Class singleton_class = the_class;

          synchronized (this) {
            try {
              wait();
            } catch (InterruptedException e) {}
          }
        }
      };

      // I'm not exactly sure why synchronization is necessary,
      // below, but without it, some JVMs complain of an
      // illegal-monitor state. The effect is to stop the wait()
      // from executing until the thread is fully started.

      synchronized (t) {
        t.setDaemon(true); // so program can shut down
        t.start();
      }
    }
  }
}
