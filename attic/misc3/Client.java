import java.io.*;
import java.net.*;

class Client {

	static void init() throws IOException {
		Socket client = new Socket ("localhost", 8100);
		BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
		PrintStream out = new PrintStream(client.getOutputStream());
		
		out.println("GET / HTTP/1.1");
		out.println();
		
		String line;
		while((line = br.readLine()) != null) {
			System.out.println(line);
		}
		client.close();
	}
	
	public static void main(String args[]) {
		try {
			init();
		} catch (IOException e) {
			System.out.println("Error " + e);
		}
	}
}
