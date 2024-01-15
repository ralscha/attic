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

public final class CheckTipAction extends LottowinBaseAction {


  public ActionForward perform(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request, HttpServletResponse response) 
                               throws IOException, ServletException {


    CheckForm checkForm = (CheckForm)form;

    String from = checkForm.getFrom();
    String to = checkForm.getTo();
    if (from.compareTo(to) > 0) {
      from = checkForm.getTo();
      to = checkForm.getFrom();
      checkForm.setTo(to);
      checkForm.setFrom(from);
    }

    CAT.debug(checkForm);

    int tono = Integer.parseInt(to);
    int fromno = Integer.parseInt(from);

    Tip[] tips = user.getTips();
    if ((tips != null) && (tips.length > 0)) {
      DefaultTableModel dtm = new DefaultTableModel();
      dtm.setColumnCount(6);
      String[] strArray = new String[6];
  
      for (int i = fromno; i <= tono; i++) {
        Draw draw = checkForm.getDraw(i);
        int[] win = new int[5];
    
        for (int j = 0; j < tips.length; j++) {      
          switch (draw.getWin(tips[j].getZArray())) {
        	  case 6 : win[4]++; break;
        	  case 51: win[3]++; break;
        	  case 5 : win[2]++; break;
        	  case 4 : win[1]++; break;
        	  case 3 : win[0]++; break;
          }      
        }


        if (checkForm.getOnlyWin()) {

          boolean hasWin = false;
          for (int n = 0; n < win.length; n++) {
            if (win[n] > 0) {
              hasWin = true;
              break;
            }
          }
          if (hasWin) {
            strArray[0] = draw.getNumber() + "/" + draw.getDrawyear();
            strArray[1] = (win[4] != 0) ? String.valueOf(win[4]) : "";
            strArray[2] = (win[3] != 0) ? String.valueOf(win[3]) : "";
            strArray[3] = (win[2] != 0) ? String.valueOf(win[2]) : "";
            strArray[4] = (win[1] != 0) ? String.valueOf(win[1]) : "";
            strArray[5] = (win[0] != 0) ? String.valueOf(win[0]) : "";
            dtm.addRow(strArray);
          }
        } else {

          strArray[0] = draw.getNumber() + "/" + draw.getDrawyear();
          strArray[1] = (win[4] != 0) ? String.valueOf(win[4]) : "";
          strArray[2] = (win[3] != 0) ? String.valueOf(win[3]) : "";
          strArray[3] = (win[2] != 0) ? String.valueOf(win[2]) : "";
          strArray[4] = (win[1] != 0) ? String.valueOf(win[1]) : "";
          strArray[5] = (win[0] != 0) ? String.valueOf(win[0]) : "";
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
    }

    // Forward control to the specified success URI
    return (new ActionForward(mapping.getInput()));


  }


}
