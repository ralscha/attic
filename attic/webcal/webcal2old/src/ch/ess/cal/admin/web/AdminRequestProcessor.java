package ch.ess.cal.admin.web;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;
import org.apache.struts.tiles.*;

import ch.ess.cal.*;


public class AdminRequestProcessor extends TilesRequestProcessor {
  
  private static final Set pathWithoutLogon = new HashSet();
  static {
    pathWithoutLogon.add("/default");
    pathWithoutLogon.add("/logon");
    pathWithoutLogon.add("/error");
  }

  protected boolean processRoles(HttpServletRequest request, HttpServletResponse response, ActionMapping mapping)
    throws IOException, ServletException {
    
    String roles[] = mapping.getRoleNames();
    if ((roles == null) || (roles.length < 1)) {
      if (pathWithoutLogon.contains(mapping.getPath())) {
        return true;
      }
      
      HttpSession session = request.getSession();  
      if ((session == null) || (session.getAttribute(Constants.ADMIN_USER_KEY) == null)) {  
        ActionForward forward = mapping.findForward("logon");
        processForwardConfig(request, response, forward);                 
        return false;
      } 
      return true;
    } else {      
      return super.processRoles(request, response, mapping);    
    }

  }

}
