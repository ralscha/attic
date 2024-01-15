package gtf.usermanager;

import javax.swing.*;
import javax.swing.table.*;
import java.util.*;
import java.sql.*;
import gtf.common.*;
import gtf.db.*;

class PriviledgesTableModel extends AbstractTableModel {

	final String[] columnNames = {"Priviledges",""};

	private int[] alignments = {SwingConstants.LEFT, SwingConstants.CENTER};
	private int[] width = {80, 15};
	
	private Set priviledges;
	
	Object[][] data ;
	
	public PriviledgesTableModel() {
		initPriviledges();
		
		data = new Object[priviledges.size()][2];

		int i = 0;
		Iterator it = priviledges.iterator();
		while (it.hasNext()) {
			String priv = (String)it.next();
			data[i][0] = priv;
			data[i++][1] = new Boolean(false);
		}

	}

	public void setSelected(String priv) {
		Set userPriv = new TreeSet();
		StringTokenizer st = new StringTokenizer(priv.trim());
		while (st.hasMoreTokens()) {
       	userPriv.add(st.nextToken());
     	}

		for (int i = 0; i < data.length; i++) {
			if (userPriv.contains((String)data[i][0]))
				data[i][1] = new Boolean(true);
			else
				data[i][1] = new Boolean(false);
		}
		fireTableDataChanged();
	
	}
	
	public void clearSelected() {
		for (int i = 0; i < data.length; i++) {
			if (((Boolean)data[i][1]).booleanValue()) {
				data[i][1] = new Boolean(false);
			}
		}
		fireTableDataChanged();
	}
	
	public String getSelected() {
		
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < data.length; i++) {
			if (((Boolean)data[i][1]).booleanValue()) {
				sb.append((String)data[i][0]).append(" ");
			}
		}
		return sb.toString().trim();
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
		if (col == 0) {
			return false;
		} else {
			return true;
		}
	}

	public int getAlignment(int col) {
		return alignments[col];
	} 

	public int getWidth(int col) {
		return width[col];
	} 
	
	/*
	  * Don't need to implement this method unless your table's
	  * data can change.
	  */
	public void setValueAt(Object value, int row, int col) {
		//System.out.println("Setting value at " + row + "," + col + " to " + value +
       //            		" (an instance of " + value.getClass() + ")");

		data[row][col] = value;
		fireTableCellUpdated(row, col);
	}

	private void initPriviledges() {
		priviledges = new TreeSet();
		ServiceCenter sc = null;
		try {
			sc = new ServiceCenter("ZRH");
		} catch (Exception e) {
			System.err.println(e);
		}
		try {
			USERTable ut = new USERTable(sc.getConnection());
			Iterator it = ut.select(null, "PRIVILEDGES");
			while(it.hasNext()) {
				USER user = (USER)it.next();
				
				StringTokenizer st = new StringTokenizer(user.getPRIVILEDGES().trim());
				while (st.hasMoreTokens()) {
         			priviledges.add(st.nextToken());
     			}
			}
		} catch (SQLException sqle) {
			System.err.println(sqle);
		}

	}
	
}