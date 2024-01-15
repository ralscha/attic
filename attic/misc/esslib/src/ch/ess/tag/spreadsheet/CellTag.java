

package ch.ess.tag.spreadsheet;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

import ch.ess.util.spreadsheet.*;



public class CellTag extends TagSupport {

  private int row = -1;
  private int column = -1;
  private String name = null;
  private boolean editable = true;
  private Object value = null;
  private String formula = null;
  private String action = null;
  private String format = null;
  private int precision = -1;

  public int getRow() {
    return this.row;
  }

  public void setRow(int row) {
    this.row = row;
  }

  public int getColumn() {
    return this.column;
  }

  public void setColumn(int column) {
    this.column = column;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean getEditable() {
    return this.editable;
  }

  public void setEditable(boolean editable) {
    this.editable = editable;
  }

  public Object getValue() {
    return this.value;
  }

  public void setValue(Object value) {

    if (value != null) {
      this.value = value;
    } else {
      this.value = "";
    }
  }

  public String getFormula() {
    return this.formula;
  }

  public void setFormula(String formula) {
    this.formula = formula;
  }

  public String getAction() {
    return this.action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public String getFormat() {
    return this.format;
  }

  public void setFormat(String format) {
    this.format = format;
  }

  public int getPrecision() {
    return this.precision;
  }

  public void setPrecision(int precision) {
    this.precision = precision;
  }

  public int doStartTag() throws JspException {

    SpreadSheetTag ssTag = (SpreadSheetTag)findAncestorWithClass(this, SpreadSheetTag.class);

    if (ssTag == null) {
      throw new JspException("not nested within a SpreadSheetTag");
    }

    HtmlSpreadsheetTableModel model = ssTag.getModel();

    if ((precision == -1) && (format == null)) {
      model.putCell(new SimpleHtmlSpreadsheetCell(row, column, name, editable, value, formula, action));
    } else {
      model.putCell(new SimpleHtmlSpreadsheetCell(row, column, name, editable, value, formula, action, format, precision));
    }

    return SKIP_BODY;
  }

  public void release() {

    super.release();

    row = -1;
    column = -1;
    name = null;
    editable = true;
    value = null;
    formula = null;
    action = null;
    format = null;
    precision = -1;
  }
}
