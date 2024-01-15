package ch.ess.common.web.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.displaytag.exception.TagStructureException;
import org.displaytag.tags.TableTag;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.1 $ $Date: 2004/02/14 16:32:52 $
  *  
  * @jsp.tag name="setProperty" body-content="JSP"
  */
public class DisplaySetPropertyTag extends BodyTagSupport {

  private String name;
  private String value;

  public DisplaySetPropertyTag() {
    init();
  }

  public int doEndTag() throws JspException {

    TableTag tableTag = (TableTag)findAncestorWithClass(this, TableTag.class);

    if (tableTag == null) {
      throw new TagStructureException(getClass(), "property", "table");
    }

    if (value == null) {
      tableTag.setProperty(name, getBodyContent().getString());
    } else {
      tableTag.setProperty(name, value);
    }

    return SKIP_BODY;
  }

  public void setName(String string) {
    name = string;
  }

  public void setValue(String string) {
    value = string;
  }

  /**
   * @jsp.attribute required="true"
   */
  public String getName() {
    return name;
  }

  /**
   * @jsp.attribute required="false"
   */
  public String getValue() {
    return value;
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
