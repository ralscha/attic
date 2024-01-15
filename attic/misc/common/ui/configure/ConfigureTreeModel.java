package common.ui.configure;

import java.util.Vector;
import java.util.Enumeration;
import java.util.StringTokenizer;

import javax.swing.tree.TreeNode;
import javax.swing.tree.DefaultTreeModel;

public class ConfigureTreeModel extends DefaultTreeModel {
	public ConfigureTreeModel() {
		super(new ConfigureTreeNode("properties"));
	}

	public void addPath(String path) {
		StringTokenizer tokenizer = new StringTokenizer(path, ".", false);

		ConfigureTreeNode node, next;
		node = (ConfigureTreeNode) getRoot();
		while (tokenizer.hasMoreTokens()) {
			String name = tokenizer.nextToken();
			next = node.getChild(name);
			if (next == null) {
				next = new ConfigureTreeNode(name);
				node.add(next);
			}
			node = next;
		}
	}

	public Vector getPaths() {
		Vector vector = new Vector();
		getPaths("", vector, (ConfigureTreeNode) getRoot());
		return vector;
	}

	public void getPaths(String path, Vector vector, ConfigureTreeNode node) {
		int count = node.getChildCount();
		ConfigureTreeNode child;
		for (int i = 0; i < count; i++) {
			child = (ConfigureTreeNode) node.getChildAt(i);
			String next, name = child.getName();
			if (path.length() == 0)
				next = name;
			else
				next = path + "." + name;
			vector.addElement(next);
			getPaths(next, vector, child);
		}
	}
}