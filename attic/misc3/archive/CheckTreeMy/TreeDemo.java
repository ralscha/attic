package CheckTree;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.plaf.metal.*;

import common.swing.*;
import java.io.*;

//swing classes
import javax.swing.text.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.tree.*;

public class TreeDemo extends JExitFrame {
	
	private DefaultMutableTreeNode root;
	private JTree tree;
	CheckListener ckListen;

	public TreeDemo() {
		super("Tree Demo");
		
		PlafPanel.setNativeLookAndFeel(true);
		
		JPanel jp = new JPanel(); // create interior panel
		jp.setLayout(new BorderLayout());
		getContentPane().add(jp);
		
		//create root node
		root = new SelectNode("Foods");
		
		//Root Checkbox
		File[] roots = File.listRoots();
		Vector rootVector = new Vector();
		for (int i = 0; i < roots.length; i++) {
			rootVector.addElement(roots[i].getPath());
		}
		JComboBox rootComboBox = new JComboBox(rootVector);
		rootComboBox.addActionListener(new ActionListener() {
      			public void actionPerformed(ActionEvent e) {
      				JComboBox cb = (JComboBox)e.getSource();
      				String rootString = (String)cb.getSelectedItem();
      				//FileNode fn = new FileNode(new File(rootString));
      				//tree.setModel(new DefaultTreeModel(fn));      				
      				tree.setModel(new FileSystemModel(rootString));
      			}});

		
		
		jp.add(rootComboBox, BorderLayout.NORTH);
		
		/*
		tree = new JTree(root); //create tree
		*/
		
		/*
		FileNode fn = new FileNode(new File((String)rootVector.get(0)));
		tree = new JTree(fn);
		*/
		
		tree = new JTree(new FileSystemModel((String)rootVector.get(0)));
		
		//sp.getViewport().add(tree);      //add to scroller
		tree.putClientProperty("JTree.lineStyle", "Angled");
	
		//create scroll pane
		JScrollPane sp = new JScrollPane(tree);
		jp.add(sp, BorderLayout.CENTER);

		//create 3 nodes, each with three sub nodes
		addNodes("Meats", "Beef", "Chicken", "Pork");
		addNodes("Vegies", "Broccolli", "Carrots", "Peas");
		addNodes("Desserts","Charlotte Russe", "Bananas Flambe","Peach Melba");
		CheckRenderer2 ckRend = new CheckRenderer2();
		
		
		tree.addMouseListener(new CheckMouseListener());
		tree.setCellRenderer(ckRend);
		
		//turn on root handle
		tree.setShowsRootHandles(true);

		tree.setBackground(Color.lightGray);
		setSize(200, 300);
		setVisible(true);
	}
	//----------------------------------------
	private void addNodes(String b, String n1, String n2, String n3) {
		SelectNode base = new SelectNode(b);
		root.add(base);
		SelectNode node = new SelectNode(n1);
		base.add(node);
		node = new SelectNode(n2);
		base.add(node);
		node = new SelectNode(n3);
		base.add(node);
	}
	//----------------------------------------
	static public void main(String[] argv) {
		new TreeDemo();
	}
}