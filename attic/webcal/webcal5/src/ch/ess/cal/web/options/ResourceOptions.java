package ch.ess.cal.web.options;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import ch.ess.base.annotation.option.Option;
import ch.ess.base.service.TranslationService;
import ch.ess.base.web.options.AbstractOptions;
import ch.ess.cal.dao.ResourceDao;
import ch.ess.cal.model.Resource;


@Option(id = "resourceOptions")
public class ResourceOptions extends AbstractOptions {

  private ResourceDao resourceDao;
  private TranslationService translationService;

  public void setTranslationService(TranslationService translationService) {
    this.translationService = translationService;
  }

  public void setResourceDao(final ResourceDao resourceDao) {
    this.resourceDao = resourceDao;
  }

  @Override
  public void init(final HttpServletRequest request) {

    List<Resource> resourceList = resourceDao.findAll();

    Locale locale = getLocale(request);

    for (Resource resource : resourceList) {
      add(translationService.getText(resource, locale), resource.getId().toString());
    }

    sort();
  }

}
