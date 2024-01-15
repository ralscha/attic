package ch.ess.common.web.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.tagext.TagSupport;

import ch.ess.common.web.UserSession;

/** 
 * @author  Ralph Schaer
 * @version $Revision: 1.2 $ $Date: 2004/05/22 16:51:13 $
 *  
 * @jsp.tag name="hasPermission" body-content="JSP"
 */
public class HasPermissionTag extends TagSupport {
  private String permission;
  private boolean inverse;

  public HasPermissionTag() {
    init();
  }

  public int doStartTag() {
    HttpServletRequest request = ((HttpServletRequest) pageContext.getRequest());
    UserSession theUser = (UserSession) request.getSession().getAttribute(ch.ess.common.Constants.USER_SESSION);

    boolean result = theUser.hasPermission(permission);

    if ((!inverse) == result) {
      return EVAL_BODY_INCLUDE;
    }

    return SKIP_BODY;
  }

  /**
   * @jsp.attribute required="true"
   */
  public void setPermission(String str) {
    permission = str;
  }

  /**
   * @jsp.attribute required="false"
   */
  public void setInverse(boolean flag) {
    inverse = flag;
  }

  public void release() {
    super.release();
    init();
  }

  private void init() {
    permission = null;
    inverse = false;
  }

}