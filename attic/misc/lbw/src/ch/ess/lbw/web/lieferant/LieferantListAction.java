package ch.ess.lbw.web.lieferant;

import java.util.List;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;
import org.apache.commons.lang.StringUtils;

import ch.ess.base.annotation.struts.Role;
import ch.ess.base.web.AbstractListAction;
import ch.ess.base.web.MapForm;
import ch.ess.base.web.SimpleListDataModel;
import ch.ess.lbw.dao.LieferantDao;
import ch.ess.lbw.model.Lieferant;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.ui.control.ControlActionContext;
import com.cc.framework.ui.model.ListDataModel;

@Role("$stammdaten")
public class LieferantListAction extends AbstractListAction {

  private LieferantDao lieferantDao;

  public void setLieferantDao(final LieferantDao lieferantDao) {
    this.lieferantDao = lieferantDao;
  }

  @Override
  public ListDataModel getDataModel(final ActionContext ctx) throws Exception {

    SimpleListDataModel dataModel = new SimpleListDataModel();

    MapForm searchForm = (MapForm)ctx.form();

    List<Lieferant> lieferants = null;
    if (searchForm != null) {        
      lieferants = lieferantDao.find(searchForm.getStringValue("name"), searchForm.getStringValue("nr"), null);
    } else {
      lieferants = lieferantDao.findAll();
    }

    for (Lieferant lieferant : lieferants) {

      DynaBean dynaBean = new LazyDynaBean("lieferantList");

      dynaBean.set("id", lieferant.getId().toString());
      
      dynaBean.set("nr", lieferant.getNr());        
      dynaBean.set("kurz", lieferant.getKurz());        
      dynaBean.set("name", lieferant.getName());        
      dynaBean.set("strasse", lieferant.getStrasse());        
      dynaBean.set("plz", lieferant.getPlz());        
      dynaBean.set("ort", lieferant.getOrt());        
      
      dynaBean.set("deletable", lieferantDao.canDelete(lieferant));
      
      dataModel.add(dynaBean);

    }


    return dataModel;
  }

  @Override
  public void deleteObject(final ControlActionContext ctx, final String key) throws Exception {
    lieferantDao.delete(key);
  }
  
  @Override
  public String getTitle(String id, ActionContext ctx) {
    if (StringUtils.isNotBlank(id)) {
      Lieferant lieferant = lieferantDao.findById(id);
      if (lieferant != null) {
        return lieferant.getNr();
      }
    }
    return null;
  }
}
