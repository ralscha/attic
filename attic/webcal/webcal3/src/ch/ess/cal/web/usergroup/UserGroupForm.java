package ch.ess.cal.web.usergroup;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;
import org.apache.struts.action.ActionMapping;

import ch.ess.cal.web.AbstractForm;

/** 
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2005/05/09 07:46:08 $
 * 
 * @struts.form name="userGroupForm"
 */
public class UserGroupForm extends AbstractForm {

  private String groupName;
  private String[] permissionIds;

  @Override
  public void reset(final ActionMapping mapping, final HttpServletRequest request) {
    permissionIds = null;
  }

  /**   
   * @struts.validator type="required"
   * @struts.validator-arg position="0" key="userGroup.groupName"
   */
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
