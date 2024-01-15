import com.mortbay.Base.*;
import com.mortbay.Util.*;
import com.mortbay.Servlets.*;
import com.mortbay.HTML.*;
import com.mortbay.HTTP.*;
import com.mortbay.HTTP.Handler.*;
import java.io.*;
import java.net.*;
import javax.servlet.*;
import java.util.*;

public class SWIFTServerConfiguration extends com.mortbay.HTTP.Configure.BaseConfiguration {
    
    public SWIFTServerConfiguration() throws IOException {
        int port = 80;
        
        String portStr = AppProperties.getProperty("port");
        if (portStr != null) {
            try {
                port = Integer.parseInt(portStr);
            } catch (NumberFormatException nfe) { 
                port = 80;
            }
        }

    	// Listen at a single port on the localhost
    	addresses=new InetAddrPort[1];
    	addresses[0]=new InetAddrPort();
    	addresses[0].inetAddress = InetAddress.getLocalHost();
    	addresses[0].port=port;

    	// Create single stack of HttpHandlers at "/"
    	httpHandlersMap=new PathMap();
    	HttpHandler[] httpHandlers = new HttpHandler[6];
    	httpHandlersMap.put("/",httpHandlers);
    	int h=0;

    	// Parameter handler
    	httpHandlers[h++] = new ParamHandler();

/*
    	// Log Handler
	    PathMap logMap = new PathMap();
    	HttpHandler log = new LogHandler(logMap, true, true);
	    httpHandlers[h++] = log;
    	logMap.put("", DiskLog.getWriter());
*/    	
        String userStr = AppProperties.getProperty("user1");
        String pwStr   = AppProperties.getProperty("pw1");

    	
    	if ((userStr != null) && (pwStr != null)) {
    	    // Authentication handler
        	PathMap authMap = new PathMap();
        	httpHandlers[h++] = new BasicAuthHandler(authMap);
        	BasicAuthRealm realm = new BasicAuthRealm("SWIFT");
           	realm.put(userStr, pwStr);
        	authMap.put("/SWIFT", realm);
         	authMap.put("/Msg", realm);         	
            authMap.put("/State", realm);

            BasicAuthRealm kontrRealm = new BasicAuthRealm("CONTROL");
            kontrRealm.put(AppProperties.getProperty("controlUser"), 
                           AppProperties.getProperty("controlPW"));
            authMap.put("/Control", kontrRealm);
            authMap.put("/MissingSWIFT", kontrRealm);
       	}

    	// Servlet Handler
    	PathMap servletMap= new PathMap();
    	servletMap.put("/SWIFTInput", new ServletHolder("SWIFTInput", "SWIFTInputServlet"));
    	servletMap.put("/Msg",   new ServletHolder("SWIFTtosn", "SWIFTtosnServlet"));
    	servletMap.put("/State",   new ServletHolder("State", "StateServlet"));
    	servletMap.put("/MissingSWIFT", new ServletHolder("SWIFTMissingServlet", "SWIFTMissingServlet"));    	
    	servletMap.put("/Payment", new ServletHolder("PaymentServlet", "PaymentServlet"));
    	
        StringTokenizer st = new StringTokenizer(AppProperties.getProperty("controlHead"), ",");
        Hashtable params[] = new Hashtable[st.countTokens()];        
        
        int i = 0;
        while (st.hasMoreTokens()) {
            params[i] = new Hashtable();
            params[i].put("Head", st.nextToken());    
            i++;
        }
        
        
        i = 0;
        st = new StringTokenizer(AppProperties.getProperty("controlFile"), ",");
        while (st.hasMoreTokens()) {
            params[i].put("File", st.nextToken());
            i++;
        }

        i = 0;
        st = new StringTokenizer(AppProperties.getProperty("controlTail"), ",");
        while (st.hasMoreTokens()) {
            params[i].put("Tail", st.nextToken());
            i++;
        }
    	
    	for (i = 0; i < params.length; i++) {
            servletMap.put("/Control"+(i+1), new ServletHolder("FileIncludeServlet","FileIncludeServlet",params[i]));
        }
    	
    	httpHandlers[h++] = new ServletHandler(servletMap);

    	// Filter handler
	    PathMap filterMap = new PathMap();
    	httpHandlers[h++] = new FilterHandler(filterMap);
	    filterMap.put("/","com.mortbay.HTTP.Filter.HtmlFilter");

        // File Handler
        httpHandlers[h++] = new FileHandler(".", "index.html");

    	// NotFound Handler
    	httpHandlers[h++] = new NotFoundHandler();
    }

}
