package ch.ess.cal.web.category;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.util.MessageResources;

import ch.ess.base.annotation.struts.Role;
import ch.ess.base.service.TranslationService;
import ch.ess.base.web.AbstractEditAction;
import ch.ess.cal.dao.CategoryDao;
import ch.ess.cal.model.Category;

import com.cc.framework.adapter.struts.ActionContext;

@Role("$admin")
public class CategoryEditAction extends AbstractEditAction<Category> {

  private TranslationService translationService;


  public void setTranslationService(TranslationService translationService) {
    this.translationService = translationService;
  }


  @Override
  protected void afterNewItem(ActionContext ctx, ActionForm form) {
    CategoryForm categoryForm = (CategoryForm)form;
    MessageResources messages = getResources(ctx.request());
    Locale locale = getLocale(ctx.request());

    categoryForm.setEntry(translationService.getEmptyNameEntry(messages, locale));
  }

  @Override
  public void formToModel(final ActionContext ctx, Category category) {


    CategoryForm categoryForm = (CategoryForm)ctx.form();

    Boolean oldValue = category.getDefaultCategory();

    category.setIcalKey(categoryForm.getIcalKey());
    category.setColour(categoryForm.getColour());
    category.setDefaultCategory(categoryForm.isDefaultCategory());
    
    if (StringUtils.isNotBlank(categoryForm.getSequence())) {
      category.setSequence(new Integer(categoryForm.getSequence()));
    } else {
      category.setSequence(null);
    }

    translationService.addTranslation(category, categoryForm.getEntry());

    Integer id = category.getId();
    CategoryDao categoryDao = (CategoryDao)getDao();
    if (categoryDao.getSize(id != null ? id.toString() : null) == 0) {
      category.setDefaultCategory(true);
      categoryForm.setDefaultCategory(true);
    } else {
      if (categoryForm.isDefaultCategory()) {
        categoryDao.updateTurnOffDefaultFlag(id != null ? id.toString() : null);
      } else {
        if ((oldValue != null) && oldValue) {
          //not allowed
          category.setDefaultCategory(true);
          categoryForm.setDefaultCategory(true);
        }
      }
    }


  }

  @Override
  public void modelToForm(final ActionContext ctx, final Category category) {
    CategoryForm categoryForm = (CategoryForm)ctx.form();

    categoryForm.setIcalKey(category.getIcalKey());
    categoryForm.setColour(category.getColour());
    if (category.getDefaultCategory() != null) {
      categoryForm.setDefaultCategory(category.getDefaultCategory());
    } else {
      categoryForm.setDefaultCategory(false);
    }

    if (category.getSequence() != null) {
      categoryForm.setSequence(category.getSequence().toString());
    } else {
      categoryForm.setSequence(null);
    }

    MessageResources messages = getResources(ctx.request());
    Locale locale = getLocale(ctx.request());

    categoryForm.setEntry(translationService.getNameEntry(messages, locale, category));

  }

  

}
