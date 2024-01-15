/*
 * $Header: /home/cvsroot/pbroker/src/ch/ess/util/spreadsheet/HtmlSpreadsheetCell.java,v 1.1.1.1 2002/02/26 06:46:55 sr Exp $
 * $Revision: 1.1.1.1 $
 * $Date: 2002/02/26 06:46:55 $
 */

package ch.ess.util.spreadsheet;

import java.awt.*;


/**
 * Insert the type's description here.
 * Creation date: (18.05.01 11:53:53)
 * @author: Daniel Ramseier
 */
public interface HtmlSpreadsheetCell extends SpreadsheetCell {

  /**
   * Insert the method's description here.
   * Creation date: (22.05.01 16:09:15)
   * @return java.awt.Color
   */
  Color getBGColor();

  /**
   * Insert the method's description here.
   * Creation date: (13.05.01 00:24:49)
   * @return java.lang.String
   */
  String getFormHtml();

  /**
   * Insert the method's description here.
   * Creation date: (13.05.01 00:25:22)
   * @return java.lang.String
   */
  String getScriptHtml();

  /**
   * Insert the method's description here.
   * Creation date: (22.05.01 08:48:59)
   * @return java.lang.String
   */
  String getStyleConst();

  /**
   * Insert the method's description here.
   * Creation date: (22.05.01 08:48:59)
   * @return java.lang.String
   */
  String getStyleInput();

  /**
   * Insert the method's description here.
   * Creation date: (22.05.01 08:48:59)
   * @return java.lang.String
   */
  String getStyleOutput();

  /**
   * Insert the method's description here.
   * Creation date: (18.05.01 16:42:39)
   * @param request javax.servlet.http.HttpServletRequest
   */
  void scanRequest(javax.servlet.http.HttpServletRequest request) throws java.text.ParseException;

  /**
   * Insert the method's description here.
   * Creation date: (22.05.01 16:10:40)
   * @param color java.awt.Color
   */
  void setBGColor(Color color);
}
