package ch.ess.test;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import ch.ess.util.spreadsheet.*;

public class SpreadSheet extends JFrame implements ActionListener {

  public SpreadSheet() {
    getContentPane().setLayout(new BorderLayout());
    JButton exitButton = new JButton("Exit");
    exitButton.addActionListener(this);
    getContentPane().add(exitButton, BorderLayout.SOUTH);
    
    SpreadsheetTableModel stm = new SpreadsheetTableModel("test");
    SpreadsheetCell cell = new SimpleSpreadsheetCell(0, 0, "first", true, new Integer(1), null, null); 
    stm.putCell(cell);

    cell = new SimpleSpreadsheetCell(1, 0, "second", true, new Integer(2), null, null);    
    stm.putCell(cell);
        
    cell = new SimpleSpreadsheetCell(3, 0, "total", false, null, "#0,0 #1,0 +", null);    
    stm.putCell(cell);    
    
    JTable table = new JTable();
    table.setModel(stm);
    getContentPane().add(table, BorderLayout.CENTER);
  }

  public void actionPerformed(ActionEvent event) {
    WindowEvent windowClosingEvent;
    windowClosingEvent = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
    this.dispatchEvent(windowClosingEvent);
  }

  public static void main(String[] args) {
    SpreadSheet ss = new SpreadSheet();
    ss.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    ss.setSize(400,400);
    ss.setVisible(true);
  }
}
