package ch.ess.cal.web.user;

import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;

import ch.ess.cal.model.User;
import ch.ess.cal.model.UserUserGroup;
import ch.ess.cal.persistence.UserDao;
import ch.ess.cal.web.AbstractListAction;
import ch.ess.cal.web.DynaListDataModel;
import ch.ess.cal.web.MapForm;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.ui.control.ControlActionContext;
import com.cc.framework.ui.model.ListDataModel;

/** 
 * @author sr
 * @version $Revision: 1.2 $ $Date: 2005/05/15 11:05:33 $ 
 * 
 * @struts.action path="/listUser" roles="$user" name="mapForm" input="/userlist.jsp" scope="session" validate="false"
 * @struts.action-forward name="success" path="/userlist.jsp"
 * @struts.action-forward name="edit" path="/editUser.do?id={0}" redirect="true"
 * @struts.action-forward name="create" path="/editUser.do" redirect="true"
 * 
 * @spring.bean name="/listUser" lazy-init="true"
 * @spring.property name="attribute" value="users"
 */
public class UserListAction extends AbstractListAction {

  private UserDao userDao;

  /** 
   * @spring.property reflocal="userDao"
   */
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
      dynaBean.set("name", user.getName());
      dynaBean.set("firstName", user.getFirstName());

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
