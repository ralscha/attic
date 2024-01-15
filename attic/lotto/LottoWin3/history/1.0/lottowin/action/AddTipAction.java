

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

public final class AddTipAction extends LottowinBaseAction {


  public ActionForward perform(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request, HttpServletResponse response) 
                               throws IOException, ServletException {


    ActionErrors errors = new ActionErrors();

    if (!CommandToken.isValid(request)) {
      CAT.debug("command token not valid");
      return (new ActionForward(mapping.getInput()));
    }

    TipForm tipForm = (TipForm)form;
    CAT.debug(tipForm);

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
      
    Tip[] tips = user.getTips();

    int id = 0;
    if (isUpdate) {
      id = updateId.intValue();
    } else {
      if (tips != null) {
        for (int i = 0; i < tips.length; i++) {
          if (id < tips[i].getId())
            id = tips[i].getId();
        }
      }
      id++;
    }

    if (CAT.isDebugEnabled())
      CAT.debug("id = " + id);      

    if (!isUpdate) {
      Tip newTip = (Tip)db.create(Tip.class); 
      int[] z = tipForm.getZahlen();
      newTip.setId(id);
   
      newTip.setZ1(z[0]);
      newTip.setZ2(z[1]);
      newTip.setZ3(z[2]);
      newTip.setZ4(z[3]);
      newTip.setZ5(z[4]);
      newTip.setZ6(z[5]);

      try {
        user.addTips(newTip);
        CAT.debug(newTip);
        db.update(user);
      } catch (Exception e) {
        CAT.error("update(user)", e);
        throw new ServletException(e);
      } 
    } else {
      Tip updateTip = user.getTip(id);
      int[] z = tipForm.getZahlen();
      updateTip.setZ1(z[0]);
      updateTip.setZ2(z[1]);
      updateTip.setZ3(z[2]);
      updateTip.setZ4(z[3]);
      updateTip.setZ5(z[4]);
      updateTip.setZ6(z[5]);

      try {
        db.update(updateTip);
      } catch (Exception e) {
        CAT.error("update(updateTip)", e);
        throw new ServletException(e);
      } 

    }

    DefaultTableModel dtm = new DefaultTableModel();
    dtm.setColumnCount(7);

    tips = user.getTips();
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
