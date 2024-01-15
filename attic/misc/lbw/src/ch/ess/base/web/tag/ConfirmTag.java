package ch.ess.base.web.tag;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;

public class ConfirmTag extends AbstractTemplateTag {

  private String key = null;
  private String functionName = "confirmRequest";

  public ConfirmTag() {
    init();
  }


  public String getKey() {
    return (this.key);
  }

  public void setKey(final String key) {
    this.key = key;
  }


  public String getFunctionName() {
    return functionName;
  }

  public void setFunctionName(final String methodName) {
    this.functionName = methodName;
  }



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

    Map<String, String> context = new HashMap<String, String>();
    context.put("functionName", functionName);
    context.put("part1", part1);
    context.put("part2", part2);

    merge("confirmTag.ftl", context);

    return SKIP_BODY;
  }


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