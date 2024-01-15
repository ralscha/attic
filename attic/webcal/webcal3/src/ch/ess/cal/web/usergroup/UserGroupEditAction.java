package ch.ess.cal.web.usergroup;

import java.util.Set;

import org.apache.commons.lang.StringUtils;

import ch.ess.cal.model.Permission;
import ch.ess.cal.model.UserGroup;
import ch.ess.cal.model.UserGroupPermission;
import ch.ess.cal.persistence.PermissionDao;
import ch.ess.cal.web.AbstractEditAction;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FormActionContext;

/** 
 * @author sr
 * @version $Revision: 1.2 $ $Date: 2005/05/15 11:05:33 $ 
 * 
 * @struts.action path="/editUserGroup" name="userGroupForm" input="/usergroupedit.jsp" scope="request" validate="false" roles="$usergroup" 
 * @struts.action-forward name="back" path="/listUserGroup.do" redirect="true"
 * 
 * @spring.bean name="/editUserGroup" lazy-init="true" 
 * @spring.property name="dao" reflocal="userGroupDao"
 **/
public class UserGroupEditAction extends AbstractEditAction<UserGroup> {

  private PermissionDao permissionDao;

  /** 
   * @spring.property reflocal="permissionDao"
   */
  public void setPermissionDao(final PermissionDao permissionDao) {
    this.permissionDao = permissionDao;
  }

  @Override
  public UserGroup formToModel(final ActionContext ctx, UserGroup userGroup) {
    
    if (userGroup == null) {
      userGroup = new UserGroup();
    }
    
    UserGroupForm userGroupForm = (UserGroupForm)ctx.form();

    userGroup.setGroupName(userGroupForm.getGroupName());

    //Permissions
    userGroup.getUserGroupPermissions().clear();

    String[] permissionIds = userGroupForm.getPermissionIds();
    if (permissionIds != null) {

      for (String id : permissionIds) {
        if (StringUtils.isNotBlank(id)) {
          Permission permission = permissionDao.get(id);
          if (permission != null) {

            UserGroupPermission userGroupPermission = new UserGroupPermission();
            userGroupPermission.setUserGroup(userGroup);
            userGroupPermission.setPermission(permission);

            userGroup.getUserGroupPermissions().add(userGroupPermission);
          }
        }
      }
    }

    return userGroup;
  }

  @Override
  public void modelToForm(final ActionContext ctx, final UserGroup userGroup) {
    
    UserGroupForm userGroupForm = (UserGroupForm)ctx.form();

    userGroupForm.setGroupName(userGroup.getGroupName());

    //Permissions
    Set<UserGroupPermission> permissions = userGroup.getUserGroupPermissions();
    String[] permissionIds = null;

    if (!permissions.isEmpty()) {
      permissionIds = new String[permissions.size()];

      int ix = 0;
      for (UserGroupPermission userGroupPermission : permissions) {
        permissionIds[ix++] = userGroupPermission.getPermission().getId().toString();
      }
    } else {
      permissionIds = null;
    }
    userGroupForm.setPermissionIds(permissionIds);
  }

  @Override
  protected void afterSave(final FormActionContext ctx) {
    ctx.session().removeAttribute("usergroupOption");
  }

}
