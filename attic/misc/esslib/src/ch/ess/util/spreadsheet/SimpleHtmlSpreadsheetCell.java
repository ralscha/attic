/*
 * $Header: /home/cvsroot/pbroker/src/ch/ess/util/spreadsheet/SimpleHtmlSpreadsheetCell.java,v 1.1.1.1 2002/02/26 06:46:56 sr Exp $
 * $Revision: 1.1.1.1 $
 * $Date: 2002/02/26 06:46:56 $
 */

package ch.ess.util.spreadsheet;

import java.awt.*;
import java.math.*;
import java.text.*;
import java.util.*;


/**
 * Insert the type's description here.
 * Creation date: (18.05.01 16:06:53)
 * @author: Daniel Ramseier
 */
public class SimpleHtmlSpreadsheetCell extends SimpleSpreadsheetCell implements HtmlSpreadsheetCell {

  protected DateFormat dateParseFormat = null;
  protected DecimalFormat numberParseFormat = null;
  protected Color bGColor = null;

  /**
   * SimpleHtmlSpreadsheetCell constructor comment.
   * @param spreadsheet ch.ess.util.spreadsheet.SpreadsheetModel
   * @param row int
   * @param column int
   * @param name java.lang.String
   * @param editable boolean
   * @param value java.lang.Object
   * @param formula java.lang.String
   * @param action java.lang.String
   */
  public SimpleHtmlSpreadsheetCell(int row, int column, String name, boolean editable,
                                   Object value, String formula, String action) {
    super(row, column, name, editable, value, formula, action);
  }

  /**
   * SimpleHtmlSpreadsheetCell constructor comment.
   * @param spreadsheet ch.ess.util.spreadsheet.SpreadsheetModel
   * @param row int
   * @param column int
   * @param name java.lang.String
   * @param editable boolean
   * @param value java.lang.Object
   * @param formula java.lang.String
   * @param action java.lang.String
   * @param format java.lang.String
   * @param precision java.lang.String
   */
  public SimpleHtmlSpreadsheetCell(int row, int column, String name, boolean editable,
                                   Object value, String formula, String action, String format, int precision) {
    super(row, column, name, editable, value, formula, action, format, precision);
  }

  /**
   * Insert the method's description here.
   * Creation date: (22.05.01 16:11:08)
   * @return java.awt.Color
   */
  public java.awt.Color getBGColor() {

    Color c = bGColor;

    if (c == null) {
      c = ((HtmlSpreadsheetTableModel)spreadsheetModel).getBGColor(row, column);
    }

    return c;
  }

  /**
   * Insert the method's description here.
   * Creation date: (21.05.01 22:27:48)
   * @return java.text.DateFormat
   */
  public java.text.DateFormat getDateParseFormat() {
    return dateParseFormat;
  }

  /**
   * Insert the method's description here.
   * Creation date: (13.05.01 00:23:14)
   * @return java.lang.String
   */
  public String getFormHtml() {

    String stC = getStyleConst();

    stC = (stC == null) ? "" : " style=\"" + stC + "\"";

    Color bgColor = getBGColor();
    String bgc = null;

    if (bgColor != null) {
      bgc = HtmlSpreadsheetTableModel.htmlColor(bgColor);
    }

    String tdbgc = (bgc == null) ? "" : " bgcolor=\"" + bgc + "\"";
    String h;

    hasAction();    // parse the action script.

    if (isEditable()) {
      String stI = getStyleInput();

      stI = (stI == null) ? "" : " style=\"" + stI + "\"";
      h = "\t\t<td" + tdbgc + stC + ">" + "<input type=\"text\" " + "name=\"" + getFormElementName() + "\" "
          + "size=\"8\" maxlength=\"8\" " + "onFocus=\"" + getName() + ".fcs()\" " + "onBlur=\"" + getName() + ".blr()\" " + stI
          + ">" + "</td>\n";
    } else {
      if (hasFormula()) {
        String stO = getStyleOutput();

        stO = " style=\"background-color: " + bgc + ";" + ((stO == null) ? "" : stO) + "\"";
        h = "\t\t<td" + tdbgc + stC + ">" + "<input type=\"text\" " + "name=\"" + getFormElementName() + "\" "
            + "size=\"8\" maxlength=\"8\" " + "onBlur=\"" + getName() + ".show()\" " + stO + ">" + "</td>\n";
        
      } else {
        h = "\t\t<td" + tdbgc + stC + ">" + value.toString() + "</td>\n";
      }
    }

    return h;
  }

  /**
   * Insert the method's description here.
   * Creation date: (21.05.01 22:28:33)
   * @return java.text.DecimalFormat
   */
  public java.text.DecimalFormat getNumberParseFormat() {
    return numberParseFormat;
  }

  /**
   * Insert the method's description here.
   * Creation date: (13.05.01 00:23:14)
   * @return java.lang.String
   */
  public String getScriptHtml() {

    String nam = "null";
    String val = "null";
    String formu = "null";
    String actio = "null";
    String h;

    if (name == null) {
      nam = "null";
    } else {
      nam = "\"" + name + "\"";
    }

    if (hasFormula()) {
      formu = formula.getJs();
    } else {
      val = "\"" + value.toString() + "\"";
    }

    if (hasAction()) {
      actio = action.getJs();
    }

    h = "new SpreadsheetCell(" + spreadsheetModel.getName() + "," + row + "," + column + "," + nam + "," + val + "," + formu
        + "," + actio + "," + "\"" + format + "\"," + precision + ");";

    return h;
  }

  /**
   * Insert the method's description here.
   * Creation date: (22.05.01 08:49:56)
   * @return java.lang.String
   */
  public java.lang.String getStyleConst() {
    return ((HtmlSpreadsheetTableModel)spreadsheetModel).getStyleConst(row, column);
  }

  /**
   * Insert the method's description here.
   * Creation date: (22.05.01 08:49:56)
   * @return java.lang.String
   */
  public java.lang.String getStyleInput() {
    return ((HtmlSpreadsheetTableModel)spreadsheetModel).getStyleInput(row, column);
  }

  /**
   * Insert the method's description here.
   * Creation date: (22.05.01 08:49:56)
   * @return java.lang.String
   */
  public java.lang.String getStyleOutput() {
    return ((HtmlSpreadsheetTableModel)spreadsheetModel).getStyleOutput(row, column);
  }

  /**
   * Insert the method's description here.
   * Creation date: (18.05.01 16:42:39)
   * @param request javax.servlet.http.HttpServletRequest
   */
  public void scanRequest(javax.servlet.http.HttpServletRequest request) throws java.text.ParseException {

    if (editable) {
      String param = request.getParameter(getFormElementName());

      if (param != null) {
        Object v = null;

        if (type == String.class) {
          v = param;
        } else if (type == Integer.class) {
          v = new Integer(Integer.parseInt(param));
        } else if (type == Float.class) {
          v = new Float(numberParseFormat.parse(param).floatValue());
        } else if (type == BigDecimal.class) {
          v = new BigDecimal(param);
        } else if (type == Float.class) {
          v = new Double(numberParseFormat.parse(param).doubleValue());
        } else if (type == Date.class) {
          v = dateParseFormat.parse(param);
        } else if (type == Boolean.class) {
          v = Boolean.valueOf(param);
        } else {

          // unsupported type
        }

        value = v;
      }
    }
  }

  /**
   * Insert the method's description here.
   * Creation date: (22.05.01 16:11:08)
   * @param color java.awt.Color
   */
  public void setBGColor(java.awt.Color color) {
    bGColor = color;
  }

  /**
   * Insert the method's description here.
   * Creation date: (21.05.01 22:27:48)
   * @param newDateParseFormat java.text.DateFormat
   */
  public void setDateParseFormat(java.text.DateFormat newDateParseFormat) {
    dateParseFormat = newDateParseFormat;
  }

  /**
   * Insert the method's description here.
   * Creation date: (21.05.01 22:28:33)
   * @param newNumberParseFormat java.text.DecimalFormat
   */
  public void setNumberParseFormat(java.text.DecimalFormat newNumberParseFormat) {
    numberParseFormat = newNumberParseFormat;
  }
}
