
package CheckTree;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;
import java.util.*;

//Tree listener for a Mouse Press
public class CheckMouseListener extends MouseAdapter {

	public void mouseClicked(MouseEvent e) {
		JTree tree = (JTree) e.getSource();
		//int selRow = tree.getRowForLocation(e.getX(), e.getY());
		TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());
		
		if (selPath != null) {
			SelectableNode node = (SelectableNode)selPath.getLastPathComponent();
			
			/*
			if (node != null) {
				if (node.getState() == JTriStateCheckBox.FULLY_SELECTED)
					subChecks(node, JTriStateCheckBox.DESELECTED);
				else if (node.getState() == JTriStateCheckBox.PARTIALLY_SELECTED)
					subChecks(node, JTriStateCheckBox.DESELECTED);
				else
					subChecks(node, JTriStateCheckBox.FULLY_SELECTED);
				
				for (int i = selPath.getPathCount()-1; i >= 0; i--) {
					SelectableNode parent = (SelectableNode)selPath.getPathComponent(i);
					if (parent != node) {
						parentCheck(parent);
					}
				}
					
					
				tree.repaint();
			}
			*/
		}
	}


	private void parentCheck(SelectableNode parent) {
		boolean fully = false;
		boolean partially = false;
		boolean de = false;
		
		Enumeration e = parent.children();

		while (e.hasMoreElements ()) {
			SelectableNode nd = (SelectableNode)e.nextElement();
			
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
	
	private void subChecks(SelectableNode node, int state) {
		node.setState(state); //current node

		//get any child nodes
		Enumeration e = node.children();
		//call this routine recursively
		//until no more childrn
		if (e.hasMoreElements ()) {
			while (e.hasMoreElements ()) {
				SelectableNode nd = (SelectableNode)e.nextElement();
				subChecks(nd, state);
			}
		} else
			node.setState(state); //last node
	}
}