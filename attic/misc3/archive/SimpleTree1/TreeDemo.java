package CheckTree.SimpleTree;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import common.swing.*;

import javax.swing.text.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.tree.*;

public class TreeDemo extends JExitFrame implements ActionListener {
	private DefaultMutableTreeNode root;
	private JCheckBox jcb;
	private JTree tree;

	public TreeDemo() {
		super("Tree Demo");
		PlafPanel.setNativeLookAndFeel(false);

		JPanel jp = new JPanel(); // create interior panel
		jp.setLayout(new BorderLayout());
		getContentPane().add(jp);

	

		//create root node
		root = new DefaultMutableTreeNode("Foods");
		tree = new JTree(root); //create tree
		tree.putClientProperty("JTree.lineStyle", "Angled");
		
		//create scroll pane
		JScrollPane sp = new JScrollPane(tree);
		jp.add("Center", sp);
		
		//sp.getViewport().add(tree); //add to scroller

		//create 3 nodes, each with three sub nodes
		addNodes("Meats", "Beef", "Chicken", "Pork");
		addNodes("Vegies", "Broccolli", "Carrots", "Peas");
		addNodes("Desserts","Charlotte Russe", "Bananas Flambe","Peach Melba");
		tree.expandPath (new TreePath(root)); //add bottom panel
		JPanel jb = new JPanel();
		jcb = new JCheckBox("Handles", false);
		jcb.addActionListener(this);
		jb.add(jcb);
		jp.add("South", jb);

		setSize(200, 300);
		setVisible(true);
	}
	//----------------------------------------
	public void actionPerformed(ActionEvent e) {
		tree.setShowsRootHandles (jcb.isSelected ());
	}
	//----------------------------------------
	private void addNodes(String b, String n1, String n2, String n3) {
		DefaultMutableTreeNode base = new DefaultMutableTreeNode(b);
		root.add(base);
		base.add(new DefaultMutableTreeNode(n1));
		base.add(new DefaultMutableTreeNode(n2));
		base.add(new DefaultMutableTreeNode(n3));
	}
	//----------------------------------------
	static public void main(String[] argv) {
		new TreeDemo();
	}
}