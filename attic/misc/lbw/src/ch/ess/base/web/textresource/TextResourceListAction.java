package ch.ess.base.web.textresource;

import java.util.List;
import java.util.Locale;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;

import ch.ess.base.annotation.struts.Role;
import ch.ess.base.dao.TextResourceDao;
import ch.ess.base.model.TextResource;
import ch.ess.base.web.AbstractListAction;
import ch.ess.base.web.SimpleListDataModel;
import ch.ess.base.xml.textresource.Resource;
import ch.ess.base.xml.textresource.TextResources;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.common.SortOrder;
import com.cc.framework.ui.control.ControlActionContext;
import com.cc.framework.ui.control.SimpleListControl;
import com.cc.framework.ui.model.ListDataModel;

@Role("$admin")
public class TextResourceListAction extends AbstractListAction {

  private TextResources textResources;
  private TextResourceDao textResourceDao;

  public void setTextResources(final TextResources textResources) {
    this.textResources = textResources;
  }

  public void setTextResourceDao(final TextResourceDao textResourceDao) {
    this.textResourceDao = textResourceDao;
  }

  @Override
  public ListDataModel getDataModel(final ActionContext ctx) throws Exception {
    Locale locale = getLocale(ctx.request());
    SimpleListDataModel dataModel = new SimpleListDataModel();

    List<TextResource> allResources = textResourceDao.findAll();

    for (TextResource textResource : allResources) {

      String name = textResource.getName();
      Resource resource = textResources.getResources().get(name);

      DynaBean dynaBean = new LazyDynaBean("textResourceList");
      dynaBean.set("id", textResource.getId().toString());
        dynaBean.set("name", resource.getDescription().get(locale));

      dataModel.add(dynaBean);
    }

    dataModel.sort("name", SortOrder.ASCENDING);

    return dataModel;
  }

  @Override
  protected void setListControlAttributes(SimpleListControl listControl) {
    listControl.setSortInfo("name", SortOrder.ASCENDING);
  }
  
  @Override
  public void deleteObject(final ControlActionContext ctx, final String key) throws Exception {
    //delete not possible
  }

}
