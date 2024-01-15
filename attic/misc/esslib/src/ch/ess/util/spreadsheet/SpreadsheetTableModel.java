/*
 * $Header: /home/cvsroot/pbroker/src/ch/ess/util/spreadsheet/SpreadsheetModel.java,v 1.2 2002/04/02 18:27:21 sr Exp $
 * $Revision: 1.2 $
 * $Date: 2002/04/02 18:27:21 $
 */

package ch.ess.util.spreadsheet;

import java.util.*;

import javax.swing.table.*;

import ch.ess.util.spreadsheet.instruction.*;


/**
 * Insert the type's description here.
 * Creation date: (02.05.01 14:31:35)
 * @author: Ralph Schaer
 */
public class SpreadsheetTableModel extends AbstractTableModel {

  protected int columnCount = 0;
  protected int rowCount = 0;
  protected Hashtable cells = new Hashtable();
  protected SpreadsheetCell emptyCell = null;
  protected Stack stack = null;
  protected String name = null;
  protected java.util.Hashtable namedCells = null;

  /**
   * SpreadsheetModel constructor comment.
   */
  public SpreadsheetTableModel(String name) {

    super();

    stack = new Stack();
    this.name = name;
  }

  /**
   * Insert the method's description here.
   * Creation date: (03.05.01 12:04:25)
   */
  public void clearReady() {

    Enumeration elements = cells.elements();

    while (elements.hasMoreElements()) {
      ((SpreadsheetCell)elements.nextElement()).clearReady();
    }
  }

  /**
   * getValueAt method comment.
   */
  public SpreadsheetCell getCellAt(int row, int column) {

    SpreadsheetCell cell = (SpreadsheetCell)cells.get(key2(row, column));

    return (cell != null) ? cell : emptyCell;
  }

  /**
   * getColumnCount method comment.
   */
  public int getColumnCount() {
    return columnCount;
  }


  /**
   * Insert the method's description here.
   * Creation date: (02.05.01 14:57:08)
   * @return java.lang.Object
   */
  public SpreadsheetCell getEmptyCell() {
    return emptyCell;
  }

  /**
   * Insert the method's description here.
   * Creation date: (13.05.01 00:04:25)
   * @return java.lang.String
   */
  public java.lang.String getName() {
    return name;
  }

  /**
   * Insert the method's description here.
   * Creation date: (05.06.01 00:29:25)
   * @return java.util.Hashtable
   */
  public java.util.Hashtable getNamedCells() {

    if (namedCells == null) {
      namedCells = new Hashtable();
    }

    return namedCells;
  }

  /**
   * getRowCount method comment.
   */
  public int getRowCount() {
    return rowCount;
  }

  /**
   * Insert the method's description here.
   * Creation date: (03.05.01 11:25:33)
   * @return java.util.Stack
   */
  public java.util.Stack getStack() {
    return stack;
  }

  /**
   * getValueAt method comment.
   */
  public Object getValueAt(int row, int column) {

    SpreadsheetCell cell = getCellAt(row, column);

    return (cell != null) ? cell.getValue() : null;
  }

  /**
   * Insert the method's description here.
   * Creation date: (02.05.01 18:11:44)
   * @return boolean
   * @param row int
   * @param column int
   */
  public boolean isCellEditable(int row, int column) {

    SpreadsheetCell cell = getCellAt(row, column);

    return (cell != emptyCell) ? cell.isEditable() : false;
  }

  /**
   * Insert the method's description here.
   * Creation date: (02.05.01 17:45:47)
   * @return java.lang.Object
   * @param row int
   * @param column int
   */
  protected static final Object key2(int row, int column) {

    String key = "R" + row + "C" + column;

    return key;
  }


  /**
   * Insert the method's description here.
   * Creation date: (03.05.01 10:35:05)
   * @return ch.ess.util.spreadsheet.Instruction[]
   * @param script java.lang.String
   * @param thisCell ch.ess.util.spreadsheet.SpreadsheetCell
   * @exception java.text.ParseException The exception description.
   */
  public ch.ess.util.spreadsheet.Sequence parseInstructions(String script, SpreadsheetCell thisCell)
          throws java.text.ParseException {

    Instruction instruction = null;
    StringTokenizer st = new StringTokenizer(script, " ");
    Instruction[] instructions = new Instruction[st.countTokens()];
    int iPtr = 0;

    while (st.hasMoreTokens()) {
      String t = st.nextToken();

      if (t.equals("+")) {
        instruction = new InstructionAdd(this);
      } else if (t.equals("-")) {
        instruction = new InstructionSub(this);
      } else if (t.equals("*")) {
        instruction = new InstructionMul(this);
      } else if (t.equals("/")) {
        instruction = new InstructionDiv(this);
      } else if (t.equals("HMtoD")) {
        instruction = new InstructionHMtoD(this);
      } else if (t.equals("DtoHM")) {
        instruction = new InstructionDtoHM(this);
      } else if (t.equals("+0")) {
        instruction = new InstructionAdd(this, new Double(0));
      } else if (t.equals("-0")) {
        instruction = new InstructionSub(this, new Double(0));
      } else if (t.equals("*0")) {
        instruction = new InstructionMul(this, new Double(0));
      } else if (t.equals("/0")) {
        instruction = new InstructionDiv(this, new Double(0));
      } else if (t.equals("HMtoD0")) {
        instruction = new InstructionHMtoD(this, new Double(0));
      } else if (t.equals("DtoHM0")) {
        instruction = new InstructionDtoHM(this, new Double(0));
      } else if (t.equals("=>!")) {
        instruction = new InstructionPut(this, true);
      } else if (t.equals("=>")) {
        instruction = new InstructionPut(this);
      } else if (t.equals("=")) {
        instruction = new InstructionAss(this);
      } else if (t.equals("#this")) {
        instruction = new InstructionGet(this, thisCell);
      } else if (t.startsWith("#")) {
        SpreadsheetCell cell = (SpreadsheetCell)this.getNamedCells().get("namedCells." + t.substring(1));

        if (cell == null) {
          StringTokenizer tt = new StringTokenizer(t.substring(1), ",");

          if (!tt.hasMoreTokens()) {
            throw (new java.text.ParseException("Syntax error: \"" + t + "\"", 0));
          }

          int r = Integer.parseInt(tt.nextToken());

          if (!tt.hasMoreTokens()) {
            throw (new java.text.ParseException("Syntax error: \"" + t + "\"", 0));
          }

          int c = Integer.parseInt(tt.nextToken());

          cell = this.getCellAt(r, c);
        }

        instruction = new InstructionGet(this, cell);
      } else {
        instruction = new InstructionLit(this, t);
      }

      instructions[iPtr] = instruction;

      iPtr++;
    }

    return new Sequence(instructions);
  }

  /**
   * Insert the method's description here.
   * Creation date: (02.05.01 15:25:44)
   * @param cell ch.ess.util.spreadsheet.SpreadsheetCell
   */
  public void putCell(SpreadsheetCell cell) {

    int row = cell.getRow();
    int column = cell.getColumn();

    if (rowCount <= row) {
      rowCount = row + 1;
    }

    if (columnCount <= column) {
      columnCount = column + 1;
    }

    cells.put(key2(row, column), cell);
    cell.setSpredsheetModel(this);

    if (cell.hasName()) {
      getNamedCells().put(cell.getName(), cell);
    }
  }

  /**
   * Insert the method's description here.
   * Creation date: (02.05.01 15:25:44)
   * @param cell ch.ess.util.spreadsheet.SpreadsheetCell
   */
  public void putCellAt(SpreadsheetCell cell, int row, int column) {

    if (rowCount <= row) {
      rowCount = row + 1;
    }

    if (columnCount <= column) {
      columnCount = column + 1;
    }

    cells.put(key2(row, column), cell);
    cell.setSpredsheetModel(this);
  }


  /**
   * Insert the method's description here.
   * Creation date: (02.05.01 14:57:08)
   * @param newEmptyCell java.lang.Object
   */
  public void setEmptyCell(SpreadsheetCell newEmptyCell) {
    emptyCell = newEmptyCell;
  }

  /**
   * Insert the method's description here.
   * Creation date: (13.05.01 00:04:25)
   * @param newName java.lang.String
   */
  public void setName(java.lang.String newName) {
    name = newName;
  }

  /**
   * Insert the method's description here.
   * Creation date: (05.06.01 00:29:25)
   * @param newNamedCells java.util.Hashtable
   */
  public void setNamedCells(java.util.Hashtable newNamedCells) {
    namedCells = newNamedCells;
  }

  /**
   * Insert the method's description here.
   * Creation date: (02.05.01 15:39:07)
   * @param value java.lang.Object
   * @param row int
   * @param column int
   */
  public void setValueAt(Object value, int row, int column) {

    SpreadsheetCell cell = getCellAt(row, column);

    if (cell == emptyCell) {
      cell = new SimpleSpreadsheetCell(row, column, null, false, value, null, null);
      this.putCell(cell);
    } else {
      cell.setValue(value);
    }
  }
}
