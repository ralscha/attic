package ch.ess.base.web.usergroup;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;
import org.apache.struts.action.ActionMapping;

import ch.ess.base.annotation.struts.ValidatorField;
import ch.ess.base.web.AbstractForm;

public class UserGroupForm extends AbstractForm {

  private String groupName;
  private String[] permissionIds;

  @Override
  public void reset(final ActionMapping mapping, final HttpServletRequest request) {
    permissionIds = null;
  }

  @ValidatorField(key = "usergroup.groupName", required = true)
  public void setGroupName(final String groupName) {
    this.groupName = groupName;
  }

  public String getGroupName() {
    return groupName;
  }

  public String[] getPermissionIds() {
    return (String[])ArrayUtils.clone(permissionIds);
  }

  public void setPermissionIds(final String[] permissionId) {
    this.permissionIds = (String[])ArrayUtils.clone(permissionId);
  }

}