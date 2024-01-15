package ch.ess.cal.web.app;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import ch.ess.cal.model.Resource;
import ch.ess.cal.persistence.ResourceDao;
import ch.ess.cal.service.impl.TranslationService;
import ch.ess.cal.web.AbstractOptions;

/** 
 * @author sr
 * @version $Revision: 1.2 $ $Date: 2005/04/04 11:31:11 $ 
 * 
 * @spring.bean name="resourceOptions" singleton="false" 
 */
public class ResourceOptions extends AbstractOptions {

  private ResourceDao resourceDao;
  private TranslationService translationService;

  /**    
   * @spring.property reflocal="translationService"
   */
  public void setTranslationService(TranslationService translationService) {
    this.translationService = translationService;
  }

  /**
   * @spring.property reflocal="resourceDao"
   */
  public void setResourceDao(final ResourceDao resourceDao) {
    this.resourceDao = resourceDao;
  }

  @Override
  public void init(final HttpServletRequest request) {

    List<Resource> resourceList = resourceDao.list();

    Locale locale = getLocale(request);

    for (Resource resource : resourceList) {
      add(translationService.getText(resource, locale), resource.getId().toString());
    }

    sort();
  }

}
