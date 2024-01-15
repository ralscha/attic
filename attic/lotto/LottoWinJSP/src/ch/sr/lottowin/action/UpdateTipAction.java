
package ch.sr.lottowin.action;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;

import ch.sr.lottowin.db.*;
import ch.sr.lottowin.form.*;

import com.objectmatter.bsf.*;

public final class UpdateTipAction extends BaseAction {

 
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
      Tip tip = UserUtil.getTip(db, user, id);

      TipForm newForm = new TipForm();
      newForm.setZ1(String.valueOf(tip.getZ1()));
      newForm.setZ2(String.valueOf(tip.getZ2()));
      newForm.setZ3(String.valueOf(tip.getZ3()));
      newForm.setZ4(String.valueOf(tip.getZ4()));
      newForm.setZ5(String.valueOf(tip.getZ5()));
      newForm.setZ6(String.valueOf(tip.getZ6()));

      request.setAttribute("tipForm", newForm);
      session.setAttribute("update", new Integer(id));
    		
      LOG.debug(tip);
      LOG.debug(newForm);

      // Forward control to the specified success URI
      return (new ActionForward(mapping.getInput()));


  
  }

}
