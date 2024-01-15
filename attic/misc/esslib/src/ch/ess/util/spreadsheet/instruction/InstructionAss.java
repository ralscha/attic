/*
 * $Header: /home/cvsroot/pbroker/src/ch/ess/util/spreadsheet/InstructionAss.java,v 1.1.1.1 2002/02/26 06:46:55 sr Exp $
 * $Revision: 1.1.1.1 $
 * $Date: 2002/02/26 06:46:55 $
 */

package ch.ess.util.spreadsheet.instruction;

import java.util.*;

import ch.ess.util.spreadsheet.*;


/**
 * Insert the type's description here.
 * Creation date: (06.05.01 22:34:50)
 * @author: Daniel Ramseier
 */
public class InstructionAss extends Instruction {

  /**
   * InstructionAssign constructor comment.
   * @param spreadSheet ch.ess.util.spreadsheet.SpreadsheetTableModel
   */
  public InstructionAss(SpreadsheetTableModel spreadSheet) {
    super(spreadSheet);
  }

  /**
   * Insert the method's description here.
   * Creation date: (06.05.01 22:37:41)
   */
  public void execute() {

    Stack s = spreadSheet.getStack();
    Object c = s.pop();
    Object p = s.pop();

    if (SpreadsheetCell.class.isAssignableFrom(c.getClass())) {
      if ((p != null) && SpreadsheetCell.class.isAssignableFrom(p.getClass())) {
        p = ((SpreadsheetCell)p).getValue();
      }

      ((SpreadsheetCell)c).assValue(p);
    }
  }

  /**
   * Insert the method's description here.
   * Creation date: (08.05.01 16:53:56)
   * @return java.lang.String
   */
  public String getJs() {
    return "iAss()";
  }
}
