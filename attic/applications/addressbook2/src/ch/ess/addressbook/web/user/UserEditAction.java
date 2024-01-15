package ch.ess.addressbook.web.user;

import ch.ess.addressbook.db.User;
import ch.ess.common.db.Persistent;
import ch.ess.common.web.PersistentAction;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.1 $ $Date: 2003/12/30 11:10:30 $ 
  * 
  * @struts.action path="/newUser" name="userForm" input=".user.list" scope="session" validate="false" roles="admin" parameter="add"
  * @struts.action path="/editUser" name="userForm" input=".user.list" scope="session" validate="false" roles="admin" parameter="edit" 
  * @struts.action path="/storeUser" name="userForm" input=".user.edit" scope="session" validate="true" parameter="store" roles="admin"
  * @struts.action path="/deleteUser" parameter="delete" validate="false" roles="admin"
  *
  * @struts.action-forward name="edit" path=".user.edit"
  * @struts.action-forward name="list" path="/listUser.do" redirect="true"
  * @struts.action-forward name="delete" path="/deleteUser.do" 
  * @struts.action-forward name="reload" path="/editUser.do" 
  */
public class UserEditAction extends PersistentAction {

  protected int deletePersistent(Long id) throws Exception {
    return User.delete(id);
  }

  protected Persistent loadPersistent(Long id) throws Exception {
    return User.load(id);
  }

  protected Persistent newPersistent() throws Exception {
    return new User();
  }

}