import java.net.*;
import java.io.*;
import java.util.*;
import gnu.regexp.*;

class Server {
	static void init() throws Exception {
	
	
		final RE regexp = new RE("^(GET|HEAD|POST|PUT|DELETE|LINK|UNLINK) [\\w]+://\\w+:{0,1}\\d*([^ ]+) ([\\w/.]+)$");
	
		ServerSocket server = new ServerSocket(3141);
		while (true) {
			final Socket sock = server.accept();
		
		new Thread(new Runnable() {
				public void run() {
		try {
			BufferedReader br1 = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			PrintStream out1 = new PrintStream(sock.getOutputStream());
			
			String line;
			List request = new ArrayList();
			
			String host = null;
			int port = 80;
			boolean post = false;
			
			//Request vom Browser empfangen
			line = br1.readLine();
			while ( (line != null) && (line.length() != 0) ) {
				System.out.println(line);
				
				REMatch match = regexp.getMatch(line);
				if (match != null) {		
					if (match.toString(1).equals("POST")) post = true; else post = false;
					request.add(match.toString(1) + " " + match.toString(2) + " " +match.toString(3));
				} else if (line.indexOf("Proxy-") != -1) {
					//request.add(line.substring(line.indexOf("Proxy-")+6));
				} else {				
					int h = line.indexOf("Host: ");
					if (h != -1) {
						int p = line.lastIndexOf(":");
						if (p == h+4) {
							host = line.substring(h+6);
						} else {
							host = line.substring(h+6, p);
							port = Integer.parseInt(line.substring(p+1).trim());
						}
					}
					
					request.add(line);
				}
				
				line = br1.readLine();
			}	
				
			Socket toWWW = new Socket(host, port);
						
			BufferedReader br2 = new BufferedReader(new InputStreamReader(toWWW.getInputStream()));
			//InputStream br2 = toWWW.getInputStream();
			PrintStream out2 = new PrintStream(toWWW.getOutputStream());
						
			//Request an Zielhost absetzten
			Iterator it = request.iterator();
			while(it.hasNext()) {
				String r = (String)it.next();
				out2.println(r);
			}
			out2.print("\r\n");
			
			if (post)
				StreamCopier.copy(sock.getInputStream(), out2);
			
			//out2.flush();
			
			//Response von Zielhost empfangen und an Browser senden
			//StreamCopier.copy(br2, out1);
			
			line = br2.readLine();
			while(line != null) {				
				System.out.println(line);
				out1.println(line);	
				line = br2.readLine();
			}	
			out1.println();
			
			toWWW.close();
			sock.close();		
			} catch (Exception e) {System.err.println(e); }
			}}).start();
			
		}
	}
	
	public static void main(String args[]) {
		try {
			init();
		} catch (Exception ioe) {
			System.err.println(ioe);
		}
	}
}

