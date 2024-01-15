import java.net.*;
import java.io.*;
import common.net.*;

//
//
// HTTPDemo
//
// Written by David Reilly, for JavaWorld
//

public class HTTPDemo {
	public static void main(String args[]) throws Exception {
		URL dataURL;
		String remote_address;
		String remote_path;
		int remote_port;

		if (args.length != 1) {
			System.out.println ("HTTPDemo url");
			System.exit(0);
		}


		try {
			// Parse URL, to extract details
			dataURL = new URL(args[0]);

			remote_address = dataURL.getHost();
			remote_path = dataURL.getFile();
			remote_port = dataURL.getPort();

			// Check port, default is 80
			if (remote_port == -1)
				remote_port = 80;

			// Connect to remote service
			Socket connection = TimedSocket.getSocket (remote_address, remote_port, 4000);

			// Set the socket timeout for ten seconds
			connection.setSoTimeout (10000);

			// Create a DataInputStream for reading from socket
			DataInputStream din = new DataInputStream (connection.getInputStream());

			// Create a PrintStream for writing to socket
			PrintStream pout = new PrintStream (connection.getOutputStream());

			// Print get request
			pout.print ("GET " + remote_path + " HTTP/1.0\n\n");

			// Read data until end of data
			for (;;) {
				String line = din.readLine();
				if (line != null)
					System.out.println (line);
				else
					break;
			}
		} catch (InterruptedIOException iioe) {
			System.err.println ("Remote host timed out");
		}
		catch (MalformedURLException mue) {
			System.err.println ("Invalid URL");
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