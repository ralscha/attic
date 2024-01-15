package gtf.servlets;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import common.util.log.*;
import common.util.*;
import gtf.control.*;

public class AWZAOpenItemsServlet extends HttpServlet {

	public void destroy() {
		super.destroy();
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	
		res.setContentType("text/html");
		
		// Get the session
    	HttpSession session = req.getSession(true);
	
		Enumeration values = req.getParameterNames();
		Vector orderVector = new Vector();
		
		while (values.hasMoreElements()) {
			String name = (String) values.nextElement();
			String value = req.getParameterValues(name)[0];
	
			orderVector.addElement(name);
		}
	
		if (orderVector.size() > 0) {
	
			PrintWriter pw = new PrintWriter(new FileWriter(AppProperties.getStringProperty("awza.check.ok.file"), true));
				
			Enumeration e = orderVector.elements();
			while(e.hasMoreElements()) {
				String orderno = (String)e.nextElement();
				
				pw.println(orderno);
					
			}
			pw.close();
		
    		String bookingsFileName = AppProperties.getStringProperty("awza.check.bookings.file");
			new AWZACheck().check(bookingsFileName);			
			
			res.sendRedirect(AppProperties.getStringProperty("awza.check.overview.url"));
    		
    	} 
	}
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);	
		String datFile = getInitParameter("props.file");
		AppProperties.setFileName(datFile);		
	}
	
}

    		
    		