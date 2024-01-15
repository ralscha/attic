package ch.ess.base.web.user;

import java.util.Set;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.annotation.ActionScope;
import org.apache.struts.annotation.Forward;
import org.apache.struts.annotation.StrutsAction;

import ch.ess.base.Util;
import ch.ess.base.model.User;
import ch.ess.base.model.UserGroup;
import ch.ess.base.model.UserUserGroup;
import ch.ess.base.persistence.UserDao;
import ch.ess.base.persistence.UserGroupDao;
import ch.ess.base.service.Config;
import ch.ess.base.web.AbstractEditAction;
import ch.ess.base.web.DynaListDataModel;
import ch.ess.base.web.UserPrincipal;

/** 
 * @author sr
 * @version $Revision: 1.4 $ $Date: 2005/07/03 11:31:19 $ 
 * 
 * @spring.bean name="/editUser" lazy-init="true" autowire="byType"
 * @spring.property name="dao" reflocal="userDao"
 **/
@StrutsAction(path = "/editUser", 
              form = UserForm.class, 
              input = "/useredit.jsp", 
              scope = ActionScope.SESSION, 
              roles = "$user", 
              forwards = @Forward(name = "back", path = "/listUser.do", redirect = true))
public class UserEditAction extends AbstractEditAction<User> {

  private UserGroupDao userGroupDao;
  private Config appConfig;

  public void setAppConfig(final Config appConfig) {
    this.appConfig = appConfig;
  }

  public void setUserGroupDao(final UserGroupDao userGroupDao) {
    this.userGroupDao = userGroupDao;
  }

  public void emailList_onSort(final ControlActionContext ctx, final String column, final SortOrder direction) {
    DynaListDataModel dataModel = (DynaListDataModel)ctx.control().getDataModel();
    dataModel.sort(column, direction);

    UserForm userForm = (UserForm)ctx.form();
    userForm.setTabset("email");

    ctx.control().execute(ctx, column, direction);
  }

  public void emailList_onDelete(final ControlActionContext ctx, final String key) throws Exception {
    DynaListDataModel dataModel = (DynaListDataModel)ctx.control().getDataModel();

    UserForm userForm = (UserForm)ctx.form();
    userForm.setTabset("email");

    for (int i = 0; i < dataModel.size(); i++) {
      if (key.equals(dataModel.getUniqueKey(i))) {

        dataModel.remove(i);
        return;
      }
    }

  }

  public void emailList_onCheck(ControlActionContext ctx, String key, SelectMode mode, boolean checked)
      throws Exception {

    //unchecked requests werden nicht verarbeitet
    //bei einem checked alle anderen auf unchecked gesetzt

    if (checked) {
      DynaListDataModel dataModel = (DynaListDataModel)ctx.control().getDataModel();

      for (int i = 0; i < dataModel.size(); i++) {
        DynaBean dynaBean = (DynaBean)dataModel.getElementAt(i);
        if (key.equals(dataModel.getUniqueKey(i))) {
          dynaBean.set("default", Boolean.TRUE);
        } else {
          dynaBean.set("default", Boolean.FALSE);
        }
      }
    }

    UserForm userForm = (UserForm)ctx.form();
    userForm.setTabset("email");
  }


  @Override
  protected void newItem(ActionContext ctx) {
    UserForm userForm = (UserForm)ctx.form();
    clearForm(userForm);

    SimpleListControl emailListControl = new SimpleListControl();
    DynaListDataModel dataModel = new DynaListDataModel();
    emailListControl.setDataModel(dataModel);    

  }

  @Override
  protected ActionErrors callValidate(final FormActionContext ctx, final ActionForm form) {
    UserForm userForm = (UserForm)form;
    return userForm.validate(appConfig, (UserDao)getDao(), ctx.mapping(), ctx.request());
  }

  @Override
  protected void afterSave(FormActionContext ctx) {
    UserForm userForm = (UserForm)ctx.form();
    userForm.setTabset("general");
  }

  @Override
  public User formToModel(final ActionContext ctx, User user) {

    if (user == null) {
      user = new User();
    }

    UserForm userForm = (UserForm)ctx.form();

    Set<UserUserGroup> userUserGroups = user.getUserUserGroups();
    for (UserUserGroup uug : userUserGroups) {
      uug.getUserGroup().getUserUserGroups().remove(uug);
    }
    user.getUserUserGroups().clear();

    String[] userGroupIds = userForm.getUserGroupIds();
    if (userGroupIds != null) {

      for (String id : userGroupIds) {
        if (StringUtils.isNotBlank(id)) {
          UserGroup userGroup = userGroupDao.get(id);
          if (userGroup != null) {

            UserUserGroup userUserGroup = new UserUserGroup();
            userUserGroup.setUser(user);
            userUserGroup.setUserGroup(userGroup);

            user.getUserUserGroups().add(userUserGroup);
            userGroup.getUserUserGroups().add(userUserGroup);

          }
        }
      }
    }

    if (!userForm.isCookieLogin()) {
      if (!UserForm.FAKE_PASSWORD.equals(userForm.getPassword())) {
        user.setPasswordHash(DigestUtils.shaHex(userForm.getPassword()));
      }
    }

    user.setLocale(Util.getLocale(userForm.getLocaleStr()));
    user.setUserName(userForm.getUserName());    
    user.setName(userForm.getName());
    user.setFirstName(userForm.getFirstName());
    user.setEmail(userForm.getEmail());

    return user;
  }

  @Override
  public void modelToForm(final ActionContext ctx, final User user) {
    
    UserForm userForm = (UserForm)ctx.form();

    userForm.setUserName(user.getUserName());    
    userForm.setName(user.getName());
    userForm.setFirstName(user.getFirstName());
    userForm.setEmail(user.getEmail());

    //UserGroups
    Set<UserUserGroup> userUserGroups = user.getUserUserGroups();
    if (!userUserGroups.isEmpty()) {
      String[] userGroupIds = new String[userUserGroups.size()];

      int ix = 0;
      for (UserUserGroup userUserGroup : userUserGroups) {
        userGroupIds[ix++] = userUserGroup.getUserGroup().getId().toString();
      }
      userForm.setUserGroupIds(userGroupIds);
    } else {
      userForm.setUserGroupIds(null);
    }

    //PASSWORD
    if (user.getPasswordHash() != null) {
      userForm.setPassword(UserForm.FAKE_PASSWORD);
      userForm.setRetypePassword(UserForm.FAKE_PASSWORD);
    } else {
      userForm.setPassword(null);
      userForm.setRetypePassword(null);
    }

    if (user.getLocale() != null) {
      userForm.setLocaleStr(user.getLocale().toString());
    }

    UserPrincipal userPrincipal = (UserPrincipal)SecurityUtil.getPrincipal(ctx.session());
    userForm.setCookieLogin(userPrincipal.isLogonCookie());


  }
}
