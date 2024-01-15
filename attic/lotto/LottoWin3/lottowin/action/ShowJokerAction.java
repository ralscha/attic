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

public final class ShowJokerAction extends Action {

  private static Category CAT = Category.getInstance(ShowJokerAction.class.getName());

  public ActionForward perform(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request, HttpServletResponse response) 
                               throws IOException, ServletException {


    HttpSession session = request.getSession();
    NDC.push(session.getId());

    try {

      User user = (User)session.getAttribute(lottowin.Constants.USER_KEY);
      if (user == null) {
        CAT.warn("user object not in session");
        return (mapping.findForward("logon"));
      }

      // remove old attributes 
      session.removeAttribute("update");
      session.removeAttribute("page");

      // add the new model
      session.setAttribute("model", new JSPTableModel(createTableModel(user)));
    		
      CAT.debug("show joker action");

      // Forward control to the specified success URI
      return (mapping.findForward("success"));

    } finally {
      NDC.pop();
    }

  }

  private TableModel createTableModel(User user) {
	  // create a DefaultTableModel
    DefaultTableModel dtm = new DefaultTableModel();
    dtm.setColumnCount(2);

    Joker[] jokers = user.getJokers();

    if (jokers == null)
      return dtm;

    String[] strArray = new String[2];

    for (int i = 0; i < jokers.length; i++) {
      strArray[0] = String.valueOf(jokers[i].getJoker());
      strArray[1] = String.valueOf(jokers[i].getId());
      dtm.addRow(strArray);
    }
    
    return dtm;
	  
	 
  }

}
