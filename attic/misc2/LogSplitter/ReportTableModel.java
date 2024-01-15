

import javax.swing.*;
import javax.swing.table.*;
import java.util.*;


public class ReportTableModel extends AbstractTableModel {

	private String[] columnNames;

	private int[] alignments = {SwingConstants.LEFT, SwingConstants.RIGHT, SwingConstants.RIGHT};
	private int[] width = {80, 40, 40};
	
	
	Object[][] data ;
	
	public ReportTableModel(String[] title, String[] columnNames, Object[][] num) {
		
    this.columnNames = columnNames;
		data = new Object[title.length][3];
    
    for (int i = 0; i < title.length; i++) {
      data[i][0] = title[i];
      data[i][1] = num[i][0];
      data[i][2] = num[i][1];
    }

	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public int getRowCount() {
		return data.length;
	}

	public String getColumnName(int col) {
		return columnNames[col];
	}

	public Object getValueAt(int row, int col) {
		return data[row][col];
	}

	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
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