package ch.ess.addressbook.action;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import org.apache.struts.action.*;
import org.apache.struts.util.*;
import ch.ess.addressbook.form.*;

public class AddContactAction extends ActionBase {

	public void perform(ActionServlet servlet, ActionMapping mapping, ActionForm form,
 			              HttpServletRequest request, HttpServletResponse response)
 	                    throws IOException, ServletException {

	
		// Extract attributes and parameters we will need
		Locale locale = getLocale(request);
		MessageResources messages = getResources(servlet);
		HttpSession session = request.getSession();
		ContactForm contactform = (ContactForm)form;
		
		// Was this transaction cancelled?
		String submit = request.getParameter("submit");
		if (submit == null)
		    submit = "Submit";
			 
		if (submit.equals("cancel")) {
		   
		    if (mapping.getFormAttribute() != null)
		        session.removeAttribute(mapping.getFormAttribute());
				  
		    RequestDispatcher rd = servlet.getServletContext().getRequestDispatcher("cancel.html");
		    rd.forward(request, response);
		    return;
		}
		

		// Validate the request parameters specified by the user
		String value = null;
		Vector errors = new Vector();
		//value = subform.getHost();
		//if ((value == null) || (value.length() < 1))
		//    errors.addElement(messages.getMessage(locale, "error.host.required"));


		// Report any errors we have discovered back to the original form
		if (errors.size() > 0) {
			String results[] = new String[errors.size()];
		 	for (int i = 0; i < results.length; i++)
		   	results[i] = (String) errors.elementAt(i);
			   request.setAttribute(ERROR_KEY, results);
		 
				RequestDispatcher rd = servlet.getServletContext().getRequestDispatcher("addContact1.jsp");

				rd.forward(request, response);
				return;
		}

		// Remove any obsolete session objects
		if (mapping.getFormAttribute() != null) {
			servlet.log("form = " + session.getAttribute(mapping.getFormAttribute()));
			session.removeAttribute(mapping.getFormAttribute());
		}

		// Forward control to the specified success URI
		RequestDispatcher rd = servlet.getServletContext().getRequestDispatcher("/success.html");
		servlet.log("requestdispatcher = " + rd);
		rd.forward(request, response);


	}


}
