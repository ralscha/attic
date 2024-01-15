/*
 * $Header: /home/cvsroot/pbroker/src/ch/ess/util/spreadsheet/InstructionGet.java,v 1.1.1.1 2002/02/26 06:46:55 sr Exp $
 * $Revision: 1.1.1.1 $
 * $Date: 2002/02/26 06:46:55 $
 */

package ch.ess.util.spreadsheet.instruction;

import java.util.*;

import ch.ess.util.spreadsheet.*;


/**
 * Insert the type's description here.
 * Creation date: (03.05.01 11:10:55)
 * @author: Daniel Ramseier
 */
public class InstructionGet extends Instruction {

  SpreadsheetCell cell = null;

  /**
   * GetInstruction constructor comment.
   * @param spreadSheet ch.ess.util.spreadsheet.SpreadsheetTableModel
   */
  public InstructionGet(SpreadsheetTableModel spreadSheet, SpreadsheetCell cell) {

    super(spreadSheet);

    this.cell = cell;
  }

  /**
   * Insert the method's description here.
   * Creation date: (03.05.01 11:13:09)
   */
  public void execute() {

    Stack s = spreadSheet.getStack();

    s.push(cell);
  }

  /**
   * Insert the method's description here.
   * Creation date: (08.05.01 16:53:56)
   * @return java.lang.String
   */
  public String getJs() {
    return "iGet(" + cell.getName() + ")";
  }
}
