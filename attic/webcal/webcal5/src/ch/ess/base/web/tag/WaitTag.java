package ch.ess.base.web.tag;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.TagUtils;

public class WaitTag extends AbstractTemplateTag {

  private String top, left;

  public WaitTag() {
    init();
  }


  public String getLeft() {
    return left;
  }


  public String getTop() {
    return top;
  }

  public void setLeft(final String left) {
    this.left = left;
  }

  public void setTop(final String top) {
    this.top = top;
  }


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

    Map<String,String> context = new HashMap<String,String>();
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

    merge("waitTag.ftl", context);

    return SKIP_BODY;
  }


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
