package ch.ess.cal.web.resourcegroup;

import java.util.List;
import java.util.Locale;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;

import ch.ess.cal.model.ResourceGroup;
import ch.ess.cal.persistence.ResourceGroupDao;
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
 * @version $Revision: 1.4 $ $Date: 2005/04/04 11:31:12 $ 
 * 
 * @struts.action path="/listResourceGroup" roles="$resourcegroup" name="mapForm" input="/resourcegrouplist.jsp" scope="session" validate="false"
 * @struts.action-forward name="success" path="/resourcegrouplist.jsp" 
 * @struts.action-forward name="edit" path="/editResourceGroup.do?id={0}" redirect="true"
 * @struts.action-forward name="create" path="/editResourceGroup.do" redirect="true"
 * 
 * @spring.bean name="/listResourceGroup" lazy-init="true"
 * @spring.property name="attribute" value="resourcegroups"
 */
public class ResourceGroupListAction extends AbstractListAction {

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
  public ListDataModel getDataModel(final ActionContext ctx) throws Exception {

    DynaListDataModel dataModel = new DynaListDataModel();

    MapForm searchForm = (MapForm)ctx.form();

    Locale locale = getLocale(ctx.request());

    String resourceGroupName = null;
    if (searchForm != null) {
      resourceGroupName = searchForm.getStringValue("resourceGroupName");
    }

    List<ResourceGroup> resourceGroups = null;
    if (resourceGroupName != null) {
      resourceGroups = resourceGroupDao.find(locale, resourceGroupName);
    } else {
      resourceGroups = resourceGroupDao.list();
    }

    for (ResourceGroup resourceGroup : resourceGroups) {

      DynaBean dynaBean = new LazyDynaBean("resourceGroupList");

      dynaBean.set("id", resourceGroup.getId().toString());
      dynaBean.set("resourceGroupName", translationService.getText(resourceGroup, locale));

      dataModel.add(dynaBean);

    }

    dataModel.sort("resourceGroupName", SortOrder.ASCENDING);

    return dataModel;
  }

  @Override
  public boolean deleteObject(final ControlActionContext ctx, final String key) throws Exception {
    return resourceGroupDao.deleteCond(key);
  }
}
