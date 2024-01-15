
package ch.ess.tag.table;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;



public class IndexTagSupport extends TagSupport {

  protected TableTag tableTag;
  protected boolean showAlways;

  public IndexTagSupport() {
    init();
  }

  public void setShowAlways(boolean b) {
    showAlways = b;
  }

  public boolean getShowAlways() {
    return showAlways;
  }

  public int doStartTag() throws JspException {

    tableTag = (TableTag)findAncestorWithClass(this, TableTag.class);

    if (tableTag == null) {
      throw new JspTagException("not nested within a TableTag");
    }

    return EVAL_BODY_INCLUDE;
  }

  public void release() {
    super.release();
    init();
  }
  
  private void init() {
    tableTag = null;
    showAlways = true;  
  }
}
