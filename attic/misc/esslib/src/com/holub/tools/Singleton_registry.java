package com.holub.tools;
import java.util.*;

import com.holub.asynch.*;

/** Use this class to keep a body of Singletons alive. Each Singleton
 *  should pass its class object to `register` from its static
 *	initializer block. For example:
 *  <pre>
 *	class Singleton
 *  {	static{ Singleton_registry.register( Singleton.class ); }
 *		//...
 *  }
 *  </pre>
 *
 *
 *	<table border=1 cellspacing=0 cellpadding=5><tr><td><font size=-1><i>
 *	<center>(c) 2000, Allen I. Holub.</center>
 *	<p>
 *	This code may not be distributed by yourself except in binary form,
 *	incorporated into a java .class file. You may use this code freely
 *	for personal purposes, but you may not incorporate it into any
 *	commercial product without
 *	the express written permission of Allen I. Holub.
 *	</font></i></td></tr></table>
 *
 *	@author Allen I. Holub
 */

class Singleton_registry {
  static {
    new JDK_11_unloading_bug_fix(Singleton_registry.class);
  }

  private Singleton_registry() {}

  static Vector singletons = new Vector();

  public synchronized static void register(Object singleton) {
    singletons.addElement(singleton);
  }
}
