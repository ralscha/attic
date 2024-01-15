package ch.ess.base.web.user;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import ch.ess.base.annotation.struts.Role;
import ch.ess.base.dao.UserDao;
import ch.ess.base.model.User;
import ch.ess.base.model.UserUserGroup;
import ch.ess.base.web.AbstractListAction;
import ch.ess.base.web.CheckableLazyDynaBean;
import ch.ess.base.web.MapForm;
import ch.ess.base.web.SimpleListDataModel;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.common.SortOrder;
import com.cc.framework.ui.SelectMode;
import com.cc.framework.ui.control.ControlActionContext;
import com.cc.framework.ui.control.SimpleListControl;
import com.cc.framework.ui.model.ListDataModel;

@Role("$admin")
public class UserListAction extends AbstractListAction {

  private UserDao userDao;

  public void setUserDao(final UserDao userDao) {
    this.userDao = userDao;
  }

  public void listControl_onCheck(ControlActionContext ctx, String key, SelectMode mode, boolean checked) throws Exception {
    User user = userDao.findById(key);
    if (!checked) {
      user.setEnabled(checked);
      userDao.save(user);
    } else {
      user.setEnabled(checked);
      userDao.save(user);    
    }
    onRefresh(ctx);
  }

  @Override
  public ListDataModel getDataModel(final ActionContext ctx) throws Exception {

    MapForm searchForm = (MapForm)ctx.form();

    List<User> userList = null;
    if (searchForm != null) {
      String userGroupIdStr = searchForm.getStringValue("userGroupId");
      userList = userDao.findWithSearchText(searchForm.getStringValue("searchtext"), userGroupIdStr);
    } else {
      userList = userDao.findAll();
    }

    SimpleListDataModel dataModel = new SimpleListDataModel();

    for (User user : userList) {

      CheckableLazyDynaBean dynaBean = new CheckableLazyDynaBean("userList");
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
      dynaBean.set("deletable", userDao.canDelete(user));

      if (user.isEnabled()) {
        dynaBean.setCheckState(1);
      } else {
        dynaBean.setCheckState(0);
      }
      
      dataModel.add(dynaBean);
    }

    
    dataModel.setExportFileName("users");
    dataModel.addExportProperty("login.userName", "userName");
    dataModel.addExportProperty("user.firstName", "firstName");
    dataModel.addExportProperty("user.name", "name");
    dataModel.addExportProperty("usergroup.userGroups", "userGroup");
    
    dataModel.sort("userName", SortOrder.ASCENDING);
    
    return dataModel;
  }

  @Override
  protected void setListControlAttributes(ActionContext ctx, SimpleListControl listControl) {
    listControl.setSortInfo("userName", SortOrder.ASCENDING);
  }
  
  @Override
  public void deleteObject(final ControlActionContext ctx, final String key) throws Exception {
    userDao.delete(key);
  }

  @Override
  public String getTitle(String id, ActionContext ctx) {
    if (StringUtils.isNotBlank(id)) {
      User user = userDao.findById(id);
      if (user != null) {
        return user.getUserName();
      }
    }
    return null;
  } 
  
}
