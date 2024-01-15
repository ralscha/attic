package ch.ess.base.web.usergroup;

import java.util.List;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;
import org.apache.struts.annotation.ActionScope;
import org.apache.struts.annotation.Forward;
import org.apache.struts.annotation.StrutsAction;

import ch.ess.base.model.UserGroup;
import ch.ess.base.persistence.UserGroupDao;
import ch.ess.base.web.AbstractListAction;
import ch.ess.base.web.DynaListDataModel;
import ch.ess.base.web.MapForm;

/** 
 * @author sr
 * @version $Revision: 1.4 $ $Date: 2005/07/03 11:31:19 $ 
 * 
 * @spring.bean name="/listUserGroup" lazy-init="true" autowire="byType" 
 * @spring.property name="attribute" value="usergroups"
 */
@StrutsAction(path = "/listUserGroup", 
              form = MapForm.class, 
              input = "/usergrouplist.jsp", 
              scope = ActionScope.SESSION, 
              roles = "$usergroup", 
              forwards = {
                @Forward(name = "success", path = "/usergrouplist.jsp"),
                @Forward(name = "edit", path = "/editUserGroup.do?id={0}", redirect = true),
                @Forward(name = "create", path = "/editUserGroup.do", redirect = true)
              })
public class UserGroupListAction extends AbstractListAction {

  private UserGroupDao userGroupDao;

  public void setUserGroupDao(final UserGroupDao userGroupDao) {
    this.userGroupDao = userGroupDao;
  }

  @Override
  public ListDataModel getDataModel(final ActionContext ctx) throws Exception {

    DynaListDataModel dataModel = new DynaListDataModel();

    MapForm searchForm = (MapForm)ctx.form();

    List<UserGroup> userGroups = null;
    if (searchForm != null) {
      userGroups = userGroupDao.find(searchForm.getStringValue("userGroupName"));
    } else {
      userGroups = userGroupDao.list();
    }

    for (UserGroup userGroup : userGroups) {

      DynaBean dynaBean = new LazyDynaBean("userGroupList");

      dynaBean.set("id", userGroup.getId().toString());
      dynaBean.set("groupName", userGroup.getGroupName());

      dataModel.add(dynaBean);

    }

    return dataModel;
  }

  @Override
  public boolean deleteObject(final ControlActionContext ctx, final String key) throws Exception {
    return userGroupDao.deleteCond(key);
  }
}
