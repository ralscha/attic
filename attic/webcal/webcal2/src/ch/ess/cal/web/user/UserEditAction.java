package ch.ess.cal.web.user;

import java.util.Map;

import org.apache.struts.action.ActionForward;

import ch.ess.cal.db.User;
import ch.ess.cal.web.EmailUtil;
import ch.ess.cal.web.MultiSelect;
import ch.ess.cal.web.WebUtils;
import ch.ess.common.db.Persistent;
import ch.ess.common.web.PersistentAction;
import ch.ess.common.web.WebContext;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.1 $ $Date: 2004/02/14 16:32:52 $ 
  * 
  * @struts.action path="/newUser" name="userForm" input=".user.list" scope="session" validate="false" roles="admin" parameter="add"
  * @struts.action path="/editUser" name="userForm" input=".user.list" scope="session" validate="false" roles="admin" parameter="edit"
  * @struts.action path="/storeUser" name="userForm" input=".user.edit" scope="session" validate="true" parameter="store" roles="admin"
  * @struts.action path="/deleteUser" parameter="delete" validate="false" roles="admin"
  *
  * @struts.action-forward name="edit" path=".user.edit"
  * @struts.action-forward name="list" path="/listUser.do" redirect="true"
  * @struts.action-forward name="delete" path="/deleteUser.do" 
  * @struts.action-forward name="reload" path="/editUser.do" 
  */
public class UserEditAction extends PersistentAction {

  protected int deletePersistent(Long id) throws Exception {
    return User.delete(id);
  }

  protected Persistent loadPersistent(Long id) throws Exception {
    return User.load(id);
  }

  protected Persistent newPersistent() throws Exception {
    return new User();
  }

  protected ActionForward store() throws Exception {

    WebContext ctx = WebContext.currentContext();
    
    UserForm uf = (UserForm)ctx.getForm();
    
    Map params = WebUtils.getChangeRequestParameterMap(ctx.getRequest());
        
    if (params.keySet().contains("page")) {      
      uf.setPage(Integer.parseInt((String)params.get("page")));      
      return ctx.getMapping().getInputForward();
    } else if (EmailUtil.handleEmailRequest(params, uf.getEmail(), uf.getEmails())) {
      if (params.keySet().contains("addemail")) {
        uf.setEmail(null);
      }      
      return ctx.getMapping().getInputForward();  
    } else if (MultiSelect.handleRequest("depo", params, uf.getAvailableDepartments(), uf.getAvailableDepartmentsId(), uf.getDepartments(), uf.getDepartmentsId())) {
      uf.setDepartmentsId(new String[0]);
      uf.setAvailableDepartmentsId(new String[0]);      
      return ctx.getMapping().getInputForward();
    } else if (MultiSelect.handleRequest("adepo", params, uf.getAvailableAccessDepartments(), uf.getAvailableAccessDepartmentsId(), uf.getAccessDepartments(), uf.getAccessDepartmentsId())) {
      uf.setAccessDepartmentsId(new String[0]);
      uf.setAvailableAccessDepartmentsId(new String[0]);      
      return ctx.getMapping().getInputForward();      
    } else if (MultiSelect.handleRequest("resgo", params, uf.getAvailableResGroups(), uf.getAvailableResGroupsId(), uf.getResGroups(), uf.getResGroupsId())) {
      uf.setResGroupsId(new String[0]);
      uf.setAvailableResGroupsId(new String[0]);      
      return ctx.getMapping().getInputForward();      
             
    } else {
      return super.store();
    }
    
    
  }
  
}