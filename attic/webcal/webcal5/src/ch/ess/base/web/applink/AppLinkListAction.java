package ch.ess.base.web.applink;

import java.util.List;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;
import org.apache.commons.lang.StringUtils;

import ch.ess.base.annotation.struts.Role;
import ch.ess.base.dao.AppLinkDao;
import ch.ess.base.model.AppLink;
import ch.ess.base.web.AbstractListAction;
import ch.ess.base.web.MapForm;
import ch.ess.base.web.SimpleListDataModel;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.common.SortOrder;
import com.cc.framework.ui.control.ControlActionContext;
import com.cc.framework.ui.control.SimpleListControl;
import com.cc.framework.ui.model.ListDataModel;

@Role("$admin")
public class AppLinkListAction extends AbstractListAction {

  private AppLinkDao appLinkDao;

  public void setAppLinkDao(final AppLinkDao appLinkDao) {
    this.appLinkDao = appLinkDao;
  }

  @Override
  public ListDataModel getDataModel(final ActionContext ctx) throws Exception {

    SimpleListDataModel dataModel = new SimpleListDataModel();

    MapForm searchForm = (MapForm)ctx.form();

    List<AppLink> appLinks = null;
    if (searchForm != null) {
    	appLinks = appLinkDao.find(searchForm.getStringValue("linkName"));
    } else {
    	appLinks = appLinkDao.findAll();
    }

    for (AppLink appLink : appLinks) {

      DynaBean dynaBean = new LazyDynaBean("appLinkList");

      dynaBean.set("id", appLink.getId().toString());
      dynaBean.set("linkName", appLink.getLinkName());
      dynaBean.set("appLink", appLink.getAppLink());
      
      dataModel.add(dynaBean);

    }

    dataModel.sort("linkName", SortOrder.ASCENDING);
    
    return dataModel;
  }

  
  
  @Override
  protected void setListControlAttributes(ActionContext ctx, SimpleListControl listControl) {
    listControl.setSortInfo("groupName", SortOrder.ASCENDING);
  }

  @Override
  public void deleteObject(final ControlActionContext ctx, final String key) throws Exception {
    appLinkDao.delete(key);
  }
  
  @Override
  public String getTitle(String id, ActionContext ctx) {
    if (StringUtils.isNotBlank(id)) {
      AppLink appLink = appLinkDao.findById(id);
      if (appLink != null) {
        return appLink.getLinkName();
      }
    }
    return null;
  } 
}
