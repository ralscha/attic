package ch.ess.cal.web.userconfig;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.Globals;

import ch.ess.cal.Constants;
import ch.ess.cal.Util;
import ch.ess.cal.enums.StringValuedEnumReflect;
import ch.ess.cal.enums.TimeEnum;
import ch.ess.cal.model.Email;
import ch.ess.cal.model.User;
import ch.ess.cal.persistence.UserConfigurationDao;
import ch.ess.cal.persistence.UserDao;
import ch.ess.cal.service.Config;
import ch.ess.cal.service.impl.UserConfig;
import ch.ess.cal.web.DynaListDataModel;
import ch.ess.cal.web.LoginCookieUtil;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FWAction;
import com.cc.framework.adapter.struts.FormActionContext;
import com.cc.framework.common.SortOrder;
import com.cc.framework.ui.SelectMode;
import com.cc.framework.ui.control.ControlActionContext;
import com.cc.framework.ui.control.SimpleListControl;

/** 
 * @author sr
 * @version $Revision: 1.3 $ $Date: 2005/05/24 05:07:09 $ 
 * 
 * @struts.action path="/userConfig" name="userConfigForm" input="/userconfigedit.jsp" scope="session" validate="false" roles="$userconfig"
 * 
 * @spring.bean name="/userConfig" lazy-init="true" 
 */
public class UserConfigAction extends FWAction {

  private Config appConfig;
  private UserConfigurationDao userConfigurationDao;
  private UserDao userDao;
  private LoginCookieUtil loginCookieUtil;

  /**
   * @spring.property reflocal="userDao"
   */
  public void setUserDao(final UserDao userDao) {
    this.userDao = userDao;
  }

  /**
   * @spring.property reflocal="userConfigurationDao" 
   */
  public void setUserConfigurationDao(final UserConfigurationDao userConfigurationDao) {
    this.userConfigurationDao = userConfigurationDao;
  }

  /**
   * @spring.property reflocal="appConfig"
   */
  public void setAppConfig(final Config appConfig) {
    this.appConfig = appConfig;
  }

  /**
   * @spring.property ref="loginCookieUtil" 
   */
  public void setLoginCookieUtil(final LoginCookieUtil loginCookieUtil) {
    this.loginCookieUtil = loginCookieUtil;
  }

  public void emailList_onSort(final ControlActionContext ctx, final String column, final SortOrder direction) {
    DynaListDataModel dataModel = (DynaListDataModel)ctx.control().getDataModel();
    dataModel.sort(column, direction);

    UserConfigForm userConfigForm = (UserConfigForm)ctx.form();
    userConfigForm.setTabset("email");

    ctx.control().execute(ctx, column, direction);
  }

  public void emailList_onDelete(final ControlActionContext ctx, final String key) throws Exception {
    DynaListDataModel dataModel = (DynaListDataModel)ctx.control().getDataModel();

    UserConfigForm userConfigForm = (UserConfigForm)ctx.form();
    userConfigForm.setTabset("email");

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

    UserConfigForm userConfigForm = (UserConfigForm)ctx.form();
    userConfigForm.setTabset("email");

  }

  public void add_onClick(final FormActionContext ctx) {

    UserConfigForm userConfigForm = (UserConfigForm)ctx.form();
    if (StringUtils.isNotBlank(userConfigForm.getAddEmail())) {

      User user = Util.getUser(ctx.session(), userDao);

      ctx.addErrors(userConfigForm.validate(user, appConfig, ctx.mapping(), ctx.request()));

      // If there are any Errors return and display an Errormessage
      if (ctx.hasErrors()) {
        ctx.forwardToInput();
        return;
      }

      DynaListDataModel dataModel = (DynaListDataModel)userConfigForm.getEmailList().getDataModel();

      int max = 0;
      for (int i = 0; i < dataModel.size(); i++) {
        String unique = dataModel.getUniqueKey(i);
        max = Math.max(max, Integer.parseInt(unique));
      }

      DynaBean dynaBean = new LazyDynaBean("emailList");
      dynaBean.set("id", String.valueOf(max + 1));
      dynaBean.set("email", userConfigForm.getAddEmail());

      if (dataModel.size() == 0) {
        dynaBean.set("default", Boolean.TRUE);
      } else {
        dynaBean.set("default", Boolean.FALSE);
      }

      dataModel.add(dynaBean);

      userConfigForm.setAddEmail(null);
    }
    userConfigForm.setTabset("email");
    ctx.forwardToInput();
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

    ctx.forwardByName(Constants.FORWARD_SUCCESS);

  }

  public void doExecute(final ActionContext ctx) throws Exception {

    UserConfigForm userConfigForm = (UserConfigForm)ctx.form();

    User user = Util.getUser(ctx.session(), userDao);

    Config userConfig = userConfigurationDao.getUserConfig(user);

    userConfigForm.setTime(userConfig.getIntegerProperty(UserConfig.LOGIN_REMEMBER_NO).toString());
    userConfigForm.setTimeUnit(userConfig.getStringProperty(UserConfig.LOGIN_REMEMBER_UNIT));
    userConfigForm.setHighlightWeekends(userConfig.getBooleanProperty(UserConfig.HIGHLIGHT_WEEKENDS, true));
    userConfigForm.setFirstDayOfWeek(String.valueOf(userConfig.getIntegerProperty(UserConfig.FIRST_DAY_OF_WEEK,
        Calendar.MONDAY)));
    userConfigForm.setMinDaysInFirstWeek(String.valueOf(userConfig.getIntegerProperty(
        UserConfig.MIN_DAYS_IN_FIRST_WEEK, 4)));

    Locale loc = user.getLocale();
    userConfigForm.setLocaleStr(loc.toString());

    userConfigForm.setLastName(user.getName());
    userConfigForm.setFirstName(user.getFirstName());
    userConfigForm.setShortName(user.getShortName());

    userConfigForm.setTimezone(user.getTimeZone().getID());

    userConfigForm.setNoRows(userConfig.getStringProperty(UserConfig.NO_ROWS, UserConfig.NO_ROWS_DEFAULT));

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

    userConfigForm.setEmailList(emailListControl);
    userConfigForm.setAddEmail(null);

    setValidUntil(ctx.request(), user, userConfig, userConfigForm);
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

    userConfig.setProperty(UserConfig.HIGHLIGHT_WEEKENDS, userConfigForm.isHighlightWeekends());
    
    if (!GenericValidator.isBlankOrNull(userConfigForm.getNoRows())) {
      Integer noRows = new Integer(userConfigForm.getNoRows());
      if (noRows.intValue() > 0) {
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
    if (GenericValidator.isInt(userConfigForm.getTime())) {
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
    userConfig.setProperty(UserConfig.MIN_DAYS_IN_FIRST_WEEK, userConfigForm.getMinDaysInFirstWeek());

    user.setLocale(Util.getLocale(userConfigForm.getLocaleStr()));

    TimeZone tz = TimeZone.getTimeZone(userConfigForm.getTimezone());
    user.setTimeZone(tz);

    user.setName(userConfigForm.getLastName());
    user.setFirstName(userConfigForm.getFirstName());

    if (StringUtils.isBlank(userConfigForm.getShortName())) {
      user.setShortName(null);
    } else {
      user.setShortName(userConfigForm.getShortName());
    }

    ctx.session().setAttribute(Globals.LOCALE_KEY, user.getLocale());
    javax.servlet.jsp.jstl.core.Config.set(ctx.session(), javax.servlet.jsp.jstl.core.Config.FMT_LOCALE, user
        .getLocale());

    ctx.addGlobalMessage("common.updateOK");

    if (!GenericValidator.isBlankOrNull(userConfigForm.getNewPassword())) {
      user.setPasswordHash(DigestUtils.shaHex(userConfigForm.getNewPassword()));

      userConfigForm.setNewPassword(null);
      userConfigForm.setOldPassword(null);
      userConfigForm.setRetypeNewPassword(null);
    }

    setValidUntil(ctx.request(), user, userConfig, userConfigForm);

    //email  
    Map<Integer, Email> idMap = new HashMap<Integer, Email>();
    for (Email email : user.getEmails()) {
      idMap.put(email.getId(), email);
    }

    List<Email> newEmailList = new ArrayList<Email>();
    int ix = 0;

    DynaListDataModel dataModel = (DynaListDataModel)userConfigForm.getEmailList().getDataModel();
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

    userConfigurationDao.save(user, userConfig);

    userDao.saveOrUpdate(user);

    userConfigForm.setTabset("general");
    ctx.forwardToInput();

  }

  private void setValidUntil(final HttpServletRequest request, final User user, final Config userConfig,
      final UserConfigForm userConfigForm) {
    if ((user.getLoginToken() != null) && (loginCookieUtil.lookForLoginCookie(request) != null)) {

      Calendar validUntil = Calendar.getInstance();
      validUntil.setTime(user.getLoginTokenTime());
      validUntil.add(Calendar.SECOND, userConfig.getIntegerProperty(UserConfig.LOGIN_REMEMBER_SECONDS).intValue());

      String dateString = DateFormat.getDateInstance(DateFormat.FULL, user.getLocale()).format(validUntil.getTime());
      dateString += " " + DateFormat.getTimeInstance(DateFormat.FULL, user.getLocale()).format(validUntil.getTime());
      userConfigForm.setRememberMeValidUntil(dateString);
    } else {
      userConfigForm.setRememberMeValidUntil(null);
    }
  }

}
