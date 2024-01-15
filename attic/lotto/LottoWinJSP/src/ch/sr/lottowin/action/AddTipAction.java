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

public final class AddTipAction extends BaseAction {

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


    TipForm tipForm = (TipForm) form;
    LOG.debug(tipForm);

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

    Tip[] tips = user.getTips();

    long id = 0;
    if (isUpdate) {
      id = updateId.intValue();
    } else {
      if (tips != null) {
        for (int i = 0; i < tips.length; i++) {
          if (id < tips[i].getId().longValue())
            id = tips[i].getId().longValue();
        }
      }
      id++;
    }

    if (LOG.isDebugEnabled())
      LOG.debug("id = " + id);

    Database db = getDb(request);
    if (!isUpdate) {
      Tip newTip = (Tip) db.create(Tip.class);
      int[] z = tipForm.getZahlen();
      newTip.setId(new Long(id));

      newTip.setZ1(new Long(z[0]));
      newTip.setZ2(new Long(z[1]));
      newTip.setZ3(new Long(z[2]));
      newTip.setZ4(new Long(z[3]));
      newTip.setZ5(new Long(z[4]));
      newTip.setZ6(new Long(z[5]));

      try {
        user.addTips(newTip);
        LOG.debug(newTip);
        db.update(user);
      } catch (Exception e) {
        LOG.error("update(user)", e);
        throw new ServletException(e);
      }
    } else {
      Tip updateTip = UserUtil.getTip(db, user, id);
      int[] z = tipForm.getZahlen();
      updateTip.setZ1(new Long(z[0]));
      updateTip.setZ2(new Long(z[1]));
      updateTip.setZ3(new Long(z[2]));
      updateTip.setZ4(new Long(z[3]));
      updateTip.setZ5(new Long(z[4]));
      updateTip.setZ6(new Long(z[5]));
      try {
        db.markUpdate(updateTip);
        db.update(user);
      } catch (Exception e) {
        LOG.error("update(updateTip)", e);
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
