package ch.ess.cal.web.category;

import java.util.Locale;

import org.apache.struts.util.MessageResources;

import ch.ess.cal.model.Category;
import ch.ess.cal.persistence.CategoryDao;
import ch.ess.cal.service.impl.TranslationService;
import ch.ess.cal.web.AbstractEditAction;

import com.cc.framework.adapter.struts.ActionContext;

/** 
 * @author sr
 * @version $Revision: 1.2 $ $Date: 2005/05/15 11:05:33 $ 
 * 
 * @struts.action path="/editCategory" name="categoryForm" input="/categoryedit.jsp" scope="session" validate="false" roles="$category" 
 * @struts.action-forward name="back" path="/listCategory.do" redirect="true"
 * 
 * @spring.bean name="/editCategory" lazy-init="true"
 * @spring.property name="dao" reflocal="categoryDao"
 **/
public class CategoryEditAction extends AbstractEditAction<Category> {

  private TranslationService translationService;

  /**    
   * @spring.property reflocal="translationService"
   */
  public void setTranslationService(TranslationService translationService) {
    this.translationService = translationService;
  }

  @Override
  protected void newItem(final ActionContext ctx) {
    CategoryForm categoryForm = (CategoryForm)ctx.form();

    clearForm(categoryForm);

    MessageResources messages = getResources(ctx.request());
    Locale locale = getLocale(ctx.request());

    categoryForm.setEntry(translationService.getEmptyNameEntry(messages, locale));

  }

  @Override
  public Category formToModel(final ActionContext ctx, Category category) {

    if (category == null) {
      category = new Category();
    }

    CategoryForm categoryForm = (CategoryForm)ctx.form();

    Boolean oldValue = category.isUnknown();

    category.setIcalKey(categoryForm.getIcalKey());
    category.setColour(categoryForm.getColour());
    category.setUnknown(categoryForm.isUnknown());

    translationService.addTranslation(category, categoryForm.getEntry());

    Integer id = category.getId();
    CategoryDao categoryDao = (CategoryDao)getDao();
    if (categoryDao.getSize(id != null ? id.toString() : null) == 0) {
      category.setUnknown(true);
      categoryForm.setUnknown(true);
    } else {
      if (categoryForm.isUnknown()) {
        categoryDao.updateTurnOffUnknownFlag(id != null ? id.toString() : null);
      } else {
        if (oldValue) {
          //not allowed
          category.setUnknown(true);
          categoryForm.setUnknown(true);
        }
      }
    }

    return category;

  }

  @Override
  public void modelToForm(final ActionContext ctx, final Category category) {
    CategoryForm categoryForm = (CategoryForm)ctx.form();

    categoryForm.setIcalKey(category.getIcalKey());
    categoryForm.setColour(category.getColour());
    if (category.isUnknown() != null) {
      categoryForm.setUnknown(category.isUnknown());
    } else {
      categoryForm.setUnknown(false);
    }

    MessageResources messages = getResources(ctx.request());
    Locale locale = getLocale(ctx.request());

    categoryForm.setEntry(translationService.getNameEntry(messages, locale, category));

  }

}
