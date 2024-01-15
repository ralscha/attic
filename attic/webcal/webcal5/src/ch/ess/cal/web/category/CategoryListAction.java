package ch.ess.cal.web.category;

import java.util.List;
import java.util.Locale;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;
import org.apache.commons.lang.StringUtils;

import ch.ess.base.annotation.struts.Role;
import ch.ess.base.service.TranslationService;
import ch.ess.base.web.AbstractListAction;
import ch.ess.base.web.MapForm;
import ch.ess.base.web.SimpleListDataModel;
import ch.ess.cal.dao.CategoryDao;
import ch.ess.cal.model.Category;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.common.SortOrder;
import com.cc.framework.ui.SelectMode;
import com.cc.framework.ui.control.ControlActionContext;
import com.cc.framework.ui.model.ListDataModel;

@Role("$admin")
public class CategoryListAction extends AbstractListAction {

  private CategoryDao categoryDao;
  private TranslationService translationService;


  public void setCategoryDao(final CategoryDao categoryDao) {
    this.categoryDao = categoryDao;
  }

  public void setTranslationService(TranslationService translationService) {
    this.translationService = translationService;
  }

  public void listControl_onCheck(ControlActionContext ctx, String key, SelectMode mode, boolean checked)
      throws Exception {

    //unchecked requests werden nicht verarbeitet
    //bei einem checked alle anderen auf unchecked gesetzt

    if (checked) {
      categoryDao.updateTurnOffDefaultFlag(key);
    }

    onRefresh(ctx);
  }

  @Override
  public ListDataModel getDataModel(final ActionContext ctx) throws Exception {

    SimpleListDataModel dataModel = new SimpleListDataModel();

    MapForm searchForm = (MapForm)ctx.form();
    Locale locale = getLocale(ctx.request());

    String categoryName = null;
    if (searchForm != null) {
      categoryName = searchForm.getStringValue("categoryName");
    }

    List<Category> categorys = null;
    if (categoryName != null) {
      categorys = categoryDao.find(locale, categoryName);
    } else {
      categorys = categoryDao.findAll();
    }

    for (Category category : categorys) {

      DynaBean dynaBean = new LazyDynaBean("categoryList");

      dynaBean.set("id", category.getId().toString());

      dynaBean.set("categoryName", translationService.getText(category, locale));
      dynaBean.set("colour", category.getColour());
      dynaBean.set("defaultCategory", Boolean.valueOf(category.getDefaultCategory()));
      if (category.getSequence() != null) {
        dynaBean.set("sequence", category.getSequence().toString());
      } else {
        dynaBean.set("sequence", "");
      }
      dynaBean.set("deletable", categoryDao.canDelete(category));

      dataModel.add(dynaBean);

    }

    dataModel.sort("categoryName", SortOrder.ASCENDING);

    return dataModel;
  }

  @Override
  public void deleteObject(final ControlActionContext ctx, final String key) throws Exception {
    categoryDao.delete(key);
  }
  
  @Override
  public String getTitle(String id, ActionContext ctx) {
    if (StringUtils.isNotBlank(id)) {
      Category category = categoryDao.findById(id);
      if (category != null) {
        Locale locale = getLocale(ctx.request());
        return translationService.getText(category, locale);
      }
    }
    return null;
  } 
}
