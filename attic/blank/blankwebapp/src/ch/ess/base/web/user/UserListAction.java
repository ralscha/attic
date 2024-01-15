package ch.ess.base.web.user;

import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;
import org.apache.struts.annotation.ActionScope;
import org.apache.struts.annotation.Forward;
import org.apache.struts.annotation.StrutsAction;

import ch.ess.base.model.User;
import ch.ess.base.model.UserUserGroup;
import ch.ess.base.persistence.UserDao;
import ch.ess.base.web.AbstractListAction;
import ch.ess.base.web.DynaListDataModel;
import ch.ess.base.web.MapForm;

/** 
 * @author sr
 * @version $Revision: 1.4 $ $Date: 2005/07/03 11:31:19 $ 
 * 
 * @spring.bean name="/listUser" lazy-init="true" autowire="byType"
 * @spring.property name="attribute" value="users"
 */

@StrutsAction(path = "/listUser", 
              form = MapForm.class, 
              input = "/userlist.jsp", 
              scope = ActionScope.SESSION, 
              roles = "$user", 
              forwards = {
                @Forward(name = "success", path = "/userlist.jsp"),
                @Forward(name = "edit", path = "/editUser.do?id={0}", redirect = true),
                @Forward(name = "create", path = "/editUser.do", redirect = true)
              })
public class UserListAction extends AbstractListAction {

  private UserDao userDao;

  public void setUserDao(final UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public ListDataModel getDataModel(final ActionContext ctx) throws Exception {

    MapForm searchForm = (MapForm)ctx.form();

    List<User> userList = null;
    if (searchForm != null) {
      String userGroupIdStr = searchForm.getStringValue("userGroupId");
      userList = userDao.findWithSearchText(searchForm.getStringValue("searchtext"), userGroupIdStr);
    } else {
      userList = userDao.list();
    }

    DynaListDataModel dataModel = new DynaListDataModel();

    for (User user : userList) {

      DynaBean dynaBean = new LazyDynaBean("userList");
      dynaBean.set("id", user.getId().toString());
      dynaBean.set("userName", user.getUserName());
      dynaBean.set("firstName", user.getFirstName());
      dynaBean.set("name", user.getName());

      String groupString = "";
      Set<UserUserGroup> userUserGroups = user.getUserUserGroups();
      for (UserUserGroup userUserGroup : userUserGroups) {
        if (groupString.length() > 0) {
          groupString += ",";
        }
        groupString += userUserGroup.getUserGroup().getGroupName();
      }

      dynaBean.set("userGroup", groupString);

      dataModel.add(dynaBean);
    }

    return dataModel;
  }

  @Override
  public boolean deleteObject(final ControlActionContext ctx, final String key) throws Exception {
    return userDao.deleteCond(key);
  }

}