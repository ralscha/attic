package ch.ess.blank.web.userconfig;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForward;

import ch.ess.blank.db.User;
import ch.ess.blank.web.WebUtils;
import ch.ess.common.Constants;
import ch.ess.common.web.HibernateAction;
import ch.ess.common.web.WebContext;

/** 
 * @author  Ralph Schaer
 * @version $Revision: 1.4 $ $Date: 2004/05/22 15:38:03 $
 *  
 * @struts.action path="/deleteCookie" name="nullForm" validate="false"
 * @struts.action-forward name="success" path="/editUserConfig.do"
 */
public class DeleteCookieAction extends HibernateAction {

  public ActionForward doAction() throws Exception {

    WebContext ctx = WebContext.currentContext();
    HttpServletRequest request = ctx.getRequest();

    Cookie loginCookie = WebUtils.lookForLoginCookie(request);
    if (loginCookie != null) {
      User user = WebUtils.getUser(request);

      user.setLoginToken(null);
      user.setLoginTokenTime(null);
      loginCookie.setMaxAge(0);
      ctx.getResponse().addCookie(loginCookie);
    }

    return findForward(Constants.FORWARD_SUCCESS);

  }
}