package CheckTree.CheckTree;

import java.awt.*;
import javax.swing.*;
import javax.swing.tree.*;

//TreeCellRenderer using a Checkbox
public class CheckRenderer extends JCheckBox implements TreeCellRenderer {
	
	Color bgray;
	public CheckRenderer() {
		super();
		bgray = Color.lightGray;
		bgray = bgray.brighter ();
	}
	
	public Component getTreeCellRendererComponent (JTree tree, Object value,
        	boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		SelectNode node = (SelectNode) value;
		setText(node.getText ());

		//set check box based on whether
		//the node is selected
		setSelected(node.getChecked ());

		//change the background if selected
		if (selected)
			setBackground(bgray);
		else
			setBackground(Color.lightGray);
		return this;
	}
}