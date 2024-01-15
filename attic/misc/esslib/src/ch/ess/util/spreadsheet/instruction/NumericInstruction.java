/*
 * $Header: /home/cvsroot/pbroker/src/ch/ess/util/spreadsheet/NumericInstruction.java,v 1.1.1.1 2002/02/26 06:46:56 sr Exp $
 * $Revision: 1.1.1.1 $
 * $Date: 2002/02/26 06:46:56 $
 */

package ch.ess.util.spreadsheet.instruction;

import java.util.*;

import ch.ess.util.spreadsheet.*;


/**
 * Insert the type's description here.
 * Creation date: (06.05.01 11:10:40)
 * @author: Daniel Ramseier
 */
public abstract class NumericInstruction extends Instruction {

  Double nullReplacement = null;

  /**
   * NumericInstruction constructor comment.
   * @param spreadSheet ch.ess.util.spreadsheet.SpreadsheetTableModel
   */
  public NumericInstruction(SpreadsheetTableModel spreadSheet) {
    super(spreadSheet);
  }

  /**
   * NumericInstruction constructor comment.
   * @param spreadSheet ch.ess.util.spreadsheet.SpreadsheetTableModel
   */
  public NumericInstruction(SpreadsheetTableModel spreadSheet, Double nullReplacement) {

    super(spreadSheet);

    this.nullReplacement = nullReplacement;
  }

  /**
   * Insert the method's description here.
   * Creation date: (06.05.01 11:14:07)
   * @return java.lang.Object
   */
  protected Object prepareParameter() {

    Stack s = spreadSheet.getStack();
    Object p = s.pop();

    if (p == null) {
      p = nullReplacement;
    } else {
      if (SpreadsheetCell.class.isAssignableFrom(p.getClass())) {
        p = ((SpreadsheetCell)p).getValue();
      }

      if (p == null) {
        p = nullReplacement;
      }
    }

    if (p != null) {
      if (String.class == p.getClass()) {
        if (((String)p).trim().equals("")) {
          p = nullReplacement;
        } else {
          try {
            Double d = Double.valueOf((String)p);

            p = d;
          } catch (Exception e) {
            if (!((String)p).startsWith("Err:")) {
              p = "Err:" + p;
            }
          }
        }
      } else {
        if (Number.class.isAssignableFrom(p.getClass())) {
          p = new Double(((Number)p).doubleValue());
        } else {
          p = "Err:" + p.toString();
        }
      }
    }

    return p;
  }
}
