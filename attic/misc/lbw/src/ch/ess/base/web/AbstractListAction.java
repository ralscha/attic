package ch.ess.base.web;

import org.apache.commons.logging.LogFactory;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.ui.control.ControlActionContext;
import com.cc.framework.ui.control.SimpleListControl;
import com.cc.framework.ui.model.ListDataModel;

public abstract class AbstractListAction extends AbstractListBaseAction {

  public void doExecute(final ActionContext ctx) throws Exception {

    try {
      if (ctx.request().getParameter("nolist") == null){
        SimpleListControl listControl = new SimpleListControl();
        listControl.setDataModel(getDataModel(ctx));
        setListControlAttributes(listControl);
        ctx.session().setAttribute(getListAttributeName(), listControl);
      }
      ctx.forwardToInput();
    } catch (Exception e) {
      LogFactory.getLog(getClass()).error("doExecute", e);
      throw e;
    }
  }

  protected void setListControlAttributes(SimpleListControl listControl) {
    //default do nothing    
  }

  public abstract ListDataModel getDataModel(final ActionContext ctx) throws Exception;

  @Override
  public void onRefresh(final ControlActionContext ctx) throws Exception {
    SimpleListControl listControl = (SimpleListControl)ctx.control();
    listControl.setDataModel(getDataModel(ctx));
  }

  @Override
  protected SimpleListDataModel getExportDataModel(final ControlActionContext ctx) {
    SimpleListDataModel dataModel = (SimpleListDataModel)ctx.control().getDataModel();
    return dataModel;
  }
}
