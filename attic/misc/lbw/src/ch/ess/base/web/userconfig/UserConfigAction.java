package ch.ess.base.web.userconfig;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.struts.Globals;

import ch.ess.base.Util;
import ch.ess.base.annotation.spring.SpringAction;
import ch.ess.base.annotation.struts.ActionScope;
import ch.ess.base.annotation.struts.StrutsAction;
import ch.ess.base.dao.UserConfigurationDao;
import ch.ess.base.dao.UserDao;
import ch.ess.base.enums.StringValuedEnumReflect;
import ch.ess.base.enums.TimeEnum;
import ch.ess.base.model.User;
import ch.ess.base.service.Config;
import ch.ess.base.service.UserConfig;
import ch.ess.base.web.CrumbsUtil;
import ch.ess.base.web.login.LoginCookieUtil;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FWAction;
import com.cc.framework.adapter.struts.FormActionContext;

@SpringAction
@StrutsAction(path = "/userConfig", 
              form = UserConfigForm.class, 
              input = "/userconfigedit.jsp", 
              scope = ActionScope.SESSION, 
              roles = "$userconfig")
public class UserConfigAction extends FWAction {

  private Config appConfig;
  private UserConfigurationDao userConfigurationDao;
  private UserDao userDao;
  private LoginCookieUtil loginCookieUtil;

  public void setUserDao(final UserDao userDao) {
    this.userDao = userDao;
  }

  public void setUserConfigurationDao(final UserConfigurationDao userConfigurationDao) {
    this.userConfigurationDao = userConfigurationDao;
  }

  public void setAppConfig(final Config appConfig) {
    this.appConfig = appConfig;
  }

  public void setLoginCookieUtil(final LoginCookieUtil loginCookieUtil) {
    this.loginCookieUtil = loginCookieUtil;
  }

  public void deleteCookie_onClick(final FormActionContext ctx) throws Exception {

    Cookie loginCookie = loginCookieUtil.lookForLoginCookie(ctx.request());
    if (loginCookie != null) {

      User user = Util.getUser(ctx.session(), userDao);
      user.setLoginToken(null);
      user.setLoginTokenTime(null);
      loginCookie.setMaxAge(0);
      ctx.response().addCookie(loginCookie);
    }

    UserConfigForm userConfigForm = (UserConfigForm)ctx.form();
    userConfigForm.setTabset("rememberme");

    ctx.forwardByName("success");

  }

  public void doExecute(final ActionContext ctx) throws Exception {

    UserConfigForm userConfigForm = (UserConfigForm)ctx.form();

    User user = Util.getUser(ctx.session(), userDao);

    Config userConfig = userConfigurationDao.getUserConfig(user);

    userConfigForm.setTime(userConfig.getIntegerProperty(UserConfig.LOGIN_REMEMBER_NO).toString());
    userConfigForm.setTimeUnit(userConfig.getStringProperty(UserConfig.LOGIN_REMEMBER_UNIT));
    userConfigForm.setFirstDayOfWeek(String.valueOf(userConfig.getIntegerProperty(UserConfig.FIRST_DAY_OF_WEEK,
        Calendar.MONDAY)));

    userConfigForm.setNoRows(userConfig.getStringProperty(UserConfig.NO_ROWS, UserConfig.NO_ROWS_DEFAULT));

    Locale loc = user.getLocale();
    userConfigForm.setLocaleStr(loc.toString());

    userConfigForm.setName(user.getName());
    userConfigForm.setFirstName(user.getFirstName());
    userConfigForm.setEmail(user.getEmail());

    setValidUntil(ctx.request(), user, userConfig, userConfigForm);
    
    CrumbsUtil.updateCallStack(this, ctx);
    CrumbsUtil.updateCrumbs(ctx);
    ctx.forwardToInput();
  }

  public void save_onClick(final FormActionContext ctx) throws Exception {

    UserConfigForm userConfigForm = (UserConfigForm)ctx.form();
    User user = Util.getUser(ctx.session(), userDao);

    ctx.addErrors(userConfigForm.validate(user, appConfig, ctx.mapping(), ctx.request()));

    if (ctx.hasErrors()) {
      ctx.forwardToInput();
      return;
    }

    Config userConfig = userConfigurationDao.getUserConfig(user);

    userConfig.setProperty(UserConfig.LOGIN_REMEMBER_NO, userConfigForm.getTime());
    userConfig.setProperty(UserConfig.LOGIN_REMEMBER_UNIT, userConfigForm.getTimeUnit());

    if (StringUtils.isNotBlank(userConfigForm.getNoRows())) {
      Integer noRows = new Integer(userConfigForm.getNoRows());
      if (noRows > 0) {
        userConfig.setProperty(UserConfig.NO_ROWS, userConfigForm.getNoRows());
        ctx.session().setAttribute("noRows", noRows.toString());
      } else {
        userConfig.setProperty(UserConfig.NO_ROWS, UserConfig.NO_ROWS_DEFAULT);
        ctx.session().setAttribute("noRows", UserConfig.NO_ROWS_DEFAULT);
        userConfigForm.setNoRows(UserConfig.NO_ROWS_DEFAULT);
      }
    }

    TimeEnum timeUnit = StringValuedEnumReflect.getEnum(TimeEnum.class, userConfigForm.getTimeUnit());
    int secondsForm = 0;
    if (NumberUtils.isDigits(userConfigForm.getTime())) {
      secondsForm = Integer.parseInt(userConfigForm.getTime());
    }

    int seconds = 0;
    if (timeUnit == TimeEnum.MINUTE) {
      seconds = secondsForm * 60;
    } else if (timeUnit == TimeEnum.HOUR) {
      seconds = secondsForm * 60 * 60;
    } else if (timeUnit == TimeEnum.DAY) {
      seconds = secondsForm * 60 * 60 * 24;
    } else if (timeUnit == TimeEnum.WEEK) {
      seconds = secondsForm * 60 * 60 * 24 * 7;
    } else if (timeUnit == TimeEnum.MONTH) {
      seconds = secondsForm * 60 * 60 * 24 * 31;
    } else if (timeUnit == TimeEnum.YEAR) {
      seconds = secondsForm * 60 * 60 * 24 * 365;
    }

    userConfig.setProperty(UserConfig.LOGIN_REMEMBER_SECONDS, seconds);
    userConfig.setProperty(UserConfig.FIRST_DAY_OF_WEEK, userConfigForm.getFirstDayOfWeek());

    user.setLocale(Util.getLocale(userConfigForm.getLocaleStr()));

    user.setName(userConfigForm.getName());
    user.setFirstName(userConfigForm.getFirstName());
    user.setEmail(userConfigForm.getEmail());

    ctx.session().setAttribute(Globals.LOCALE_KEY, user.getLocale());
    javax.servlet.jsp.jstl.core.Config.set(ctx.session(), javax.servlet.jsp.jstl.core.Config.FMT_LOCALE, user
        .getLocale());

    ctx.addGlobalMessage("common.updateOK");

    if (StringUtils.isNotBlank(userConfigForm.getNewPassword())) {
      user.setPasswordHash(DigestUtils.shaHex(userConfigForm.getNewPassword()));

      userConfigForm.setNewPassword(null);
      userConfigForm.setOldPassword(null);
      userConfigForm.setRetypeNewPassword(null);
    }

    setValidUntil(ctx.request(), user, userConfig, userConfigForm);

    userConfigurationDao.save(user, userConfig);

    userDao.save(user);

    ctx.forwardToInput();

  }

  private void setValidUntil(final HttpServletRequest request, final User user, final Config userConfig,
      final UserConfigForm userConfigForm) {
    if ((user.getLoginToken() != null) && (loginCookieUtil.lookForLoginCookie(request) != null)) {

      Calendar validUntil = Calendar.getInstance();
      validUntil.setTime(user.getLoginTokenTime());
      validUntil.add(Calendar.SECOND, userConfig.getIntegerProperty(UserConfig.LOGIN_REMEMBER_SECONDS));

      String dateString = DateFormat.getDateInstance(DateFormat.FULL, user.getLocale()).format(validUntil.getTime());
      dateString += " " + DateFormat.getTimeInstance(DateFormat.FULL, user.getLocale()).format(validUntil.getTime());
      userConfigForm.setRememberMeValidUntil(dateString);
    } else {
      userConfigForm.setRememberMeValidUntil(null);
    }
  }

}
