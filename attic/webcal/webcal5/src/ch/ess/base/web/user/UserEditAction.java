package ch.ess.base.web.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;

import ch.ess.base.Constants;
import ch.ess.base.Util;
import ch.ess.base.annotation.spring.Autowire;
import ch.ess.base.annotation.spring.SpringAction;
import ch.ess.base.annotation.struts.Role;
import ch.ess.base.dao.UserConfigurationDao;
import ch.ess.base.dao.UserDao;
import ch.ess.base.dao.UserGroupDao;
import ch.ess.base.model.User;
import ch.ess.base.model.UserGroup;
import ch.ess.base.model.UserUserGroup;
import ch.ess.base.service.AppConfig;
import ch.ess.base.service.Config;
import ch.ess.base.service.TemplateMailSender;
import ch.ess.base.web.AbstractEditAction;
import ch.ess.base.web.UserPrincipal;
import ch.ess.cal.dao.TimeCustomerDao;
import ch.ess.cal.dao.TimeProjectDao;
import ch.ess.cal.dao.TimeTaskDao;
import ch.ess.cal.model.Email;
import ch.ess.cal.model.TimeCustomer;
import ch.ess.cal.model.UserTimeCustomer;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FormActionContext;
import com.cc.framework.security.SecurityUtil;

@SpringAction(autowire=Autowire.BYNAME)
@Role("$admin")
public class UserEditAction extends AbstractEditAction<User> {

  private UserGroupDao userGroupDao;
  private Config appConfig;
  private TemplateMailSender passwordMailSender;
  private TimeCustomerDao timeCustomerDao;
  private UserDao userDao;

  public void setTimeCustomerDao(TimeCustomerDao timeCustomerDao) {
    this.timeCustomerDao = timeCustomerDao;
  }
  
  public void setUserDao(UserDao userDao) {
	    this.userDao = userDao;
  }
  
  public void setAppConfig(final Config appConfig) {
    this.appConfig = appConfig;
  }

  public void setUserGroupDao(final UserGroupDao userGroupDao) {
    this.userGroupDao = userGroupDao;
  }

  public void setPasswordMailSender(TemplateMailSender passwordMailSender) {
    this.passwordMailSender = passwordMailSender;
  }

  @Override
  public void save_onClick(FormActionContext ctx) throws Exception {
    if (Constants.hasFeature("usersunlimited")) {
      super.save_onClick(ctx);
      return;
    }
        
    if (getDao().findAll().size() < 10) {
      super.save_onClick(ctx);
    } else {
      ctx.forwardToInput();
    }
  }

  @Override
  protected void afterNewItem(ActionContext ctx, ActionForm form) {
    UserForm userForm = (UserForm)form;
    userForm.setEnabled(true);
    EmailFormListControl emailListControl = new EmailFormListControl();
    emailListControl.populateEmptyList();
    userForm.setEmailList(emailListControl);
  }

  @Override
  protected ActionErrors callValidate(final ActionContext ctx, final ActionForm form) {
    UserForm userForm = (UserForm)form;
    return userForm.validate(appConfig, (UserDao)getDao(), ctx.mapping(), ctx.request());
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

      userForm.getEmailList().add(ctx, userForm.getAddEmail());
      userForm.setAddEmail(null);
    }

    userForm.setTabset("email");

    ctx.forwardToInput();
  }

  public void passwordCreate_onClick(final FormActionContext ctx) {
    UserForm userForm = (UserForm)ctx.form();
    
    String id = userForm.getModelId();
    User user = getDao().findById(id);
    
    
    String newPassword = RandomStringUtils.randomAlphanumeric(8);
    user.setPasswordHash(DigestUtils.shaHex(newPassword));
    getDao().save(user);

    userForm.setLoginPassword(UserForm.FAKE_PASSWORD);
    userForm.setRetypePassword(UserForm.FAKE_PASSWORD);
    
    Map<String, String> context = new HashMap<String, String>();
    context.put("password", newPassword);
    context.put("username", user.getUserName());
    try {

      SimpleMailMessage mailMessage = new SimpleMailMessage();
      mailMessage.setFrom(appConfig.getStringProperty(AppConfig.MAIL_SENDER));
      mailMessage.setTo(user.getDefaultEmail());
      passwordMailSender.send(user.getLocale(), mailMessage, context);
    } catch (MailException e) {
      LogFactory.getLog(getClass()).error("send password mail", e);
    }

    ctx.addGlobalMessage("login.passwordMailSent");

    ctx.forwardToInput();
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

    
    //Per Default dem Benutzer alle Kunden zuweisen
    if(user.getUserTimeCustomers() == null || user.getUserTimeCustomers().isEmpty()){
	    for(TimeCustomer timeCustomer : timeCustomerDao.findAll()){
		    UserTimeCustomer userTimeCustomer = new UserTimeCustomer();
		    userTimeCustomer.setTimeCustomer(timeCustomer);
		    userTimeCustomer.setUser(user);          
		    timeCustomer.getUserTimeCustomers().add(userTimeCustomer);
	    }
    }
    
    
    if (StringUtils.isNotBlank(userForm.getLoginPassword())) {
    if (!userForm.isCookieLogin()) {
      if (!UserForm.FAKE_PASSWORD.equals(userForm.getLoginPassword())) {
        user.setPasswordHash(DigestUtils.shaHex(userForm.getLoginPassword()));
      }
    }
    } else {
      user.setPasswordHash(null);
    }

    user.setLocale(Util.getLocale(userForm.getLocaleStr()));
    user.setTimeZone(TimeZone.getTimeZone(userForm.getTimezone()));
    user.setUserName(userForm.getLoginName());    
    user.setName(userForm.getName());
    user.setFirstName(userForm.getFirstName());   
    
    if (!userForm.isEnabled()) {
      if ((user.getId() == null) || ((UserDao)getDao()).canDelete(user)) {
        user.setEnabled(userForm.isEnabled());
      } else {
        userForm.setEnabled(true);
      }
    } else {
      user.setEnabled(userForm.isEnabled());
    }

    if (StringUtils.isBlank(userForm.getShortName())) {
      user.setShortName(null);
    } else {
      user.setShortName(userForm.getShortName());
    }

    //emails
    userForm.getEmailList().formToModel(user, user.getEmails());
    int ix = 0;
    for (Email email : user.getEmails()) {
      email.setSequence(ix++);
    }

  }

  @Override
  public void modelToForm(final ActionContext ctx, final User user) {
    
    UserForm userForm = (UserForm)ctx.form();

    userForm.setLoginName(user.getUserName());
    userForm.setName(user.getName());
    userForm.setFirstName(user.getFirstName());
    userForm.setShortName(user.getShortName());
    userForm.setEnabled(user.isEnabled());

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

    boolean copyMode = (ctx.request().getParameter("copyid") != null);
    
    if (!copyMode) {
    //PASSWORD
    if (user.getPasswordHash() != null) {
        userForm.setLoginPassword(UserForm.FAKE_PASSWORD);
      userForm.setRetypePassword(UserForm.FAKE_PASSWORD);
    } else {
        userForm.setLoginPassword(null);
      userForm.setRetypePassword(null);
    }
    } else {
      userForm.setLoginPassword(null);
      userForm.setRetypePassword(null);
    }

    if (user.getLocale() != null) {
      userForm.setLocaleStr(user.getLocale().toString());
    }

    userForm.setTimezone(user.getTimeZone().getID());

    UserPrincipal userPrincipal = (UserPrincipal)SecurityUtil.getPrincipal(ctx.session());
    userForm.setCookieLogin(userPrincipal.isLogonCookie());

    //emails
    EmailFormListControl emailListControl = new EmailFormListControl();
    emailListControl.populateList(ctx, user.getEmails());
    userForm.setEmailList(emailListControl);
    
    userForm.setAddEmail(null);
    userForm.setTabset("general");

  }
  
}
