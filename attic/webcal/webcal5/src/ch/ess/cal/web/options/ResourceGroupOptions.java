package ch.ess.cal.web.options;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import ch.ess.base.annotation.option.Option;
import ch.ess.base.service.TranslationService;
import ch.ess.base.web.options.AbstractOptions;
import ch.ess.cal.dao.ResourceGroupDao;
import ch.ess.cal.model.ResourceGroup;


@Option(id = "resourceGroupOptions")
public class ResourceGroupOptions extends AbstractOptions {

  private ResourceGroupDao resourceGroupDao;
  private TranslationService translationService;

  public void setResourceGroupDao(final ResourceGroupDao resourceGroupDao) {
    this.resourceGroupDao = resourceGroupDao;
  }

  public void setTranslationService(TranslationService translationService) {
    this.translationService = translationService;
  }

  @Override
  public void init(final HttpServletRequest request) {

    List<ResourceGroup> resourceGroupList = resourceGroupDao.findAll();

    Locale locale = getLocale(request);

    for (ResourceGroup resourceGroup : resourceGroupList) {
      add(translationService.getText(resourceGroup, locale), resourceGroup.getId().toString());
    }

    sort();
  }

}
