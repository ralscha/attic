
package ch.ess.util.spreadsheet;

import java.util.regex.*;

import javax.swing.table.*;



public class SimpleSpreadsheetCell implements SpreadsheetCell {

  protected int column = -1;
  protected int row = -1;
  protected Object value = null;
  protected boolean editable = false;
  protected SpreadsheetTableModel spreadsheetModel = null;
  protected String formulaSource = null;
  protected String actionSource = null;
  protected String name = null;
  protected Sequence formula = null;
  protected Sequence action = null;
  protected boolean ready = false;
  protected Class type = String.class;
  protected int precision = 100;
  protected String format = "0.00";

  /**
   * SimpleSpreadsheetCell constructor comment.
   */
  public SimpleSpreadsheetCell(int row, int column, String name, boolean editable, Object value,
                               String formula, String action) {

    super();

    this.row = row;
    this.column = column;
    this.name = name;
    this.editable = editable;
    this.value = value;

    try {
      this.setFormula(formula);
    } catch (Exception e) {
      System.out.println("Error parsing formula in cell " + getName() + " \"" + formula + "\"");

      value = "Err:parsing formula";
    }

    try {
      this.setAction(action);
    } catch (Exception e) {
      System.out.println("Error parsing action in cell " + getName() + " \"" + action + "\"");
    }

  }

  /**
   * SimpleSpreadsheetCell constructor comment.
   */
  public SimpleSpreadsheetCell(int row, int column, String name, boolean editable, Object value,
                               String formula, String action, String format, int precision) {

    super();

    this.row = row;
    this.column = column;
    this.name = name;
    this.editable = editable;
    this.value = value;
    this.format = format;
    this.precision = precision;

    try {
      this.setFormula(formula);
    } catch (Exception e) {
      System.out.println("Error parsing formula in cell " + getName() + " \"" + formula + "\"");

      value = "Err:parsing formula";
    }

    try {
      this.setAction(action);
    } catch (Exception e) {
      System.out.println("Error parsing action in cell " + getName() + " \"" + action + "\"");
    }
    
  }

  /**
   * assigns any value without executing the cell's action.
   * Creation date: (12.05.01 08:23:44)
   * @param value java.lang.Object
   */
  public void assValue(java.lang.Object value) {
    this.value = value;
  }

  /**
   * Insert the method's description here.
   * Creation date: (03.05.01 12:02:31)
   */
  public void clearReady() {
    ready = false;
  }

  /**
   * Insert the method's description here.
   * Creation date: (03.05.01 06:59:00)
   * @return java.lang.String
   */
  public java.lang.String getAction() {
    return actionSource;
  }

  /**
   * Insert the method's description here.
   * Creation date: (03.05.01 17:23:12)
   * @return int
   */
  public int getColumn() {
    return column;
  }

  /**
   * Insert the method's description here.
   * Creation date: (25.05.01 08:23:17)
   * @return java.lang.String
   */
  public java.lang.String getFormat() {
    return format;
  }

  /**
   * Insert the method's description here.
   * Creation date: (09.05.01 10:39:19)
   * @return java.lang.String
   */
  public String getFormElementName() {

    String n;

    if (name != null) {
      n = name;
    } else {
      n = spreadsheetModel.getName() + "_r" + this.row + "c" + this.column;
    }

    return n;
  }

  /**
   * Insert the method's description here.
   * Creation date: (03.05.01 06:59:00)
   * @return java.lang.String
   */
  public java.lang.String getFormula() {
    return formulaSource;
  }

  /**
   * Insert the method's description here.
   * Creation date: (09.05.01 10:39:19)
   * @return java.lang.String
   */
  public String getName() {

    String n;

    if (name != null) {
      n = "namedCells." + name;
    } else {
      n = spreadsheetModel.getName() + ".c[" + this.row + "][" + this.column + "]";
    }

    return n;
  }

  /**
   * Insert the method's description here.
   * Creation date: (25.05.01 08:20:18)
   * @return double
   */
  public int getPrecision() {
    return precision;
  }

  /**
   * Insert the method's description here.
   * Creation date: (03.05.01 17:23:12)
   * @return int
   */
  public int getRow() {
    return row;
  }

  /**
   * Insert the method's description here.
   * Creation date: (21.05.01 06:11:55)
   * @return java.lang.Class
   */
  public java.lang.Class getType() {
    return type;
  }

  /**
   * Insert the method's description here.
   * Creation date: (02.05.01 15:28:18)
   * @return java.lang.Object
   */
  public Object getValue() {

    if (hasFormula()) {
      if (!ready) {
        formula.execute();

        ready = true;
      }
    }

    return value;
  }

  /**
   * Insert the method's description here.
   * Creation date: (12.05.01 17:09:22)
   * @return boolean
   */
  protected boolean hasAction() {

    if ((action == null) && (actionSource != null)) {
      try {
        this.action = spreadsheetModel.parseInstructions(substituteRowColPlaceholders(actionSource), this);
      } catch (Exception e) {
        System.out.println("Error parsing action in cell " + getName() + " \"" + actionSource + "\"");

        actionSource = null;
      }
    }

    return action != null;
  }

  /**
   * Insert the method's description here.
   * Creation date: (12.05.01 17:09:22)
   * @return boolean
   */
  protected boolean hasFormula() {

    if ((formula == null) && (formulaSource != null)) {
      try {
        this.formula = spreadsheetModel.parseInstructions(substituteRowColPlaceholders(formulaSource), this);
      } catch (Exception e) {
        System.out.println("Error parsing formula in cell " + getName() + " \"" + formulaSource + "\"");

        value = "Err:parsing formula";
        formulaSource = null;
      }
    }

    return formula != null;
  }

  /**
   * Insert the method's description here.
   * Creation date: (05.06.01 00:41:08)
   * @return boolean
   */
  public boolean hasName() {
    return (name != null);
  }

  /**
   * Insert the method's description here.
   * Creation date: (02.05.01 18:09:12)
   * @return boolean
   */
  public boolean isEditable() {
    return editable;
  }


  /**
   * Assigns a NUMERIC value to the the cell.
   * Values containing errors are not assigned.
   * If the value was successfully assigned and executeAction is true,
   * the cell's action is executed.
   * Creation date: (12.05.01 08:23:44)
   * @param value java.lang.Object
   * @param executeAction boolean
   */
  public void putValue(Object value, boolean executeAction) {

    if ((value != null) && Double.class.isAssignableFrom(value.getClass())) {
      this.value = value;

      if (executeAction && hasAction()) {
        action.execute();
      }
    }
  }

  /**
   * Insert the method's description here.
   * Creation date: (03.05.01 06:59:00)
   * @param value java.lang.String
   * @exception java.text.ParseException The exception description.
   */
  public void setAction(java.lang.String action) throws java.text.ParseException {
    this.actionSource = action;
  }

  /**
   * Insert the method's description here.
   * Creation date: (02.05.01 18:09:12)
   * @param newEditable boolean
   */
  public void setEditable(boolean newEditable) {
    editable = newEditable;
  }

  /**
   * Insert the method's description here.
   * Creation date: (25.05.01 08:23:17)
   * @param newFormat java.lang.String
   */
  public void setFormat(java.lang.String newFormat) {
    format = newFormat;
  }

  /**
   * Insert the method's description here.
   * Creation date: (03.05.01 06:59:00)
   * @param value java.lang.String
   * @exception java.text.ParseException The exception description.
   */
  public void setFormula(java.lang.String formula) throws java.text.ParseException {
    this.formulaSource = (formula != null) ? formula + " #this =" : null;
  }

  /**
   * Insert the method's description here.
   * Creation date: (09.05.01 10:40:47)
   * @param name java.lang.String
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Insert the method's description here.
   * Creation date: (25.05.01 08:20:18)
   * @param newPrecision int
   */
  public void setPrecision(int newPrecision) {
    precision = newPrecision;
  }

  /**
   * Insert the method's description here.
   * Creation date: (03.05.01 07:40:38)
   * @param model javax.swing.table.TableModel
   */
  public void setSpredsheetModel(SpreadsheetTableModel model) {
    spreadsheetModel = model;
  }


  public void setType(java.lang.Class newType) {
    type = newType;
  }


  public void setValue(Object value) {

    this.value = value;

    if (hasAction()) {
      action.execute();
    }

    spreadsheetModel.clearReady();
    ((AbstractTableModel)spreadsheetModel).fireTableDataChanged();
  }


  public String substituteRowColPlaceholders(String source) {

    Pattern pattern = Pattern.compile("\\?,");
    Matcher matcher = pattern.matcher(source);    
    String x = matcher.replaceAll(row + ",");

    pattern = Pattern.compile(",\\?");
    matcher = pattern.matcher(x);    
    x = matcher.replaceAll("," + column);

    return x;
  }
}
