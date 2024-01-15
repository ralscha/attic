

package ch.ess.tag.table;

import javax.servlet.jsp.*;



public class IndexTag extends IndexTagSupport {

  public int doStartTag() throws JspException {

    super.doStartTag();

    if (tableTag.getModel().getTotalPages() > 1) {
      return EVAL_BODY_INCLUDE;
    } else {
      return SKIP_BODY;
    }
  }
}
