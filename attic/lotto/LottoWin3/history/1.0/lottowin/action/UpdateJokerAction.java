
package lottowin.action;

import java.io.IOException;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.struts.action.*;
import org.apache.struts.util.*;
import lottowin.form.*;
import lottowin.resource.*;
import lottowin.db.*;
import com.objectmatter.bsf.*;
import ch.ess.taglib.table.*;
import javax.swing.table.*;
import org.log4j.*;

public final class UpdateJokerAction extends LottowinBaseAction {


  public ActionForward perform(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request, HttpServletResponse response) 
                               throws IOException, ServletException {



	  String idstr = request.getParameter("id");
	  if (idstr == null) {
      CAT.warn("id not in request");
      return (new ActionForward(mapping.getInput()));	
	  }

    int id = 0;
    try {
      id = Integer.parseInt(idstr);
    } catch (NumberFormatException nfe) {
      CAT.warn("parseInt id", nfe);
      return (new ActionForward(mapping.getInput()));	    
    }

    if (CAT.isDebugEnabled())
      CAT.debug("id = " + id);

    String pagestr = request.getParameter("page");
    if (pagestr != null) {      

      if (CAT.isDebugEnabled())
        CAT.debug("page = " + pagestr);

      try {
        session.setAttribute("page", new Integer(pagestr));    
      } catch (NumberFormatException nfe) { 
        CAT.warn("parse pagestr", nfe);
      }      
    }
      
    Joker joker = user.getJoker(id);
    JokerForm newForm = new JokerForm();

    newForm.setJoker(joker.getJoker());

    request.setAttribute("jokerForm", newForm);
    session.setAttribute("update", new Integer(id));
    
    CAT.debug(joker);
    CAT.debug(newForm);
    	
    // Forward control to the specified success URI
    return (new ActionForward(mapping.getInput()));


  }

}
