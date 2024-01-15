
package ch.ess.util.spreadsheet;

import java.awt.*;
import java.text.*;
import java.util.*;

import javax.servlet.http.*;

import ch.ess.util.*;



public class HtmlSpreadsheetTableModel extends SpreadsheetTableModel {

  protected static final DateFormat defaultDateParseFormat = new SimpleDateFormat2k("dd.MM.yy");

  static {
    defaultDateParseFormat.setLenient(false);
  }

  protected static final DecimalFormat defaultNumberParseFormat = new DecimalFormat("#,##0.0E0");
  protected DateFormat dateParseFormat = defaultDateParseFormat;
  protected DecimalFormat numberParseFormat = defaultNumberParseFormat;
  protected java.awt.Color[] bGColorsRow = null;
  protected java.awt.Color[] bGColorsColumn = null;
  protected String styleConst = null;
  protected String styleInput = null;
  protected String styleOutput = null;

  /**
   * HtmlSpreadsheetModel constructor comment.
   * @param name java.lang.String
   */
  public HtmlSpreadsheetTableModel(String name) {
    super(name);
  }

  /**
   * Insert the method's description here.
   * Creation date: (22.05.01 09:24:30)
   * @return byte[]
   * @param row int
   * @param column int
   */
  public java.awt.Color getBGColor(int row, int column) {

    Color rc = getBGColorRow(row);
    Color cc = getBGColorColumn(column);
    Color c = null;

    if (rc != null) {
      if (cc != null) {
        int rcr = rc.getRed();
        int rcg = rc.getGreen();
        int rcb = rc.getBlue();
        int ccr = cc.getRed();
        int ccg = cc.getGreen();
        int ccb = cc.getBlue();
        int r = rcr + (256 - rcr) * ccr / 256;
        int g = rcg + (256 - rcg) * ccg / 256;
        int b = rcb + (256 - rcb) * ccb / 256;

        c = new Color(r, g, b);
      } else {
        c = rc;
      }
    } else {
      if (cc != null) {
        c = cc;
      }
    }

    return c;
  }

  /**
   * Insert the method's description here.
   * Creation date: (22.05.01 09:24:30)
   * @return byte[]
   * @param column int
   */
  public java.awt.Color getBGColorColumn(int column) {

    Color c = null;

    if (bGColorsColumn != null) {
      c = bGColorsColumn[column % bGColorsColumn.length];
    }

    return c;
  }

  /**
   * Insert the method's description here.
   * Creation date: (22.05.01 09:24:30)
   * @return byte[]
   * @param row int
   */
  public java.awt.Color getBGColorRow(int row) {

    Color c = null;

    if (bGColorsRow != null) {
      c = bGColorsRow[row % bGColorsRow.length];
    }

    return c;
  }

  /**
   * Insert the method's description here.
   * Creation date: (22.05.01 15:28:13)
   * @return java.awt.Color[]
   */
  public java.awt.Color[] getBGColorsColumn() {
    return bGColorsColumn;
  }

  /**
   * Insert the method's description here.
   * Creation date: (22.05.01 15:27:44)
   * @return java.awt.Color[]
   */
  public java.awt.Color[] getBGColorsRow() {
    return bGColorsRow;
  }

  /**
   * Insert the method's description here.
   * Creation date: (21.05.01 22:07:13)
   * @return java.text.DateFormat
   */
  public java.text.DateFormat getDateParseFormat() {
    return dateParseFormat;
  }

  /**
   * Insert the method's description here.
   * Creation date: (13.05.01 01:07:40)
   * @return java.lang.String
   */
  public java.lang.String getFormHtml() {

    String h = "";

    h += "<table border=\"0\"><tr><td bgcolor=\"Black\">\n";
    h += "<table cellspacing=\"1\" cellpadding=\"1\" border=\"0\" bgcolor=\"Silver\" bordercolor=\"Black\">\n";

    for (int r = 0; r < getRowCount(); r++) {
      h += "\t<tr>\n";

      for (int c = 0; c < getColumnCount(); c++) {
        HtmlSpreadsheetCell cell = (HtmlSpreadsheetCell)getCellAt(r, c);

        if (cell != null) {
          h += cell.getFormHtml();
        } else {
          Color bgc = getBGColor(r, c);
          String tdbgc = (bgc == null) ? "" : " bgcolor=\"" + htmlColor(bgc) + "\"";

          h += "\t\t<td" + tdbgc + ">&nbsp</td>\n";
        }
      }

      h += "\t</tr>\n";
    }

    h += "</table>\n";
    h += "</td></tr></table>\n";

    return h;
  }

  /**
   * Insert the method's description here.
   * Creation date: (21.05.01 22:10:15)
   * @return java.text.DecimalFormat
   */
  public java.text.DecimalFormat getNumberParseFormat() {
    return numberParseFormat;
  }

  /**
   * Insert the method's description here.
   * Creation date: (13.05.01 01:07:40)
   * @return java.lang.String
   */
  public java.lang.String getScriptHtml() {

    String h = "";

    h += "\tvar " + name + " = new Spreadsheet(" + rowCount + "," + columnCount + ",\"" + name + "\");\n";

    Enumeration cc = cells.elements();

    while (cc.hasMoreElements()) {
      HtmlSpreadsheetCell cell = (HtmlSpreadsheetCell)cc.nextElement();

      h += "\t" + cell.getScriptHtml() + "\n";
    }

    h += "\t" + name + ".clearReady();\n";
    h += "\t" + name + ".show();\n";

    return h;
  }

  /**
   * Insert the method's description here.
   * Creation date: (22.05.01 08:49:44)
   * @return java.lang.String
   * @param row int
   * @param column int
   */
  public java.lang.String getStyleConst(int row, int column) {
    return styleConst;
  }

  /**
   * Insert the method's description here.
   * Creation date: (22.05.01 08:49:44)
   * @return java.lang.String
   * @param row int
   * @param column int
   */
  public java.lang.String getStyleInput(int row, int column) {
    return styleInput;
  }

  /**
   * Insert the method's description here.
   * Creation date: (22.05.01 08:49:44)
   * @return java.lang.String
   * @param row int
   * @param column int
   */
  public java.lang.String getStyleOutput(int row, int column) {
    return styleOutput;
  }

  protected static String hexDigit(byte x) {

    StringBuffer sb = new StringBuffer();
    char c;

    // First nibble
    c = (char)((x >> 4) & 0xf);

    if (c > 9) {
      c = (char)((c - 10) + 'a');
    } else {
      c = (char)(c + '0');
    }

    sb.append(c);

    // Second nibble
    c = (char)(x & 0xf);

    if (c > 9) {
      c = (char)((c - 10) + 'a');
    } else {
      c = (char)(c + '0');
    }

    sb.append(c);

    return sb.toString();
  }

  protected static String htmlColor(Color color) {

    String c = null;

    if (color != null) {
      c = "#" + hexDigit((byte)color.getRed()) + hexDigit((byte)color.getGreen()) + hexDigit((byte)color.getBlue());
    }

    return c;
  }

  /**
   * Insert the method's description here.
   * Creation date: (18.05.01 16:31:18)
   * @param request javax.servlet.http.HttpServletRequest
   */
  public void scanRequest(HttpServletRequest request) throws java.text.ParseException {

    Enumeration cs = cells.elements();

    while (cs.hasMoreElements()) {
      HtmlSpreadsheetCell cell = (HtmlSpreadsheetCell)cs.nextElement();

      cell.scanRequest(request);
    }
  }

  /**
   * Insert the method's description here.
   * Creation date: (22.05.01 15:28:13)
   * @param newBGColorsColumn java.awt.Color[]
   */
  public void setBGColorsColumn(java.awt.Color[] newBGColorsColumn) {
    bGColorsColumn = newBGColorsColumn;
  }

  /**
   * Insert the method's description here.
   * Creation date: (22.05.01 15:27:44)
   * @param newBGColorsRow java.awt.Color[]
   */
  public void setBGColorsRow(java.awt.Color[] newBGColorsRow) {
    bGColorsRow = newBGColorsRow;
  }

  /**
   * Insert the method's description here.
   * Creation date: (21.05.01 22:07:13)
   * @param newDateParseFormat java.text.DateFormat
   */
  public void setDateParseFormat(java.text.DateFormat newDateParseFormat) {
    dateParseFormat = newDateParseFormat;
  }

  /**
   * Insert the method's description here.
   * Creation date: (21.05.01 22:10:15)
   * @param newNumberParseFormat java.text.DecimalFormat
   */
  public void setNumberParseFormat(java.text.DecimalFormat newNumberParseFormat) {
    numberParseFormat = newNumberParseFormat;
  }

  /**
   * Insert the method's description here.
   * Creation date: (22.05.01 15:28:13)
   * @param style String
   */
  public void setStyleConst(String style) {
    styleConst = style;
  }

  /**
   * Insert the method's description here.
   * Creation date: (22.05.01 15:28:13)
   * @param style String
   */
  public void setStyleInput(String style) {
    styleInput = style;
  }

  /**
   * Insert the method's description here.
   * Creation date: (22.05.01 15:28:13)
   * @param style String
   */
  public void setStyleOutput(String style) {
    styleOutput = style;
  }
}
