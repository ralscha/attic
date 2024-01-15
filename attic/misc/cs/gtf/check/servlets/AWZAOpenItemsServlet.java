package gtf.check.servlets;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

import gtf.check.AWZACheck;

import common.util.AppProperties;

public class AWZAOpenItemsServlet extends HttpServlet implements gtf.common.Constants {

	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	
		res.setContentType("text/html");
	
		Enumeration values = req.getParameterNames();
		Vector orderVector = new Vector();
		
		while (values.hasMoreElements()) {
			String name = (String) values.nextElement();
			String value = req.getParameterValues(name)[0];
	
			orderVector.addElement(name);
		}
	
		if (orderVector.size() > 0) {
	
			PrintWriter pw = new PrintWriter(new FileWriter(AppProperties.getStringProperty(P_AWZA_CHECK_OK_FILE), true));
				
			Enumeration e = orderVector.elements();
			while(e.hasMoreElements()) {
				String orderno = (String)e.nextElement();
				
				pw.println(orderno);
					
			}
			pw.close();
		
			new AWZACheck().check();			
			
			res.sendRedirect(AppProperties.getStringProperty(P_AWZA_CHECK_OVERVIEW_URL));
    		
    	} 
	}
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);	
		String datFile = getInitParameter(P_PROPS_FILE);
		AppProperties.setFileName(datFile);		
	}
	
}

    		
    		