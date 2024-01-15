package CheckTree.ImageTree;


import java.awt.*;
import javax.swing.*;
import javax.swing.tree.*;

//cell renderer using check box images
public class CheckRenderer extends JLabel implements TreeCellRenderer {
	Color bgray;
	ImageIcon check, uncheck;
	public CheckRenderer() {
		super();
		//read in checkbox icons

		check = new ImageIcon(
          		Toolkit.getDefaultToolkit().createImage(getClass().getResource("check.gif")));
		uncheck = new ImageIcon(
            		Toolkit.getDefaultToolkit().createImage(getClass().getResource("uncheck.gif")));


		setHorizontalTextPosition(RIGHT);
	}
	public Component getTreeCellRendererComponent(JTree tree, Object value,
        	boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		//get the current node
		SelectNode node = (SelectNode) value;
		//set its text
		setText(node.getText ());
		//set one icon or the other
		if (node.getChecked ())
			setIcon(check);
		else
			setIcon(uncheck);

		return this; //return this instance
	}
}