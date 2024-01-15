
package ch.sr.lottowin.action;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;

import ch.sr.lottowin.db.*;
import ch.sr.lottowin.form.*;

import com.objectmatter.bsf.*;

public final class UpdateJokerAction extends BaseAction {

 
  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request, HttpServletResponse response) 
                               throws IOException, ServletException {


    HttpSession session = request.getSession();


      User user = (User)session.getAttribute(ch.sr.lottowin.Constants.USER_KEY);
      if (user == null) {
        LOG.warn("user object not in session");
        return (mapping.findForward("logon"));
      }

	    String idstr = request.getParameter("id");
	    if (idstr == null) {
        LOG.warn("id not in request");
        return (new ActionForward(mapping.getInput()));	
	    }

      int id = 0;
      try {
        id = Integer.parseInt(idstr);
      } catch (NumberFormatException nfe) {
        LOG.warn("parseInt id", nfe);
        return (new ActionForward(mapping.getInput()));	    
      }

      if (LOG.isDebugEnabled())
        LOG.debug("id = " + id);

      String pagestr = request.getParameter("page");
      if (pagestr != null) {      

        if (LOG.isDebugEnabled())
          LOG.debug("page = " + pagestr);

        try {
          session.setAttribute("page", new Integer(pagestr));    
        } catch (NumberFormatException nfe) { 
          LOG.warn("parse pagestr", nfe);
        }      
      }
      
      Database db = getDb(request);
      Joker joker = UserUtil.getJoker(db, user, id);
      JokerForm newForm = new JokerForm();
	
      newForm.setJoker(joker.getJoker());

      request.setAttribute("jokerForm", newForm);
      session.setAttribute("update", new Integer(id));
    	
    	LOG.debug(joker);
      LOG.debug(newForm);
    		
      // Forward control to the specified success URI
      return (new ActionForward(mapping.getInput()));


  }

}
