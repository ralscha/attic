package ch.ess.addressbook.web.user;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.hibernate.Hibernate;

import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ch.ess.addressbook.db.User;
import ch.ess.addressbook.db.UserRole;
import ch.ess.common.Constants;
import ch.ess.common.web.HibernateAction;
import ch.ess.common.web.MapForm;

/** 
  * @author  Ralph Schaer
  * @version 1.0, 13.07.2003 
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
          });

  }

  public ActionForward doAction(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response)
    throws Exception {

    MapForm searchForm = (MapForm)form;
    String searchText = (String)searchForm.getValue("searchText");

    Iterator resultIt;

    if (!GenericValidator.isBlankOrNull(searchText)) {         
      resultIt = User.findWithSearchtext(searchText);
    } else {
      resultIt = User.findAll();
    }

    List resultDynaList = new ArrayList();

    while (resultIt.hasNext()) {
      User user = (User)resultIt.next();
      DynaBean dynaBean = resultClass.newInstance();
      dynaBean.set("id", user.getId());
      dynaBean.set("userName", user.getUserName());
      dynaBean.set("name", user.getName());
      dynaBean.set("firstName", user.getFirstName());

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

    request.getSession().setAttribute(Constants.RESULT_ID, resultDynaList);
    Hibernate.close(resultIt);

    if (resultDynaList.isEmpty()) {
      addOneMessage(request, Constants.ACTION_MESSAGE_NO_RESULTS);
    }

    return mapping.findForward(Constants.FORWARD_SUCCESS);

  }

}
