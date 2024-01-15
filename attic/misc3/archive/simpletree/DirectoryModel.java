

package simpletree;

import javax.swing.*;
import javax.swing.table.*;
import java.io.*;
import java.text.*;
import java.util.*;

public class DirectoryModel extends AbstractTableModel {

	private final static Object dirIcon = UIManager.get("FileView.directoryIcon");
	private final static Object fileIcon = UIManager.get("FileView.fileIcon");

	private final static String[] colNames = {"Type", "Name", "Size", "Modified"};
	private final static Class[] colClasses = {Icon.class, String.class, 
    		String.class, String.class};
	private final static int[] alignments = {SwingConstants.CENTER, SwingConstants.LEFT,
                                    		 SwingConstants.RIGHT, SwingConstants.LEFT};

	private final static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
	private final static DecimalFormat numberFormat;

	
	static {
		numberFormat = new DecimalFormat("#,###");
		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		dfs.setDecimalSeparator('.');
		dfs.setGroupingSeparator('\'');
		numberFormat.setDecimalFormatSymbols(dfs);
	}

	private FileNode fileNode;

	public DirectoryModel(FileNode fileNode) {
		this.fileNode = fileNode;
	}

    public void setDirectory(FileNode node) {
		fileNode = node;
    	fireTableDataChanged();
    }	
	
    public void setDirectory(int row) {
    	if (!fileNode.isLeaf()) {
    		fileNode = fileNode.getChildren()[row];
			fireTableDataChanged();    		
    	}
    }
    
    public FileNode getNode() {
    	return fileNode;
    }
    
	public int getRowCount() {
		FileNode children[] = fileNode.getChildren();
		if (fileNode.isLeaf())
			return 1;
		else 
			return children.length;
	}
	
	public Class getColumnClass(int column) {
		return colClasses[column];
	}

	public int getColumnCount() {
		return colNames.length;
	}

	public String getColumnName(int column) {
		return colNames[column];
	}

	public Object getValueAt(int row, int column) {
		FileNode fn;
		if (fileNode.isLeaf())
			fn = fileNode;
		else
			fn = fileNode.getChildren()[row];
			
		File file = fn.getFile();

		Object value = null;
		try {
			switch (column) {
				case 0:
					value = file.isFile() ? fileIcon : dirIcon;
					break;

				case 1:
					value = file.getName();
					break;
				case 2:
					value = file.isFile() ? numberFormat.format(file.length()) : "";
					break;
				case 3:
					value = dateFormat.format(new Date(file.lastModified()));
					break;
			}
		} catch (SecurityException se) { }

		return value;

	}


	public void setValueAt(Object value, Object node, int column) {}

	public boolean isCellEditable(Object node, int column) {
		return false;
	}


	//Misc
	public static int getAlignment(int column) {
		return alignments[column];
	}

}