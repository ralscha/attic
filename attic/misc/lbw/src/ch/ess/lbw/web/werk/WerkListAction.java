package ch.ess.lbw.web.werk;

import java.util.List;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;
import org.apache.commons.lang.StringUtils;

import ch.ess.base.annotation.struts.Role;
import ch.ess.base.web.AbstractListAction;
import ch.ess.base.web.MapForm;
import ch.ess.base.web.SimpleListDataModel;
import ch.ess.lbw.dao.WerkDao;
import ch.ess.lbw.model.Werk;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.ui.control.ControlActionContext;
import com.cc.framework.ui.model.ListDataModel;

@Role("$stammdaten")
public class WerkListAction extends AbstractListAction {

  private WerkDao werkDao;

  public void setWerkDao(final WerkDao werkDao) {
    this.werkDao = werkDao;
  }

  @Override
  public ListDataModel getDataModel(final ActionContext ctx) throws Exception {

    SimpleListDataModel dataModel = new SimpleListDataModel();

    MapForm searchForm = (MapForm)ctx.form();

    List<Werk> werks = null;
    if (searchForm != null) {        
      werks = werkDao.find(searchForm.getStringValue("name"));
    } else {
      werks = werkDao.findAll();
    }

    for (Werk werk : werks) {

      DynaBean dynaBean = new LazyDynaBean("werkList");

      dynaBean.set("id", werk.getId().toString());
      
      dynaBean.set("name", werk.getName());        
      
      dynaBean.set("deletable", werkDao.canDelete(werk));
      
      dataModel.add(dynaBean);

    }


    return dataModel;
  }

  @Override
  public void deleteObject(final ControlActionContext ctx, final String key) throws Exception {
    werkDao.delete(key);
  }
  
  @Override
  public String getTitle(String id, ActionContext ctx) {
    if (StringUtils.isNotBlank(id)) {
      Werk werk = werkDao.findById(id);
      if (werk != null) {
        return werk.getName();
      }
    }
    return null;
  }
}
