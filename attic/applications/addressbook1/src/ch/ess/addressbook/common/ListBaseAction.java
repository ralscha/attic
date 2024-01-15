package ch.ess.addressbook.common;

import javax.servlet.http.*;
import javax.swing.table.*;

import org.apache.struts.action.*;

import ch.ess.tag.table.*;

/**
 * @version $Revision: 1.1 $ $Date: 2003/03/31 06:38:35 $
 * @author $Author: sr $
 */
public abstract class ListBaseAction extends Action {

  public static JSPTableModel createJSPTableModel(HttpSession session, TableModel model, String name, boolean keepCurrent) {
    JSPTableModel tableModel = (JSPTableModel) session.getAttribute(name);

    if (tableModel != null) {
      tableModel.setModel(new TableSorter(model), keepCurrent);
    } else {
      tableModel = new JSPTableModel(new TableSorter(model));
      session.setAttribute(name, tableModel);
    }

    return tableModel;
  }

  public static JSPTableModel createJSPTableModel(HttpSession session, TableModel model, String name) {
    return createJSPTableModel(session, model, name, false);
  }

  protected static JSPTableModel createJSPTableModel(HttpSession session, TableModel model) {
    return createJSPTableModel(session, model, "model", false);
  }

  public static void removeNulls(Object[] array) {
    for (int x = 0; x < array.length; x++) {

      if (array[x] == null) {
        array[x] = ch.ess.Constants.BLANK_STRING;
      }
    }
  }

  protected void addEmptyListErrorMessage(TableModel dtm, HttpServletRequest request, String errorKey) {
    if ((dtm != null) && (dtm.getRowCount() <= 0)) {
      ActionErrors errors = new ActionErrors();
      errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(errorKey));
      saveErrors(request, errors);
    }
  }

  protected void addEmptyListErrorMessage(JSPTableModel jtm, HttpServletRequest request, String errorKey) {
    addEmptyListErrorMessage(jtm.getModel(), request, errorKey);
  }

}