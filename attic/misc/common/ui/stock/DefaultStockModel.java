package common.ui.stock;
/*
=====================================================================

  DefaultStockModel.java
  
  Created by Claude Duguay
  Copyright (c) 2001
  
=====================================================================
*/

import java.io.*;
import java.awt.*;
import java.text.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

public class DefaultStockModel implements StockModel
{
  protected List listeners = new ArrayList();
  protected List rows = new ArrayList();
  
  protected String[] columnNames =
    {"Date", "Open", "High",
     "Low", "Close", "Volume"};
  protected Class[] columnClass =
    {Date.class, Double.class, Double.class,
     Double.class, Double.class, Long.class};
  
  protected static final DateFormat dateFormat =
    new SimpleDateFormat("dd-MMM-yy");

  public int getRowCount()
  {
    return rows.size();
  }
  
  public int getColumnCount()
  {
    return columnNames.length;
  }
  
  public String getColumnName(int index)
  {
    return columnNames[index];
  }
  
  public Class getColumnClass(int index)
  {
    return columnClass[index];
  }
  
  public boolean isCellEditable(int row, int col)
  {
    return false;
  }
  
  public Object getValueAt(int row, int col)
  {
    List list = (List)rows.get(row);
    return list.get(col);
  }
  
  public void setValueAt(Object val, int row, int col)
  {
  }
  
  public void addTableModelListener(TableModelListener l)
  {
    listeners.add(l);
  }
  
  public void removeTableModelListener(TableModelListener l)
  {
    listeners.remove(l);
  }
  
  public void parseFile(String filename)
    throws IOException, ParseException
  {
    rows = new ArrayList();
    BufferedReader reader = new BufferedReader(
      new FileReader(filename));
    String line;
    reader.readLine(); // skip header
    while ((line = reader.readLine()) != null)
    {
      rows.add(parseLine(line));
    }
    reader.close();
  }
  
  protected List parseLine(String line)
    throws ParseException
  {
    List list = new ArrayList();
    StringTokenizer tokenizer =
      new StringTokenizer(line, ",", false);
    list.add(dateFormat.parse(tokenizer.nextToken()));
    list.add(Double.valueOf(tokenizer.nextToken()));
    list.add(Double.valueOf(tokenizer.nextToken()));
    list.add(Double.valueOf(tokenizer.nextToken()));
    list.add(Double.valueOf(tokenizer.nextToken()));
    list.add(Long.valueOf(tokenizer.nextToken()));
    //System.out.println(list);
    return list;
  }

  public Date getDate(int row)
  {
    return (Date)getValueAt(row, StockModel.DATE);
  }
  
  public double getOpen(int row)
  {
    Object val = getValueAt(row, StockModel.OPEN);
    return ((Double)val).doubleValue();
  }
  
  public double getHigh(int row)
  {
    Object val = getValueAt(row, StockModel.HIGH);
    return ((Double)val).doubleValue();
  }
  
  public double getLow(int row)
  {
    Object val = getValueAt(row, StockModel.LOW);
    return ((Double)val).doubleValue();
  }
  
  public double getClose(int row)
  {
    Object val = getValueAt(row, StockModel.CLOSE);
    return ((Double)val).doubleValue();
  }
  
  public long getVolume(int row)
  {
    Object val = getValueAt(row, StockModel.VOLUME);
    return ((Long)val).longValue();
  }
}

