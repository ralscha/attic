package ch.ess.blank.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.RequestUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import ch.ess.blank.db.User;
import ch.ess.blank.resource.AppConfig;
import ch.ess.blank.resource.UserConfig;
import ch.ess.common.Constants;
import ch.ess.common.service.mail.MailException;
import ch.ess.common.service.mail.MailMessage;
import ch.ess.common.service.mail.TemplateMailSender;
import ch.ess.common.util.PasswordDigest;
import ch.ess.common.util.TimedValues;
import ch.ess.common.util.Variant;
import ch.ess.common.web.ClientInfo;
import ch.ess.common.web.HibernateAction;
import ch.ess.common.web.UserSession;
import ch.ess.common.web.WebContext;

/** 
 * @author  Ralph Schaer
 * @version $Revision: 1.3 $ $Date: 2004/05/22 16:18:22 $ 
 * 
 * @struts.action path="/login" name="loginForm" input=".login" scope="request" validate="true"
 * @struts.action path="/loginDirect" name="loginForm" input=".login" scope="request" validate="false"
 * @struts.action-forward name="success" path=".default"
 */
public class LoginAction extends HibernateAction {

  private static final Log LOG = LogFactory.getLog(LoginAction.class);

  private static final int TRY_WAIT_TIME = 1200; // 20 minutes

  public ActionForward doAction() throws Exception {

    WebContext ctx = WebContext.currentContext();
    LoginForm loginForm = (LoginForm) ctx.getForm();

    //START Passwort mail
    if (!GenericValidator.isBlankOrNull(loginForm.getNewPassword())) {

      User user = User.find(loginForm.getUserName());
      if (user != null) {
        String newPassword = RandomStringUtils.randomAlphanumeric(8);
        user.setPasswordHash(DigestUtils.shaHex(newPassword));
        sendPasswordMail(user, newPassword);

        ActionMessages messages = new ActionMessages();
        messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("login.passwordMailSent"));
        saveMessages(ctx.getRequest(), messages);
      }

      return ctx.getMapping().getInputForward();
    }
    //END Passwort mail

    //START Login Cookie
    Cookie loginCookie = WebUtils.lookForLoginCookie(ctx.getRequest());
    if (loginCookie != null) {

      User user = User.findWithLoginToken(loginCookie.getValue());

      if (user != null) {

        UserConfig config = UserConfig.getUserConfig(user);

        if (isTokenValid(user, config)) {
          setSessionObjects(ctx.getRequest(), user, true);
          return getLoginOkForward(ctx.getResponse(), loginForm);
        }
        user.setLoginToken(null);
        user.setLoginTokenTime(null);
        loginCookie.setMaxAge(0);
        ctx.getResponse().addCookie(loginCookie);
        return ctx.getMapping().getInputForward();

      }
      loginCookie.setMaxAge(0);
      ctx.getResponse().addCookie(loginCookie);
      return ctx.getMapping().getInputForward();

    }
    //END Login Cookie

    //START Normal Login
    String encryptedPassword = DigestUtils.shaHex(loginForm.getPassword());

    if (!logonTriesOK(loginForm.getUserName(), ctx.getRequest())) {
      return null;
    }

    User user = User.find(loginForm.getUserName(), encryptedPassword);

    if (user != null) {

      TimedValues.removeValue(loginForm.getUserName());
      UserConfig config = UserConfig.getUserConfig(user);

      if (loginForm.isRemember()) {

        if (!isTokenValid(user, config)) {

          String[] data = new String[4];
          data[0] = user.getName();
          data[1] = user.getUserName();
          data[2] = String.valueOf(System.currentTimeMillis());
          data[3] = user.getId().toString();

          user.setLoginToken(PasswordDigest.createLoginToken(data));
          user.setLoginTokenTime(new Date());
        }

        Cookie newCookie = new Cookie(ch.ess.blank.Constants.COOKIE_REMEMBER, user.getLoginToken());
        newCookie.setMaxAge(config.getIntegerProperty(UserConfig.LOGIN_REMEMBER_SECONDS).intValue());
        ctx.getResponse().addCookie(newCookie);

      }

      //login ok
      setSessionObjects(ctx.getRequest(), user, false);
      return getLoginOkForward(ctx.getResponse(), loginForm);

    }
    //login failed
    ActionMessages errors = new ActionMessages();
    errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("login.loginFailed"));
    saveErrors(ctx.getRequest(), errors);
    return ctx.getMapping().getInputForward();

  }

  //END Normal Login

  private ActionForward getLoginOkForward(HttpServletResponse response, LoginForm loginForm) throws IOException {
    if (!GenericValidator.isBlankOrNull(loginForm.getTo())) {
      response.sendRedirect(response.encodeRedirectURL(loginForm.getTo()));
      return null;
    }
    return findForward(Constants.FORWARD_SUCCESS);

  }

  private boolean isTokenValid(User user, UserConfig config) {

    if (user.getLoginToken() == null) {
      return false;
    }

    Calendar today = Calendar.getInstance();

    Calendar cal = Calendar.getInstance();
    cal.setTime(user.getLoginTokenTime());
    cal.add(Calendar.SECOND, config.getIntegerProperty(UserConfig.LOGIN_REMEMBER_SECONDS).intValue());

    return (cal.after(today));

  }

  private void setSessionObjects(HttpServletRequest request, User user, boolean cookieLogin) throws ServletException {
    HttpSession session = request.getSession();

    UserSession userSession = UserSession.getUserSession(user, cookieLogin);
    session.setAttribute(Constants.USER_SESSION, userSession);

    Locale locale = user.getLocale();
    session.setAttribute(Globals.LOCALE_KEY, locale);
    javax.servlet.jsp.jstl.core.Config.set(session, javax.servlet.jsp.jstl.core.Config.FMT_LOCALE, locale);

    int timeoutInMinutes = AppConfig.getIntegerProperty(AppConfig.SESSION_TIMEOUT, 30);
    session.setMaxInactiveInterval(timeoutInMinutes * 60);

    ClientInfo info = new ClientInfo();
    RequestUtils.populate(info, request);
    session.setAttribute(Constants.CLIENT_INFO, info);

    Map map = new HashMap();
    map.put("name", user.getName());
    map.put("firstName", user.getFirstName());
    session.setAttribute("static_username", map);
  }

  private void sendPasswordMail(User user, String newPassword) {
    Map context = new HashMap();
    context.put("password", newPassword);
    try {
      WebApplicationContext wactx = WebApplicationContextUtils.getWebApplicationContext(getServlet()
          .getServletContext());
      TemplateMailSender sender = (TemplateMailSender) wactx.getBean("PasswordMailSender");

      MailMessage mm = new MailMessage();
      mm.addTo(user.getEmail());
      sender.send(user.getLocale(), mm, context);
    } catch (MailException e) {
      LOG.error("send password mail", e);
    } catch (UnsupportedEncodingException e) {
      LOG.error("send password mail", e);
    }
  }

  private boolean logonTriesOK(String userName, HttpServletRequest request) {
    Variant tries = TimedValues.getValue(userName);

    if (tries != null) {
      int t = tries.getInt() + 1;

      TimedValues.addValue(userName, t, TRY_WAIT_TIME);

      if (t >= 10) {
        if (t % 10 == 0) {

          Map context = new HashMap();

          context.put("user", userName);
          context.put("tries", new Integer(t));
          context.put("ip", request.getRemoteAddr());

          try {

            WebApplicationContext wactx = WebApplicationContextUtils.getWebApplicationContext(getServlet()
                .getServletContext());
            TemplateMailSender sender = (TemplateMailSender) wactx.getBean("MultipleLoginMailSender");

            MailMessage mm = new MailMessage();
            mm.addTo(AppConfig.getStringProperty(AppConfig.ERROR_MAIL_RECEIVER));
            sender.send(Constants.ENGLISH_LOCALE, mm, context);

          } catch (MailException e) {
            LOG.error("send logon warning", e);
          } catch (UnsupportedEncodingException e) {
            LOG.error("send logon warning", e);
          }
        }

        return false;
      }
    } else {
      TimedValues.addValue(userName, 1, TRY_WAIT_TIME);
    }

    return true;
  }

}