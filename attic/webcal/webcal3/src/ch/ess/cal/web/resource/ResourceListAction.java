package ch.ess.cal.web.resource;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;

import ch.ess.cal.model.Resource;
import ch.ess.cal.persistence.ResourceDao;
import ch.ess.cal.service.impl.TranslationService;
import ch.ess.cal.web.AbstractListAction;
import ch.ess.cal.web.DynaListDataModel;
import ch.ess.cal.web.MapForm;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.common.SortOrder;
import com.cc.framework.ui.control.ControlActionContext;
import com.cc.framework.ui.model.ListDataModel;

/** 
 * @author sr
 * @version $Revision: 1.3 $ $Date: 2005/04/04 11:31:12 $ 
 * 
 * @struts.action path="/listResource" roles="$resource" name="mapForm" input="/resourcelist.jsp" scope="session" validate="false"
 * @struts.action-forward name="success" path="/resourcelist.jsp" 
 * @struts.action-forward name="edit" path="/editResource.do?id={0}" redirect="true"
 * @struts.action-forward name="create" path="/editResource.do" redirect="true"
 * 
 * @spring.bean name="/listResource" lazy-init="true"
 * @spring.property name="attribute" value="resources"
 */
public class ResourceListAction extends AbstractListAction {

  private ResourceDao resourceDao;
  private TranslationService translationService;

  /**    
   * @spring.property reflocal="resourceDao"
   */
  public void setResourceDao(final ResourceDao resourceDao) {
    this.resourceDao = resourceDao;
  }

  /**    
   * @spring.property reflocal="translationService"
   */
  public void setTranslationService(TranslationService translationService) {
    this.translationService = translationService;
  }

  @Override
  public ListDataModel getDataModel(final ActionContext ctx) throws Exception {

    DynaListDataModel dataModel = new DynaListDataModel();

    MapForm searchForm = (MapForm)ctx.form();
    Locale locale = getLocale(ctx.request());

    String resourceName = null;
    if (searchForm != null) {
      resourceName = searchForm.getStringValue("resourceName");
    }

    List resources = null;
    if (resourceName != null) {
      resources = resourceDao.find(locale, resourceName);
    } else {
      resources = resourceDao.list();
    }

    for (Iterator iter = resources.iterator(); iter.hasNext();) {
      Resource resource = (Resource)iter.next();

      DynaBean dynaBean = new LazyDynaBean("resourceList");

      dynaBean.set("id", resource.getId().toString());

      dynaBean.set("resourceName", translationService.getText(resource, locale));
      dynaBean.set("resourceGroupName", translationService.getText(resource.getResourceGroup(), locale));

      dataModel.add(dynaBean);

    }

    dataModel.sort("resourceName", SortOrder.ASCENDING);

    return dataModel;
  }

  @Override
  public boolean deleteObject(final ControlActionContext ctx, final String key) throws Exception {
    return resourceDao.deleteCond(key);
  }
}
