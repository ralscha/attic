package ch.ess.cal.web.app;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import ch.ess.cal.model.Category;
import ch.ess.cal.persistence.CategoryDao;
import ch.ess.cal.service.impl.TranslationService;
import ch.ess.cal.web.AbstractOptions;

/** 
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2005/05/09 07:46:06 $ 
 * 
 * @spring.bean name="categoryOptions" singleton="false" 
 */
public class CategoryOptions extends AbstractOptions {

  private CategoryDao categoryDao;
  private TranslationService translationService;

  /**    
   * @spring.property reflocal="translationService"
   */
  public void setTranslationService(TranslationService translationService) {
    this.translationService = translationService;
  }

  /**
   * @spring.property reflocal="categoryDao"
   */
  public void setCategoryDao(final CategoryDao categoryDao) {
    this.categoryDao = categoryDao;
  }

  @Override
  public void init(final HttpServletRequest request) {

    List<Category> categoryList = categoryDao.list();

    for (Category category : categoryList) {
      add(translationService.getText(category, getLocale(request)), category.getId().toString());
    }

    sort();
  }

}
