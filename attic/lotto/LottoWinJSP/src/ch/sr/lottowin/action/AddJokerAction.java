package ch.sr.lottowin.action;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.swing.table.*;

import org.apache.struts.action.*;

import ch.ess.tag.table.*;
import ch.sr.lottowin.db.*;
import ch.sr.lottowin.form.*;

import com.objectmatter.bsf.*;

public final class AddJokerAction extends BaseAction {

  public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
               throws IOException, ServletException {

    HttpSession session = request.getSession();
    Database db = getDb(request);

    User user = (User) session.getAttribute(ch.sr.lottowin.Constants.USER_KEY);
    if (user == null) {
      LOG.warn("user object not in session");
      return (mapping.findForward("logon"));
    }

    JokerForm jokerForm = (JokerForm) form;
    LOG.debug(jokerForm);

    request.removeAttribute(mapping.getAttribute());

    // is an upate action
    boolean isUpdate = false;
    Integer updateId = (Integer) session.getAttribute("update");
    if (updateId != null) {
      LOG.debug("it is an update action");
      session.removeAttribute("update");
      isUpdate = true;
    }

    Integer page = (Integer) session.getAttribute("page");
    if (page != null) {

      if (LOG.isDebugEnabled())
        LOG.debug("page = " + page);

      session.removeAttribute("page");
    }

    Joker[] jokers = user.getJokers();

    long id = 0;
    if (isUpdate) {
      id = updateId.intValue();
    } else {
      if (jokers != null) {
        for (int i = 0; i < jokers.length; i++) {
          if (id < jokers[i].getId().longValue())
            id = jokers[i].getId().longValue();
        }
      }
      id++;
    }

    if (LOG.isDebugEnabled())
      LOG.debug("id = " + id);

    if (!isUpdate) {
      Joker newJoker = (Joker) db.create(Joker.class);
      String j = jokerForm.getJoker();
      newJoker.setId(new Long(id));
      newJoker.setJoker(j);

      try {
        user.addJokers(newJoker);
        LOG.debug(newJoker);
        db.update(user);
      } catch (Exception e) {
        LOG.error("update(user)", e);
        throw new ServletException(e);
      }
    } else {
      Joker updateJoker = UserUtil.getJoker(db, user, id);
      LOG.debug(updateJoker);

      if (updateJoker != null) {

        updateJoker.setJoker(jokerForm.getJoker());
        db.markUpdate(updateJoker);

        try {
          db.update(user);
        } catch (Exception e) {
          LOG.error("update(updateJoker)", e);
          throw new ServletException(e);
        }
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

    JSPTableModel tableModel = (JSPTableModel) session.getAttribute("model");
    if (tableModel == null) {
      LOG.debug("create new JSPTableModel");
      tableModel = new JSPTableModel(dtm);
      session.setAttribute("model", tableModel);
    } else {
      LOG.debug("reuse JSPTableModel");
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
