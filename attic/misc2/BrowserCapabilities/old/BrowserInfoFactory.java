

public class BrowserInfoFactory {
	
	public static BrowserInfo getBrowserInfo(javax.servlet.http.HttpServletRequest req, javax.servlet.jsp.PageContext context, String referer) {
	
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
				req.setAttribute("referer", referer);
				context.forward("info.jsp");
			} catch (Exception e) { }
			return null;
		}
	}

}