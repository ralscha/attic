package CheckTree;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;
import java.util.*;

//Tree listener for a Mouse Press
public class CheckListener implements TreeSelectionListener  {
	
	
    public void valueChanged(TreeSelectionEvent e) {

		JTree tree = (JTree)e.getSource();
		

		SelectNode node = (SelectNode)tree.getLastSelectedPathComponent ();
        
		if (node != null) {
			if (node.getState() == JTriStateCheckBox.FULLY_SELECTED)
				subChecks(node, JTriStateCheckBox.DESELECTED);
			else
				subChecks(node, JTriStateCheckBox.FULLY_SELECTED);
				
			tree.repaint();
		}
	}
	
	private void subChecks(SelectNode node, int state) {
		node.setState(state); //current node

		//get any child nodes
		Enumeration e = node.children ();

		//call this routine recursively
		//until no more childrn
		if (e.hasMoreElements ()) {
			while (e.hasMoreElements ()) {
				SelectNode nd = (SelectNode)e.nextElement ();
				subChecks(nd, state);
			}
		} else
			node.setState(state); //last node
	}
}