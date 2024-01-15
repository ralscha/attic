package ch.ess.base.web.usergroup;

import java.util.Set;

import org.apache.commons.lang.StringUtils;

import ch.ess.base.annotation.struts.Role;
import ch.ess.base.dao.PermissionDao;
import ch.ess.base.dao.UserDao;
import ch.ess.base.model.Permission;
import ch.ess.base.model.UserGroup;
import ch.ess.base.model.UserGroupPermission;
import ch.ess.base.web.AbstractEditAction;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FormActionContext;

@Role("$admin")
public class UserGroupEditAction extends AbstractEditAction<UserGroup> {

  private PermissionDao permissionDao;
  private UserDao userDao;

  public void setPermissionDao(final PermissionDao permissionDao) {
    this.permissionDao = permissionDao;
  }

  public void setUserDao(UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public void formToModel(final ActionContext ctx, UserGroup userGroup) {

    UserGroupForm userGroupForm = (UserGroupForm)ctx.form();
    userGroup.setGroupName(userGroupForm.getGroupName());

    boolean hasAdminRight = false;
    boolean foundAdminRight = false;

    for (UserGroupPermission permission : userGroup.getUserGroupPermissions()) {
      if ("admin".equals(permission.getPermission().getPermission())) {
        hasAdminRight = true;
        break;
      }
    }

    //Permissions
    userGroup.getUserGroupPermissions().clear();

    String[] permissionIds = userGroupForm.getPermissionIds();
    if (permissionIds != null) {

      for (String id : permissionIds) {
        if (StringUtils.isNotBlank(id)) {
          Permission permission = permissionDao.findById(id);
          if (permission != null) {

            if ("admin".equals(permission.getPermission())) {
              foundAdminRight = true;
            }

            UserGroupPermission userGroupPermission = new UserGroupPermission();
            userGroupPermission.setUserGroup(userGroup);
            userGroupPermission.setPermission(permission);

            userGroup.getUserGroupPermissions().add(userGroupPermission);
          }
        }
      }
    }

    if (!foundAdminRight && hasAdminRight) {
      if (userDao.getAdminUserCount(null, userGroup) == 0) {
        UserGroupPermission userGroupPermission = new UserGroupPermission();
        userGroupPermission.setUserGroup(userGroup);
        userGroupPermission.setPermission(permissionDao.findByPermission("admin"));

        userGroup.getUserGroupPermissions().add(userGroupPermission);
        modelToForm(ctx, userGroup);
      }
    }

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
  protected void afterSave(final FormActionContext ctx, UserGroup userGroup) {
    ctx.session().removeAttribute("usergroupOption");
  }

}
