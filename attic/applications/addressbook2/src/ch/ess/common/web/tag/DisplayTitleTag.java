package ch.ess.common.web.tag;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;

import org.displaytag.tags.ColumnTag;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.1 $ $Date: 2003/12/30 11:10:29 $
  *  
  * @jsp.tag name="title" body-content="JSP"
  */
public class DisplayTitleTag extends BodyTagSupport {

  public int doAfterBody() throws JspTagException {
    Tag parentTag = getParent();
    if (parentTag instanceof ColumnTag) {
      ColumnTag columnTag = (ColumnTag)parentTag;
      columnTag.setTitle(getBodyContent().getString());
    } else {
      throw new JspTagException("Use title tag inside column tag.");
    }
    return SKIP_BODY;
  }
}
