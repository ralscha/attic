import java.io.*;
import java.util.*;
import java.net.*;

public class Downloader
{
	/*
	 * Destination directory
	 */
	private String destination = null;

	/*
	 * URL of the first page
	 */
	private URL firstPageURL = null;

	/*
	 * Host Name. "java.sun.com" in http://java.sun.com/products/jdk/1.2
	 */
	private String hostName = null;

	/*
	 * Hold the URL of the page that is currently being downloaded
	 */
	private URL currentURL = null;

	/*
	 * Hold the base URL (in the <base tag) if present
	 */
	private URL baseTagURL = null;

	/*
	 * Maintains the overall list of URLs. Helps in detecting if the page
	 * is already downloaded.
	 */
	static Vector URLs = new Vector(100);

	public Downloader(URL u, String dest)
	{
		firstPageURL = u;
		hostName = u.getHost();
		destination = dest;
	}

	void download()
	{
			startDownload(firstPageURL);
	}

	/*
	 * This is the most important routine. Its purpose:
	 * 1. Download the page
	 * 2. Get the list of links on this page in a vector
	 * 3. Repeat the same process over each element of Vector recursively
	 */
	private void startDownload(URL u)
	{
		Vector listOfURL = new Vector(100);
		currentURL = u;

		// Setting the input stream
		InputStream in = null;
		try
		{
			in = u.openStream();
		}
		catch(IOException e)
		{
			System.err.println("Unable to download : " + u);
			System.err.println(e.getMessage());
		}

		// Setting the output stream with the help of ExtendedURL class
		ExtendedURL eURL = new ExtendedURL(u);
		File fdir = new File(destination + eURL.getDirectory());
		if(!fdir.exists())
			fdir.mkdirs();

		File ffile = null;
		String fileName = eURL.getFile();
		// if the file name is null, set it to index.html
		if(fileName == null) 
			ffile = new File(fdir, "index.html");
		else
			ffile = new File(fdir, fileName);


		OutputStream out = null;
		try
		{
			if(!ffile.isDirectory())
				out = new FileOutputStream(ffile);
		}
		catch(FileNotFoundException e)
		{
			System.err.println("Unable to create file : " + ffile.getPath());
			System.err.println(e.getMessage());
		}
		catch(IOException e)
		{
			System.err.println(e.getMessage());
		}

		// Printing status info
		System.out.println("Downloading ... [" + currentURL + "]");
		System.out.println("    To: " + ffile);

		if(fileName != null && nonTextfile(fileName))
			// download binary file
			downloadNonTextfile(in, out);
		else
			// get the list of links
			listOfURL = downloadAndFillVector(in, out);

		// loop over the list to download files
		int sizeOfVector = 0;
		if(listOfURL != null)
			sizeOfVector = listOfURL.size();

		for(int i = 0; i < sizeOfVector; i++)
			startDownload((URL)listOfURL.elementAt(i)); 
	}

	/* 
	 * This method takes file name as the argument and returns true
	 * if the file is a binary (not a text file) and false otherwise.
	 */
	private boolean nonTextfile(String file)
	{
		String lcFile = file.toLowerCase();
		if(lcFile.endsWith(".gif") || lcFile.endsWith(".jpg") ||
			lcFile.endsWith(".jpeg")) 
				return true;
		return false;
	}

	/*
	 * Download the binary files (simple copy)
	 */
	private void downloadNonTextfile(InputStream in, OutputStream out)
	{
		int nbytes = 0;	// number of bytes read
		byte [] buffer = new byte[4096]; // buffer to hold the data
		if(in == null || out == null)
			return;
		try
		{
			while((nbytes = in.read(buffer)) != -1)
				out.write(buffer, 0, nbytes);
		}
		catch(IOException e)
		{
			System.err.println(e.getMessage());
		}
		finally
		{
			try
			{
				if(in != null)
					in.close();
				if(out != null)
					out.close();
			}
			catch(IOException e) { }
		}
	}

	/*
	 * This method does the first phase of parsing and calls other
	 * methods for subsequent parsing. It returns a vector. This
	 * vector contains all the links in the page being downloaded.
	 */
	private Vector downloadAndFillVector(InputStream in, OutputStream out)
	{
		Vector v = new Vector(100);
		int c;

		if(out == null || in == null)
			return null;

		try
		{
			while((c = in.read()) != -1)
			{
				if(c == '<')
				{
					out.write(c);
					c = in.read();
					if(c == -1)
						break;
					// start of comment <!--
					else if(c == '!')
					{
						out.write(c);
						c = in.read();
						if(c == -1)
							break;
						else if(c == '-')
						{
							out.write(c);
							c = in.read();
							if(c == -1)
								break;
							else if(c == '-')
							{
								// it is a comment
								out.write(c);
								while((c = in.read()) != -1)
								{
									// search for end of comment -->
									if(c == '-')
									{
										c = in.read();
										if(c == '-')
										{
											c = in.read();
											if(c == '>')
											{
												out.write('-');
												out.write('-');
												out.write('>');
												break;
											}
											else
												out.write(c);
										}
										else
											out.write(c);
									}
									else
										out.write(c);
								}
							}
							else
								out.write(c);
						}
						else
							out.write(c);
					}
					// <a
					else if(c == 'a' || c == 'A')
					{
						out.write(c);
						StringBuffer s = new StringBuffer(256);
						while((c = in.read()) != '>')
							s.append((char)c);
						s = modifyLink(out, new String(s));
						if(s != null && s.length() != 0)
							v.addElement(new String(s));
						out.write(c);
					}
					// <base
					else if(c == 'b' || c == 'B')
					{
						c = in.read();
						if(c == -1)
							break;
						else if(c == 'a' || c == 'A')
						{
							c = in.read();
							if(c == -1)
								break;
							else if(c == 's' || c == 'S')
							{
								c = in.read();
								if(c == -1)
									break;
								else if(c == 'e' || c == 'E') 
								{
									StringBuffer s = new StringBuffer(256);
									s.append("base");
									while((c = in.read()) != '>')
									{
										/*
										 * Do not write anything to base tag
										 * in the document. We will modify the
										 * links appropriately.
										 */
										s.append((char)c);
									}
									String comment =
										"!-- Base tag is reomoved --";
									// add comment to the document
									out.write(comment.getBytes());
									comment = null;
									out.write(c);
									setBaseTagURL(new String(s));
								}
								else
								{
									out.write('b');
									out.write('a');
									out.write('s');
									out.write(c);
								}
							}
							else
							{
								out.write('b');
								out.write('a');
								out.write(c);
							}
						}
						else
						{
							out.write('b');
							out.write(c);
						}
					}
					// <img
					else if(c == 'i' || c == 'I')
					{
						out.write(c);
						c = in.read();
						if(c == -1)
							break;
						else if(c == 'm' || c == 'M')
						{
							out.write(c);
							c = in.read();
							if(c == -1)
								break;
							else if(c == 'g' || c == 'G')
							{
								out.write(c);
								StringBuffer s = new StringBuffer(256);
								while((c = in.read()) != '>')
									s.append((char)c);
								s = modifyLink(out, new String(s));
								if(s != null && s.length() != 0)
									v.addElement(new String(s));
								out.write(c);
							}
							else
								out.write(c);
						}
						else
							out.write(c);
					}
					// <frame
					else if(c == 'f' || c == 'F')
					{
						out.write(c);
						c = in.read();
						if(c == -1)
							break;
						else if(c == 'r' || c == 'R')
						{
							out.write(c);
							c = in.read();
							if(c == -1)
								break;
							else if(c == 'a' || c == 'A')
							{
								out.write(c);
								c = in.read();
								if(c == -1)
									break;
								else if(c == 'm' || c == 'M')
								{
									out.write(c);
									c = in.read();
									if(c == -1)
										break;
									else if(c == 'e' || c == 'E')
									{
										out.write(c);
										c = in.read();
										if(c == -1)
											break;
										else if(Character.isWhitespace((char)c))
										{
											StringBuffer s =
												new StringBuffer(256);
											s.append((char)c);
											while((c = in.read()) != '>')
												s.append((char)c);
											s = modifyLink(out, new String(s));
											if(s != null && s.length() != 0)
												v.addElement(new String(s));
											out.write(c);
										}
										else
											out.write(c);
									}
									else
										out.write(c);
								}
								else
									out.write(c);
							}
							else
								out.write(c);
						}
						else
							out.write(c);
					}
					else
						out.write(c);
				}
				else
					out.write(c);
			}
		}
		catch(IOException e)
		{
			System.err.println(e.getMessage());
		}
		finally
		{
			try
			{
				if(in != null)
					in.close();
				if(out != null)
					out.close();
			}
			catch(IOException e) { }
		}

		if(v.size() == 0) // there are no links on this page
			return null;

		return formVectorOfURLs(v);
	}

	/*
	 * This method performs the second phase of parsing.
	 * Returns a string that contains the unmodified link.
	 */
	private StringBuffer modifyLink(OutputStream out, String href)
		throws IOException
	{
		String lchref = href.toLowerCase();
		int hmaxIndex = href.length();

		int idx1, idx2;
		if((idx1 = lchref.indexOf("href")) != -1)
			idx1 += 4;
		else if((idx1 = lchref.indexOf("src")) != -1)
			idx1 += 3;
		else
		{
			out.write(href.getBytes());
			return null;
		}
		lchref = null;

		idx2 = href.indexOf('=', idx1);
		// should never happen
		if(idx2 == -1)
		{
			out.write(href.getBytes());
			return null;
		}
		idx2++;
		out.write(href.substring(0,idx2).getBytes());

		char c;
		while(Character.isWhitespace(c = href.charAt(idx2)))
		{
			out.write(c);
			idx2++;
		}

		if(c == '\"')
		{
			out.write(c);
			idx2++;
		}

		// idx2 is now the index of the start of the link.
		return processLink(out, href, idx2, hmaxIndex);
	}

	/*
	 * This method does the final parsing. 
	 * This method actually manipulates the links in the page
	 * being downloaded.
	 */
	private StringBuffer processLink(OutputStream out, String href,
		int j, int len) throws IOException
	{
		int c = 0;
		StringBuffer str = new StringBuffer(256);

		// should never happen
		if(j >= len)
			return str;

		while(j < len && (c = href.charAt(j)) != '\"' &&
			!Character.isWhitespace((char)c) && c != '#')
		{
			str.append((char)c);
			j++;
		}

		String tmpStr = str.toString();

		// if the link starts with 'http' i.e. an absolute link
		if(tmpStr.startsWith("http://"))
		{
			URL u = null;
			try
			{
				u = new URL(tmpStr);
			}
			catch(MalformedURLException e)
			{
				System.err.println(e.getMessage());
			}
			if(hostName.equals(u.getHost()))
			{
				out.write(destination.getBytes());
				out.write(u.getFile().getBytes());
			}
			else
				out.write(tmpStr.getBytes());
		}
		else if(tmpStr.indexOf(':') != -1)
		{
			// protocol other than http i.e. ftp, news, mailto
			out.write(tmpStr.getBytes());
		}
		else
		{
			if(baseTagURL == null)
			{
				// if the link starts with '/'
				if(tmpStr.startsWith("/"))
					out.write(destination.getBytes());
				out.write(tmpStr.getBytes());
			}
			else
			{
				URL tmpURL = new URL(baseTagURL, tmpStr);
				out.write(destination.getBytes());
				out.write(tmpURL.getFile().getBytes());
				tmpURL = null;
			}
		}

		if(tmpStr.endsWith("/"))
			out.write("index.html".getBytes());

		out.write(href.substring(j, len).getBytes());
		tmpStr = null;
		return str;
	}

	/*
	 * This method extracts the base URL specified in the base tag
	 * from the base string passed to it after first phase of parsing
	 */

	private void setBaseTagURL(String base)
	{
		StringBuffer s = new StringBuffer(256);
		if(base == null)	// should not happen
			return;

		int baseLen = base.length();
		String lcbase = base.toLowerCase();
		int idx1, idx2;
		if((idx1 = lcbase.indexOf("href")) != -1)
			idx1 += 4;
		else
			return;

		idx2 = base.indexOf('=', idx1);
		// should never happen
		if(idx2 == -1)
			return;
		idx2++;

		char c;
		while(Character.isWhitespace(c = base.charAt(idx2)))
			idx2++;

		if(c == '\"')
			idx2++;

		while(idx2 < baseLen && (c = base.charAt(idx2)) != '\"' &&
			!Character.isWhitespace((char)c))
		{
			s.append((char)c);
			idx2++;
		}

		String baseHref = new String(s);
		try
		{
			baseTagURL = new URL(baseHref);
		}
		catch(MalformedURLException e)
		{
			System.err.println(e.getMessage());
		}
		base = null;
	}

	/*
	 * This method returns a vector. Now this vector contains
	 * actual URLs. It takes the base string (link in the base tag)
	 * or current document's URL and a vector of links and generates
	 * complete URLs.
	 */

	private Vector formVectorOfURLs(Vector v)
	{
		URLlist ulist = null;

		if(baseTagURL == null)
			ulist = new URLlist(currentURL, v);
		else
			ulist = new URLlist(baseTagURL, v);

		baseTagURL = null;
		return ulist.getURLs();
			
	}
}
