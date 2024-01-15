package ch.ess.cal.web.options;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import ch.ess.base.annotation.option.Option;
import ch.ess.base.service.TranslationService;
import ch.ess.base.web.options.AbstractOptions;
import ch.ess.cal.dao.CategoryDao;
import ch.ess.cal.model.Category;

@Option(id = "categoryOptions")
public class CategoryOptions extends AbstractOptions {

  private CategoryDao categoryDao;
  private TranslationService translationService;

  public void setTranslationService(TranslationService translationService) {
    this.translationService = translationService;
  }

  public void setCategoryDao(final CategoryDao categoryDao) {
    this.categoryDao = categoryDao;
  }

  @Override
  public void init(final HttpServletRequest request) {

    List<Category> categoryList = categoryDao.findAll();

    for (Category category : categoryList) {
      add(translationService.getText(category, getLocale(request)), category.getId().toString());
    }

    sort();
  }

}
