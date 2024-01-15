package ch.ess.cal.web.resource;

import java.util.Locale;

import org.apache.struts.util.MessageResources;

import ch.ess.cal.model.Resource;
import ch.ess.cal.model.ResourceGroup;
import ch.ess.cal.persistence.ResourceGroupDao;
import ch.ess.cal.service.impl.TranslationService;
import ch.ess.cal.web.AbstractEditAction;

import com.cc.framework.adapter.struts.ActionContext;

/** 
 * @author sr
 * @version $Revision: 1.2 $ $Date: 2005/04/04 11:31:12 $ 
 * 
 * @struts.action path="/editResource" name="resourceForm" input="/resourceedit.jsp" scope="session" validate="false" roles="$resource" 
 * @struts.action-forward name="back" path="/listResource.do" redirect="true"
 * 
 * @spring.bean name="/editResource" lazy-init="true"
 * @spring.property name="clazz" value="ch.ess.cal.model.Resource" 
 * @spring.property name="dao" reflocal="resourceDao"
 **/
public class ResourceEditAction extends AbstractEditAction {

  private ResourceGroupDao resourceGroupDao;
  private TranslationService translationService;

  /** 
   * @spring.property reflocal="resourceGroupDao"
   */
  public void setResourceGroup(final ResourceGroupDao resourceGroupDao) {
    this.resourceGroupDao = resourceGroupDao;
  }

  /**    
   * @spring.property reflocal="translationService"
   */
  public void setTranslationService(TranslationService translationService) {
    this.translationService = translationService;
  }

  @Override
  protected void newItem(final ActionContext ctx) {
    ResourceForm resourceFrom = (ResourceForm)ctx.form();

    clearForm(resourceFrom);

    MessageResources messages = getResources(ctx.request());
    Locale locale = getLocale(ctx.request());

    resourceFrom.setEntry(translationService.getEmptyNameEntry(messages, locale));

  }

  @Override
  public void formToModel(final ActionContext ctx, final Object model) {
    Resource resource = (Resource)model;
    ResourceForm resourceForm = (ResourceForm)ctx.form();

    resource.setResourceGroup((ResourceGroup)resourceGroupDao.get(resourceForm.getResourceGroupId()));

    translationService.addTranslation(resource, resourceForm.getEntry());
  }

  @Override
  public void modelToForm(final ActionContext ctx, final Object model) {
    Resource resource = (Resource)model;
    ResourceForm resourceForm = (ResourceForm)ctx.form();

    resourceForm.setResourceGroupId(resource.getResourceGroup().getId().toString());

    MessageResources messages = getResources(ctx.request());
    Locale locale = getLocale(ctx.request());

    resourceForm.setEntry(translationService.getNameEntry(messages, locale, resource));
  }

}