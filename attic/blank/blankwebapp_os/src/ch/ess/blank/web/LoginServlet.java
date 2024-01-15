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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Transaction;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.Globals;
import org.apache.struts.taglib.TagUtils;
import org.apache.struts.util.RequestUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import ch.ess.blank.db.User;
import ch.ess.blank.resource.AppConfig;
import ch.ess.blank.resource.UserConfig;
import ch.ess.common.Constants;
import ch.ess.common.db.HibernateSession;
import ch.ess.common.service.mail.MailException;
import ch.ess.common.service.mail.MailMessage;
import ch.ess.common.service.mail.TemplateMailSender;
import ch.ess.common.util.PasswordDigest;
import ch.ess.common.util.TimedValues;
import ch.ess.common.util.Variant;
import ch.ess.common.web.ClientInfo;

/** 
 * @author  Ralph Schaer
 * @version $Revision: 1.13 $ $Date: 2004/05/22 12:24:29 $ 
 * 
 * @web.servlet name="login"
 * @web.servlet-mapping url-pattern="/login/*" 
 */
public final class LoginServlet extends HttpServlet {

  private static final Log LOG = LogFactory.getLog(LoginServlet.class);
  private static final int TRY_WAIT_TIME = 1200; // 20 minutes

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    doPost(request, response);
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

    Cookie loginCookie = WebUtils.lookForLoginCookie(request);

    boolean passwordMail = false;
    boolean doLogin = true;

    String encryptedPassword = "";
    String username = "";
    User user = null;

    Transaction tx = null;
    try {
      tx = HibernateSession.currentSession().beginTransaction();

      //login cookie available
      if (loginCookie != null) {

        user = User.findWithLoginToken(loginCookie.getValue());

        if (user != null) {

          UserConfig config = UserConfig.getUserConfig(user);

          if (isTokenValid(user, config)) {
            username = user.getUserName();
            encryptedPassword = user.getPasswordHash();

            setSessionObjects(request, user);

          } else {
            user.setLoginToken(null);
            user.setLoginTokenTime(null);
            loginCookie.setMaxAge(0);
            response.addCookie(loginCookie);
            doLogin = false;
          }
        } else {
          loginCookie.setMaxAge(0);
          response.addCookie(loginCookie);
          doLogin = false;
        }

      } else {

        username = request.getParameter("userName");

        if (request.getParameter("newPassword") != null) {

          user = User.find(username);

          if (user != null) {
            String newPassword = RandomStringUtils.randomAlphanumeric(8);
            user.setPasswordHash(DigestUtils.shaHex(newPassword));
            sendPasswordMail(user, newPassword);
            passwordMail = true;
          }

        } else {
          //no login cookie

          String password = request.getParameter("password");
          String remember = request.getParameter("remember");

          if (!GenericValidator.isBlankOrNull(password)) {
            encryptedPassword = DigestUtils.shaHex(password);
          }

          if (!logonTriesOK(username, request)) {
            return;
          }

          user = User.find(username, encryptedPassword);

          if (user != null) {

            TimedValues.removeValue(username);
            UserConfig config = UserConfig.getUserConfig(user);

            setSessionObjects(request, user);

            if (!GenericValidator.isBlankOrNull(remember)) {

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
              response.addCookie(newCookie);

            }
          }
        }
      }

      if ((user != null) && (!passwordMail) && (doLogin)) {
        Map map = new HashMap();
        map.put("name", user.getName());
        map.put("firstName", user.getFirstName());
        request.getSession().setAttribute("static_username", map);
      }

      tx.commit();

      String req;
      if ((!passwordMail) && (doLogin)) {
        req = request.getContextPath() + "/j_security_check?j_username=" + TagUtils.getInstance().encodeURL(username)
            + "&j_password=" + TagUtils.getInstance().encodeURL(encryptedPassword);
      } else {
        req = request.getContextPath();
      }

      response.sendRedirect(response.encodeRedirectURL(req));
    } catch (HibernateException e) {
      HibernateSession.rollback(tx);
      throw new ServletException(e);
    } finally {
      HibernateSession.closeSession();
    }
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

  private void setSessionObjects(HttpServletRequest request, User user) throws ServletException {
    HttpSession session = request.getSession();
    Locale locale = user.getLocale();
    session.setAttribute(Globals.LOCALE_KEY, locale);
    Config.set(session, Config.FMT_LOCALE, locale);

    int timeoutInMinutes = AppConfig.getIntegerProperty(AppConfig.SESSION_TIMEOUT, 30);
    session.setMaxInactiveInterval(timeoutInMinutes * 60);

    ClientInfo info = new ClientInfo();
    RequestUtils.populate(info, request);
    session.setAttribute(Constants.CLIENT_INFO, info);

  }

  private void sendPasswordMail(User user, String newPassword) {
    Map context = new HashMap();
    context.put("password", newPassword);
    try {
      WebApplicationContext wactx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
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

            WebApplicationContext wactx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
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