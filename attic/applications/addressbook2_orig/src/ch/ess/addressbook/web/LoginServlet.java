package ch.ess.addressbook.web;

import java.io.IOException;
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

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.Globals;
import org.apache.struts.taglib.TagUtils;

import ch.ess.addressbook.db.User;
import ch.ess.addressbook.mail.AppMailType;
import ch.ess.addressbook.resource.AppConfig;
import ch.ess.addressbook.resource.UserConfig;
import ch.ess.common.db.HibernateSession;
import ch.ess.common.mail.Mail;
import ch.ess.common.mail.MailException;
import ch.ess.common.util.PasswordDigest;

/** 
  * @author  Ralph Schaer
  * @version 1.0, 13.07.2003 
  * 
  * @web.servlet name="login" display-name="Login Servlet" load-on-startup="1"
  * @web.servlet-mapping url-pattern="/login/*" 
  */
public final class LoginServlet extends HttpServlet {

  private static final Log LOG = LogFactory.getLog(LoginServlet.class);

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    doPost(request, response);
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

    Cookie loginCookie = RememberMeUtil.lookForLoginCookie(request);

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

            setUserLocale(request, user, config);

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
            user.setPasswordHash(PasswordDigest.getDigestString(newPassword));
            sendPasswordMail(user, newPassword);
            passwordMail = true;
          }

        } else {
          //no login cookie

          String password = request.getParameter("password");
          String remember = request.getParameter("remember");

          if (!GenericValidator.isBlankOrNull(password)) {
            encryptedPassword = PasswordDigest.getDigestString(password);
          }

          user = User.find(username, encryptedPassword);

          if (user != null) {
            UserConfig config = UserConfig.getUserConfig(user);

            setUserLocale(request, user, config);

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
              
              Cookie newCookie = new Cookie(ch.ess.addressbook.Constants.COOKIE_REMEMBER, user.getLoginToken());
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
        req =
          request.getContextPath()
            + "/j_security_check?j_username="
            + TagUtils.getInstance().encodeURL(username)
            + "&j_password="
            + TagUtils.getInstance().encodeURL(encryptedPassword);
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

  private void setUserLocale(HttpServletRequest request, User user, UserConfig config) {
    HttpSession session = request.getSession();
    Locale locale = user.getLocale();
    session.setAttribute(Globals.LOCALE_KEY, locale);
    session.setAttribute("static_noRows", config.getStringProperty(UserConfig.NO_ROWS, "10"));
    Config.set(session, Config.FMT_LOCALE, locale);
    
    int timeoutInMinutes = AppConfig.getIntegerProperty(AppConfig.SESSION_TIMEOUT, 30);    
    session.setMaxInactiveInterval(timeoutInMinutes * 60);
  }

  private void sendPasswordMail(User user, String newPassword) {
    Map context = new HashMap();
    context.put("recipient", user.getEmail());
    context.put("newPassword", newPassword);
    try {
      Mail.send(AppMailType.PASSWORD, context, user.getLocale());
    } catch (MailException e1) {
      LOG.error("send password mail", e1);
    }
  }
}
