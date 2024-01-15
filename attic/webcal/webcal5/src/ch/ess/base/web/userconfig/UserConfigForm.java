package ch.ess.base.web.userconfig;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import ch.ess.base.annotation.struts.Validator;
import ch.ess.base.annotation.struts.ValidatorField;
import ch.ess.base.annotation.struts.Variable;
import ch.ess.base.model.User;
import ch.ess.base.service.AppConfig;
import ch.ess.base.service.Config;
import ch.ess.base.web.AbstractForm;
import ch.ess.base.web.SimpleListDataModel;
import ch.ess.base.web.user.EmailFormListControl;

public class UserConfigForm extends AbstractForm {

  private String loginName;

  private String name;
  private String firstName;
  private String shortName;

  private String timeUnit;
  private String time;

  private String oldPassword;
  private String newPassword;
  private String retypeNewPassword;
  private String firstDayOfWeek;
  private String minDaysInFirstWeek;

  private String localeStr;
  private String noRows;
  private String timezone;

  private String overviewWidth;
  private String overviewHeight;
  
  private String timefactor;

  private EmailFormListControl emailList;
  private String addEmail;

  private String rememberMeValidUntil;

  private String tabset = "general";
  private String groupId;

  private boolean highlightWeekends;
  private boolean showQuickAdd;
  private String taskDefaultView;

  @Override
  public void reset(final ActionMapping mapping, final HttpServletRequest request) {
    oldPassword = null;
    newPassword = null;
    retypeNewPassword = null;
    highlightWeekends = false;
    showQuickAdd = false;
  }

  public boolean isHighlightWeekends() {
    return highlightWeekends;
  }

  public void setHighlightWeekends(boolean highlightWeekends) {
    this.highlightWeekends = highlightWeekends;
  }

  public boolean isShowQuickAdd() {
    return showQuickAdd;
  }

  public void setShowQuickAdd(boolean showQuickAdd) {
    this.showQuickAdd = showQuickAdd;
  }

  public String getNewPassword() {
    return newPassword;
  }

  public String getOldPassword() {
    return oldPassword;
  }

  public String getRetypeNewPassword() {
    return retypeNewPassword;
  }

  public String getTime() {
    return time;
  }

  public String getLocaleStr() {
    return localeStr;
  }

  public String getTimeUnit() {
    return timeUnit;
  }

  public String getTimezone() {
    return timezone;
  }

  @ValidatorField(key = "userconfig.timezone", required = true)
  public void setTimezone(final String string) {
    timezone = string;
  }

  @ValidatorField(key = "userconfig.newPassword", validators = @Validator(name = "identical", vars = @Variable(key = "user.retypePassword", name = "secondProperty", value = "retypeNewPassword")))
  public void setNewPassword(final String string) {
    newPassword = string;
  }

  public void setOldPassword(final String string) {
    oldPassword = string;
  }

  public void setRetypeNewPassword(final String string) {
    retypeNewPassword = string;
  }

  @ValidatorField(key = "userconfig.loginRemember", required = true, validators = {@Validator(name = "integer"),
      @Validator(name = "positive")})
  public void setTime(final String str) {
    time = str;
  }

  @ValidatorField(key = "user.language", required = true)
  public void setLocaleStr(final String string) {
    localeStr = string;
  }

  @ValidatorField(key = "userconfig.loginRemember", required = true)
  public void setTimeUnit(final String string) {
    timeUnit = string;
  }

  public String getOverviewHeight() {
    return overviewHeight;
  }

  @ValidatorField(key = "userconfig.overviewHeight", required = true, validators = {@Validator(name = "integer"),
      @Validator(name = "positive")})
  public void setOverviewHeight(String overviewHeight) {
    this.overviewHeight = overviewHeight;
  }

  public String getOverviewWidth() {
    return overviewWidth;
  }

  @ValidatorField(key = "userconfig.overviewWidth", required = true, validators = {@Validator(name = "integer"),
      @Validator(name = "positive")})
  public void setOverviewWidth(String overviewWidth) {
    this.overviewWidth = overviewWidth;
  }

  @ValidatorField(key = "user.name", required = true)
  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public String getFirstName() {
    return firstName;
  }

  @ValidatorField(key = "user.firstName", required = true)
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getShortName() {
    return shortName;
  }

  public void setShortName(String shortName) {
    this.shortName = shortName;
  }

  public String getFirstDayOfWeek() {
    return firstDayOfWeek;
  }

  @ValidatorField(key = "userconfig.firstDayOfWeek", required = true)
  public void setFirstDayOfWeek(final String firstDay) {
    firstDayOfWeek = firstDay;
  }

  public String getMinDaysInFirstWeek() {
    return minDaysInFirstWeek;
  }

  @ValidatorField(key = "userconfig.minDaysInFirstWeek", required = true)
  public void setMinDaysInFirstWeek(String minDaysInFirstWeek) {
    this.minDaysInFirstWeek = minDaysInFirstWeek;
  }

  public String getRememberMeValidUntil() {
    return rememberMeValidUntil;
  }

  public void setRememberMeValidUntil(final String rememberMeValidUntil) {
    this.rememberMeValidUntil = rememberMeValidUntil;
  }

  @ValidatorField(key = "user.eMail", required = false, validators = @Validator(name = "email"))
  public void setAddEmail(final String email) {
    this.addEmail = email;
  }

  public String getAddEmail() {
    return addEmail;
  }

  public String getLoginName() {
    return loginName;
  }

  public void setLoginName(String loginName) {
    this.loginName = loginName;
  }

  public String getTaskDefaultView() {
    return taskDefaultView;
  }

  public void setTaskDefaultView(String taskDefaultView) {
    this.taskDefaultView = taskDefaultView;
  }

  public EmailFormListControl getEmailList() {
    return emailList;
  }

  public void setEmailList(EmailFormListControl emailList) {
    this.emailList = emailList;
  }

  public String getNoRows() {
    return noRows;
  }

  @ValidatorField(key = "userconfig.noRows", required = true, validators = {@Validator(name = "integer"),
      @Validator(name = "positive")})
  public void setNoRows(final String noRows) {
    this.noRows = noRows;
  }

  public String getTabset() {
    return tabset;
  }

  public void setTabset(String tabset) {
    this.tabset = tabset;
  }

  public String getGroupId() {
    return groupId;
  }

  public void setGroupId(String groupId) {
    this.groupId = groupId;
  }

  public String getTimefactor() {
    return timefactor;
  }

  @ValidatorField(key = "userconfig.timefactor", required = false, validators = @Validator(name = "double"))
  public void setTimefactor(String timefactor) {
    this.timefactor = timefactor;
  }

  public ActionErrors validate(final User user, final Config appConfig, final ActionMapping mapping,
      final HttpServletRequest request) {
    ActionErrors errors = super.validate(mapping, request);
    if (errors == null) {
      errors = new ActionErrors();
    }

    if (StringUtils.isBlank(getAddEmail())) {
      SimpleListDataModel dataModel = (SimpleListDataModel)getEmailList().getDataModel();
      if (dataModel.size() == 0) {
        errors.add(ActionMessages.GLOBAL_MESSAGE,
            new ActionMessage("errors.required", translate(request, "user.eMail")));
      }
    }

    if (StringUtils.isNotBlank(newPassword)) {

      if (StringUtils.isNotBlank(newPassword)) {
        if (newPassword.length() < appConfig.getIntegerProperty(AppConfig.PASSWORD_MINLENGTH, 0)) {
          errors.add("newPassword", new ActionMessage("userconfig.passwordTooShort"));
        }
      }

      if (user != null) {
        if (!validatePassword(user.getPasswordHash(), oldPassword)) {
          errors.add("oldPassword", new ActionMessage("userconfig.oldPasswordNotCorrect"));
        }
      }

    }

    return errors;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

  private boolean validatePassword(final String hashPassword, final String clearTextPassword) {

    if (clearTextPassword == null) {
      return false;
    }

    String digest = DigestUtils.shaHex(clearTextPassword);
    return digest.equals(hashPassword);
  }

}
