package ctree;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.BorderLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import common.ui.ctree.*;

public class JComponentTreeTest extends JPanel implements ActionListener {
	LineBorder border = new LineBorder(Color.black, 1);

	JComponentTree tree;
	JButton north, south, east, west;
	JButton left, center, right;
	JButton straight, square;

	public JComponentTreeTest() {
		setLayout(new BorderLayout());

		tree = new JComponentTree();
		randomTree(tree, 3, 3);

		add("Center",
    		new JScrollPane(tree, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                    		JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS));

		JPanel dir = new JPanel();
		dir.setLayout(new GridLayout(1, 4));
		dir.add(north = new JButton("North"));
		north.addActionListener(this);
		dir.add(west = new JButton("West"));
		west.addActionListener(this);
		dir.add(east = new JButton("East"));
		east.addActionListener(this);
		dir.add(south = new JButton("South"));
		south.addActionListener(this);

		JPanel align = new JPanel();
		align.setLayout(new GridLayout(1, 3));
		align.add(left = new JButton("Left/Top"));
		left.addActionListener(this);
		align.add(center = new JButton("Center"));
		center.addActionListener(this);
		align.add(right = new JButton("Right/Bottom"));
		right.addActionListener(this);

		JPanel lines = new JPanel();
		lines.setLayout(new GridLayout(1, 3));
		lines.add(straight = new JButton("Straight"));
		straight.addActionListener(this);
		lines.add(square = new JButton("Square"));
		square.addActionListener(this);

		JPanel control = new JPanel();
		control.setLayout(new GridLayout(3, 1));
		control.add(lines);
		control.add(align);
		control.add(dir);
		add("North", control);
	}

	public void randomTree(JComponentTree tree, int depth, int width) {
		ComponentTreeNode root = tree.addNode(null, new JLabel("Root Node"));
		randomSubTree(tree, root, depth, width);
	}

	public void randomSubTree(JComponentTree tree, ComponentTreeNode parent, int depth,
                          	int width) {
		if (depth <= 0)
			return;
		int count = (int)(Math.random() * width) + 1;
		ComponentTreeNode child;
		for (int i = 0; i < count; i++) {
			child = randomComponent(tree, parent);
			randomSubTree(tree, child, depth - 1, width);
		}
	}

	public ComponentTreeNode randomComponent(JComponentTree tree, ComponentTreeNode parent) {
		int r = (int)(Math.random() * 4);
		JComponent c;
		switch (r) {
			case 0:
				c = new JButton("Button");
				break;
			case 1:
				c = new JCheckBox("Check Box", true);
				break;
			case 2:
				JComboBox box = new JComboBox();
				box.addItem("One");
				box.addItem("Two");
				box.addItem("Three");
				box.addItem("Four");
				c = box;
				break;
			default:
				c = new JLabel("Label");
				c.setBorder(border);
		}
		return tree.addNode(parent, c);
	}

	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		if (source == north)
			tree.setDirection(JComponentTree.NORTH);
		if (source == south)
			tree.setDirection(JComponentTree.SOUTH);
		if (source == east)
			tree.setDirection(JComponentTree.EAST);
		if (source == west)
			tree.setDirection(JComponentTree.WEST);

		if (source == left)
			tree.setAlignment(JComponentTree.LEFT);
		if (source == center)
			tree.setAlignment(JComponentTree.CENTER);
		if (source == right)
			tree.setAlignment(JComponentTree.RIGHT);

		if (source == straight)
			tree.setLineType(JComponentTree.STRAIGHT);
		if (source == square)
			tree.setLineType(JComponentTree.SQUARE);
	}

	public static void main(String[] args) {
		common.swing.PlafPanel.setNativeLookAndFeel(true);
		JFrame frame = new JFrame("JComponentTree Test");
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add("Center", new JComponentTreeTest());
		frame.setBackground(Color.lightGray);
		frame.setBounds(0, 0, 640, 480);
		frame.setDefaultCloseOperation(3);		
		frame.show();
	}

}