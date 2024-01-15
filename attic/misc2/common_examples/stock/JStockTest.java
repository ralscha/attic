package stock;
/*
=====================================================================

  JStock.java

  Created by Claude Duguay
  Copyright (c) 2001

=====================================================================
*/

import java.awt.*;
import javax.swing.*;
import common.ui.stock.*;
import java.util.*;

public class JStockTest extends JPanel
{
  public JStockTest(String stock)
  {
    Locale.setDefault(Locale.ENGLISH);
    
    DefaultStockModel model = new DefaultStockModel();
    try
    {
      model.parseFile(stock + ".csv");
    }
    catch (Exception e) {e.printStackTrace();}
    
    JStock chart = new JStock(model);
    JScrollPane scrollPane = new JScrollPane(chart);
    
    JPanel tablePanel = new JPanel(new GridLayout());
    tablePanel.setBorder(
      BorderFactory.createEmptyBorder(4, 4, 4, 4));
    tablePanel.add(new JScrollPane(new JTable(model)));
    
    JTabbedPane tabs = new JTabbedPane();
    tabs.addTab("Chart", scrollPane);
    tabs.addTab("Table", tablePanel);
    
    setLayout(new BorderLayout());
    setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
    add(BorderLayout.NORTH, new JLabel(
      "Chart and Table for '" + stock + "' Stock",
      JLabel.CENTER));
    add(BorderLayout.CENTER, tabs);
    
    setPreferredSize(new Dimension(500, 550));
  }
  
  public static void main(String[] args)
  {
    JFrame frame = new JFrame("JStock Test");
    frame.getContentPane().setLayout(new GridLayout());
    frame.getContentPane().add(new JStockTest("stock\\IBM"));
    frame.pack();
    frame.show();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
}

