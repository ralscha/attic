
package CheckTree;

import javax.swing.event.TreeModelListener;
import java.io.File;
import java.util.*;
import java.text.*;
import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.table.TableModel;
import com.klg.jclass.util.treetable.*;

public class FileSystemModel implements JCTreeTableModel {

	protected Object root;
	
	private final static String[]  colNames = {"Name", "Selected",  "Size", "Type", "Modified"};
	private final static Class[]  colClasses = {String.class, FileNode.class, String.class, String.class, String.class};
	private final static int[] alignments = {SwingConstants.LEFT, SwingConstants.CENTER,
													SwingConstants.RIGHT, SwingConstants.LEFT,
													SwingConstants.LEFT};


	private final static JTriStateCheckBox triStateCB = new JTriStateCheckBox();
	private final static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
	private final static DecimalFormat numberFormat;
	
	static {
		numberFormat = new DecimalFormat("#,###");
		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		dfs.setDecimalSeparator('.');
		dfs.setGroupingSeparator('\'');
		numberFormat.setDecimalFormatSymbols(dfs);
	}
	
	public FileSystemModel() {
		root = new FileNode(new File(File.separator));
	}

	public FileSystemModel(String path) {
		root = new FileNode(new File(path));
	}
	
	///////////////////
	// JCTreeTableModel
	///////////////////
	public Class getColumnClass(int column) {
		return colClasses[column];
	}
	
	public int getColumnCount() {
		return colNames.length;
	}
	
	public String getColumnName(int column) {
		return colNames[column];
	}
	public Object getValueAt(Object node, int column) {
		FileNode fn = (FileNode)node;
		File file = fn.getFile();
		
		Object value = null;
		try {
			switch(column) {
			case 0:
				value = file.getName();
				break;
			case 1:
				value = fn;
				break;				
			case 2:
				value = file.isFile() ? numberFormat.format(file.length()) : "";
				break;
		    case 3:
				value = file.isFile() ?  "File" : "Dir";
				break;
		    case 4:
				value = dateFormat.format(new Date(file.lastModified()));
				break;
		    }
		}
		catch (SecurityException se) {	}
		
		return value;	
	}
	
	public void setValueAt(Object value, Object node, int column) {}
		
	public boolean isCellEditable(Object node, int column) {
		return false;
	}
	
	///////////
	//TreeModel
	///////////
	
	public Object getRoot() {
		return root;
	}
	
	public Object getChild(Object parent, int index) {
		TreeNode tn = (TreeNode)parent;
		return tn.getChildAt(index);
	}

	public int getChildCount(Object parent) {
		TreeNode tn = (TreeNode)parent;
		return tn.getChildCount();		
	}

	public boolean isLeaf(Object node) {
		TreeNode tn = (TreeNode)node;
		return tn.isLeaf();
	}

	public void valueForPathChanged(TreePath path, Object newValue) { }

	public int getIndexOfChild(Object parent, Object child) {
		TreeNode tn = (TreeNode)parent;
		return tn.getIndex((TreeNode)child);
	}

	public void addTreeModelListener(TreeModelListener l) { }
	public void removeTreeModelListener(TreeModelListener l) { }
	
	//Misc
	public static int getAlignment(int column) {
		return alignments[column];
	}

}