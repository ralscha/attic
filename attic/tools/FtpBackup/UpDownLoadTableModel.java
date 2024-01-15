import javax.swing.table.*;
import java.util.*;
import common.util.*;

public class UpDownLoadTableModel extends AbstractTableModel {
	private final String[] columnNames = {"Path","File", "Include"};
	private Object[][] data ;
	
	public UpDownLoadTableModel() {
		List fileList = AppProperties.getStringArrayProperty("file");
		List pathList = AppProperties.getStringArrayProperty("path");
		
		data = new Object[fileList.size()][columnNames.length];
		for (int i = 0; i < fileList.size(); i++) {
			data[i][0] = pathList.get(i);
			data[i][1] = fileList.get(i);
			data[i][2] = new Boolean(true);
		}		
	}
	
	public boolean isFileInclude() {
		for (int i = 0; i < getRowCount(); i++) {
			if (isInclude(i))
				return true;
		}
		return false;
	}
	
	public boolean isInclude(int index) {
		return ((Boolean)data[index][2]).booleanValue();
	}
	
	public String getPath(int index) {
		return (String)data[index][0];
	}
	
	public String getFileName(int index) {
		return (String)data[index][1];
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
	
	   if (col == 2) { 
	       return true;
	   } else {
	       return false;
	   }
	}
	
	public void setValueAt(Object value, int row, int col) {
		data[row][col] = value;
	}
	
}