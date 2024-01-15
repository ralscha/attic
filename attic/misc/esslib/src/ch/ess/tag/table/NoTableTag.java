

package ch.ess.tag.table;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import javax.swing.table.*;

public class NoTableTag extends TagSupport {

  protected String name;
  protected String scope;

  public NoTableTag() {
    init();
  }
 
  //Name property
  public void setName(String value) {
    name = value;
  }

  public String getName() {
    return name;
  }

  //Scope property
  public void setScope(String value) {
    scope = value;
  }

  public String getScope() {
    return scope;
  }

  public int doStartTag() throws JspException {

    Object model = TableTag.lookup(pageContext, name, scope);
    boolean empty = true;

    if (model != null) {
      if (model instanceof TableModel) {
        empty = ((TableModel)model).getRowCount() <= 0;
      } else if (model instanceof JSPTableModel) {
        empty = ((JSPTableModel)model).getTotalRowCount() <= 0;
      }
    }

    if (empty) {
      return EVAL_BODY_INCLUDE;
    } else {
      return SKIP_BODY;
    }
  }

  public int doEndTag() throws JspException {
    return EVAL_PAGE;
  }

  public void release() {
    super.release();
    init();
  }
  
  private void init() {
    name = null;
    scope = null;  
  }
}
