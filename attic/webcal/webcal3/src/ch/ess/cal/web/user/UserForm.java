package ch.ess.cal.web.user;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import ch.ess.cal.persistence.UserDao;
import ch.ess.cal.service.Config;
import ch.ess.cal.service.impl.AppConfig;
import ch.ess.cal.web.AbstractForm;
import ch.ess.cal.web.DynaListDataModel;

import com.cc.framework.ui.control.SimpleListControl;

/** 
 * @author sr
 * @version $Revision: 1.3 $ $Date: 2005/05/17 06:04:25 $
 *  
 * @struts.form name="userForm"
 */
public class UserForm extends AbstractForm {

  public static final String FAKE_PASSWORD = "hasPassword";

  private String password;
  private String retypePassword;
  private String[] userGroupIds;
  private String localeStr;
  private boolean cookieLogin;

  private String userName;
  private String lastName;
  private String firstName;
  private String shortName;
  private String addEmail;
  private String timezone;
  private String tabset = "general";

  private SimpleListControl emailList;

  @Override
  public void reset(ActionMapping mapping, HttpServletRequest request) {
    userGroupIds = null;
  }

  public String getTimezone() {
    return timezone;
  }

  /**   
   * @struts.validator type="required"
   * @struts.validator-arg position="0" key="userconfig.timezone"
   */
  public void setTimezone(final String string) {
    timezone = string;
  }

  /**   
   * @struts.validator type="required"
   * @struts.validator-arg position="0" key="login.userName"
   */
  public void setUserName(final String userName) {
    this.userName = userName;
  }

  public String getUserName() {
    return userName;
  }

  /**   
   * @struts.validator type="required"
   * @struts.validator-arg position="0" key="user.lastName"
   */
  public void setLastName(final String name) {
    this.lastName = name;
  }

  public String getLastName() {
    return lastName;
  }

  /**   
   * @struts.validator type="required"
   * @struts.validator-arg position="0" key="user.firstName"
   */
  public void setFirstName(final String firstName) {
    this.firstName = firstName;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getShortName() {
    return shortName;
  }

  public void setShortName(String shortName) {
    this.shortName = shortName;
  }

  public void setAddEmail(final String email) {
    this.addEmail = email;
  }

  public String getAddEmail() {
    return addEmail;
  }

  public String getPassword() {
    return password;
  }

  public String getRetypePassword() {
    return retypePassword;
  }

  /**   
   * @struts.validator type="validwhen,identical"
   * @struts.validator-arg position="0" key="login.password"
   * @struts.validator-arg position="1" key="user.retypePassword"
   * @struts.validator-var name="secondProperty" value="retypePassword"
   * @struts.validator-var name="test" value="((cookieLogin == 'true') or (*this* != null))"
   */
  public void setPassword(final String string) {
    password = string;
  }

  /**   
   * @struts.validator type="validwhen"
   * @struts.validator-arg position="0" key="user.retypePassword"
   * @struts.validator-var name="test" value="((cookieLogin == 'true') or (*this* != null))"
   */
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

  /**   
   * @struts.validator type="required"
   * @struts.validator-arg position="0" key="user.language"
   */
  public void setLocaleStr(final String string) {
    localeStr = string;
  }

  public boolean isCookieLogin() {
    return cookieLogin;
  }

  public void setCookieLogin(final boolean cookieLogin) {
    this.cookieLogin = cookieLogin;
  }

  public SimpleListControl getEmailList() {
    return emailList;
  }

  public void setEmailList(SimpleListControl emailList) {
    this.emailList = emailList;
  }

  public String getTabset() {
    return tabset;
  }

  public void setTabset(String tabset) {
    this.tabset = tabset;
  }

  public ActionErrors validate(final Config appConfig, final UserDao userDao, final ActionMapping mapping,
      final HttpServletRequest request) {

    if (GenericValidator.isBlankOrNull(getAddEmail())) {

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

      DynaListDataModel dataModel = (DynaListDataModel)getEmailList().getDataModel();
      if (dataModel.size() == 0) {
        errors.add(ActionMessages.GLOBAL_MESSAGE,
            new ActionMessage("errors.required", translate(request, "user.eMail")));
      }

      if (errors.isEmpty()) {
        if (userDao.findExcludeId(getUserName(), getModelId()) != null) {
          errors.add("userName", new ActionMessage("error.username.exists"));
        }
      }

      return errors;
    }

    if (!GenericValidator.isEmail(getAddEmail())) {
      ActionErrors errors = new ActionErrors();
      errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.email", translate(request, "user.eMail")));
      return errors;
    }

    return null;

  }

}
