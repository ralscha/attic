/*
 * $Header: /home/cvsroot/pbroker/src/ch/ess/util/spreadsheet/InstructionMul.java,v 1.1.1.1 2002/02/26 06:46:55 sr Exp $
 * $Revision: 1.1.1.1 $
 * $Date: 2002/02/26 06:46:55 $
 */

package ch.ess.util.spreadsheet.instruction;

import ch.ess.util.spreadsheet.*;


/**
 * Insert the type's description here.
 * Creation date: (03.05.01 11:35:42)
 * @author: Daniel Ramseier
 */
public class InstructionMul extends BinaryNumericInstruction {

  /**
   * InstructionAdd constructor comment.
   * @param spreadSheet ch.ess.util.spreadsheet.SpreadsheetTableModel
   */
  public InstructionMul(SpreadsheetTableModel spreadSheet) {
    super(spreadSheet);
  }

  /**
   * InstructionAdd constructor comment.
   * @param spreadSheet ch.ess.util.spreadsheet.SpreadsheetTableModel
   */
  public InstructionMul(SpreadsheetTableModel spreadSheet, Double nullReplacement) {
    super(spreadSheet, nullReplacement);
  }

  /**
   * Insert the method's description here.
   * Creation date: (06.05.01 21:23:40)
   * @return double
   * @param param1 double
   * @param param2 double
   */
  public double compute(double param1, double param2) {
    return param1 * param2;
  }

  /**
   * Insert the method's description here.
   * Creation date: (08.05.01 16:53:56)
   * @return java.lang.String
   */
  public String getJs() {
    return "iMul()";
  }
}
