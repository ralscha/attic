package ch.ess.base.web.usergroup;

import java.util.List;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;
import org.apache.commons.lang.StringUtils;

import ch.ess.base.annotation.struts.Role;
import ch.ess.base.dao.UserGroupDao;
import ch.ess.base.model.UserGroup;
import ch.ess.base.web.AbstractListAction;
import ch.ess.base.web.MapForm;
import ch.ess.base.web.SimpleListDataModel;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.common.SortOrder;
import com.cc.framework.ui.control.ControlActionContext;
import com.cc.framework.ui.control.SimpleListControl;
import com.cc.framework.ui.model.ListDataModel;

@Role("$admin")
public class UserGroupListAction extends AbstractListAction {

  private UserGroupDao userGroupDao;

  public void setUserGroupDao(final UserGroupDao userGroupDao) {
    this.userGroupDao = userGroupDao;
  }

  @Override
  public ListDataModel getDataModel(final ActionContext ctx) throws Exception {

    SimpleListDataModel dataModel = new SimpleListDataModel();

    MapForm searchForm = (MapForm)ctx.form();

    List<UserGroup> userGroups = null;
    if (searchForm != null) {
      userGroups = userGroupDao.find(searchForm.getStringValue("userGroupName"));
    } else {
      userGroups = userGroupDao.findAll();
    }

    for (UserGroup userGroup : userGroups) {

      DynaBean dynaBean = new LazyDynaBean("userGroupList");

      dynaBean.set("id", userGroup.getId().toString());
      dynaBean.set("groupName", userGroup.getGroupName());

      dynaBean.set("deletable", userGroupDao.canDelete(userGroup));
      
      dataModel.add(dynaBean);

    }

    dataModel.sort("groupName", SortOrder.ASCENDING);
    
    return dataModel;
  }

  
  
  @Override
  protected void setListControlAttributes(ActionContext ctx, SimpleListControl listControl) {
    listControl.setSortInfo("groupName", SortOrder.ASCENDING);
  }

  @Override
  public void deleteObject(final ControlActionContext ctx, final String key) throws Exception {
    userGroupDao.delete(key);
  }
  
  @Override
  public String getTitle(String id, ActionContext ctx) {
    if (StringUtils.isNotBlank(id)) {
      UserGroup userGroup = userGroupDao.findById(id);
      if (userGroup != null) {
        return userGroup.getGroupName();
      }
    }
    return null;
  } 
}
