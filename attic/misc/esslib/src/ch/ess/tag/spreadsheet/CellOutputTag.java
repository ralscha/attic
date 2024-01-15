package ch.ess.tag.spreadsheet;

import java.io.*;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

import ch.ess.util.spreadsheet.*;


//<input type="text" name="t" size="8" maxlength="8" onFocus="namedCells.t.fcs()" onBlur="namedCells.t.blr()"  style="text-align: right;">

public class CellOutputTag extends TagSupport {

  private String name = null;
  private String accesskey = null;
  private String tabindex = null;
  private String maxlength = null;
  private String size = null;
  private String style = null;
  private String styleClass = null;
  private String styleId = null;

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAccesskey() {
    return this.accesskey;
  }

  public void setAccesskey(String accesskey) {
    this.accesskey = accesskey;
  }

  public String getTabindex() {
    return this.tabindex;
  }

  public void setTabindex(String tabindex) {
    this.tabindex = tabindex;
  }

  public String getMaxlength() {
    return this.maxlength;
  }

  public void setMaxlength(String maxlength) {
    this.maxlength = maxlength;
  }

  public String getSize() {
    return size;
  }

  public void setSize(String size) {
    this.size = size;
  }

  public String getStyle() {
    return style;
  }

  public void setStyle(String style) {
    this.style = style;
  }

  public String getStyleClass() {
    return styleClass;
  }

  public void setStyleClass(String styleClass) {
    this.styleClass = styleClass;
  }

  public String getStyleId() {
    return styleId;
  }

  public void setStyleId(String styleId) {
    this.styleId = styleId;
  }

  public int doStartTag() throws JspException {

    SpreadSheetTag ssTag = (SpreadSheetTag)findAncestorWithClass(this, SpreadSheetTag.class);

    if (ssTag == null) {
      throw new JspException("not nested within a SpreadSheetTag");
    }

    HtmlSpreadsheetTableModel model = ssTag.getModel();
    SimpleSpreadsheetCell cell = (SimpleSpreadsheetCell)model.getNamedCells().get("namedCells." + name);
    StringBuffer results = new StringBuffer("<input type=\"text\" ");

    results.append("name=\"");
    results.append(cell.getFormElementName());
    results.append("\"");

    if (!cell.isEditable()) {
      results.append(" readonly=\"readonly\"");
    }

    if (accesskey != null) {
      results.append(" accesskey=\"");
      results.append(accesskey);
      results.append("\"");
    }

    if (maxlength != null) {
      results.append(" maxlength=\"");
      results.append(maxlength);
      results.append("\"");
    }

    if (size != null) {
      results.append(" size=\"");
      results.append(size);
      results.append("\"");
    }

    if (tabindex != null) {
      results.append(" tabindex=\"");
      results.append(tabindex);
      results.append("\"");
    }

    if (style != null) {
      results.append(" style=\"");
      results.append(style);
      results.append("\"");
    }

    if (styleClass != null) {
      results.append(" class=\"");
      results.append(styleClass);
      results.append("\"");
    }

    if (styleId != null) {
      results.append(" id=\"");
      results.append(styleId);
      results.append("\"");
    }

    results.append(" onfocus=\"");
    results.append(cell.getName());
    results.append(".fcs();\"");
    results.append(" onblur=\"");
    results.append(cell.getName());
    results.append(".blr();\"");
    results.append(">");

    JspWriter writer = pageContext.getOut();

    try {
      writer.print(results.toString());
    } catch (IOException e) {
      throw new JspException(e.toString());
    }

    return SKIP_BODY;
  }

  public void release() {

    super.release();

    name = null;
    accesskey = null;
    tabindex = null;
    maxlength = null;
    size = null;
    style = null;
    styleClass = null;
    styleId = null;
  }
}
