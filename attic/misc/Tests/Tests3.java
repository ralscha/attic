
import java.io.*;
import com.hothouseobjects.tags.*;
import java.util.*;
import java.net.*;

public class Tests3 {

	public static void downloadRessource(URL url, String fileName) {
		
		InputStream pageStream = null;

		try {
			pageStream = url.openStream();
			if (pageStream == null)
				return;
				
		} catch (Exception error) {
			System.out.println("get(host, file) failed!" + error);
			return;
		}
				
		InputStream source = null;
		OutputStream dest = null;
		try {
			source = new BufferedInputStream(pageStream);;
			dest = new FileOutputStream(fileName);
			byte[] buffer = new byte[1024];
			int bytes_read;
			while (true) {
				bytes_read = source.read(buffer);
				if (bytes_read == -1)
					break;
				dest.write(buffer, 0, bytes_read);
			}
		} catch (IOException ioe) {
			System.err.println(ioe);
		} finally { 
			if (source != null)
				try {
					source.close();
				} catch (IOException e) { }
			if (dest!= null)
				try {
					dest.close();
				} catch (IOException e) { }
		} 

	}
	
	public static String downloadWWWPage(URL pageURL) {
		InputStream pageStream = null;

		try {
			pageStream = pageURL.openStream();
			if (pageStream == null)
				return("");
				
		} catch (Exception error) {
			System.out.println("get(host, file) failed!" + error);
			return "";
		}

		BufferedReader in = new BufferedReader(new InputStreamReader(pageStream));
		StringBuffer pageBuffer = new StringBuffer();
		String line;

		try {
			while ((line = in.readLine()) != null) {
				// System.out.println(line);
				pageBuffer.append(line);
			}
			in.close();
		} catch (Exception error) {
			System.out.println("IOException: " + error);
		}

		return pageBuffer.toString();
	}



	public static void main(String[] args) {
		/*
		try {
			downloadRessource(new URL("http://www.geocities.com/~director95/ibrator.mov"),"ibrator.mov");

				
		} catch (Exception e) {
			System.err.println(e);
		}
		*/
		if (args.length == 1) {
			try {

				URL goURL = new URL(args[0]);
				
				Reader reader = new InputStreamReader(goURL.openStream());
				TagTiller tiller = new TagTiller(reader);
				Tag theParsedPage = tiller.getTilledTags();

				Vector vect = theParsedPage.collectByType("A");
				Iterator it = vect.iterator();
				while (it.hasNext()) {
					
					Tag t = (Tag) it.next();
					String url = t.getAttributeValue("HREF", true);
					int posStart = url.indexOf("('") + 2;
					int posEnd   = url.lastIndexOf("',");
					if ((posStart != -1) && (posEnd != -1)) {
						String goToStr = "http://www.sevac.com/"+url.substring(posStart, posEnd);
							System.out.println(goToStr);
						URL goToURL = new URL(goToStr);
						String file = goToURL.getFile();
						int p = file.lastIndexOf("/")+1;
						if (p != -1) {
							file = file.substring(p);
							String page = downloadWWWPage(goToURL);	
							FileWriter fw = new FileWriter(file);
							fw.write(page);
							fw.close();
						}
						
					}

				}
			} catch (Exception e) {
				System.err.println(e);
			}
		}
		
	}
}