

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

public final class RemoveTipAction extends Action {

  private static Category CAT = Category.getInstance(RemoveTipAction.class.getName());

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
      Tip tip = user.getTip(id);
                    
      CAT.debug(tip);

      try {
    	  if (tip != null)
          db.delete(tip);
      } catch (Exception e) {
        CAT.error("delete(tip)", e);
        throw new ServletException(e);
      }

      Tip[] tips = user.getTips();
      if ((tips != null) && (tips.length > 0)) {
        for (int i = 1; i <= tips.length; i++) {
          try {
            db.delete(tips[i-1]);
          } catch (Exception e) {
            CAT.error("delete(tips[i-1]", e);
            throw new ServletException(e);  
          }
          if (tips[i-1].getId() != i)
            tips[i-1].setId(i);
      
          try {
            user.addTips(tips[i-1]);
          } catch (Exception e) {
            CAT.error("addTips(tips[i-i])", e);
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
      dtm.setColumnCount(7);

      tips = user.getTips();
      if (tips != null) {
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
