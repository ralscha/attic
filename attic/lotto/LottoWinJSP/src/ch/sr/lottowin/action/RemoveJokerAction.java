package ch.sr.lottowin.action;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.swing.table.*;

import org.apache.struts.action.*;

import ch.ess.tag.table.*;
import ch.sr.lottowin.db.*;

import com.objectmatter.bsf.*;

public final class RemoveJokerAction extends BaseAction {

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
    Joker joker = UserUtil.getJoker(db, user, id);

    LOG.debug(joker);

    if (joker != null) {
      user.removeJokers(joker);
      db.update(user);
    }

    Joker[] jokers = user.getJokers();
    if ((jokers != null) && (jokers.length > 0)) {
      for (int i = 0; i < jokers.length; i++) {

        Joker newJoker = (Joker) db.create(Joker.class);
        newJoker.setId(new Long(i + 1));
        newJoker.setJoker(jokers[i].getJoker());
        newJoker.setUserid(jokers[i].getUserid());

        user.removeJokers(jokers[i]);
        db.update(user);
        user.addJokers(newJoker);
        db.update(user);
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
