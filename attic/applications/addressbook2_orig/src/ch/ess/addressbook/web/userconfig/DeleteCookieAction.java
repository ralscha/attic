package ch.ess.addressbook.web.userconfig;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ch.ess.addressbook.db.User;
import ch.ess.addressbook.web.RememberMeUtil;
import ch.ess.addressbook.web.WebUtils;
import ch.ess.common.Constants;
import ch.ess.common.web.HibernateAction;

/** 
  * @author  Ralph Schaer
  * @version 1.0, 13.07.2003 
  * 
  * @struts.action path="/deleteCookie" name="nullForm" validate="false"
  * @struts.action-forward name="success" path="/editUserConfig.do"
  */
public class DeleteCookieAction extends HibernateAction {

  public ActionForward doAction(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response)
    throws Exception {

    Cookie loginCookie = RememberMeUtil.lookForLoginCookie(request);
    if (loginCookie != null) {
      User user = WebUtils.getUser(request);

       user.setLoginToken(null);
       user.setLoginTokenTime(null);
       loginCookie.setMaxAge(0);
       response.addCookie(loginCookie);       
      }

    return mapping.findForward(Constants.FORWARD_SUCCESS);


}
}
