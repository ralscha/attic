package ch.ess.common.web.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.3 $ $Date: 2003/11/11 19:13:14 $
  *  
  * @jsp.tag name="isUserInRole" body-content="JSP"
  */
public class IsUserInRoleTag extends TagSupport {
  private String role;
  private boolean inverse;

  public IsUserInRoleTag() {
    init();
  }

  public int doStartTag() throws JspException {
    HttpServletRequest request = ((HttpServletRequest)pageContext.getRequest());
    boolean result = request.isUserInRole(role);

    if ((!inverse) == result) {
      return EVAL_BODY_INCLUDE;
    }

    return SKIP_BODY;
  }

  /**
   * @jsp.attribute required="true"
   */
  public void setRole(String str) {
    role = str;
  }

  /**
   * @jsp.attribute required="false"
   */
  public void setInverse(boolean b) {
    inverse = b;
  }

  public void release() {
    super.release();
    init();
  }

  private void init() {
    role = null;
    inverse = false;
  }

}
