import java.net.*;
import java.io.*;
import java.util.*;

class Server {
	static void init() throws IOException {
		ServerSocket server = new ServerSocket(3141);
		while (true) {
			Socket sock = server.accept();
		
			BufferedInputStream br1 = new BufferedInputStream(sock.getInputStream());
			PrintStream out1 = new PrintStream(sock.getOutputStream());
			
			String line;
			List request = new ArrayList();
			
			String host = null;
			int port = 80;
			
			//Request vom Browser empfangen
			line = readLine(br1);
			while (!line.equals("\r\n")) {
				System.out.println(line);
				request.add(line);
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
				line = readLine(br1);
			}	
			request.add("\r\n");
				
			Socket toWWW = new Socket(host, port);
			
			
			BufferedReader br2 = new BufferedReader(new InputStreamReader(toWWW.getInputStream()));
			PrintStream out2 = new PrintStream(toWWW.getOutputStream());
			
			
			out2.println("GET / HTTP/1.0");
			out2.println();
			
			/*
			//Request an Zielhost absetzten
			Iterator it = request.iterator();
			while(it.hasNext()) {
				String r = (String)it.next();
				out2.write(toByteArray(r.toCharArray()));
			}
			*/
			
			//Response von Zielhost empfangen und an Browser senden
			while((line = br2.readLine()) != null) {
				System.out.println(line);
				out1.println(line);				
			}	
			out1.println();
			
			toWWW.close();
			sock.close();		
		}
	}

	private static byte[] toByteArray(char[] chararray) {
		byte[] byteArray = new byte[chararray.length];
		for (int i = 0; i < chararray.length; i++) {
			byteArray[i] = (byte)chararray[i];
		}
		return byteArray;
	}
	
	private static String readLine(InputStream is) throws IOException {
		StringBuffer sb = new StringBuffer();
		int c = is.read();
		while ((c != -1) && (c != '\n')) {
			sb.append((char)c);
			c = is.read();
		}
		sb.append('\n');
		return sb.toString();
	}
	
	public static void main(String args[]) {
		try {
			init();
		} catch (IOException ioe) {
			System.err.println(ioe);
		}
	}
}

