package ch.ess.blank.web.usergroup;

import ch.ess.blank.db.UserGroup;
import ch.ess.common.db.Persistent;
import ch.ess.common.web.PersistentAction;

/** 
 * @struts.action path="/newUserGroup" name="userGroupForm" input=".userGroup.list" scope="session" validate="false" roles="usergroup" parameter="add"
 * @struts.action path="/editUserGroup" name="userGroupForm" input=".userGroup.list" scope="session" validate="false" roles="usergroup" parameter="edit" 
 * @struts.action path="/storeUserGroup" name="userGroupForm" input=".userGroup.edit" scope="session" validate="true" parameter="store" roles="usergroup"
 * @struts.action path="/deleteUserGroup" parameter="delete" validate="false" roles="usergroup"
 *
 * @struts.action-forward name="edit" path=".userGroup.edit"
 * @struts.action-forward name="list" path="/listUserGroup.do" redirect="true"
 * @struts.action-forward name="delete" path="/deleteUserGroup.do" 
 * @struts.action-forward name="reload" path="/editUserGroup.do" 
 */
public class UserGroupEditAction extends PersistentAction {

  protected int deletePersistent(Long id) throws Exception {
    return UserGroup.delete(id);
  }

  protected Persistent loadPersistent(Long id) throws Exception {
    return UserGroup.load(id);
  }

  protected Persistent newPersistent() throws Exception {
    return new UserGroup();
  }

}