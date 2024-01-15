package ch.ess.cal.web.app;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import ch.ess.cal.model.ResourceGroup;
import ch.ess.cal.persistence.ResourceGroupDao;
import ch.ess.cal.service.impl.TranslationService;
import ch.ess.cal.web.AbstractOptions;

/** 
 * @author sr
 * @version $Revision: 1.2 $ $Date: 2005/04/04 11:31:10 $ 
 * 
 * @spring.bean name="resourceGroupOptions" singleton="false" 
 */
public class ResourceGroupOptions extends AbstractOptions {

  private ResourceGroupDao resourceGroupDao;
  private TranslationService translationService;

  /**
   * @spring.property reflocal="resourceGroupDao"
   */
  public void setResourceGroupDao(final ResourceGroupDao resourceGroupDao) {
    this.resourceGroupDao = resourceGroupDao;
  }

  /**    
   * @spring.property reflocal="translationService"
   */
  public void setTranslationService(TranslationService translationService) {
    this.translationService = translationService;
  }

  @Override
  public void init(final HttpServletRequest request) {

    List<ResourceGroup> resourceGroupList = resourceGroupDao.list();

    Locale locale = getLocale(request);

    for (ResourceGroup resourceGroup : resourceGroupList) {
      add(translationService.getText(resourceGroup, locale), resourceGroup.getId().toString());
    }

    sort();
  }

}
