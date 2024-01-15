import gnu.regexp.*;
import java.util.*;
import javax.servlet.*;

public class BrowserCapabilities {

    
	private static List capabilitiesList = null;
	private static Capability defaultCap = null;

	public static Capability getBrowserCapability(javax.servlet.http.HttpServletRequest req) {	
		return getBrowserCapability(req.getHeader("user-agent"));
	}
		
	public static Capability getBrowserCapability(String userAgent) {
		try {
		
			REMatch match = null;
			
			if (capabilitiesList == null) {
				capabilitiesList = new BrowsCapFileReader().getCapabilities();
				
				Iterator it = capabilitiesList.iterator();
				while(it.hasNext()) {
					Capability cap = (Capability)it.next();
					match = cap.getRegex().getMatch("Default Browser Capability Settings");
					if (match != null) {
						defaultCap = cap;
						break;
					}
				}
				
			} 
			
			Iterator it = capabilitiesList.iterator();
			
			while(it.hasNext()) {
				Capability cap = (Capability)it.next();
				match = cap.getRegex().getMatch(userAgent);
				if (match != null) {
					cap.setUseragent(userAgent);
					return cap;
				} 
			}
			
			defaultCap.setUseragent(userAgent);
			return defaultCap;
			
		} catch (Exception e) {
			System.err.println(e);
			return null;
		}
	}


	/*
	request.getHeader("user-agent")

	oder zB gleich alle Header ausgeben:

	Enumeration e = req.getHeaderNames();
	while(e.hasMoreElements()) {
	  String name = (String)e.nextElement();
	  out.println("http-"+name+" = "+req.getHeader(name)+"<BR>");
	}
	*/


}