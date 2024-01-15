package ch.sr.lottowin.action;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.swing.table.*;

import org.apache.struts.action.*;

import ch.ess.tag.table.*;
import ch.sr.lottowin.db.*;

public final class ShowTipAction extends BaseAction {

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

    
      session.setAttribute("model", new JSPTableModel(createTableModel(user)));

      LOG.debug("show tip action");
    		
      // Forward control to the specified success URI
      return (mapping.findForward("success"));



  }

  private DefaultTableModel createTableModel(User user) {
	  // create a DefaultTableModel
	  DefaultTableModel dtm = new DefaultTableModel();
    dtm.setColumnCount(7);

    Tip[] tips = user.getTips();

    if (tips == null)
      return dtm;

    String[] strArray = new String[7];

    for (int i = 0; i < tips.length; i++) {
      strArray[0] = String.valueOf(tips[i].getZ1());
      strArray[1] = String.valueOf(tips[i].getZ2());
      strArray[2] = String.valueOf(tips[i].getZ3());
      strArray[3] = String.valueOf(tips[i].getZ4());
      strArray[4] = String.valueOf(tips[i].getZ5());
      strArray[5] = String.valueOf(tips[i].getZ6());
      strArray[6] = String.valueOf(tips[i].getId());
      dtm.addRow(strArray);
    }
	
	  return dtm; 
  }

}
