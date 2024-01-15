
package ch.ess.util.spreadsheet.instruction;

import java.util.*;

import ch.ess.util.spreadsheet.*;


public abstract class BinaryNumericInstruction extends NumericInstruction {


  public BinaryNumericInstruction(SpreadsheetTableModel spreadSheet) {
    super(spreadSheet);
  }

  public BinaryNumericInstruction(SpreadsheetTableModel spreadSheet, Double nullReplacement) {
    super(spreadSheet, nullReplacement);
  }

  public double compute(double param1, double param2) {
    return 0;
  }

  public void execute() {

    Stack s = spreadSheet.getStack();
    Object p2 = prepareParameter();
    Object p1 = prepareParameter();
    Object result = null;

    if ((p1 != null) && (p2 != null)) {
      if ((Double.class == p1.getClass()) && (Double.class == p2.getClass())) {
        try {
          result = new Double(compute(((Double)p1).doubleValue(), ((Double)p2).doubleValue()));
        } catch (Exception e) {
          result = "Err:" + e.toString();
        }
      } else {
        if (Double.class != p1.getClass()) {
          result = p1;
        } else {
          result = p2;
        }
      }
    }

    s.push(result);
  }
}
