package ch.ess.google;

import java.util.*;

import javax.swing.*;
import javax.swing.table.*;


public class SearchResultTableModel extends AbstractTableModel {
  
  private final String[] columnNames = {"Titel", "URL"};
  private int[] alignments = {SwingConstants.LEFT, SwingConstants.LEFT  };
  private int[] width = {80, 150};
  
  private Vector dataVector;
  
  public SearchResultTableModel() {
    dataVector = new Vector();
  }
  
  public void addRow(String title, String url) {
    Vector rowVector = new Vector();
    rowVector.add(title);
    rowVector.add(url);
    dataVector.add(rowVector);
  }
  
  public void clear() {
    dataVector.clear();
  }

  public Object getValueAt(int row, int col) {
    Vector rowVector = (Vector)dataVector.elementAt(row);
    return rowVector.elementAt(col);
  }
  
  public void setValueAt(Object aValue, int row, int column) {
    Vector rowVector = (Vector)dataVector.elementAt(row);
    rowVector.setElementAt(aValue, column);
  } 
  
  public int getColumnCount() {
    return columnNames.length;
  } 

  public int getRowCount() {
    return dataVector.size();
  } 

  public String getColumnName(int col) {
    return columnNames[col];
  } 
  

  public Class getColumnClass(int col) {
    return getValueAt(0, col).getClass();
  } 

  public boolean isCellEditable(int row, int col) {
    return false;
  } 

  public int getAlignment(int col) {
    return alignments[col];
  } 

  public int getWidth(int col) {
    return width[col];
  } 

}