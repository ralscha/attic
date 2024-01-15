package ch.ess.base.web.tag;

import java.net.MalformedURLException;

import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.TagUtils;
import org.apache.velocity.VelocityContext;



/**
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2005/07/03 04:48:21 $ 
 * 
 * @jsp.tag name="wait" body-content="empty"
 */
public class WaitTag extends AbstractVelocityTag {

  private String top, left;

  public WaitTag() {
    init();
  }

  /**
   * @jsp.attribute required="false" rtexprvalue="true"   
   */
  public String getLeft() {
    return left;
  }

  /**
   * @jsp.attribute required="false" rtexprvalue="true"   
   */
  public String getTop() {
    return top;
  }

  public void setLeft(final String left) {
    this.left = left;
  }

  public void setTop(final String top) {
    this.top = top;
  }

  /**
   * Process the wait tag.
   * 
   * @exception JspException
   *              if a JSP exception has occurred
   */
  @Override
  public int doStartTag() throws JspException {

    String imageurl = null;
    try {
      imageurl = TagUtils.getInstance().computeURL(pageContext, null, null, "/images/wait.gif", null, null, null, null,
          false);
    } catch (MalformedURLException e) {
      TagUtils.getInstance().saveException(pageContext, e);
      throw new JspException(e);
    }

    String waittext = getMessage("common.pleaseWait");

    VelocityContext context = new VelocityContext();
    context.put("waittext", waittext);
    context.put("image", imageurl);
    if (top != null) {
      context.put("top", top);
    } else {
      context.put("top", "200");
    }

    if (left != null) {
      context.put("left", left);
    } else {
      context.put("left", "200");
    }

    merge("/ch/ess/base/web/tag/waitTag.vm", context);

    return SKIP_BODY;
  }

  /**
   * Release all allocated resources.
   */
  @Override
  public void release() {
    super.release();
    init();
  }

  private void init() {
    top = null;
    left = null;
  }

}