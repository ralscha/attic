package ch.ess.cal.web.resourcegroup;

import java.util.Map;

import org.apache.struts.action.ActionForward;

import ch.ess.cal.db.ResourceGroup;
import ch.ess.cal.web.MultiSelect;
import ch.ess.cal.web.WebUtils;
import ch.ess.common.db.Persistent;
import ch.ess.common.web.PersistentAction;
import ch.ess.common.web.WebContext;

/** 
  * @author  Ralph Schaer
  * @version 1.0, 30.09.2003
  *
  * @struts.action path="/newResourceGroup" name="resourceGroupForm" input=".resourcegroup.list" scope="session" validate="false" parameter="add" roles="admin" 
  * @struts.action path="/editResourceGroup" name="resourceGroupForm" input=".resourcegroup.list" scope="session" validate="false" parameter="edit" roles="admin"
  * @struts.action path="/storeResourceGroup" name="resourceGroupForm" input=".resourcegroup.edit" scope="session" validate="true" parameter="store" roles="admin"
  * @struts.action path="/deleteResourceGroup" parameter="delete" validate="false" roles="admin"
  *
  * @struts.action-forward name="edit" path=".resourcegroup.edit"
  * @struts.action-forward name="list" path="/listResourceGroup.do" redirect="true"
  * @struts.action-forward name="delete" path="/deleteResourceGroup.do" 
  * @struts.action-forward name="reload" path="/editResourceGroup.do" 
  */
public class ResourceGroupEditAction extends PersistentAction {

  protected int deletePersistent(Long id) throws Exception {
    return ResourceGroup.delete(id);
  }

  protected Persistent loadPersistent(Long id) throws Exception {
    return ResourceGroup.load(id);
  }

  protected Persistent newPersistent() throws Exception {
    return new ResourceGroup();
  }

  protected ActionForward store() throws Exception {
    
    WebContext ctx = WebContext.currentContext();

    ResourceGroupForm rgf = (ResourceGroupForm)ctx.getForm();
    Map params = WebUtils.getChangeRequestParameterMap(ctx.getRequest());

    if (params.keySet().contains("page")) {      
      rgf.setPage(Integer.parseInt((String)params.get("page")));      
      return ctx.getMapping().getInputForward();
    } else if (MultiSelect
      .handleRequest(
        "reso",
        params,
        rgf.getAvailableResources(),
        rgf.getAvailableResourcesId(),
        rgf.getResources(),
        rgf.getResourcesId())) {
      rgf.setResourcesId(new String[0]);
      rgf.setAvailableResourcesId(new String[0]);
      return ctx.getMapping().getInputForward();
    } else if (
      MultiSelect.handleRequest(
        "depo",
        params,
        rgf.getAvailableDepartments(),
        rgf.getAvailableDepartmentsId(),
        rgf.getDepartments(),
        rgf.getDepartmentsId())) {
      rgf.setDepartmentsId(new String[0]);
      rgf.setAvailableDepartmentsId(new String[0]);
      return ctx.getMapping().getInputForward();
    } else if (
      MultiSelect.handleRequest(
        "usero",
        params,
        rgf.getAvailableUsers(),
        rgf.getAvailableUsersId(),
        rgf.getUsers(),
        rgf.getUsersId())) {
      rgf.setUsersId(new String[0]);
      rgf.setAvailableUsersId(new String[0]);
      return ctx.getMapping().getInputForward();
    } else {
      return super.store();
    }
  }

}