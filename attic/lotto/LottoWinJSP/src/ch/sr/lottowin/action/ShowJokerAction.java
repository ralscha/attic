package ch.sr.lottowin.action;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.swing.table.*;

import org.apache.struts.action.*;

import ch.ess.tag.table.*;
import ch.sr.lottowin.db.*;

public final class ShowJokerAction extends BaseAction {

  
  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request, HttpServletResponse response) 
                               throws IOException, ServletException {


    HttpSession session = request.getSession();


      User user = (User)session.getAttribute(ch.sr.lottowin.Constants.USER_KEY);
      if (user == null) {
        LOG.warn("user object not in session");
        return (mapping.findForward("logon"));
      }

      // remove old attributes 
      session.removeAttribute("update");
      session.removeAttribute("page");

      // add the new model
      session.setAttribute("model", new JSPTableModel(createTableModel(user)));
    		
      LOG.debug("show joker action");

      // Forward control to the specified success URI
      return (mapping.findForward("success"));


 
  }

  private DefaultTableModel createTableModel(User user) {
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
