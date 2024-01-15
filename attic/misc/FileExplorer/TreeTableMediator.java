
import javax.swing.*;
import javax.swing.table.*;
import java.util.*;

public class TreeTableMediator {

	private JTree tree;
	private JTable table;
	private FileNode fileNode;
	private DirectoryModel directoryModel;
	
	public TreeTableMediator() {
		tree = null;
		table = null;
		fileNode = null;
		directoryModel = null;
	}
	
	public void setDirectoryModel(DirectoryModel dm) {
		if (fileNode != null) {
			dm.setDirectory(fileNode);			
		}
		directoryModel = dm;
	}
	
	public void setFileNode(FileNode node) {
		fileNode = node;
		if (directoryModel != null)
			directoryModel.setDirectory(node);
	}
	
	public void setTree(JTree tree) {
		this.tree = tree;
	}
	
	public void setTable(JTable table) {
		this.table = table;
	}
	
	public void updateTree(int row, int column) {
		tree.repaint();
	}
	
	public void updateTable() {
		directoryModel.fireTableDataChanged();
	}
	
	public void parentCheck(SelectableNode parent) {
		boolean fully = false;
		boolean partially = false;
		boolean de = false;

		Enumeration e = parent.children();

		while (e.hasMoreElements ()) {
			SelectableNode nd = (SelectableNode) e.nextElement();

			if (nd.getState() == JTriStateCheckBox.FULLY_SELECTED)
				fully = true;
			else if (nd.getState() == JTriStateCheckBox.PARTIALLY_SELECTED)
				partially = true;
			else if (nd.getState() == JTriStateCheckBox.DESELECTED)
				de = true;


			if (fully && !partially && !de)
				parent.setState(JTriStateCheckBox.FULLY_SELECTED);
			else if (de && !partially && !fully)
				parent.setState(JTriStateCheckBox.DESELECTED);
			else
				parent.setState(JTriStateCheckBox.PARTIALLY_SELECTED);
		}

	}

	
	public void parentCheckRec(SelectableNode parent) {
		boolean fully = false;
		boolean partially = false;
		boolean de = false;

		if (parent == null) return;
		
		Enumeration e = parent.children();

		while (e.hasMoreElements ()) {
			SelectableNode nd = (SelectableNode) e.nextElement();

			if (nd.getState() == JTriStateCheckBox.FULLY_SELECTED)
				fully = true;
			else if (nd.getState() == JTriStateCheckBox.PARTIALLY_SELECTED)
				partially = true;
			else if (nd.getState() == JTriStateCheckBox.DESELECTED)
				de = true;


			if (fully && !partially && !de)
				parent.setState(JTriStateCheckBox.FULLY_SELECTED);
			else if (de && !partially && !fully)
				parent.setState(JTriStateCheckBox.DESELECTED);
			else
				parent.setState(JTriStateCheckBox.PARTIALLY_SELECTED);
		}
		
		parentCheckRec((SelectableNode)parent.getParent());
	
	}
	
	
	public void subChecks(SelectableNode node, int state) {
		node.setState(state); //current node

		//get any child nodes
		Enumeration e = node.children();
		//call this routine recursively
		//until no more childrn
		if (e.hasMoreElements ()) {
			while (e.hasMoreElements ()) {
				SelectableNode nd = (SelectableNode) e.nextElement();
				subChecks(nd, state);
			}
		} else
			node.setState(state); //last node
	}
	
}