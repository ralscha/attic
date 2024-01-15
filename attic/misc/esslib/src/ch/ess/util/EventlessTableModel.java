

package ch.ess.util;

import java.io.*;
import java.util.*;

import javax.swing.table.*;



public class EventlessTableModel extends AbstractTableModel implements Serializable {

  private Vector dataVector;
  protected Vector columnIdentifiers;

  
  public EventlessTableModel() {
    this((Vector)null, 0);
  }

  public EventlessTableModel(int numRows, int numColumns) {
    
    Vector names = new Vector(numColumns);

    names.setSize(numColumns);
    setColumnIdentifiers(names);

    dataVector = new Vector();

    setNumRows(numRows);
  }

  public EventlessTableModel(Vector columnNames, int numRows) {

    setColumnIdentifiers(columnNames);

    dataVector = new Vector();

    setNumRows(numRows);
  }

  public EventlessTableModel(Object[] columnNames, int numRows) {
    this(convertToVector(columnNames), numRows);
  }

  public EventlessTableModel(Vector data, Vector columnNames) {
    setDataVector(data, columnNames);
  }

  public EventlessTableModel(Object[][] data, Object[] columnNames) {
    setDataVector(data, columnNames);
  }

  public void setDataVector(Object[][] newData, Object[] columnNames) {
    setDataVector(convertToVector(newData), convertToVector(columnNames));
  }

  public void setDataVector(Vector newData, Vector columnNames) {

    if (newData == null) {
      throw new IllegalArgumentException("setDataVector() - Null parameter");
    }

    // Clear all the previous data.
    dataVector = new Vector(0);

    // Install the new column structure, this will fireTableStructureChanged
    setColumnIdentifiers(columnNames);

    // Add the new rows.
    dataVector = newData;
  }

  public Vector getDataVector() {
    return dataVector;
  }

  public void setColumnIdentifiers(Vector newIdentifiers) {

    if (newIdentifiers != null) {
      columnIdentifiers = newIdentifiers;
    } else {
      columnIdentifiers = new Vector();
    }

    // Generate notification
    fireTableStructureChanged();
  }

  public void setColumnIdentifiers(Object[] newIdentifiers) {
    setColumnIdentifiers(convertToVector(newIdentifiers));
  }

  public void setNumRows(int newSize) {

    if ((newSize < 0) || (newSize == getRowCount())) {
      return;
    }

    int oldNumRows = getRowCount();

    if (newSize <= getRowCount()) {

      // newSize is smaller than our current size, so we can just
      // let Vector discard the extra rows
      dataVector.setSize(newSize);

      // Generate notification
      fireTableRowsDeleted(getRowCount(), oldNumRows - 1);
    } else {
      int columnCount = getColumnCount();

      // We are adding rows to the model
      while (getRowCount() < newSize) {
        Vector newRow = new Vector(columnCount);

        newRow.setSize(columnCount);
        dataVector.addElement(newRow);
      }

      // Generate notification
      fireTableRowsInserted(oldNumRows, getRowCount() - 1);
    }
  }

  public void setRowCount(int rowCount) {
    setNumRows(rowCount);
  }

  public void setColumnCount(int columnCount) {

    for (int r = 0; r < getRowCount(); r++) {
      Vector row = (Vector)dataVector.elementAt(r);

      row.setSize(columnCount);
    }

    columnIdentifiers.setSize(columnCount);
    fireTableStructureChanged();
  }

  public void addRow(Vector rowData) {

    if (rowData == null) {
      rowData = new Vector(getColumnCount());
    } else {
      rowData.setSize(getColumnCount());
    }

    dataVector.addElement(rowData);
  }

  public void addRow(Object[] rowData) {
    addRow(convertToVector(rowData));
  }

  public int getRowCount() {
    return dataVector.size();
  }

  public int getColumnCount() {
    return columnIdentifiers.size();
  }

  public String getColumnName(int column) {

    if ((columnIdentifiers == null) || (columnIdentifiers.size() <= column)) {
      return super.getColumnName(column);
    }

    Object id = columnIdentifiers.elementAt(column);

    if (id == null) {
      return super.getColumnName(column);
    } else {
      return id.toString();
    }
  }

  public boolean isCellEditable(int row, int column) {
    return true;
  }

  public Object getValueAt(int row, int column) {

    Vector rowVector = (Vector)dataVector.elementAt(row);

    return rowVector.elementAt(column);
  }

  public void setValueAt(Object aValue, int row, int column) {

    Vector rowVector = (Vector)dataVector.elementAt(row);

    rowVector.setElementAt(aValue, column);
  }

  protected static Vector convertToVector(Object[] anArray) {

    if (anArray == null) {
      return null;
    }

    Vector v = new Vector(anArray.length);

    for (int i = 0; i < anArray.length; i++) {
      v.addElement(anArray[i]);
    }

    return v;
  }

  protected static Vector convertToVector(Object[][] anArray) {

    if (anArray == null) {
      return null;
    }

    Vector v = new Vector(anArray.length);

    for (int i = 0; i < anArray.length; i++) {
      v.addElement(convertToVector(anArray[i]));
    }

    return v;
  }
  




}    

