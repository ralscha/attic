
package ch.ess.tag.table;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;


public class ParamTag extends TagSupport {

  protected String name;
  protected String value;

  public ParamTag() {
    init();
  }

  public void setName(String val) {
    name = val;
  }

  public String getName() {
    return name;
  }

  public void setValue(String val) {
    value = val;
  }

  public String getValue() {
    return value;
  }

  public int doStartTag() throws JspException {

    TableTag tableTag = (TableTag)findAncestorWithClass(this, TableTag.class);

    if (tableTag == null) {
      throw new JspTagException("not nested within a TableTag");
    }

    tableTag.addParam(name, value);

    return SKIP_BODY;
  }

  public void release() {
    super.release();
    init();
  }
  
  private void init() {
    name = null;
    value = null;  
  }
}
