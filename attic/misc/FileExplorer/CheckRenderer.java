

import java.awt.*;
import javax.swing.*;
import javax.swing.tree.*;
import common.ui.borders.*;

public class CheckRenderer implements TreeCellRenderer {
	
	private final static JIconCheckBox openedCB = new JIconCheckBox();
	private final static JIconCheckBox closedCB = new JIconCheckBox();
	private final static JIconCheckBox leafCB = new JIconCheckBox();
	
	public CheckRenderer() {
		openedCB.setIcon(UIManager.getIcon("Tree.openIcon"));
		closedCB.setIcon(UIManager.getIcon("Tree.closedIcon"));
		leafCB.setIcon(UIManager.getIcon("FileView.fileIcon"));
	}
	
	public Component getTreeCellRendererComponent (JTree tree, Object value,
        	boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        		
		SelectableNode node = (SelectableNode)value;
		JIconCheckBox selCB;	
		
		if (expanded && !leaf) {
			selCB = openedCB;
		} else if (!expanded && !leaf) {
			selCB = closedCB;
		} else {
   			selCB = leafCB;
		}
   
		selCB.setText(node.toString());
		selCB.setSelected(node.getState());
		if (selected)
			selCB.setBackground(UIManager.getColor("textHighlight"));
		else 
			selCB.setBackground(Color.lightGray);

		return selCB;
	}

}