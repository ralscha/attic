package ch.ess.cal.web.department;

import java.util.Map;

import org.apache.struts.action.ActionForward;

import ch.ess.cal.db.Department;
import ch.ess.cal.web.EmailUtil;
import ch.ess.cal.web.MultiSelect;
import ch.ess.cal.web.WebUtils;
import ch.ess.common.db.Persistent;
import ch.ess.common.web.PersistentAction;
import ch.ess.common.web.WebContext;

/** 
  * @author  Ralph Schaer
  * @version 1.0, 29.09.2003 
  *
  * @struts.action path="/newDepartment" name="departmentForm" input=".department.list" scope="session" validate="false" parameter="add" roles="admin" 
  * @struts.action path="/editDepartment" name="departmentForm" input=".department.list" scope="session" validate="false" parameter="edit" roles="admin"
  * @struts.action path="/storeDepartment" name="departmentForm" input=".department.edit" scope="session" validate="true" parameter="store" roles="admin"
  * @struts.action path="/deleteDepartment" parameter="delete" validate="false" roles="admin"
  *
  * @struts.action-forward name="edit" path=".department.edit"
  * @struts.action-forward name="list" path="/listDepartment.do" redirect="true"
  * @struts.action-forward name="delete" path="/deleteDepartment.do" 
  * @struts.action-forward name="reload" path="/editDepartment.do" 
  */
public class DepartmentEditAction extends PersistentAction {

  protected int deletePersistent(Long id) throws Exception {
    return Department.delete(id);
  }

  protected Persistent loadPersistent(Long id) throws Exception {
    return Department.load(id);
  }

  protected Persistent newPersistent() throws Exception {
    return new Department();
  }

  protected ActionForward store() throws Exception {

    WebContext ctx = WebContext.currentContext();
    
    DepartmentForm df = (DepartmentForm)ctx.getForm();

    Map params = WebUtils.getChangeRequestParameterMap(ctx.getRequest());

    if (params.keySet().contains("page")) {      
      df.setPage(Integer.parseInt((String)params.get("page")));      
      return ctx.getMapping().getInputForward();
    } else if (EmailUtil.handleEmailRequest(params, df.getEmail(), df.getEmails())) {
      if (params.keySet().contains("addemail")) {
        df.setEmail(null);
      }
      return ctx.getMapping().getInputForward();
    } else if (MultiSelect.handleRequest("usero", params, df.getAvailableUsers(), df.getAvailableUsersId(), df.getUsers(), df.getUsersId())) {
      df.setUsersId(new String[0]);
      df.setAvailableUsersId(new String[0]);      
      return ctx.getMapping().getInputForward();
    } else if (MultiSelect.handleRequest("ausero", params, df.getAvailableAccessUsers(), df.getAvailableAccessUsersId(), df.getAccessUsers(), df.getAccessUsersId())) {
      df.setAccessUsersId(new String[0]);
      df.setAvailableAccessUsersId(new String[0]);      
      return ctx.getMapping().getInputForward();   
    } else if (MultiSelect.handleRequest("resgo", params, df.getAvailableResGroups(), df.getAvailableResGroupsId(), df.getResGroups(), df.getResGroupsId())) {
      df.setResGroupsId(new String[0]);
      df.setAvailableResGroupsId(new String[0]);      
      return ctx.getMapping().getInputForward();           
    } else {
      return super.store();
    }

  }
}