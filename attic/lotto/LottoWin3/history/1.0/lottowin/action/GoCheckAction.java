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

public final class GoCheckAction extends LottowinBaseAction {


  public ActionForward perform(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request, HttpServletResponse response) 
                               throws IOException, ServletException {


    // remove old attributes 
    session.removeAttribute("update");
    session.removeAttribute("page");
	  session.removeAttribute("model");

    CAT.debug("go check action");

    // Forward control to the specified success URI
    return (mapping.findForward("success"));


  }

}
