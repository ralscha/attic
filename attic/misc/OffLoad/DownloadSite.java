import java.net.*;
import java.io.*;

public class DownloadSite
{
	public static void main(String [] args)
	{
		/*
		 * The following three lines should be included only if you
		 * are behind the firewall and accessing the net through a
		 * proxy server.
		 */
		 /*
		System.getProperties().put("proxySet", "true");
		System.getProperties().put("proxyHost", "fox");
		System.getProperties().put("proxyPort", "8080");
		*/
		/*
		 * fox is the name of the proxy server
		 * 8080 is the proxy port
		 */
		

		String dest = null;
		URL u = null;
		File f = null;

		// get the command line arguments
		switch(args.length)
		{
			case 0:
				System.err.println(
					"Usage: java DownloadSite site_URL [location]");
				System.exit(-1);
				break;
			case 1:
				dest = System.getProperty("user.dir");
				break;
			case 2:
				f = new File(args[1]);
				dest = f.getAbsolutePath();
				break;
			default:
				System.err.println(
					"Usage: java DownloadSite site_URL [location]");
				System.exit(-1);
				break;
		}

		try
		{
			u = new URL(args[0]);
		}
		catch(MalformedURLException e)
		{
			System.err.println(e.getMessage());
			System.err.println("Malformed URL. Check the URL provided.");
		}

		// Start work after initialization
		Downloader d = new Downloader(u, dest);
		d.download();
	}
}
