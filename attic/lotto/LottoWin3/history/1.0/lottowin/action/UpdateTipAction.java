
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

public final class UpdateTipAction extends LottowinBaseAction {

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

    Tip tip = user.getTip(id);

    TipForm newForm = new TipForm();
    newForm.setZ1(String.valueOf(tip.getZ1()));
    newForm.setZ2(String.valueOf(tip.getZ2()));
    newForm.setZ3(String.valueOf(tip.getZ3()));
    newForm.setZ4(String.valueOf(tip.getZ4()));
    newForm.setZ5(String.valueOf(tip.getZ5()));
    newForm.setZ6(String.valueOf(tip.getZ6()));

    request.setAttribute("tipForm", newForm);
    session.setAttribute("update", new Integer(id));
    	
    CAT.debug(tip);
    CAT.debug(newForm);

    // Forward control to the specified success URI
    return (new ActionForward(mapping.getInput()));

  
  }

}
