package ch.ess.base.web.usergroup;

import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.annotation.ActionScope;
import org.apache.struts.annotation.Forward;
import org.apache.struts.annotation.StrutsAction;

import ch.ess.base.model.Permission;
import ch.ess.base.model.UserGroup;
import ch.ess.base.model.UserGroupPermission;
import ch.ess.base.persistence.PermissionDao;
import ch.ess.base.web.AbstractEditAction;

/** 
 * @author sr
 * @version $Revision: 1.4 $ $Date: 2005/07/03 11:31:19 $ 
 * 
 * @spring.bean name="/editUserGroup" lazy-init="true" autowire="byType"  
 * @spring.property name="dao" reflocal="userGroupDao"
 **/
@StrutsAction(path = "/editUserGroup", 
              form = UserGroupForm.class, 
              input = "/usergroupedit.jsp", 
              scope = ActionScope.REQUEST, 
              roles = "$usergroup", 
              forwards = @Forward(name = "back", path = "/listUserGroup.do", redirect = true))
public class UserGroupEditAction extends AbstractEditAction<UserGroup> {

  private PermissionDao permissionDao;

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
