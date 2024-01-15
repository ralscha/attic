/*
 * $Header: /home/cvsroot/pbroker/src/ch/ess/util/spreadsheet/InstructionHMtoD.java,v 1.1.1.1 2002/02/26 06:46:55 sr Exp $
 * $Revision: 1.1.1.1 $
 * $Date: 2002/02/26 06:46:55 $
 */

package ch.ess.util.spreadsheet.instruction;

import ch.ess.util.spreadsheet.*;

/**
 * Insert the type's description here.
 * Creation date: (27.05.01 21:04:18)
 * @author: Daniel Ramseier
 */
public class InstructionHMtoD extends UnaryNumericInstruction {

  /**
   * InstructionHMtoD constructor comment.
   * @param spreadSheet ch.ess.util.spreadsheet.SpreadsheetTableModel
   */
  public InstructionHMtoD(SpreadsheetTableModel spreadSheet) {
    super(spreadSheet);
  }

  /**
   * InstructionHMtoD constructor comment.
   * @param spreadSheet ch.ess.util.spreadsheet.SpreadsheetTableModel
   * @param nullReplacement java.lang.Double
   */
  public InstructionHMtoD(SpreadsheetTableModel spreadSheet, Double nullReplacement) {
    super(spreadSheet, nullReplacement);
  }

  /**
   * Insert the method's description here.
   * Creation date: (06.05.01 21:23:40)
   * @return double
   * @param param double
   */
  public double compute(double param) {

    SplitDouble p = new SplitDouble(param);

    return ((p.getIPart() + ((((double)Math.round(p.getFPart() / 0.006)) / 100))) * p.getSPart());
  }

  /**
   * Insert the method's description here.
   * Creation date: (08.05.01 16:53:56)
   * @return java.lang.String
   */
  public String getJs() {
    return "iHMtoD()";
  }
}
