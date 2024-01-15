

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;
import java.util.*;

//Tree listener for a Mouse Press
public class CheckMouseListener extends MouseAdapter implements ActionListener {
 
	private JPopupMenu popup;
	private JMenuItem selectMenuItem, deselectMenuItem;
	private static final String SELECT_CMD = "select";
	private static final String DESELECT_CMD = "deselect";
	private SelectableNode node;
	private JTree tree;
	private TreePath selPath;
	private TreeTableMediator mediator;
	
	public CheckMouseListener(TreeTableMediator ttm) {
		node = null;
		popup = new JPopupMenu();
		selectMenuItem = new JMenuItem("select");
		selectMenuItem.setActionCommand(SELECT_CMD);
		selectMenuItem.addActionListener(this);
		popup.add(selectMenuItem);
		deselectMenuItem = new JMenuItem("deselect");
		deselectMenuItem.setActionCommand(DESELECT_CMD);
		deselectMenuItem.addActionListener(this);
		popup.add(deselectMenuItem);
		
		mediator = ttm;
	}

	public void actionPerformed(ActionEvent ae) {

		if (node != null) {
			if (node.getState() == JTriStateCheckBox.FULLY_SELECTED)
				mediator.subChecks(node, JTriStateCheckBox.DESELECTED);
			else if (node.getState() == JTriStateCheckBox.PARTIALLY_SELECTED)
				mediator.subChecks(node, JTriStateCheckBox.DESELECTED);
			else
				mediator.subChecks(node, JTriStateCheckBox.FULLY_SELECTED);

			for (int i = selPath.getPathCount() - 1; i >= 0; i--) {
				SelectableNode parent = (SelectableNode) selPath.getPathComponent(i);
				if (parent != node) {
					mediator.parentCheck(parent);
				}
			}


			tree.repaint();


		}
	}


	public void mousePressed(MouseEvent e) {
		maybeShowPopup(e);
	}

	public void mouseReleased(MouseEvent e) {
		maybeShowPopup(e);
	}

	private void maybeShowPopup(MouseEvent e) {
		if (e.isPopupTrigger()) {


			tree = (JTree) e.getSource();
			//int selRow = tree.getRowForLocation(e.getX(), e.getY());
			selPath = tree.getPathForLocation(e.getX(), e.getY());

			if (selPath != null) {
				node = (SelectableNode) selPath.getLastPathComponent();


				if ((node.getState() == JTriStateCheckBox.FULLY_SELECTED) ||
    					(node.getState() == JTriStateCheckBox.PARTIALLY_SELECTED)) {
					selectMenuItem.setEnabled(false);
					deselectMenuItem.setEnabled(true);
				} else {
					selectMenuItem.setEnabled(true);
					deselectMenuItem.setEnabled(false);
				}

				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		}
	}



	
}