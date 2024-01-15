import java.io.*;
import javax.servlet.http.*;

public class BrowserInfoFactoryEx {
	
	private final static String INFO_HTML = "info.html";
	
	public static BrowserInfo getBrowserInfo(HttpServletRequest req, 
														  HttpServletResponse response) { 

		String heightstr = req.getParameter("height");
		String widthstr = req.getParameter("width");
		
		if ((heightstr != null) && (widthstr != null)) {
			
			int height = -1;
			int width = -1;
			try {	
				height = Integer.parseInt(heightstr);
				width = Integer.parseInt(widthstr);
			} catch (NumberFormatException nfe) { }
			
			BrowserInfo info = new BrowserInfo(req.getHeader("user-agent"));			
			info.setJavascriptOn(true);
			info.setScreenWidth(width);
			info.setScreenHeight(height);
			return info;
		} else if (req.getParameter("N") != null) {
		   BrowserInfo info = new BrowserInfo(req.getHeader("user-agent"));
			info.setJavascriptOn(false);
			return info;
		} else {
			try {
			
				StringBuffer referersb = HttpUtils.getRequestURL(req);
				
				if (req.getQueryString() != null) {
					referersb.append("?").append(req.getQueryString());
				}
				
				String referer = referersb.toString();
				
				PrintWriter pw = response.getWriter();			
				
				InputStream is = BrowserInfoFactoryEx.class.getResourceAsStream(INFO_HTML);				
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
			   String line;
				while((line = br.readLine()) != null) {
					int pos = line.indexOf("REFERER");
					if (pos != -1) {
						pw.print(line.substring(0, pos));
						pw.print(referer);
						pw.println(line.substring(pos+7));

					} else {
						pw.println(line);
					}	
				}
				
				response.flushBuffer();
			} catch (Exception e) { }			
			
			return null;
		}
	}

}