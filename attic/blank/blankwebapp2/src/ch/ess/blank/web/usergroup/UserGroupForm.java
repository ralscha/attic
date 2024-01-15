package ch.ess.blank.web.usergroup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;

import org.apache.struts.action.ActionMapping;

import ch.ess.blank.db.Permission;
import ch.ess.blank.db.UserGroup;
import ch.ess.common.db.HibernateSession;
import ch.ess.common.web.PersistentForm;

/** 
 * @struts.form name="userGroupForm"
 */
public class UserGroupForm extends PersistentForm {

  private Long[] permissionIds;

  public void reset(ActionMapping mapping, HttpServletRequest request) {
    permissionIds = null;
  }

  /**   
   * @struts.validator type="required"
   * @struts.validator-arg position="0" key="userGroup.groupName"
   */
  public void setGroupName(String groupName) {
    ((UserGroup) getPersistent()).setGroupName(getTrimmedString(groupName));
  }

  public String getGroupName() {
    return ((UserGroup) getPersistent()).getGroupName();
  }

  public Long[] getPermissionIds() {
    return permissionIds;
  }

  public void setPermissionIds(Long[] permissionId) {
    this.permissionIds = permissionId;
  }

  protected void fromForm() throws HibernateException {

    UserGroup userGroup = (UserGroup) getPersistent();

    userGroup.getPermissions().clear();

    if (permissionIds != null) {
      Session sess = HibernateSession.currentSession();

      for (int i = 0; i < permissionIds.length; i++) {
        if (permissionIds[i].longValue() > 0) {
          Permission permission = (Permission) sess.get(Permission.class, permissionIds[i]);
          if (permission != null) {
            userGroup.getPermissions().add(permission);
          }
        }
      }
    }

  }

  protected void toForm() throws HibernateException {

    UserGroup userGroup = (UserGroup) getPersistent();
    if (userGroup != null) {

      List permissionIdsList = new ArrayList();

      Set permissionsSet = userGroup.getPermissions();
      for (Iterator it = permissionsSet.iterator(); it.hasNext();) {
        Permission permission = (Permission) it.next();
        permissionIdsList.add(permission.getId());
      }

      setPermissionIds((Long[]) permissionIdsList.toArray(new Long[permissionIdsList.size()]));

    } else {
      permissionIds = null;
    }

  }

}