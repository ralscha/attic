package ch.ess.pbroker.action;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import org.apache.struts.action.*;
import org.apache.struts.util.*;
import ch.ess.pbroker.form.*;
import ch.ess.pbroker.session.*;
import ch.ess.pbroker.db.*;
import com.codestudio.util.*;

public class LoginAction extends ActionBase {

	public ActionForward perform(ActionServlet servlet, ActionMapping mapping, ActionForm form,
 			              HttpServletRequest request, HttpServletResponse response)
 	                    throws IOException, ServletException {

	
		// Extract attributes and parameters we will need
		Locale locale = getLocale(request);
		MessageResources messages = getResources(servlet);
		HttpSession session = request.getSession();
		LoginForm loginForm = (LoginForm)form;
		
			 
		if (loginForm.getLogon() != null) {
		   
        String userstr = loginForm.getUser();
        String password = loginForm.getPassword();
        String errorText = null;


        Benutzer benutzer = null;
        try {
          benutzer = getBenutzer(userstr);          
        } catch (SQLException sqle) {
          ServletException se = new ServletException(sqle);
          throw se;
        } catch (PoolPropsException ppe) {
          throw new ServletException(ppe);
        }

        if (benutzer != null) {
          if (benutzer.getPasswort().equals(password)) {

  		      if (mapping.getFormAttribute() != null)
	  	          session.removeAttribute(mapping.getFormAttribute());

            User user = new User(benutzer);
            session.setAttribute(ch.ess.pbroker.Constants.USER_KEY, user);

            return (mapping.findForward("success"));
          } else {
            errorText = "Passwort stimmt nicht";
          }
        } 
        
        loginForm.clearPassword();
        
        if ((userstr != null) || (password != null)) {
          if (errorText == null)
            errorText = "Fehlerhafte Eingabe";
                   
          request.setAttribute(ch.ess.pbroker.Constants.ERROR_KEY, errorText);
        }

  		  if (mapping.getFormAttribute() != null)
	  	      session.removeAttribute(mapping.getFormAttribute());
	  	  session.setAttribute(mapping.getFormAttribute(), loginForm);
	  	               
        return (mapping.findForward("login"));

		} else {
      return (mapping.findForward("login"));
		}
		
  }

  private Benutzer getBenutzer(String loginid) throws SQLException, PoolPropsException {
	  return Db.getBenutzerTable().selectOne("LOGINID = '" + loginid + "'");
  }

}
