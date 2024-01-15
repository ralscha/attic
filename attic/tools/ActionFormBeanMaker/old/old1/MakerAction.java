

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import org.apache.struts.action.*;
import org.apache.struts.util.*;


public class MakerAction extends ActionBase {

	public ActionForward perform(ActionServlet servlet, ActionMapping mapping, ActionForm form,
 			              HttpServletRequest request, HttpServletResponse response)
 	                    throws IOException, ServletException {


		// Extract attributes and parameters we will need
		Locale locale = getLocale(request);
		MessageResources messages = getResources(servlet);
		HttpSession session = request.getSession();
		MakerForm makerForm = (MakerForm)form;
    makerForm.processCheckboxes();

   servlet.log("testcheck = " + request.getParameter("testcheck"));


		// Was this transaction cancelled?
		if (makerForm.getSubmit().equalsIgnoreCase("cancel")) {
		   
		    if (mapping.getFormAttribute() != null)
		        session.removeAttribute(mapping.getFormAttribute());
        
        return (mapping.findForward("test"));
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
	 
      return (mapping.findForward("test"));
		}

		// Remove any obsolete session objects
		//if (mapping.getFormAttribute() != null) {
		//	session.removeAttribute(mapping.getFormAttribute());
		//}

		// Forward control to the specified success URI

  	return (mapping.findForward("test"));
	}


}
