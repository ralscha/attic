

import javax.swing.*;
import javax.swing.table.*;
import java.io.*;
import java.text.*;
import java.util.*;

public class DirectoryModel extends AbstractTableModel {

	private final static Object dirIcon = UIManager.get("FileView.directoryIcon");
	private final static Object fileIcon = UIManager.get("FileView.fileIcon");

	
	public final static String COL_TYPE = "Type";
	public final static String COL_NAME = "Name";
	public final static String COL_SELECTED = "Selected";
	public final static String COL_SIZE = "Size";
	public final static String COL_MODIFIED = "Modified";

	private final static String[] colNames = {COL_TYPE, COL_NAME, COL_SELECTED, COL_SIZE, COL_MODIFIED};
	private final static int changeableColumn = 2;
	private final static Class[] colClasses = {Icon.class, String.class, FileNode.class,
    		String.class, String.class};

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
	private TreeTableMediator mediator;
	
	public DirectoryModel(FileNode fileNode, TreeTableMediator ttm) {
		this.fileNode = fileNode;
		this.mediator = ttm;
	}

    public void setDirectory(FileNode node) {
    	if (!node.isLeaf()) {
			fileNode = node;
	    	fireTableDataChanged();
    	}
    }	
	
    public void setDirectory(int row) {
    	if (!fileNode.isLeaf()) {
    		FileNode tmpFN = fileNode.getChildren()[row];
    		if (!tmpFN.isLeaf()) {
    			fileNode = tmpFN;
				fireTableDataChanged();    		
    		}
    	}
    }
    
    public FileNode getNode() {
    	return fileNode;
    }
    
	public int getRowCount() {
		if (fileNode.isLeaf())
			return 1;
		else 
			return fileNode.getChildren().length;
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
		FileNode fn = fileNode.getChildren()[row];
			
		File file = fn.getFile();

		Object value = null;
		try {
			switch (column) {
				case 0:
					value = file.isFile() ? fileIcon : dirIcon;
					break;
				case 1:
					String name = file.getName();
					if (name.length() > 0)
						value = name;
					else
						value = file.getPath();
					break;
				case 2:
					value = fn;
					break;
				case 3:
					value = file.isFile() ? formatNumber(file.length()) : "";
					break;
				case 4:
					value = dateFormat.format(new Date(file.lastModified()));
					break;
			}
		} catch (SecurityException se) { }

		return value;

	}

	private String formatNumber(long len) {
		if (len < 1024) {
			return numberFormat.format(len)+"B";
		} else {
			len = len / 1024;
			if (len % 1024 != 0)
				len++;
				
			return numberFormat.format(len)+"KB"; 	
		}
			
		
	}
	
	public void setValueAt(Object value, int row, int column) {
		FileNode fn = fileNode.getChildren()[row];
		
		fn.setState(((Integer)value).intValue());
		
		mediator.subChecks(fn, fn.getState());
		mediator.parentCheckRec((SelectableNode)fn.getParent());		
		
		fireTableCellUpdated(row, column);
		mediator.updateTree(row, column);
		
	}

	public boolean isCellEditable(int row, int column) {
		
		if (column == changeableColumn)
			return true;
		else
			return false;
		
	}


}