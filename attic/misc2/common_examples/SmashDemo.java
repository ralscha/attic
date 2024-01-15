import java.net.*;
import java.io.*;
import common.net.*;


public class SmashDemo {
  
  private final static String  PROTOCOL = "SMASHP/1.0";
  
  
  public static void main(String args[]) throws Exception {
    String remote_address = "192.168.20.200";
    int remote_port = 7077;
    
    try {

      // Connect to remote service
      Socket connection = TimedSocket.getSocket (remote_address, remote_port, 4000);

      // Set the socket timeout for ten seconds
      connection.setSoTimeout (10000);

      // Create a DataInputStream for reading from socket
      DataInputStream din = new DataInputStream (connection.getInputStream());

      // Create a PrintStream for writing to socket
      PrintStream pout = new PrintStream (connection.getOutputStream());

      // Print get request
      pout.print ("SENDMSG * " + PROTOCOL +"\r\n");

      pout.print("Phone: 0795016439\r\n");
      pout.print("Operator: swisscom\r\n");
      pout.print("Content-Length: 10\r\n");
      pout.print("User-Agent: jsmash/0.1\r\n");
      pout.print("\r\n");
      pout.print("1234567890");
      
      // Read data until end of data
      for (;;) {
        String line = din.readLine();
        if (line != null)
          System.out.println (line);
        else
          break;
      }
      
      
      connection.close();
    } catch (InterruptedIOException iioe) {
      System.err.println ("Remote host timed out");
    }
    catch (SocketException se) {
      System.err.println ("Socket exception");
    }
    catch (IOException ioe) {
      System.err.println ("Network I/O error - " + ioe);
    }

    // Terminate all idle threads, such as any still
    // blocked by I/O bound operations
    System.exit(0);
  }

}