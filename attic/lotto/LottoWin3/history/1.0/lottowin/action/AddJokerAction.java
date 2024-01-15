

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
import ch.ess.taglib.misc.*;
import org.log4j.*;

public final class AddJokerAction extends LottowinBaseAction {


  public ActionForward perform(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request, HttpServletResponse response) 
                               throws IOException, ServletException {

     

    if (!CommandToken.isValid(request)) {
      CAT.debug("command token not valid");
      return (new ActionForward(mapping.getInput()));
    }

    JokerForm jokerForm = (JokerForm)form;
    CAT.debug(jokerForm);

    request.removeAttribute(mapping.getAttribute());
  
    // is an upate action
    boolean isUpdate = false;
    Integer updateId = (Integer)session.getAttribute("update");
    if (updateId != null) {
      CAT.debug("it is an update action");
      session.removeAttribute("update");
      isUpdate = true;
    }

    Integer page = (Integer)session.getAttribute("page");
    if (page != null) {
    
      if (CAT.isDebugEnabled())
        CAT.debug("page = " + page);
    
      session.removeAttribute("page");
    }
    
    Joker[] jokers = user.getJokers();

    int id = 0;
    if (isUpdate) {
      id = updateId.intValue();
    } else {
      if (jokers != null) {
        for (int i = 0; i < jokers.length; i++) {
          if (id < jokers[i].getId())
            id = jokers[i].getId();
        }
      }
      id++;
    }

    if (CAT.isDebugEnabled())
      CAT.debug("id = " + id);

    if (!isUpdate) {
      Joker newJoker = (Joker)db.create(Joker.class); 
      String j = jokerForm.getJoker();
      newJoker.setId(id);   
      newJoker.setJoker(j);
       
      try {
        user.addJokers(newJoker);
        CAT.debug(newJoker);
        db.update(user);
      } catch (Exception e) {
        CAT.error("update(user)", e);
        throw new ServletException(e);
      }
    } else {
      Joker updateJoker = user.getJoker(id);
      CAT.debug(updateJoker);

      updateJoker.setJoker(jokerForm.getJoker());
           
      try {
        db.update(updateJoker);
      } catch (Exception e) {
        CAT.error("update(updateJoker)", e);
        throw new ServletException(e);
      }
    }

    DefaultTableModel dtm = new DefaultTableModel();
    dtm.setColumnCount(2);

    jokers = user.getJokers();
    String[] strArray = new String[2];
    
    for (int i = 0; i < jokers.length; i++) {
      strArray[0] = String.valueOf(jokers[i].getJoker());
      strArray[1] = String.valueOf(jokers[i].getId());
      dtm.addRow(strArray);
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

    if (page == null)
      tableModel.setCurrentPage(tableModel.getTotalPages());
    else
      tableModel.setCurrentPage(page.intValue());

    // Forward control to the specified success URI
    return (new ActionForward(mapping.getInput()));

  }


}
