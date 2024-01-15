package ch.ess.base.web;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForward;
import org.apache.struts.annotation.ActionScope;
import org.apache.struts.annotation.Forward;
import org.apache.struts.annotation.StrutsAction;
import org.apache.struts.annotation.StrutsActions;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;

import ch.ess.base.Constants;
import ch.ess.base.model.User;
import ch.ess.base.persistence.UserConfigurationDao;
import ch.ess.base.persistence.UserDao;
import ch.ess.base.service.AppConfig;
import ch.ess.base.service.Config;
import ch.ess.base.service.TemplateMailSender;
import ch.ess.base.service.UserConfig;

/** 
 * @author sr
 * @version $Revision: 1.4 $ $Date: 2005/07/03 11:31:19 $ 
 * 
 * @spring.bean name="/login,/loginDirect,/loginNewPassword" lazy-init="true"  autowire="byType"
 **/
@StrutsActions (actions = {
  @StrutsAction(path = "/login", 
      form = LoginForm.class, 
      input = "/login.jsp", 
      scope = ActionScope.REQUEST,
      validate = true,
      forwards = @Forward(name = "success", path = "/default.jsp")),
  @StrutsAction(path = "/loginDirect", 
      form = LoginForm.class, 
      input = "/login.jsp", 
      scope = ActionScope.REQUEST,
      validate = true,
      forwards = @Forward(name = "success", path = "/default.jsp")),
  @StrutsAction(path = "/loginNewPassword", 
      form = LoginNewPasswordForm.class, 
      input = "/login.jsp", 
      scope = ActionScope.REQUEST,
      validate = true,
      forwards = @Forward(name = "success", path = "/default.jsp"))      
})
public class LoginAction extends AbstractAction {

  private UserDao userDao;
  private UserConfigurationDao userConfigurationDao;
  private Config appConfig;
  private TemplateMailSender passwordSender;
  private LoginCookieUtil loginCookieUtil;

  public void setPasswordSender(final TemplateMailSender passwordSender) {
    this.passwordSender = passwordSender;
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

    
  @Override
  public ActionForward execute() throws Exception {

    WebContext ctx = WebContext.currentContext();
    
    if (ctx.getForm() instanceof LoginNewPasswordForm) { 
      newPassword();
      return ctx.getInputForward();
    }
    
    
    Cookie loginCookie = loginCookieUtil.lookForLoginCookie(ctx.getRequest());
    if (loginCookie != null) {

      User user = userDao.findWithLoginToken(loginCookie.getValue());

      if (user != null) {

        Config config = userConfigurationDao.getUserConfig(user);

        if (isTokenValid(user, config)) {

          setSessionObjects(user, config, true);          
          return loginOkForward();
        } 
        user.setLoginToken(null);
        user.setLoginTokenTime(null);
        loginCookie.setMaxAge(0);
        ctx.getResponse().addCookie(loginCookie);
        return ctx.getInputForward();
      } 
      loginCookie.setMaxAge(0);
      ctx.getResponse().addCookie(loginCookie);
      return ctx.getInputForward();
    }
    
    

    LoginForm loginForm = (LoginForm)ctx.getForm();

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
        ctx.getResponse().addCookie(newCookie);

      }

      //login ok
      setSessionObjects(user, config, false);
      
      return loginOkForward();

    } 

    //login failed
    addOneErrorRequest("login.loginFailed");
    return ctx.getInputForward();
    
    
  }

  private void newPassword() throws Exception {
    WebContext ctx = WebContext.currentContext();
    LoginNewPasswordForm loginForm = (LoginNewPasswordForm)ctx.getForm();

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
        mailMessage.setTo(user.getEmail());
        passwordSender.send(user.getLocale(), mailMessage, context);
      } catch (MailException e) {
        LogFactory.getLog(getClass()).error("send password mail", e);
      }

      addOneMessageRequest("login.passwordMailSent");
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

  private ActionForward loginOkForward() throws IOException {
    WebContext ctx = WebContext.currentContext();
    LoginForm loginForm = (LoginForm)ctx.getForm();
    
    if (StringUtils.isNotBlank(loginForm.getTo())) {
      ctx.getResponse().sendRedirect(ctx.getResponse().encodeRedirectURL(loginForm.getTo()));
      return null;
    } 
    
    return ctx.findForward("success");    
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

  private void setSessionObjects(final User user, final Config config,
      final boolean cookieLogin) throws Exception {

    WebContext ctx = WebContext.currentContext();
    HttpSession session = ctx.getSession(); 
    
    Set<String> permissions = userDao.getPermission(user.getId().toString());

    UserPrincipal userPrincipal = new UserPrincipal(permissions, cookieLogin, user.getId().toString());

    session.setAttribute(Constants.USER_SESSION, userPrincipal);

    Locale locale = user.getLocale();
    session.setAttribute(Globals.LOCALE_KEY, locale);
    javax.servlet.jsp.jstl.core.Config.set(session, javax.servlet.jsp.jstl.core.Config.FMT_LOCALE, locale);

    int timeoutInMinutes = appConfig.getIntegerProperty(AppConfig.SESSION_TIMEOUT, 30);
    session.setMaxInactiveInterval(timeoutInMinutes * 60);

    Map<String, String> map = new HashMap<String, String>();
    map.put("name", user.getName());

    session.setAttribute("header_variables", map);

    String noRows = config.getStringProperty(UserConfig.NO_ROWS, UserConfig.NO_ROWS_DEFAULT);
    session.setAttribute("noRows", noRows);
  }

}
