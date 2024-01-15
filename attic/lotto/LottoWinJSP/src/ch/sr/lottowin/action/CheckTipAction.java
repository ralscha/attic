package ch.sr.lottowin.action;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.swing.table.*;

import org.apache.struts.action.*;

import ch.ess.tag.table.*;
import ch.sr.lottowin.db.*;
import ch.sr.lottowin.form.*;

public final class CheckTipAction extends BaseAction {

  public ActionForward execute(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response)
    throws IOException, ServletException {

    HttpSession session = request.getSession();

    User user = (User) session.getAttribute(ch.sr.lottowin.Constants.USER_KEY);
    if (user == null) {
      LOG.warn("user object not in session");
      return (mapping.findForward("logon"));
    }

    CheckForm checkForm = (CheckForm) form;

    String from = checkForm.getFrom();
    String to = checkForm.getTo();

    int tono = Integer.parseInt(to);
    int fromno = Integer.parseInt(from);        
        
        
    if (fromno > tono) {
      from = checkForm.getTo();
      to = checkForm.getFrom();
      checkForm.setTo(to);
      checkForm.setFrom(from);
    }

    LOG.debug(checkForm);
    tono = Integer.parseInt(to);
    fromno = Integer.parseInt(from); 


    Tip[] tips = user.getTips();
    if ((tips != null) && (tips.length > 0)) {
      DefaultTableModel dtm = new DefaultTableModel();
      dtm.setColumnCount(6);
      String[] strArray = new String[6];

      for (int i = fromno; i <= tono; i++) {
        Draw draw = checkForm.getDraw(i);
        int[] win = new int[5];

        for (int j = 0; j < tips.length; j++) {
          switch (DrawUtil.getWin(draw, tips[j].getZArray())) {

            case 6 :
              win[4]++;
              break;
            case 51 :
              win[3]++;
              break;
            case 5 :
              win[2]++;
              break;
            case 4 :
              win[1]++;
              break;
            case 3 :
              win[0]++;
              break;
          }
        }

        if (checkForm.getOnlyWin()) {
          LOG.debug("show only win");

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
          LOG.debug("show all");

          strArray[0] = draw.getNumber() + "/" + draw.getDrawyear();
          strArray[1] = (win[4] != 0) ? String.valueOf(win[4]) : "";
          strArray[2] = (win[3] != 0) ? String.valueOf(win[3]) : "";
          strArray[3] = (win[2] != 0) ? String.valueOf(win[2]) : "";
          strArray[4] = (win[1] != 0) ? String.valueOf(win[1]) : "";
          strArray[5] = (win[0] != 0) ? String.valueOf(win[0]) : "";
          dtm.addRow(strArray);
        }

      }

      JSPTableModel tableModel = (JSPTableModel) session.getAttribute("model");
      if (tableModel == null) {
        LOG.debug("create new JSPTableModel");
        tableModel = new JSPTableModel(dtm);
        session.setAttribute("model", tableModel);
      } else {
        LOG.debug("reuse JSPTableModel");
        tableModel.setModel(dtm);
      }
    }

    // Forward control to the specified success URI
    return (new ActionForward(mapping.getInput()));

  }

}


