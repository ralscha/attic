package ch.ess.base.web.user;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.annotation.Validator;
import org.apache.struts.annotation.ValidatorField;
import org.apache.struts.annotation.Variable;

import ch.ess.base.persistence.UserDao;
import ch.ess.base.service.AppConfig;
import ch.ess.base.service.Config;
import ch.ess.base.web.AbstractForm;

/** 
 * @author sr
 * @version $Revision: 1.3 $ $Date: 2005/07/03 10:25:04 $
 */
public class UserForm extends AbstractForm {

  public static final String FAKE_PASSWORD = "hasPassword";

  private String password;
  private String retypePassword;
  private String[] userGroupIds;
  private String localeStr;
  private boolean cookieLogin;

  private String userName;
  private String name;
  private String firstName;
  private String email;
  private String tabset = "general";


  @Override
  public void reset(ActionMapping mapping, HttpServletRequest request) {
    userGroupIds = null;
  }


  @ValidatorField(key="login.userName", required=true)
  public void setUserName(final String userName) {
    this.userName = userName;
  }

  public String getUserName() {
    return userName;
  }

    
  public String getFirstName() {
    return firstName;
  }

  @ValidatorField(key="user.firstName", required=true)
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }


  @ValidatorField(key="user.name", required=true)
  public void setName(final String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }


  public String getEmail() {
    return email;
  }

  @ValidatorField(key="user.eMail", required=true, validators=@Validator(name="email"))
  public void setEmail(String email) {
    this.email = email;
  }


  public String getPassword() {
    return password;
  }

  public String getRetypePassword() {
    return retypePassword;
  }

  @ValidatorField(key="login.password", 
                  validators={@Validator(name="identical",                                          
                                         vars=@Variable(key="user.retypePassword", name="secondProperty", value="retypePassword")),
                              @Validator(name="validwhen", 
                                         vars=@Variable(name="test", value="((cookieLogin == 'true') or (*this* != null))"))                                         
                             }  
                 )
  public void setPassword(final String string) {
    password = string;
  }

  @ValidatorField(key="user.retypePassword", 
                  validators=@Validator(name="validwhen", 
                                        vars=@Variable(name="test", value="((cookieLogin == 'true') or (*this* != null))")))
  public void setRetypePassword(final String string) {
    retypePassword = string;
  }

  public String[] getUserGroupIds() {
    return (String[])ArrayUtils.clone(userGroupIds);
  }

  public void setUserGroupIds(final String[] ids) {
    userGroupIds = (String[])ArrayUtils.clone(ids);
  }

  public String getLocaleStr() {
    return localeStr;
  }

  @ValidatorField(key="user.language", required=true)
  public void setLocaleStr(final String string) {
    localeStr = string;
  }

  public boolean isCookieLogin() {
    return cookieLogin;
  }

  public void setCookieLogin(final boolean cookieLogin) {
    this.cookieLogin = cookieLogin;
  }


  public String getTabset() {
    return tabset;
  }

  public void setTabset(String tabset) {
    this.tabset = tabset;
  }

  public ActionErrors validate(final Config appConfig, final UserDao userDao, final ActionMapping mapping,
      final HttpServletRequest request) {


    ActionErrors errors = super.validate(mapping, request);
    if (errors == null) {
      errors = new ActionErrors();
    }

    if (!isCookieLogin()) {
      if (!GenericValidator.isBlankOrNull(getPassword()) && !FAKE_PASSWORD.equals(getPassword())) {

        if (getPassword().length() < appConfig.getIntegerProperty(AppConfig.PASSWORD_MINLENGTH, 0)) {
          errors.add("password", new ActionMessage("userconfig.passwordTooShort"));
        }
      }
    }


    if (errors.isEmpty()) {
      if (userDao.findExcludeId(getUserName(), getModelId()) != null) {
        errors.add("userName", new ActionMessage("error.username.exists"));
      }
    }

    return errors;

  }




}