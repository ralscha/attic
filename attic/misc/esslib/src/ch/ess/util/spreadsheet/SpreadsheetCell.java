
package ch.ess.util.spreadsheet;




public interface SpreadsheetCell {


  String getAction();

  String getFormula();

  Object getValue();

  boolean isEditable();

  void setAction(String action) throws java.text.ParseException;

  void setFormula(String formula) throws java.text.ParseException;

  void setValue(Object value);

  void assValue(Object value);

  void clearReady();

  int getColumn();

  String getName();

  int getRow();

  Class getType();

  boolean hasName();


  /**
   * Assigns a NUMERIC value to the the cell.
   * Values containing errors are not assigned.
   * If the value was successfully assigned and executeAction is true,
   * the cell's action is executed.
   * Creation date: (02.05.01 15:13:09)
   * @param value java.lang.Object
   * @param executeAction boolean
   */
  void putValue(Object value, boolean executeAction);

  void setName(String name);

  void setSpredsheetModel(SpreadsheetTableModel model);

  void setType(Class type);
}
