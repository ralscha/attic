package ch.ess.cal.web.app;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.Globals;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;

import ch.ess.cal.Constants;
import ch.ess.cal.model.User;
import ch.ess.cal.persistence.UserConfigurationDao;
import ch.ess.cal.persistence.UserDao;
import ch.ess.cal.service.Config;
import ch.ess.cal.service.TemplateMailSender;
import ch.ess.cal.service.event.impl.EventUtil;
import ch.ess.cal.service.impl.AppConfig;
import ch.ess.cal.service.impl.UserConfig;
import ch.ess.cal.web.LoginCookieUtil;
import ch.ess.cal.web.UserPrincipal;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FWAction;
import com.cc.framework.adapter.struts.FormActionContext;
import com.cc.framework.security.SecurityUtil;

/** 
 * @author sr
 * @version $Revision: 1.4 $ $Date: 2005/06/07 18:42:52 $ 
 * 
 * @struts.action path="/login" name="loginForm" input="/login.jsp" scope="request" validate="false"
 * @struts.action path="/loginDirect" name="loginForm" input="/login.jsp" scope="request" validate="false"
 * @struts.action path="/loginNewPassword" name="loginNewPasswordForm" input="/login.jsp" scope="request" validate="false"
 * @struts.action-forward name="success" path="/default.jsp"
 *
 * @spring.bean name="/login,/loginDirect,/loginNewPassword" lazy-init="true"
 **/
public class LoginAction extends FWAction {

  private UserDao userDao;
  private UserConfigurationDao userConfigurationDao;
  private Config appConfig;
  private TemplateMailSender passwordSender;
  private LoginCookieUtil loginCookieUtil;

  /**
   * @spring.property ref="passwordMailSender"   
   */
  public void setPasswordSender(final TemplateMailSender passwordSender) {
    this.passwordSender = passwordSender;
  }

  /**
   * @spring.property reflocal="appConfig"
   */
  public void setAppConfig(final Config appConfig) {
    this.appConfig = appConfig;
  }

  /**
   * @spring.property reflocal="userConfigurationDao"
   */
  public void setUserConfigurationDao(final UserConfigurationDao userConfigurationDao) {
    this.userConfigurationDao = userConfigurationDao;
  }

  /**
   * @spring.property reflocal="userDao"   
   */
  public void setUserDao(final UserDao userDao) {
    this.userDao = userDao;
  }

  /** 
   * @spring.property ref="loginCookieUtil"
   */
  public void setLoginCookieUtil(final LoginCookieUtil loginCookieUtil) {
    this.loginCookieUtil = loginCookieUtil;
  }

  public void doExecute(final ActionContext ctx) throws Exception {

    Cookie loginCookie = loginCookieUtil.lookForLoginCookie(ctx.request());
    if (loginCookie != null) {

      User user = userDao.findWithLoginToken(loginCookie.getValue());

      if (user != null) {

        Config config = userConfigurationDao.getUserConfig(user);

        if (isTokenValid(user, config)) {

          LoginForm loginForm = (LoginForm)ctx.form();

          setSessionObjects(ctx, user, config, true);
          loginOkForward(ctx, loginForm);
        } else {
          user.setLoginToken(null);
          user.setLoginTokenTime(null);
          loginCookie.setMaxAge(0);
          ctx.response().addCookie(loginCookie);
          ctx.forwardToInput();
        }
      } else {
        loginCookie.setMaxAge(0);
        ctx.response().addCookie(loginCookie);
        ctx.forwardToInput();
      }
    }
  }

  public void newPassword_onClick(final FormActionContext ctx) throws Exception {

    LoginNewPasswordForm loginForm = (LoginNewPasswordForm)ctx.form();

    // validate the form
    ctx.addErrors(loginForm.validate(ctx.mapping(), ctx.request()));

    // check errors
    if (ctx.hasErrors()) {
      // return to the Login.jsp
      ctx.forwardToInput();
      return;
    }

    if (!GenericValidator.isBlankOrNull(loginForm.getName())) {

      User user = userDao.find(loginForm.getName());

      if (user != null) {
        String newPassword = RandomStringUtils.randomAlphanumeric(8);
        user.setPasswordHash(DigestUtils.shaHex(newPassword));
        userDao.saveOrUpdate(user);

        Map<String, String> context = new HashMap<String, String>();
        context.put("password", newPassword);
        try {

          SimpleMailMessage mailMessage = new SimpleMailMessage();
          mailMessage.setFrom(appConfig.getStringProperty(AppConfig.MAIL_SENDER));
          mailMessage.setTo(user.getDefaultEmail());
          passwordSender.send(user.getLocale(), mailMessage, context);
        } catch (MailException e) {
          LogFactory.getLog(getClass()).error("send password mail", e);
        }

        ctx.addGlobalMessage("login.passwordMailSent");
      }

    }

    ctx.forwardToInput();
  }

  public void login_onClick(final FormActionContext ctx) throws Exception {

    LoginForm loginForm = (LoginForm)ctx.form();

    // validate the form
    ctx.addErrors(loginForm.validate(ctx.mapping(), ctx.request()));

    // check errors
    if (ctx.hasErrors()) {
      // return to the Login.jsp
      ctx.forwardToInput();
      return;
    }

    String encryptedPassword = DigestUtils.shaHex(loginForm.getPassword());

    User user = userDao.find(loginForm.getUserName(), encryptedPassword);

    if (user != null) {

      Config config = userConfigurationDao.getUserConfig(user);

      if (loginForm.isRemember()) {

        if (!isTokenValid(user, config)) {

          String[] data = new String[4];
          data[0] = user.getName();
          data[1] = user.getUserName();
          data[2] = String.valueOf(System.currentTimeMillis());
          data[3] = user.getId().toString();

          user.setLoginToken(createLoginToken(data));
          user.setLoginTokenTime(new Date());
          userDao.saveOrUpdate(user);
        }

        Cookie newCookie = loginCookieUtil.createNewCookie(user);
        newCookie.setMaxAge(config.getIntegerProperty(UserConfig.LOGIN_REMEMBER_SECONDS));
        ctx.response().addCookie(newCookie);

      }

      //login ok
      setSessionObjects(ctx, user, config, false);
      loginOkForward(ctx, loginForm);

    } else {
      //login failed
      ctx.addGlobalError("login.loginFailed");
      ctx.forwardToInput();

    }

  }

  private String createLoginToken(final String[] data) {
    if (data == null) {
      return null;
    }

    StringBuilder sb = new StringBuilder(100);
    for (int i = 0; i < data.length; i++) {
      sb.append(data[i]);
    }

    return DigestUtils.shaHex(sb.toString());

  }

  private void loginOkForward(final ActionContext ctx, final LoginForm loginForm) throws IOException {
    if (!GenericValidator.isBlankOrNull(loginForm.getTo())) {
      ctx.response().sendRedirect(ctx.response().encodeRedirectURL(loginForm.getTo()));
    } else {
      ctx.forwardByName(Constants.FORWARD_SUCCESS);
    }
  }

  private boolean isTokenValid(final User user, final Config config) {

    if (user.getLoginToken() == null) {
      return false;
    }

    Calendar today = Calendar.getInstance();

    Calendar cal = Calendar.getInstance();
    cal.setTime(user.getLoginTokenTime());
    cal.add(Calendar.SECOND, config.getIntegerProperty(UserConfig.LOGIN_REMEMBER_SECONDS).intValue());

    return (cal.after(today));

  }

  private void setSessionObjects(final ActionContext ctx, final User user, final Config config,
      final boolean cookieLogin) throws Exception {

    Set<String> permissions = userDao.getPermission(user.getId().toString());

    UserPrincipal userPrincipal = new UserPrincipal(permissions, cookieLogin, user.getId().toString());

    SecurityUtil.registerPrincipal(ctx.session(), userPrincipal);

    Locale locale = user.getLocale();
    ctx.session().setAttribute(Globals.LOCALE_KEY, locale);
    javax.servlet.jsp.jstl.core.Config.set(ctx.session(), javax.servlet.jsp.jstl.core.Config.FMT_LOCALE, locale);

    int timeoutInMinutes = appConfig.getIntegerProperty(AppConfig.SESSION_TIMEOUT, 30);
    ctx.session().setMaxInactiveInterval(timeoutInMinutes * 60);

    Map<String, String> map = new HashMap<String, String>();
    map.put("name", user.getName());
    map.put("firstName", user.getFirstName());

    Calendar today = EventUtil.getTodayCalendar(user, config);
    map.put("day", String.valueOf(today.get(Calendar.DAY_OF_YEAR)));
    map.put("weekno", String.valueOf(today.get(Calendar.WEEK_OF_YEAR)));
    DateFormat format = new SimpleDateFormat(Constants.getDateFormatPattern());
    map.put("date", format.format(today.getTime()));

    ctx.session().setAttribute("header_variables", map);

    String noRows = config.getStringProperty(UserConfig.NO_ROWS, "15");
    ctx.session().setAttribute("noRows", noRows);
  }

}
