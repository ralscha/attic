

package lottowin.action;

import java.io.IOException;
import java.util.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import org.apache.struts.action.*;
import org.apache.struts.util.*;
import lottowin.form.*;
import lottowin.resource.*;
import lottowin.db.*;
import com.objectmatter.bsf.*;
import javax.swing.table.*;
import ch.ess.taglib.table.*;
import org.log4j.*;

public final class RemoveJokerAction extends Action {

  private static Category CAT = Category.getInstance(RemoveJokerAction.class.getName());

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

      Database db = AppConfig.getDatabase();
      Joker joker = user.getJoker(id);

      CAT.debug(joker);
                    
      try {
    	  if (joker != null)
          db.delete(joker);
      } catch (Exception e) {
        CAT.error("delete(joker)", e);
        throw new ServletException(e);
      }

      Joker[] jokers = user.getJokers();
      if ((jokers != null) && (jokers.length > 0)) {
        for (int i = 1; i <= jokers.length; i++) {
          try {
            db.delete(jokers[i-1]);
          } catch (Exception e) {
            CAT.error("delete(jokers[i-1])", e);
            throw new ServletException(e);  
          }
          if (jokers[i-1].getId() != i)
            jokers[i-1].setId(i);
      
          try {
            user.addJokers(jokers[i-1]);
          } catch (Exception e) {
            CAT.error("addJokers(jokers[i-i])", e);
            throw new ServletException(e);        
          }
        }

        try {
          db.update(user);
        } catch (Exception e) {
          CAT.error("update(user)", e);
          throw new ServletException(e);
        }
      }

      DefaultTableModel dtm = new DefaultTableModel();
      dtm.setColumnCount(2);

      jokers = user.getJokers();
    
      if (jokers != null) {
        String[] strArray = new String[2];

        for (int i = 0; i < jokers.length; i++) {
          strArray[0] = String.valueOf(jokers[i].getJoker());
          strArray[1] = String.valueOf(jokers[i].getId());
          dtm.addRow(strArray);
        }
      }

      JSPTableModel tableModel = (JSPTableModel)session.getAttribute("model");
      if (tableModel == null) {
        CAT.debug("create new JSPTableModel");
        tableModel = new JSPTableModel(dtm);
        session.setAttribute("model", tableModel);
      } else {
        CAT.debug("reuse JSPTableModel");
        tableModel.setModel(dtm);
      }

      // Forward control to the specified success URI
      return (new ActionForward(mapping.getInput()));

    } finally {
      NDC.pop();
    }

  }


}
