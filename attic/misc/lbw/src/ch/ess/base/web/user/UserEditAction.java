package ch.ess.base.web.user;

import java.util.Set;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import ch.ess.base.Util;
import ch.ess.base.annotation.struts.Role;
import ch.ess.base.dao.UserDao;
import ch.ess.base.dao.UserGroupDao;
import ch.ess.base.model.User;
import ch.ess.base.model.UserGroup;
import ch.ess.base.model.UserUserGroup;
import ch.ess.base.service.Config;
import ch.ess.base.web.AbstractEditAction;
import ch.ess.base.web.UserPrincipal;
import ch.ess.lbw.dao.WerkDao;
import ch.ess.lbw.model.UserWerk;
import ch.ess.lbw.model.Werk;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FormActionContext;
import com.cc.framework.security.SecurityUtil;

@Role("$admin")
public class UserEditAction extends AbstractEditAction<User> {

  private UserGroupDao userGroupDao;
  private Config appConfig;
  private WerkDao werkDao;

  public void setAppConfig(final Config appConfig) {
    this.appConfig = appConfig;
  }

  public void setUserGroupDao(final UserGroupDao userGroupDao) {
    this.userGroupDao = userGroupDao;
  }


  public void setWerkDao(WerkDao werkDao) {
    this.werkDao = werkDao;
  }

  
  @Override
  protected ActionErrors callValidate(final ActionContext ctx, final ActionForm form) {
    UserForm userForm = (UserForm)form;
    return userForm.validate(appConfig, (UserDao)getDao(), ctx.mapping(), ctx.request());
  }

  @Override
  protected void afterSave(FormActionContext ctx, User user) {
    UserForm userForm = (UserForm)ctx.form();
    userForm.setTabset("general");
  }

  @Override
  public void formToModel(final ActionContext ctx, User user) {


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
          UserGroup userGroup = userGroupDao.findById(id);
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
    
    Set<UserWerk> userWerke = user.getUserWerke();
    for (UserWerk uw : userWerke) {
      uw.getWerk().getUserWerke().remove(uw);
    }
    
    user.getUserWerke().clear();

    String[] werkIds = userForm.getWerkIds();
    if (werkIds != null) {

      for (String id : werkIds) {
        if (StringUtils.isNotBlank(id)) {
          Werk werk = werkDao.findById(id);
          if (werk != null) {

            UserWerk userWerk = new UserWerk();
            userWerk.setUser(user);
            userWerk.setWerk(werk);

            user.getUserWerke().add(userWerk);
            werk.getUserWerke().add(userWerk);

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
    
    user.setKriteriumPreis(userForm.isKriteriumPreis());
    user.setKriteriumSap(userForm.isKriteriumSap());
    user.setKriteriumService(userForm.isKriteriumService());
    user.setKriteriumTeilequalitaet(userForm.isKriteriumTeilequalitaet());

  }

  @Override
  public void modelToForm(final ActionContext ctx, final User user) {

    UserForm userForm = (UserForm)ctx.form();

    userForm.setUserName(user.getUserName());
    userForm.setName(user.getName());
    userForm.setFirstName(user.getFirstName());
    userForm.setEmail(user.getEmail());
    
    
    if (user.getKriteriumPreis() != null) {
      userForm.setKriteriumPreis(user.getKriteriumPreis());
    } else {
      userForm.setKriteriumPreis(false);
    }
    if (user.getKriteriumSap() != null) {
      userForm.setKriteriumSap(user.getKriteriumSap());
    } else {
      userForm.setKriteriumSap(false);
    }
    if (user.getKriteriumService() != null) {
      userForm.setKriteriumService(user.getKriteriumService());
    } else {
      userForm.setKriteriumService(false);
    }
    if (user.getKriteriumTeilequalitaet() != null) {
      userForm.setKriteriumTeilequalitaet(user.getKriteriumTeilequalitaet());
    } else {
      userForm.setKriteriumTeilequalitaet(false);
    }    

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

    
    //Werks
    Set<UserWerk> userWerke = user.getUserWerke();
    if (!userWerke.isEmpty()) {
      String[] werkIds = new String[userWerke.size()];

      int ix = 0;
      for (UserWerk userWerk : userWerke) {
        werkIds[ix++] = userWerk.getWerk().getId().toString();
      }
      userForm.setWerkIds(werkIds);
    } else {
      userForm.setWerkIds(null);
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
