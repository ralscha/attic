package ch.ess.tag.misc;


import org.apache.commons.lang.*;

import ch.ess.util.*;

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

  public String getAppendToEnd() {
    return this.appendToEnd;
  }

  public void setAppendToEnd(String s) {
    this.appendToEnd = s;
  }

  public String changeString(String text) {

    int l = NumberUtils.stringToInt(lower);
    int u = NumberUtils.stringToInt(upper);

    String txt = Util.truncateNicely(text, l, u, this.appendToEnd, true);
    
    if ((tooltip) && (!text.trim().equals("")) && (txt.endsWith("..."))) {
      txt = "<div title=\""+ text +"\">" + txt + "</div>";
    }
    
    return txt;
    
  }

  public void initAttributes() {

    this.lower = "10";
    this.upper = "-1";
    this.appendToEnd = "...";
    this.tooltip = true;

  }

  public boolean getTooltip() {
    return tooltip;
  }

  public void setTooltip(boolean tooltip) {
    this.tooltip = tooltip;
  }

}
