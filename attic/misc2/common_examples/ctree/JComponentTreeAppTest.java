package ctree;

import java.awt.Color;
import java.awt.Image;
import java.awt.GridLayout;
import java.awt.BorderLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;

import javax.swing.border.*;
import common.ui.ctree.*;

public class JComponentTreeAppTest extends JPanel implements ActionListener {
	ImageIcon icon = new ImageIcon("Objects.gif");
	Border border = new CompoundBorder(new EtchedBorder(EtchedBorder.LOWERED),
                                   	new EmptyBorder(2, 2, 2, 2));

	JComponentTree tree;
	JButton north, south, east, west;
	JButton left, center, right;
	JButton straight, square;

	public JComponentTreeAppTest() {
		setLayout(new BorderLayout());
		tree = new JComponentTree();

		ComponentTreeNode jframe = tree.addNode(null, label("JFrame", loadImage("JFrameColor32.gif")));

		ComponentTreeNode jmenubar = tree.addNode(jframe, label("JMenuBar", loadImage("JMenuBarColor32.gif")));

		ComponentTreeNode jmenu1 = tree.addNode(jmenubar, label("JMenu", loadImage("JMenuColor32.gif")));

		ComponentTreeNode jmenu2 = tree.addNode(jmenubar, label("JMenu", loadImage("JMenuColor32.gif")));

		ComponentTreeNode jdialog = tree.addNode(jframe, label("JDialog", loadImage("JDialogColor32.gif")));

		ComponentTreeNode jtabbedpane =
  		tree.addNode(jdialog, label("JTabbedPane", loadImage("JTabbedPaneColor32.gif")));

		ComponentTreeNode jbutton = tree.addNode(jdialog, label("JButton", loadImage("JButtonColor32.gif")));

		ComponentTreeNode jpanel1 = tree.addNode(jtabbedpane, label("JPanel", loadImage("JPanelColor32.gif")));

		ComponentTreeNode jlabel = tree.addNode(jpanel1, label("JLabel", loadImage("JLabelColor32.gif")));

		ComponentTreeNode jlist = tree.addNode(jpanel1, label("JList", loadImage("JListColor32.gif")));

		ComponentTreeNode jpanel2 = tree.addNode(jtabbedpane, label("JPanel", loadImage("JPanelColor32.gif")));

		ComponentTreeNode jslider = tree.addNode(jpanel2, label("JSlider", loadImage("JSliderColor32.gif")));

		ComponentTreeNode jcheckbox = tree.addNode(jpanel2, label("JCheckBox", loadImage("JCheckBoxColor32.gif")));

		ComponentTreeNode jinternalframe =
  		tree.addNode(jframe, label("JInternalFrame", loadImage("JInternalFrameColor32.gif")));

		ComponentTreeNode jeditorpane =
  		tree.addNode(jinternalframe, label("JEditorPane", loadImage("JEditorPaneColor32.gif")));

		ComponentTreeNode jpopupmenu =
  		tree.addNode(jinternalframe, label("JPopupMenu", loadImage("JPopupMenuColor32.gif")));

		ComponentTreeNode jtoolbar = tree.addNode(jframe, label("JToolBar", loadImage("JToolBarColor32.gif")));

		add("Center",
    		new JScrollPane(tree, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                    		JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS));

		JPanel dir = new JPanel();
		dir.setLayout(new GridLayout(1, 4));
		dir.add(north = new JButton("North"));
		dir.add(west = new JButton("West"));
		dir.add(east = new JButton("East"));
		dir.add(south = new JButton("South"));

		north.addActionListener(this);
		south.addActionListener(this);
		east.addActionListener(this);
		west.addActionListener(this);

		JPanel align = new JPanel();
		align.setLayout(new GridLayout(1, 3));
		align.add(left = new JButton("Left"));
		align.add(center = new JButton("Center"));
		align.add(right = new JButton("Right"));

		left.addActionListener(this);
		center.addActionListener(this);
		right.addActionListener(this);

		JPanel lines = new JPanel();
		lines.setLayout(new GridLayout(1, 3));
		lines.add(straight = new JButton("Straight"));
		lines.add(square = new JButton("Square"));

		straight.addActionListener(this);
		square.addActionListener(this);

		JPanel control = new JPanel();
		control.setLayout(new GridLayout(3, 1));
		control.add(lines);
		control.add(align);
		control.add(dir);
		add("North", control);
	}

	public ImageIcon loadImage(String file) {
		return new ImageIcon("/JavaClasses/classes/com/sun/java/swing/beaninfo/images/" + file);
	}

	public JLabel label(String text) {
		return label(text, icon);
	}

	public JLabel label(String text, ImageIcon icon) {
		JLabel label = new JLabel(text, icon, JLabel.CENTER);
		label.setVerticalAlignment(JLabel.TOP);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setVerticalTextPosition(JLabel.BOTTOM);
		label.setHorizontalTextPosition(JLabel.CENTER);
		//label.setBackground(Color.orange);
		//label.setBorder(border);
		//label.setOpaque(true);
		return label;
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
		JFrame frame = new JFrame("Application Hiearchy");
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add("Center", new JComponentTreeAppTest());
		frame.setBackground(Color.lightGray);
		frame.setBounds(0, 0, 640, 480);
		frame.setDefaultCloseOperation(3);
		frame.show();
	}

}