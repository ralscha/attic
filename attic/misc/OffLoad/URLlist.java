import java.net.*;
import java.util.*;

class URLlist
{
	private URL baseURL = null;
	private String hostName = null;
	private Vector listOfURLs = null;
	private Vector rlistOfURLs = new Vector(100);

	URLlist(URL u, Vector v)
	{
		listOfURLs = v;
		hostName = u.getHost();
		baseURL = u;
	}

	/*
	 * Returns a vector of URLs
	 */
	Vector getURLs()
	{
		int sizeOfVec = listOfURLs.size();
		for(int i = 0; i < sizeOfVec; i++)
		{
			String href = (String)listOfURLs.elementAt(i);
			try
			{
				if(href != "")
				{
					if(href.startsWith("http://"))
					{
						ExtendedURL eURL = new ExtendedURL(new URL(href));
						addToList(eURL.getURL());
					}
					else if(href.indexOf(':') != -1)
					{
						// do nothing
					}
					else
					{
						ExtendedURL eURL = new ExtendedURL(baseURL, href);
						addToList(eURL.getURL());
					}
				}
			}
			catch(MalformedURLException e)
			{
				System.err.println(e.getMessage());
				System.err.println("Could not generate URL using " + baseURL +
					" and " + href);
			}
		}
		return rlistOfURLs;
	}

	/*
	 * Add the URL to the Vector.
	 * It checks if the URL is already present in the list and adds
	 * only if the URL is not already present.
	 */
	private void addToList(URL u)
	{
		if(u == null)
			return;

		if(hostName.equals(u.getHost()))
		{
			if(!Downloader.URLs.contains(u))
			{
				rlistOfURLs.addElement(u);
				Downloader.URLs.addElement(u);
			}
		}
	}
}
