package ch.ess.cal.web.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import ch.ess.cal.Util;
import ch.ess.cal.model.Email;
import ch.ess.cal.model.User;
import ch.ess.cal.model.UserGroup;
import ch.ess.cal.model.UserUserGroup;
import ch.ess.cal.persistence.UserDao;
import ch.ess.cal.persistence.UserGroupDao;
import ch.ess.cal.service.Config;
import ch.ess.cal.web.AbstractEditAction;
import ch.ess.cal.web.DynaListDataModel;
import ch.ess.cal.web.UserPrincipal;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FormActionContext;
import com.cc.framework.common.SortOrder;
import com.cc.framework.security.SecurityUtil;
import com.cc.framework.ui.SelectMode;
import com.cc.framework.ui.control.ControlActionContext;
import com.cc.framework.ui.control.SimpleListControl;

/** 
 * @author sr
 * @version $Revision: 1.3 $ $Date: 2005/05/17 06:04:25 $ 
 * 
 * @struts.action path="/editUser" name="userForm" input="/useredit.jsp" scope="session" validate="false" roles="$user"
 * @struts.action-forward name="back" path="/listUser.do" redirect="true"
 * 
 * @spring.bean name="/editUser" lazy-init="true"
 * @spring.property name="dao" reflocal="userDao"
 **/

public class UserEditAction extends AbstractEditAction<User> {

  private UserGroupDao userGroupDao;
  private Config appConfig;

  /** 
   * @spring.property reflocal="appConfig"
   */
  public void setAppConfig(final Config appConfig) {
    this.appConfig = appConfig;
  }

  /**
   * @spring.property reflocal="userGroupDao"  
   */
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

  public void add_onClick(final FormActionContext ctx) {

    UserForm userForm = (UserForm)ctx.form();
    if (StringUtils.isNotBlank(userForm.getAddEmail())) {
      ActionErrors errors = callValidate(ctx, userForm);
      ctx.addErrors(errors);

      // If there are any Errors return and display an Errormessage
      if (ctx.hasErrors()) {
        ctx.forwardToInput();
        return;
      }

      DynaListDataModel dataModel = (DynaListDataModel)userForm.getEmailList().getDataModel();

      int max = 0;
      for (int i = 0; i < dataModel.size(); i++) {
        String unique = dataModel.getUniqueKey(i);
        max = Math.max(max, Integer.parseInt(unique));
      }

      DynaBean dynaBean = new LazyDynaBean("emailList");
      dynaBean.set("id", String.valueOf(max + 1));
      dynaBean.set("email", userForm.getAddEmail());

      if (dataModel.size() == 0) {
        dynaBean.set("default", Boolean.TRUE);
      } else {
        dynaBean.set("default", Boolean.FALSE);
      }

      dataModel.add(dynaBean);

      userForm.setAddEmail(null);
    }

    userForm.setTabset("email");

    ctx.forwardToInput();
  }

  @Override
  protected void newItem(ActionContext ctx) {
    UserForm userForm = (UserForm)ctx.form();
    clearForm(userForm);

    SimpleListControl emailListControl = new SimpleListControl();
    DynaListDataModel dataModel = new DynaListDataModel();
    emailListControl.setDataModel(dataModel);
    userForm.setEmailList(emailListControl);

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
    user.setTimeZone(TimeZone.getTimeZone(userForm.getTimezone()));
    user.setUserName(userForm.getUserName());
    user.setFirstName(userForm.getFirstName());
    user.setName(userForm.getLastName());

    if (StringUtils.isBlank(userForm.getShortName())) {
      user.setShortName(null);
    } else {
      user.setShortName(userForm.getShortName());
    }

    //email  
    Map<Integer, Email> idMap = new HashMap<Integer, Email>();
    for (Email email : user.getEmails()) {
      idMap.put(email.getId(), email);
    }

    List<Email> newEmailList = new ArrayList<Email>();
    int ix = 0;

    DynaListDataModel dataModel = (DynaListDataModel)userForm.getEmailList().getDataModel();
    for (int i = 0; i < dataModel.size(); i++) {
      DynaBean dynaBean = (DynaBean)dataModel.getElementAt(i);
      Boolean def = (Boolean)dynaBean.get("default");
      String emailStr = (String)dynaBean.get("email");
      Integer id = new Integer(dataModel.getUniqueKey(i));

      Email email = idMap.get(id);
      if (email != null) {
        email.setDefaultEmail(def.booleanValue());
        email.setEmail(emailStr);
        email.setSequence(ix++);
        newEmailList.add(email);
        idMap.remove(id);
      } else {
        Email e = new Email();
        e.setDefaultEmail(def.booleanValue());
        e.setEmail(emailStr);
        e.setSequence(ix++);
        e.setUser(user);
        newEmailList.add(e);
      }
    }

    user.getEmails().clear();
    user.getEmails().addAll(newEmailList);

    return user;
  }

  @Override
  public void modelToForm(final ActionContext ctx, final User user) {
    
    UserForm userForm = (UserForm)ctx.form();

    userForm.setUserName(user.getUserName());
    userForm.setFirstName(user.getFirstName());
    userForm.setLastName(user.getName());
    userForm.setShortName(user.getShortName());

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

    userForm.setTimezone(user.getTimeZone().getID());

    UserPrincipal userPrincipal = (UserPrincipal)SecurityUtil.getPrincipal(ctx.session());
    userForm.setCookieLogin(userPrincipal.isLogonCookie());

    DynaListDataModel dataModel = new DynaListDataModel();

    Set<Email> emailList = user.getEmails();
    for (Email email : emailList) {

      DynaBean dynaBean = new LazyDynaBean("emailList");
      dynaBean.set("id", email.getId().toString());
      dynaBean.set("email", email.getEmail());
      dynaBean.set("default", Boolean.valueOf(email.isDefaultEmail()));

      dataModel.add(dynaBean);

    }

    SimpleListControl emailListControl = new SimpleListControl();
    emailListControl.setDataModel(dataModel);

    userForm.setEmailList(emailListControl);
    userForm.setAddEmail(null);

  }
}
