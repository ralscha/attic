package common.ui.ctree;

import java.awt.Component;

import javax.swing.tree.DefaultMutableTreeNode;

public class ComponentTreeNode extends DefaultMutableTreeNode {
	public ComponentTreeNode(Component obj) {
		super(obj);
	}

	public ComponentTreeNode(Component obj, boolean allowsChildren) {
		super(obj, allowsChildren);
	}

	public Component getComponent() {
		return (Component) getUserObject();
	}
}