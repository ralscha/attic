package com.holub.tools;

import java.lang.reflect.*;

import com.holub.io.*;

/* This class is a generic Runnable object that allows you to set up
 * a listener at run time rather than compile time. Instead of doing
 * this:
 * <pre>
 *	class My_class
 *	{
 *		private f( Object argument ){ ... }
 *
 * 		private class My_action implements Runnable
 * 		{	Object argument;
 *			My_action( Object argument )
 *			{	this.argument = argument;
 *			}
 *			public static void Run()
 *			{	f( argument );
 *			}
 * 		}
 *
 *		void set_up_a_listener
 *		{	a_notifier.add_listener( new My_action("hello"); );
 *		}
 *	}
 * </pre>
 * you can say:
 * <pre>
 *	class My_class
 *	{
 *		private f( Object argument ){ ... }
 *
 *		void set_up_a_listener
 *		{	a_notifier.add_listener(
 *					new Generic_command( this, "f", new Object[]{ "hello" } ));
 *		}
 *	}
 * </pre>
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
 *
 */

public class Generic_command implements Runnable {
  Object receiver;
  Method message;
  Object[] arguments;

  /**
   * Create a command object --- a runnable object that runs an
   * arbitrary Java method. Calling <code>run</code> invokes the
   *			method with the arguments passed to the Generic_command-object's
   *			constructor.
   * @param	receiver The receiver of the message: <code>x</code>
   *			in <code>x.f(a,b)</code>. This argument can be 
   *			<code>this</code> if you want to call a local method.
   * @param	method A string holding the name of a public method
   *			of the class of the <code>receiver</code> object.
   *			<code>"f"</code> for <code>x.f(a,b)</code>.
   * @param	arguments An array of Objects, one for each argument.
   *			Primitive-type objects should be wrapped in the standard
   *			java.lang wrappers. Given:
   *			<pre>
   *			int a	 = 10;
   *			String b = "hello world";
   *			x.f(a,b);
   *			</pre>
   *			use
   *			<pre>
   *			new Object[]{ new Integer(a), b };
   *			</pre>
   *			or
   *			<pre>
   *			new Object[]{ new Integer(10), "hello world" };
   *			</pre>
   *	@throws	NoSuchMethodException	if a function with the given name,
   *			whose argument types match the types specified in the
   *			<code>arguments</code> array, doesn't exist.
   *	@throws	SecurityException if the current application isn't allowed
   *			to use the Introspection APIs---an untrusted applet, for
   *			example.
   */

  public Generic_command(Object receiver, String method, Object[] arguments)
    throws NoSuchMethodException, SecurityException {
    this.receiver = receiver;
    this.arguments = (arguments == null) ? new Object[0] : arguments;

    Class[] types = (arguments == null) ? new Class[0] : classes(arguments);
    try {
      message = receiver.getClass().getMethod(method, types);
    } catch (NoSuchMethodException e) {
      StringBuffer message = new StringBuffer();

      message.append("\n\tMethod not found: ");
      message.append(receiver.getClass().getName());
      message.append("$");
      message.append(method);
      message.append("(");

      for (int i = 0; i < types.length; ++i) {
        message.append(types[i].getName());
        if (i < types.length - 1)
          message.append(", ");
      }
      message.append(")\n");

      throw new NoSuchMethodException(message.toString());
    }
  }

  /**
   * Create an array of class objects, one for each element of <code>
   * arguments</code>, that represents the class of each argument.
   */
  private Class[] classes(Object[] arguments) {
    Class[] classes = new Class[arguments.length];

    for (int i = 0; i < arguments.length; ++i)
      classes[i] = arguments[i].getClass();

    return classes;
  }

  /**
   * Execute the method specified in the constructor, passing it the
   * arguments specified in the constructor.
   */
  public void run() {
    try {
      message.invoke(receiver, arguments);
    } catch (IllegalAccessException e) {
      E.rror("Internal Access Error: Generic_command$run()");
    } catch (InvocationTargetException e) {
      E.rror("Internal Target Error: Generic_command$run()");
    }
  }

  static class Test {
    public void print(String first, String second) // must be public
    {
      System.out.println(first + " " + second);
    }

    public Test() throws Exception {
      Runnable command = new Generic_command(this, "print", new Object[] { "hello", "world" });
      command.run();
    }

    public static void main(String[] args) throws Exception {
      new Test();
    }
  }
}
