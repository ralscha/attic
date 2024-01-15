import javax.servlet.*;
import com.mortbay.HTTP.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class SWIFTServer {

    private HttpServer httpServer = null;

    public void stopServer() {
        if (httpServer != null) {
            httpServer.close();
            httpServer = null;
        }
        DiskLog.log(DiskLog.INFO, getClass().getName(), "Server stopped");
    }   
    
    public void destroyServlets() {
        Enumeration e = httpServer.getServletNames();
        while (e.hasMoreElements()) {
            Servlet s = (Servlet)httpServer.getServlet((String)e.nextElement());
            s.destroy();
        }
    }
    
    public void startServer() {
        SWIFTServerConfiguration config;
        try {
            config = new SWIFTServerConfiguration();
            httpServer = new HttpServer(config);
        } catch (Exception e) {
            DiskLog.log(DiskLog.FATAL, getClass().getName(), "startServer " + e);
        }
        DiskLog.log(DiskLog.INFO, getClass().getName(), "Server started");
    }   
    
    public SWIFTServer() {
    	startServer();
      	new FileDataReaderRunner(this);
    	
    	
    	
    }

    public static void main(String args[]) {        
        new SWIFTServer();    
    }
}
