package ch.ess.base.web.login;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;

import ch.ess.base.annotation.spring.Autowire;
import ch.ess.base.annotation.spring.SpringAction;
import ch.ess.base.annotation.struts.ActionScope;
import ch.ess.base.annotation.struts.Forward;
import ch.ess.base.annotation.struts.StrutsAction;
import ch.ess.base.annotation.struts.StrutsActions;
import ch.ess.base.dao.UserConfigurationDao;
import ch.ess.base.dao.UserDao;
import ch.ess.base.model.User;
import ch.ess.base.service.AppConfig;
import ch.ess.base.service.Config;
import ch.ess.base.service.TemplateMailSender;
import ch.ess.base.service.UserConfig;
import ch.ess.base.web.UserPrincipal;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FWAction;
import com.cc.framework.adapter.struts.FormActionContext;
import com.cc.framework.security.SecurityUtil;

@SpringAction(autowire=Autowire.BYNAME)

@StrutsActions (actions = {
  @StrutsAction(path = "/login", 
      form = LoginForm.class, 
      input = "/login.jsp", 
      scope = ActionScope.REQUEST,  
      forwards = @Forward(name = "success", path = "/default.jsp")),
  @StrutsAction(path = "/loginDirect", 
      form = LoginForm.class, 
      input = "/login.jsp", 
      scope = ActionScope.REQUEST,  
      forwards = @Forward(name = "success", path = "/default.jsp")),
  @StrutsAction(path = "/loginNewPassword", 
      form = LoginNewPasswordForm.class, 
      input = "/login.jsp", 
      scope = ActionScope.REQUEST,  
      forwards = @Forward(name = "success", path = "/default.jsp"))      
})
public class LoginAction extends FWAction {

  private UserDao userDao;
  private UserConfigurationDao userConfigurationDao;
  private Config appConfig;
  private TemplateMailSender passwordMailSender;
  private LoginCookieUtil loginCookieUtil;

  public void setPasswordMailSender(final TemplateMailSender passwordMailSender) {
    this.passwordMailSender = passwordMailSender;
  }

  public void setAppConfig(final Config appConfig) {
    this.appConfig = appConfig;
  }

  public void setUserConfigurationDao(final UserConfigurationDao userConfigurationDao) {
    this.userConfigurationDao = userConfigurationDao;
  }

  public void setUserDao(final UserDao userDao) {
    this.userDao = userDao;
  }

  public void setLoginCookieUtil(final LoginCookieUtil loginCookieUtil) {
    this.loginCookieUtil = loginCookieUtil;
  }

  public void doExecute(final ActionContext ctx) throws Exception {

    if (ctx.form() instanceof LoginNewPasswordForm) {
      newPassword(ctx);
      return;
    }

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
    newPassword(ctx);
  }

  private void newPassword(final ActionContext ctx) throws Exception {
    LoginNewPasswordForm loginForm = (LoginNewPasswordForm)ctx.form();

    // validate the form
    ctx.addErrors(loginForm.validate(ctx.mapping(), ctx.request()));

    // check errors
    if (ctx.hasErrors()) {
      // return to the Login.jsp
      ctx.forwardToInput();
      return;
    }

    if (StringUtils.isNotBlank(loginForm.getName())) {

      User user = userDao.findByName(loginForm.getName());

      if (user != null) {
        String newPassword = RandomStringUtils.randomAlphanumeric(8);
        user.setPasswordHash(DigestUtils.shaHex(newPassword));
        userDao.save(user);

        Map<String, String> context = new HashMap<String, String>();
        context.put("password", newPassword);
        try {

          SimpleMailMessage mailMessage = new SimpleMailMessage();
          mailMessage.setFrom(appConfig.getStringProperty(AppConfig.MAIL_SENDER));
          mailMessage.setTo(user.getEmail());
          passwordMailSender.send(user.getLocale(), mailMessage, context);
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

    User user = userDao.findByNameAndToken(loginForm.getUserName(), encryptedPassword);

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
          userDao.save(user);
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
    for (String element : data) {
      sb.append(element);
    }

    return DigestUtils.shaHex(sb.toString());

  }

  private void loginOkForward(final ActionContext ctx, final LoginForm loginForm) throws IOException {
    if (StringUtils.isNotBlank(loginForm.getTo())) {
      ctx.response().sendRedirect(ctx.response().encodeRedirectURL(loginForm.getTo()));
    } else {
      ctx.forwardByName("success");
    }
  }

  private boolean isTokenValid(final User user, final Config config) {

    if (user.getLoginToken() == null) {
      return false;
    }

    Calendar today = Calendar.getInstance();

    Calendar cal = Calendar.getInstance();
    cal.setTime(user.getLoginTokenTime());
    cal.add(Calendar.SECOND, config.getIntegerProperty(UserConfig.LOGIN_REMEMBER_SECONDS));

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

    ctx.session().setAttribute("header_variables", map);

    String noRows = config.getStringProperty(UserConfig.NO_ROWS, UserConfig.NO_ROWS_DEFAULT);
    ctx.session().setAttribute("noRows", noRows);
  }

}
