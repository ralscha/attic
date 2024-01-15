package ch.ess.cal.web.user;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.struts.action.ActionForward;

import ch.ess.cal.db.User;
import ch.ess.cal.db.UserRole;
import ch.ess.common.Constants;
import ch.ess.common.web.HibernateAction;
import ch.ess.common.web.MapForm;
import ch.ess.common.web.WebContext;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.1 $ $Date: 2004/02/14 16:32:52 $ 
  * 
  * @struts.action path="/listUser" name="mapForm" scope="session" validate="false" roles="admin"
  * @struts.action-forward name="success" path=".user.list"
  */
public class UserListAction extends HibernateAction {

  private static DynaClass resultClass;

  static {
    resultClass =
      new BasicDynaClass(
        "ResultClassUser",
        null,
        new DynaProperty[] {
          new DynaProperty("id", Long.class),
          new DynaProperty("userName", String.class),
          new DynaProperty("name", String.class),
          new DynaProperty("firstName", String.class),
          new DynaProperty("role", String.class),
          new DynaProperty("canDelete", Boolean.class)
          });

  }

  public ActionForward doAction() throws Exception {

    WebContext ctx = WebContext.currentContext();

    MapForm searchForm = (MapForm)ctx.getForm();
    String searchText = (String)searchForm.getValue("searchText");
    String roleId = (String)searchForm.getValue("roleId");

    Iterator resultIt;

    
    resultIt = User.findWithSearchtext(searchText, roleId);

    List resultDynaList = new ArrayList();

    while (resultIt.hasNext()) {
      User user = (User)resultIt.next();
      DynaBean dynaBean = resultClass.newInstance();
      dynaBean.set("id", user.getId());
      dynaBean.set("userName", user.getUserName());
      dynaBean.set("name", user.getName());
      dynaBean.set("firstName", user.getFirstName());
      dynaBean.set("canDelete", new Boolean(user.canDelete()));

      String roleString = "";
      Set roles = user.getUserRoles();
      for (Iterator rit = roles.iterator(); rit.hasNext();) {
        UserRole r = (UserRole)rit.next();
        if (roleString.length() > 0) {
          roleString += ",";
        }
        roleString += r.getRoleName();
      }

      dynaBean.set("role", roleString);

      resultDynaList.add(dynaBean);
    }

    ctx.getSession().setAttribute(Constants.RESULT_ID, resultDynaList);

    if (resultDynaList.isEmpty()) {
      addOneMessageRequest(Constants.ACTION_MESSAGE_NO_RESULTS);
    }

    return findForward(Constants.FORWARD_SUCCESS);

  }

}
