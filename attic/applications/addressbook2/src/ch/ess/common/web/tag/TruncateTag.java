package ch.ess.common.web.tag;

import org.apache.commons.lang.math.NumberUtils;

import ch.ess.common.util.Util;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.1 $ $Date: 2003/12/30 11:10:29 $
  * 
  * @jsp.tag name="truncate" body-content="JSP" 
  */
public class TruncateTag extends StringTagSupport {

  private String lower;
  private String upper;
  private String appendToEnd;
  private boolean tooltip;

  public TruncateTag() {
    initAttributes();
  }

  /**
   * Get the lower property
   * @return String lower property
   * @jsp.attribute required="false" rtexprvalue="true"   
   */
  public String getLower() {
    return this.lower;
  }

  /**
   * Set the upper property
   * @param lower String property
   */
  public void setLower(String l) {
    this.lower = l;
  }

  /**
   * Get the upper property
   * @return String upper property
   * @jsp.attribute required="false" rtexprvalue="true" 
   */
  public String getUpper() {
    return this.upper;
  }

  /**
   * Set the upper property
   * @param upper String property
   */
  public void setUpper(String u) {
    this.upper = u;
  }

  /**   
   * @jsp.attribute required="false" rtexprvalue="true" 
   */
  public String getAppendToEnd() {
    return this.appendToEnd;
  }

  /**
   * @jsp.attribute required="false" rtexprvalue="true" 
   */
  public boolean getTooltip() {
    return tooltip;
  }

  public void setTooltip(boolean tooltip) {
    this.tooltip = tooltip;
  }

  public void setAppendToEnd(String s) {
    this.appendToEnd = s;
  }

  public String changeString(String text) {

    int l = NumberUtils.toInt(lower);
    int u = NumberUtils.toInt(upper);

    String txt = Util.truncateNicely(text, l, u, this.appendToEnd, true);

    if ((tooltip) && (!text.trim().equals("")) && (txt.endsWith("..."))) {
      txt = "<div title=\"" + text + "\">" + txt + "</div>";
    }

    return txt;

  }

  public void initAttributes() {

    this.lower = "10";
    this.upper = "-1";
    this.appendToEnd = "...";
    this.tooltip = true;

  }

}
