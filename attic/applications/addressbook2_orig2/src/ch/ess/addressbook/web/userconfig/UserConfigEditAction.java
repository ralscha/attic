package ch.ess.addressbook.web.userconfig;

import java.text.DateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;

import net.sf.hibernate.HibernateException;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForward;

import ch.ess.addressbook.db.User;
import ch.ess.addressbook.resource.UserConfig;
import ch.ess.addressbook.web.WebUtils;
import ch.ess.common.Constants;
import ch.ess.common.util.PasswordDigest;
import ch.ess.common.web.DispatchAction;
import ch.ess.common.web.WebContext;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.15 $ $Date: 2003/11/11 19:06:51 $ 
  * 
  * @struts.action path="/storeUserConfig" name="userConfigForm" input=".userconfig.edit" scope="session" validate="true" parameter="store"
  * @struts.action path="/editUserConfig" name="userConfigForm" input=".userconfig.edit" scope="session" validate="false" parameter="edit"
  * @struts.action-forward name="success" path=".userconfig.edit"
  */
public class UserConfigEditAction extends DispatchAction {

  protected ActionForward store() throws Exception {

    WebContext ctx = WebContext.currentContext();
    HttpServletRequest request = ctx.getRequest();

    UserConfigForm prefForm = (UserConfigForm)ctx.getForm();

    User user = WebUtils.getUser(request);
    prefForm.fromForm(user);

    HttpSession session = request.getSession();
    session.setAttribute(Globals.LOCALE_KEY, user.getLocale());
    Config.set(session, Config.FMT_LOCALE, user.getLocale());

    addOneMessageRequest(Constants.ACTION_MESSAGE_UPDATE_OK);

    if (!GenericValidator.isBlankOrNull(prefForm.getNewPassword())) {
      user.setPasswordHash(PasswordDigest.getDigestString(prefForm.getNewPassword()));

      prefForm.setNewPassword(null);
      prefForm.setOldPassword(null);
      prefForm.setRetypeNewPassword(null);
    }

    setValidUntil(request, user);

    return findForward(Constants.FORWARD_SUCCESS);

  }

  protected ActionForward edit() throws Exception {

    WebContext ctx = WebContext.currentContext();
    HttpServletRequest request = ctx.getRequest();

    User user = WebUtils.getUser(request);
    UserConfigForm userConfigForm = (UserConfigForm)ctx.getForm();
    userConfigForm.toForm(user);

    setValidUntil(request, user);

    return findForward(Constants.FORWARD_SUCCESS);

  }

  private void setValidUntil(HttpServletRequest request, User user) throws HibernateException, NumberFormatException {
    if ((user.getLoginToken() != null) && (WebUtils.lookForLoginCookie(request) != null)) {

      UserConfig config = UserConfig.getUserConfig(user);
      Calendar validUntil = Calendar.getInstance();
      validUntil.setTime(user.getLoginTokenTime());
      validUntil.add(Calendar.SECOND, config.getIntegerProperty(UserConfig.LOGIN_REMEMBER_SECONDS).intValue());

      String dateString = DateFormat.getDateInstance(DateFormat.FULL, user.getLocale()).format(validUntil.getTime());
      dateString += " " + DateFormat.getTimeInstance(DateFormat.FULL, user.getLocale()).format(validUntil.getTime());
      request.getSession().setAttribute("rememberMeValidUntil", dateString);
    } else {
      request.getSession().removeAttribute("rememberMeValidUntil");
    }
  }

  protected ActionForward add() throws Exception {
    return null;
  }

  protected ActionForward delete() throws Exception {
    return null;
  }

}
