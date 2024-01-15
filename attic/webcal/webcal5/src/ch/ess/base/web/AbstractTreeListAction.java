package ch.ess.base.web;

import org.apache.commons.logging.LogFactory;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FormActionContext;
import com.cc.framework.common.SortOrder;
import com.cc.framework.ui.control.ControlActionContext;
import com.cc.framework.ui.control.TreelistControl;
import com.cc.framework.ui.model.TreeGroupDataModel;

public abstract class AbstractTreeListAction extends AbstractListBaseAction {

  public void doExecute(final ActionContext ctx) throws Exception {
    try {
      if (ctx.request().getParameter("nolist") == null) {
        TreelistControl treeListControl = new TreelistControl();
        treeListControl.setDataModel(getDataModel(ctx));
        setTreeListControlAttributes(ctx, treeListControl);
        ctx.session().setAttribute(getListAttributeName(), treeListControl);
      }
      ctx.forwardToInput();
    } catch (Exception e) {
      LogFactory.getLog(getClass()).error("doExecute", e);
      throw e;
    }
  }

  @Override
  public void onRefresh(final ControlActionContext ctx) throws Exception {
    TreelistControl listControl = (TreelistControl)ctx.control();
    int tempCurrentPage = listControl.getCurrentPage();
    listControl.setDataModel(getDataModel(ctx));
    listControl.setCurrentPage(tempCurrentPage);
  }

  protected void setTreeListControlAttributes(final ActionContext ctx, TreelistControl listControl) {
    //default do nothing    
  }

  public void onSort(final ControlActionContext ctx, final String column, final SortOrder direction) throws Exception {

    DynaTreeDataModel dataModel = (DynaTreeDataModel)ctx.control().getDataModel();
    dataModel.sort(column, direction);

    ctx.control().execute(ctx, column, direction);
    ctx.forwardToInput();
  }

  public abstract TreeGroupDataModel getDataModel(final ActionContext ctx) throws Exception;

  @Override
  protected SimpleListDataModel getExportDataModel(final ControlActionContext ctx) {
    DynaTreeDataModel treeModel = (DynaTreeDataModel)ctx.control().getDataModel();
    SimpleListDataModel dataModel = new SimpleListDataModel();
    dataModel.setExportPropertiesList(treeModel.getExportPropertiesList());
    dataModel.setExportFileName(treeModel.getExportFileName());
    flatten(dataModel, treeModel);
    return dataModel;
  }

  private void flatten(SimpleListDataModel dataModel, DynaTreeDataModel treeModel) {
    dataModel.add(treeModel);
    for (int i = 0; i < treeModel.size(); i++) {
      flatten(dataModel, (DynaTreeDataModel)treeModel.getChild(i));
    }
  }

}
