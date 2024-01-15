
import java.io.*;
import java.net.*;

public class GoogleSeeker {
	public static void main(String args[]) throws Exception {

		System.getProperties().put("proxySet", "true");
		System.getProperties().put("proxyHost", "192.168.20.200");
		System.getProperties().put("proxyPort", "8080");

		String s = "";

		if (args.length == 0)
			s = "Teletubbies On Tour";
		else
			for (int i = 0; i < args.length; i++)
				s += args[i] + " ";

		s.trim();
		s = "q=" + URLEncoder.encode(s);

		URL u = new URL("http://www.google.com/search?" + s);

		BufferedReader in = new BufferedReader(new InputStreamReader(u.openStream()));

		String line, response = null;

		while ((line = in.readLine()) != null)
			response += line + "\n";

		System.out.print(response);
	}
}
