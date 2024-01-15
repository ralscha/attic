package ch.ess.util.spreadsheet.instruction;

import ch.ess.util.spreadsheet.*;

public abstract class Instruction {

  SpreadsheetTableModel spreadSheet;

  public Instruction(SpreadsheetTableModel spreadSheet) {
    this.spreadSheet = spreadSheet;
  }

  public abstract void execute();

  public abstract String getJs();

  public SpreadsheetCell getRelatedCell() {
    return null;
  }
}
