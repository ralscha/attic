import javax.swing.*;
import javax.swing.table.*;
import java.util.*;
import java.sql.*;
import gtf.common.*;
import gtf.db.*;

class MyTableModel extends AbstractTableModel {

	final String[] columnNames = {"Priviledge","Selected"};
	private Set priviledges;
	
	Object[][] data ;
	
	public MyTableModel(Set selected) {
		initPriviledges();
		
		data = new Object[priviledges.size()][2];

		int i = 0;
		Iterator it = priviledges.iterator();
		while (it.hasNext()) {
			String priv = (String)it.next();
			data[i][0] = priv;
			
			if (selected.contains(priv))
				data[i++][1] = new Boolean(true);
			else
				data[i++][1] = new Boolean(false);
		}

	}

	public Set getSelected() {
		Set selected = new TreeSet();
		for (int i = 0; i < data.length; i++) {
			if (((Boolean)data[i][1]).booleanValue())
				selected.add(data[i][0]);
		}
		return selected;
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
			sc.closeConnection();
		} catch (SQLException sqle) {
			System.err.println(sqle);
		}

	}
	
}