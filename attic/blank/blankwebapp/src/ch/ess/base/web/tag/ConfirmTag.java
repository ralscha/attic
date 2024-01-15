package ch.ess.base.web.tag;

import javax.servlet.jsp.JspException;

import org.apache.velocity.VelocityContext;


/**
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2005/07/03 04:48:21 $
 * 
 * @jsp.tag name="confirm" body-content="empty"
 */
public class ConfirmTag extends AbstractVelocityTag {

  private String key = null;
  private String functionName = "confirmRequest";

  public ConfirmTag() {
    init();
  }

  /**
   * @jsp.attribute required="true" rtexprvalue="true"   
   */
  public String getKey() {
    return (this.key);
  }

  public void setKey(final String key) {
    this.key = key;
  }

  /**
   * @jsp.attribute required="false" rtexprvalue="true"   
   */
  public String getFunctionName() {
    return functionName;
  }

  public void setFunctionName(final String methodName) {
    this.functionName = methodName;
  }

  // --------------------------------------------------------- Public Methods

  /**
   * Process the confirm tag.
   * 
   * @exception JspException
   *              if a JSP exception has occurred
   */
  @Override
  public int doStartTag() throws JspException {

    String part1 = key;
    String part2 = null;

    if (key != null) {
      part1 = getMessage(key);
    }

    int partIndex = -1;
    if (part1 != null) {
      partIndex = part1.indexOf("{0}");
    }

    if (partIndex != -1) {

      String tmp = part1;
      part1 = tmp.substring(0, partIndex);
      part2 = tmp.substring(partIndex + 3);
    }

    VelocityContext context = new VelocityContext();
    context.put("functionName", functionName);
    context.put("part1", part1);
    context.put("part2", part2);

    merge("/ch/ess/base/web/tag/confirmTag.vm", context);

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
    key = null;
    functionName = "confirmRequest";
  }
}