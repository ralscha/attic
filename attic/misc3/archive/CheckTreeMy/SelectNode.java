package CheckTree;

import java.awt.*;
import javax.swing.*;
import javax.swing.tree.*;

public class SelectNode extends DefaultMutableTreeNode implements SelectableNode {
	private int state;
	private String text;

	public SelectNode(String label) {
		super(label);
		text = label;
		state = JTriStateCheckBox.DESELECTED;
	}
	
	public String getText() {
		return text;
	}
	
	public void setState(int state) {
		this.state = state;
	}
	
	public int getState() {
		return state;
	}

}