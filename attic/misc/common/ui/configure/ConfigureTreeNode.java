package common.ui.configure;


import java.util.Enumeration;
import javax.swing.tree.DefaultMutableTreeNode;

public class ConfigureTreeNode extends DefaultMutableTreeNode {
	public ConfigureTreeNode(String name) {
		super(name);
	}

	public String getName() {
		return (String) getUserObject();
	}

	public ConfigureTreeNode getChild(String name) {
		ConfigureTreeNode node;
		Enumeration enum = children();
		while (enum.hasMoreElements()) {
			node = (ConfigureTreeNode) enum.nextElement();
			if (node.getName().equals(name))
				return node;
		}
		return null;
	}
}