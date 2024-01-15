package ch.sr.lottowin.action;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.swing.table.*;

import org.apache.struts.action.*;

import ch.ess.tag.table.*;
import ch.sr.lottowin.db.*;

import com.objectmatter.bsf.*;

public final class RemoveTipAction extends BaseAction {

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

    String idstr = request.getParameter("id");
    if (idstr == null) {
      LOG.warn("id not in request");
      return (new ActionForward(mapping.getInput()));
    }

    int id = 0;
    try {
      id = Integer.parseInt(idstr);
    } catch (NumberFormatException nfe) {
      LOG.warn("parseInt id", nfe);
      return (new ActionForward(mapping.getInput()));
    }

    if (LOG.isDebugEnabled())
      LOG.debug("id = " + id);

    Database db = getDb(request);
    Tip tip = UserUtil.getTip(db, user, id);

    LOG.debug(tip);
    
    if (tip != null) {
      user.removeTips(tip);
      db.update(user);
    }

    Tip[] tips = user.getTips();
    if ((tips != null) && (tips.length > 0)) {
      for (int i = 0; i < tips.length; i++) {
        Tip newTip = (Tip)db.create(Tip.class);
        newTip.setId(new Long(i+1));
        newTip.setUser(tips[i].getUser());
        newTip.setZ1(tips[i].getZ1());
        newTip.setZ2(tips[i].getZ2());
        newTip.setZ3(tips[i].getZ3());
        newTip.setZ4(tips[i].getZ4());
        newTip.setZ5(tips[i].getZ5());                                
        newTip.setZ6(tips[i].getZ6());
        
        user.removeTips(tips[i]);
        db.update(user);
        user.addTips(newTip);
        db.update(user);
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

    JSPTableModel tableModel = (JSPTableModel) session.getAttribute("model");
    if (tableModel == null) {
      LOG.debug("create new JSPTableModel");
      tableModel = new JSPTableModel(dtm);
      session.setAttribute("model", tableModel);
    } else {
      LOG.debug("reuse JSPTableModel");
      tableModel.setModel(dtm);
    }

    // Forward control to the specified success URI
    return (new ActionForward(mapping.getInput()));

  }

}
