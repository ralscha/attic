/*
 * $Header: /home/cvsroot/pbroker/src/ch/ess/util/spreadsheet/InstructionLit.java,v 1.1.1.1 2002/02/26 06:46:55 sr Exp $
 * $Revision: 1.1.1.1 $
 * $Date: 2002/02/26 06:46:55 $
 */

package ch.ess.util.spreadsheet.instruction;

import java.util.*;

import ch.ess.util.spreadsheet.*;


/**
 * Insert the type's description here.
 * Creation date: (06.05.01 21:49:49)
 * @author: Daniel Ramseier
 */
public class InstructionLit extends Instruction {

  Object constValue = null;

  /**
   * InstructionConst constructor comment.
   * @param spreadSheet ch.ess.util.spreadsheet.SpreadsheetTableModel
   */
  public InstructionLit(SpreadsheetTableModel spreadSheet, Object constValue) {

    super(spreadSheet);

    this.constValue = constValue;
  }

  /**
   * Insert the method's description here.
   * Creation date: (06.05.01 21:53:40)
   */
  public void execute() {

    Stack s = spreadSheet.getStack();

    s.push(constValue);
  }

  /**
   * Insert the method's description here.
   * Creation date: (08.05.01 16:53:56)
   * @return java.lang.String
   */
  public String getJs() {
    return "iLit(" + constValue + ")";
  }
}
