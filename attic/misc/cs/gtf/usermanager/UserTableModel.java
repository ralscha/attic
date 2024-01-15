package gtf.usermanager;

import javax.swing.table.*;
import javax.swing.*;
import java.sql.*;
import java.util.*;
import gtf.db.USER;
import gtf.db.USERTable;


class UserTableModel extends AbstractTableModel {
	private final String[] columnNames = {"User ID", "Name"};
	private int[] alignments = {SwingConstants.LEFT, SwingConstants.LEFT	};
	private int[] width = {80, 150};
	private List userList = null;
	
	public void populateData(Connection conn) throws SQLException {
		
		if (userList == null)
			userList = new ArrayList();
		else
			userList.clear();
		
		USERTable ut = new USERTable(conn);
		
		Iterator it = ut.select();
		while(it.hasNext()) {
			USER user = (USER)it.next();
			userList.add(user);
		}

		//fireTableDataChanged();

	} 

	public Object getValueAt(int row, int col) {
		try {
			
			if (col == 0)
				return ((USER)userList.get(row)).getUSERID().trim();
			else if (col == 1)
				return ((USER)userList.get(row)).getNAME().trim();
			else if (col == 2)
				return (USER)userList.get(row);
			else
				return null;
		} catch (IndexOutOfBoundsException ioob) {
			return null;
		}
	} 	
	
	public int getColumnCount() {
		return columnNames.length;
	} 

	public int getRowCount() {
		if (userList != null) {
			return userList.size();
		} else {
			return 0;
		} 
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