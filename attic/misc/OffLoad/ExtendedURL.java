import java.net.*;
import java.io.*;
import java.util.StringTokenizer;

public class ExtendedURL
{
	private String dir = null;
	private String file = null;
	private String protocol = null;
	private String host = null;
	private URL newURL = null;

	public ExtendedURL(URL u)
	{
		protocol = u.getProtocol();
		host = u.getHost();
		newURL = u;
		parse(u);
	}

	public ExtendedURL(URL u, String spec)
	{
		protocol = u.getProtocol();
		host = u.getHost();
		try
		{
			u = new URL(u, spec);
		}
		catch(MalformedURLException e)
		{
			System.err.println(e.getMessage());
		}
		newURL = u;
		parse(u);
	}

	/*
	 * Returns the URL. Note if the dir is null, it returns null
	 * and thus this URL is not added to the list.
	 */
	public URL getURL()
	{
		if(dir == null)
			return null;
		return newURL;
	}

	/*
	 * Returns the file name only (excluding the directory)
	 */
	public String getFile()
	{
		return file;
	}

	/*
	 * Returns the directory
	 */
	public String getDirectory()
	{
		return dir;
	}

	/*
	 * Does the parsing and extract directory and file from
	 * the URL
	 */
	private void parse(URL url)
	{
		String tmpStr = url.getFile();
		int len = tmpStr.length();
		int idx = 0;

		// remove the query string
		if((idx = tmpStr.indexOf('?')) != -1)
		{
			/*
			 *  u is "protocol://machinename/script?querystring"
			 *  tmpStr is "script?querystring"
			 *  we don't want to do anything here
			 */
		}
		else if(tmpStr.endsWith("/"))
		{
			// file null, directory not null
			dir = tmpStr.substring(0, tmpStr.length());
		}
		else
		{
			idx = tmpStr.lastIndexOf('/');
			if(idx != -1)
			{
				dir = tmpStr.substring(0, idx);
				if(idx + 1 <= len)
				{
					file = tmpStr.substring(idx + 1, len);
					if(!fileOfInterest(file))
					{
						file = null;
						dir = null;
					}
				}
			}
			else
				dir = "/";
				
		}
	}

	/*
	 * This method receives a file name and returns true if we
	 * are interested in the file i.e. if we want to download the
	 * file. This function should be modified if we want other types
	 * of file to be downloaded
	 */
	private boolean fileOfInterest(String file)
	{
		if(file == null)
			return false;
		if(file.endsWith(".html") || file.endsWith(".htm") ||
			file.endsWith(".shtml") || file.endsWith(".gif") ||
			file.endsWith(".jpg") || file.endsWith(".jpeg") ||
			file.endsWith(".txt"))
				return true;
		else
			return false;

	}
}
