package ch.ess.cal.web.usergroup;

import java.util.List;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;

import ch.ess.cal.model.UserGroup;
import ch.ess.cal.persistence.UserGroupDao;
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
 * @struts.action path="/listUserGroup" roles="$usergroup" name="mapForm" input="/usergrouplist.jsp" scope="session" validate="false"
 * @struts.action-forward name="success" path="/usergrouplist.jsp" 
 * @struts.action-forward name="edit" path="/editUserGroup.do?id={0}" redirect="true"
 * @struts.action-forward name="create" path="/editUserGroup.do" redirect="true"
 * 
 * @spring.bean name="/listUserGroup" lazy-init="true" 
 * @spring.property name="attribute" value="usergroups"
 */
public class UserGroupListAction extends AbstractListAction {

  private UserGroupDao userGroupDao;

  /**    
   * @spring.property reflocal="userGroupDao"
   */
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
