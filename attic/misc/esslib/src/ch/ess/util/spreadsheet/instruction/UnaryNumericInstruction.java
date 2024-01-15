/*
 * $Header: /home/cvsroot/pbroker/src/ch/ess/util/spreadsheet/UnaryNumericInstruction.java,v 1.1.1.1 2002/02/26 06:46:56 sr Exp $
 * $Revision: 1.1.1.1 $
 * $Date: 2002/02/26 06:46:56 $
 */

package ch.ess.util.spreadsheet.instruction;

import java.util.*;

import ch.ess.util.spreadsheet.*;


/**
 * Insert the type's description here.
 * Creation date: (06.05.01 21:19:58)
 * @author: Daniel Ramseier
 */
public abstract class UnaryNumericInstruction extends NumericInstruction {

  /**
   * BinaryNumericInstruction constructor comment.
   * @param spreadSheet ch.ess.util.spreadsheet.SpreadsheetTableModel
   */
  public UnaryNumericInstruction(SpreadsheetTableModel spreadSheet) {
    super(spreadSheet);
  }

  /**
   * BinaryNumericInstruction constructor comment.
   * @param spreadSheet ch.ess.util.spreadsheet.SpreadsheetTableModel
   * @param nullReplacement java.lang.Double
   */
  public UnaryNumericInstruction(SpreadsheetTableModel spreadSheet, Double nullReplacement) {
    super(spreadSheet, nullReplacement);
  }

  /**
   * Insert the method's description here.
   * Creation date: (06.05.01 21:23:40)
   * @return double
   * @param param1 double
   * @param param2 double
   */
  public double compute(double param) {
    return 0;
  }

  /**
   * Insert the method's description here.
   * Creation date: (06.05.01 21:20:56)
   */
  public void execute() {

    Stack s = spreadSheet.getStack();
    Object p = prepareParameter();
    Object result = null;

    if (p != null) {
      if (Double.class == p.getClass()) {
        try {
          result = new Double(compute(((Double)p).doubleValue()));
        } catch (Exception e) {
          result = "Err:" + e.toString();
        }
      } else {
        result = p;
      }
    }

    s.push(result);
  }
}
