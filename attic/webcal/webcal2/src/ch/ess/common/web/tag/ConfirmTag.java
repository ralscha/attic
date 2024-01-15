package ch.ess.common.web.tag;

import javax.servlet.jsp.JspException;

import org.apache.velocity.VelocityContext;

/**
  * @author  Ralph Schaer
  * @version $Revision: 1.1 $ $Date: 2004/02/14 16:32:52 $
  * 
  * @jsp.tag name="confirm" body-content="empty"
  */
public class ConfirmTag extends VelocityTag {

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

  public void setKey(String key) {
    this.key = key;
  }

  /**
   * @jsp.attribute required="false" rtexprvalue="true"   
   */
  public String getFunctionName() {
    return functionName;
  }

  public void setFunctionName(String methodName) {
    this.functionName = methodName;
  }

  // --------------------------------------------------------- Public Methods

  /**
   * Process the confirm tag.
   * 
   * @exception JspException if a JSP exception has occurred
   */
  public int doStartTag() throws JspException {


    String part1 = key;
    String part2 = null;

    if (key != null) {
      part1 = getMessage(key);
    }

    int p = part1.indexOf("{0}");

    if (p != -1) {

      String tmp = part1;
      part1 = tmp.substring(0, p);
      part2 = tmp.substring(p + 3);
    }

    VelocityContext context = new VelocityContext();
    context.put("functionName", functionName);
    context.put("part1", part1);
    context.put("part2", part2);
    
    merge("/ch/ess/common/web/tag/confirmTag.vm", context);

    return SKIP_BODY;
  }

  /**
   * Release all allocated resources.
   */
  public void release() {
    super.release();
    init();
  }

  private void init() {
    key = null;
    functionName = "confirmRequest";
  }
}