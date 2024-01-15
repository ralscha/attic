package ch.ess.addressbook.web.userconfig;

import java.text.DateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Transaction;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ch.ess.addressbook.db.User;
import ch.ess.addressbook.resource.UserConfig;
import ch.ess.addressbook.web.RememberMeUtil;
import ch.ess.addressbook.web.WebUtils;
import ch.ess.common.Constants;
import ch.ess.common.util.PasswordDigest;
import ch.ess.common.web.DispatchAction;

/** 
  * @author  Ralph Schaer
  * @version 1.0, 13.07.2003 
  * 
  * @struts.action path="/storeUserConfig" name="userConfigForm" input=".userconfig.edit" scope="session" validate="true" parameter="store"
  * @struts.action path="/editUserConfig" name="userConfigForm" input=".userconfig.edit" scope="session" validate="false" parameter="edit"
  * @struts.action-forward name="success" path=".userconfig.edit"
  */
public class UserConfigEditAction extends DispatchAction {

  protected ActionForward store(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response,
    Transaction tx)
    throws Exception {

    UserConfigForm prefForm = (UserConfigForm)form;

    User user = WebUtils.getUser(request);
    prefForm.fromForm(user);

    request.getSession().setAttribute(Globals.LOCALE_KEY, user.getLocale());

    addOneMessage(request, Constants.ACTION_MESSAGE_UPDATE_OK);

    if (!GenericValidator.isBlankOrNull(prefForm.getNewPassword())) {
      user.setPasswordHash(PasswordDigest.getDigestString(prefForm.getNewPassword()));
    }

    setValidUntil(request, user);
    
    //no of rows    
    request.getSession().setAttribute("static_noRows", String.valueOf(prefForm.getNoRows()));

    return mapping.findForward(Constants.FORWARD_SUCCESS);

  }

  protected ActionForward edit(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response)
    throws Exception {

    User user = WebUtils.getUser(request);
    UserConfigForm userConfigForm = (UserConfigForm)form;
    userConfigForm.toForm(getResources(request), user);

    setValidUntil(request, user);

    return mapping.findForward(Constants.FORWARD_SUCCESS);

  }

  private void setValidUntil(HttpServletRequest request, User user) throws HibernateException, NumberFormatException {
    if ((user.getLoginToken() != null) && (RememberMeUtil.lookForLoginCookie(request) != null)) {

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

  protected ActionForward add(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response)
    throws Exception {

    return null;
  }

  protected ActionForward delete(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response)
    throws Exception {
    return null;
  }

}
