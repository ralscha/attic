package ch.ess.cal.web;

import org.apache.commons.logging.LogFactory;

import ch.ess.cal.Constants;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FWAction;
import com.cc.framework.adapter.struts.FormActionContext;
import com.cc.framework.common.SortOrder;
import com.cc.framework.ui.control.ControlActionContext;
import com.cc.framework.ui.control.SimpleListControl;
import com.cc.framework.ui.model.ListDataModel;

/**
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2005/05/09 07:46:07 $
 */
public abstract class AbstractListAction extends FWAction {

  private String attribute;

  public void setAttribute(final String attribute) {
    this.attribute = attribute;
  }

  public void doExecute(final ActionContext ctx) throws Exception {

    try {
      SimpleListControl userList = new SimpleListControl();
      userList.setDataModel(getDataModel(ctx));
      ctx.session().setAttribute(attribute, userList);
      ctx.forwardByName(Constants.FORWARD_SUCCESS);
    } catch (Exception e) {
      LogFactory.getLog(getClass()).error("doExecute", e);
      throw e;
    }
  }

  public abstract ListDataModel getDataModel(final ActionContext ctx) throws Exception;

  public void onRefresh(final ControlActionContext ctx) throws Exception {
    SimpleListControl userList = (SimpleListControl)ctx.control();
    userList.setDataModel(getDataModel(ctx));
  }

  public void onEdit(final ControlActionContext ctx, final String key) {
    ctx.forwardByName(Constants.FORWARD_EDIT, key);
  }

  public void onDelete(final ControlActionContext ctx, final String key) throws Exception {
    if (deleteObject(ctx, key)) {
      ctx.addGlobalMessage("common.deleteOK");
    } else {
      ctx.addGlobalMessage("common.deleteNOK");
    }
    onRefresh(ctx);
  }

  public abstract boolean deleteObject(final ControlActionContext ctx, final String key) throws Exception;

  public void onSubmit(final FormActionContext ctx) throws Exception {
    doExecute(ctx);
  }

  public void search_onClick(final FormActionContext ctx) throws Exception {
    doExecute(ctx);
  }

  public void onCreate(final ControlActionContext ctx) {
    ctx.forwardByName(Constants.FORWARD_CREATE);
  }

  public void onSort(final ControlActionContext ctx, final String column, final SortOrder direction) {

    DynaListDataModel dataModel = (DynaListDataModel)ctx.control().getDataModel();
    dataModel.sort(column, direction);

    ctx.control().execute(ctx, column, direction);
    ctx.forwardByName(Constants.FORWARD_SUCCESS);
  }
}
