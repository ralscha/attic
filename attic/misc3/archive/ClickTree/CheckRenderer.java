package CheckTree.ClickTree;

import java.awt.*;
import javax.swing.*;
import javax.swing.tree.*;

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
		setSelected(node.getChecked ());
		if (selected)
			setBackground(bgray);
		else
			setBackground(Color.lightGray);

		return this;
	}

}