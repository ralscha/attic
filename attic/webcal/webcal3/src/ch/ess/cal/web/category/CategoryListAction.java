package ch.ess.cal.web.category;

import java.util.List;
import java.util.Locale;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;

import ch.ess.cal.model.Category;
import ch.ess.cal.persistence.CategoryDao;
import ch.ess.cal.service.impl.TranslationService;
import ch.ess.cal.web.AbstractListAction;
import ch.ess.cal.web.DynaListDataModel;
import ch.ess.cal.web.MapForm;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.common.SortOrder;
import com.cc.framework.ui.SelectMode;
import com.cc.framework.ui.control.ControlActionContext;
import com.cc.framework.ui.model.ListDataModel;

/** 
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2005/05/09 07:46:07 $ 
 * 
 * @struts.action path="/listCategory" roles="$category" name="mapForm" input="/categorylist.jsp" scope="session" validate="false"
 * @struts.action-forward name="success" path="/categorylist.jsp" 
 * @struts.action-forward name="edit" path="/editCategory.do?id={0}" redirect="true"
 * @struts.action-forward name="create" path="/editCategory.do" redirect="true"
 * 
 * @spring.bean name="/listCategory" lazy-init="true"
 * @spring.property name="attribute" value="categorys"
 */
public class CategoryListAction extends AbstractListAction {

  private CategoryDao categoryDao;
  private TranslationService translationService;

  /**    
   * @spring.property reflocal="categoryDao"
   */
  public void setCategoryDao(final CategoryDao categoryDao) {
    this.categoryDao = categoryDao;
  }

  /**    
   * @spring.property reflocal="translationService"
   */
  public void setTranslationService(TranslationService translationService) {
    this.translationService = translationService;
  }

  public void categorys_onCheck(ControlActionContext ctx, String key, SelectMode mode, boolean checked)
      throws Exception {

    //unchecked requests werden nicht verarbeitet
    //bei einem checked alle anderen auf unchecked gesetzt

    if (checked) {
      categoryDao.updateTurnOffUnknownFlag(key);
    }

    onRefresh(ctx);
  }

  @Override
  public ListDataModel getDataModel(final ActionContext ctx) throws Exception {

    DynaListDataModel dataModel = new DynaListDataModel();

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
      categorys = categoryDao.list();
    }

    for (Category category : categorys) {

      DynaBean dynaBean = new LazyDynaBean("categoryList");

      dynaBean.set("id", category.getId().toString());

      dynaBean.set("categoryName", translationService.getText(category, locale));
      dynaBean.set("colour", category.getColour());
      dynaBean.set("unknown", Boolean.valueOf(category.isUnknown()));

      dataModel.add(dynaBean);

    }

    dataModel.sort("categoryName", SortOrder.ASCENDING);

    return dataModel;
  }

  @Override
  public boolean deleteObject(final ControlActionContext ctx, final String key) throws Exception {
    return categoryDao.deleteCond(key);
  }
}
