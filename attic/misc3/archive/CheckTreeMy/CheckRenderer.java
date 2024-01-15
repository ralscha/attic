package CheckTree;

import java.awt.*;
import javax.swing.*;
import javax.swing.tree.*;
import common.ui.borders.*;

public class CheckRenderer extends JTriStateCheckBox implements TreeCellRenderer {
	
	public Component getTreeCellRendererComponent (JTree tree, Object value,
        	boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        		
		SelectableNode node = (SelectableNode)value;
		setText(node.toString());
		setSelected(node.getState());
		setBackground(Color.lightGray);
		return this;
	}

}