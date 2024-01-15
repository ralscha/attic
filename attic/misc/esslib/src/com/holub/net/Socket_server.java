package com.holub.net;

import java.io.*;
import java.net.*;
import java.util.*;

import com.holub.asynch.*;

/**
 *	A generic server-side socket that efficiently executes client-related
 *  actions on their own threads. Use it like this:
 *	<PRE>
 *	class Connection_strategy implements Socket_server.Client
 *	{	
 *		protected void communicate( Socket socket )
 *		{
 *			// Called every time a connection is made to
 *			// a client, encapsulates whatever communication
 *			// needs to occur. This method runs on its own
 *			// thread, but Client object are recycled for
 *			// subsequent connections, so should reinitialize
 *			// themselves at the top of the communicate() method.
 *		}
 *
 *		public Object replicate()
 *		{	return new Connection_strategy();
 *		}
 *
 *	};
 *
 *	Socket_server echo_server = new Socket_server
 *								( 	port_number, 
 *									expected_connection_count,
 *									new Connection_strategy(),
 *									new Socket_server.Death()
 *									{	public void on_exit( Exception e )
 *										{ // performed when server aborts or
 *										  // is killed
 *										}
 *									}
 *								);
 *	echo_server.start();
 *	//...
 *	echo_server.kill();
 *  </PRE>
 * The <code>Client</code> derivative encapsulates whatever action must
 * be performed when a client connects to the current server. The
 * <code>communicate()</code> method runs on its own thread, and is passed
 * the socket that is connected to the client. <code>Client</code> objects
 * are manufactured by the <code>Socket_server</code>, and a minimal
 * number of objects are created and recycled as necessary. Consequently,
 * the <code>socket</code> argument should not be cached. Moreover,
 * the <code>Client</code> object should reinitialize itself
 * every time the <code>communicate()</code> method is called; it's
 * only called once per connection.
 * (This is <u>not</u> the same architecture as a Servlet.
 * One instance of the <code>Client</code> will exist for <em>each</em>
 * connection, so the object can maintain a unique state in local
 * fields if it wishes.
 * Once the action completes, however, the object might be used again
 * for another connection, so it should reinitialize itself at the
 * top of the <code>action</code> method.)
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

public class Socket_server extends Thread {
  /**********************************************************************
   * Extend this class and override the <code>communicate</code> method to
   * provide an object to service an individual client connection.
   * Objects of this class are recycled, so you should reinitialize
   * the object, if necessary, at the top of the call to <code>communicate()</code>
   * This class is very tightly coupled to the Socket_server, which
   * accesses a few of its fields directly.
   */

  public abstract static class Client implements Runnable {
    LinkedList client_handlers; // initialized on creation.
    Socket socket; // Reinitialized by the
    // Socket_server on
    // each use.

    /** Must be public, but do not override this method */
    public void run() {
      try {
        communicate(socket);
        socket.close();
      } catch (Exception e) { /* ignore */
      }

      synchronized (client_handlers) {
        client_handlers.addLast(this); // Put self back into queue to
      } // be used again
    }

    /** A gang-of-four "Template Method." Override this method to provide
     *	an operation to perform when  connected to a client. The
     *  <code>client</code> socket is already hooked up to the client,
     *  and is closed automatically after <code>communicate()</code> returns.
     *	The <code>communicate()</code> operation executes on its own thread.
     *	<p>
     *	<code>Client</code> objects are not released to the garbage collector
     *	after they are used; rather, they are recycled for subsequent
     *	connections. A well-behaved <code>communicate()</code> method will
     *	initialize the <code>Client</code> object before doing anything else,
     *	and will <code>null</code> out all references to which it has access
     *  just before terminating (so that the associated memory can be
     *  garbage collected between client connections).
     *	<p>
     *  Any exceptions thrown out of this message handler are ignored, which
     *  is probably not a great strategy, but given the generic nature of
     *  an "action," I don't have much choice. Consequently, you should
     *	catch all exceptions within <code>communicate()</code>.
     */
    protected abstract void communicate(Socket client);

    /** Return a pre-initialized copy of the current object. This method is
     *  something like <code>clone</code>, but there's
     *  no requirement that the returned object be an
     *  exact replica of the prototype. In any event, <code>clone()</code> is
     *  <code>protected</code>, so it's rather awkward to use.
     */
    public abstract Client replicate();
  }

  /**********************************************************************
   *	The Socket_server.Death object is passed into the server to
   *  specify a shut-down action. Its <code>on_exit()</code> method
   *  is called when the server thread shuts down. 
   *  @param culprit	This argument is <code>null</code> for a normal
   *					shutdown, otherwise it's the exception object that
   *					caused the shutdown.
   */

  public interface Death {
    void on_exit(Exception culprit);
  }

  //----------------------------------------------------------------------
  // All of the following should be final. A compiler bug (that's
  // in all compiler versions up to and including 1.2) won't permit it.

  private /*final*/
  ServerSocket main;
  private /*final*/
  Thread_pool threads;
  private /*final*/
  Socket_server.Client prototype_client;
  private /*final*/
  Socket_server.Death shutdown_operation;
  private final LinkedList client_handlers = new LinkedList();

  /**********************************************************************
   * Thrown by the <code>Socket_server</code> constructor if it can't
   * be created for some reason.
   */

  public class Not_created extends RuntimeException {
    public Not_created(Exception e) {
      super("Couldn't create socket: " + e.getMessage());
    }
  }

  /**********************************************************************
   * Create a socket-server thread that creates and maintains a server-side
   * socket, dispatching client actions on individual threads when
   * clients connect.
   * @param port_number			The port number to use for the main socket
   * @param expected_connections	The number of simultaneous connections
   *								that you expect. More connections are
   *								supported, but the threads that service
   *								them may need to be created dynamically
   *								when the client connects.
   * @param action				A <code>Socket_server.Client</code>
   *								derivative that encapsulates the action
   *								performed on each client connection. This
   *								object is cloned on an as-needed basis to
   *								service clients. This cloning is an example
   *								of the gang-of-four Prototype pattern:
   *								The <code>action</code> object is a prototype
   *								that's used to create the objects that are
   *								actually used.
   * @param shutdown_strategy		<code>Socket_server.Death</code> object that
   *								encapsulates the
   *								action to take when the server thread
   *								shuts down (either by being passed
   *								a {@link kill} message, or by some
   *								internal error).
   * @throws Not_created			If the object can't be created successfully
   *								for one of various reasons (the socket
   *								can't be opened, client actions can't
   *								be manufactured, etc.).
   */

  public Socket_server(
    int port_number,
    int expected_connections,
    Socket_server.Client connection_strategy,
    Socket_server.Death shutdown_strategy)
    throws Not_created {
    try {
      main = new ServerSocket(port_number);
      threads = new Thread_pool(expected_connections, 0);
      prototype_client = connection_strategy;
      shutdown_operation = shutdown_strategy;

      for (int i = expected_connections; --i >= 0;)
        client_handlers.addLast(create_client_from_prototype());

      setDaemon(true); // Don't keep the process alive
    } catch (Exception e) {
      throw new Not_created(e);
    }
  }

  /*******************************************************************
   * Create a copy of the Client prototype and initialize any relevant
   * fields. We can't do this particularly easily since we're replicating
   * a class whose name is not known to us (a derived class of the Client)
   */

  private Client create_client_from_prototype() {
    Client replica = prototype_client.replicate();
    replica.client_handlers = this.client_handlers;
    return replica;
  }

  /*******************************************************************
   * Implements the "accept" loop, waits for clients to connect and
   * when a client does connect, executes the <code>communicate()</code>
   * method on a clone of the <code>Client</code>
   * prototype passed into the constructor. This method is executed
   * on its own thread.
   * If the <code>accept()</code> fails catastrophically, the
   * thread shuts down, and the shut_down object passed to the
   * constructor is notified (with a null argument).
   */

  public void run() {
    try {
      Client client;
      while (!isInterrupted()) //#accept_loop
        {
        synchronized (client_handlers) {
          client = client_handlers.size() > 0 ? (Client) client_handlers.removeLast() : create_client_from_prototype();
        }
        client.socket = main.accept(); // Reach into the client-handler
        // object and modify its socket
        // handle.
        if (isInterrupted()) {
          client.socket.close();
          break;
        }

        threads.execute(client);
      }
    } catch (Exception e) {
      if (shutdown_operation != null)
        shutdown_operation.on_exit(e);
    } finally {
      threads.close();
      if (shutdown_operation != null)
        shutdown_operation.on_exit(null);
    }
  }

  /*******************************************************************
   * Shut down the Socket_server thread gracefully. All associated
   * threads are terminated (but those that are working on serving
   * a client will remain active until the service is complete).
   * The main socket is closed as well.
   */

  public void kill() {
    try {
      threads.close();
      interrupt();
      main.close();
    } catch (IOException e) { /*ignore*/
    }
  }

  /*******************************************************************
   * A small test class. Creates a socket server that implements an]
   * echo server, then opens two connections to it and runs
   * a string through each connection.
   */
  static public class Test {
    static class Connection_strategy extends Socket_server.Client {
      protected void communicate(Socket socket) {
        try {
          BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

          OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream());

          String line;
          while ((line = in.readLine()) != null) {
            out.write(line, 0, line.length());
            out.write("\n", 0, 1);
            out.flush();
          }
          socket.close();
        } catch (Exception e) {
          System.out.println(e.toString());
        }
      }

      public Socket_server.Client replicate() {
        return new Connection_strategy();
      }
    }
    //------------------------------------------------------------
    public static void main(String[] args) throws Exception {

      Socket_server echo_server = new Socket_server(9999, 10, new Connection_strategy(), new Socket_server.Death() {
        public void on_exit(Exception e) {
          System.out.println("exit processed correctly, null=" + e);
        }
      });

      echo_server.start();
      connect("Hello\n");
      connect("World\n");
      echo_server.kill();

      // Give the server a chance to shut down. It's a daemon
      // so might be shut down abruptly before the kill can
      // be fully processed.

      Thread.sleep(20);
    }
    //------------------------------------------------------------
    private static void connect(String message) throws Exception {
      Socket client = new Socket("localhost", 9999);
      BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
      OutputStreamWriter out = new OutputStreamWriter(client.getOutputStream());

      // Write the argument string up to the echo server.
      out.write(message, 0, message.length());
      out.flush();

      // wait for it to be echoed back to us, then print it.

      String response = in.readLine() + "\n";

      System.out.print("   " + response + "   =" + message);
      System.out.println(response.equals(message) ? "\t\t(passed)" : "\t\t**** FAILED ****");

      client.close();
    }
  }
}
