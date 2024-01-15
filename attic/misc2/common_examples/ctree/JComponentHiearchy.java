package ctree;

import java.awt.*;

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

public class JComponentHiearchy extends JPanel implements ActionListener {
	ImageIcon icon = new ImageIcon( Toolkit.getDefaultToolkit().createImage(
	                         getClass().getResource("Objects.gif")));
	
	Border border = new CompoundBorder(new EtchedBorder(EtchedBorder.LOWERED),
                                   	new EmptyBorder(1, 2, 1, 2));

	JComponentTree tree;
	JButton north, south, east, west;
	JButton left, center, right;
	JButton straight, square;

	public JComponentHiearchy() {
		setLayout(new BorderLayout());
		tree = new JComponentTree(JComponentTree.EAST, JComponentTree.CENTER,
                          		JComponentTree.STRAIGHT, 20, 5);

		ComponentTreeNode jcomponent = tree.addNode(null, label("JComponnent"));

		ComponentTreeNode abstractbutton = tree.addNode(jcomponent, label("AbstractButton"));

		ComponentTreeNode jbutton = tree.addNode(abstractbutton, label("JButton"));

		ComponentTreeNode jmenuitem = tree.addNode(abstractbutton, label("JMenuItem"));

		ComponentTreeNode jmenu = tree.addNode(jmenuitem, label("JMenu"));

		ComponentTreeNode jcheckboxmenuitem = tree.addNode(jmenuitem, label("JCheckBoxMenuItem"));

		ComponentTreeNode jradiobuttonmenu = tree.addNode(jmenuitem, label("JRadioButtonMenu"));

		ComponentTreeNode jtogglebutton = tree.addNode(abstractbutton, label("JToggleButton"));

		ComponentTreeNode jcheckbox = tree.addNode(jtogglebutton, label("JCheckBox"));

		ComponentTreeNode jradiobutton = tree.addNode(jtogglebutton, label("JRadioButton"));

		ComponentTreeNode jcombobox = tree.addNode(jcomponent, label("JComboBox"));

		ComponentTreeNode jlabel = tree.addNode(jcomponent, label("JLabel"));

		ComponentTreeNode jlist = tree.addNode(jcomponent, label("JList"));

		ComponentTreeNode jmenubar = tree.addNode(jcomponent, label("JMenuBar"));

		ComponentTreeNode jpanel = tree.addNode(jcomponent, label("JPanel"));

		ComponentTreeNode jpopupmenu = tree.addNode(jcomponent, label("JPopupMenu"));

		ComponentTreeNode jscrollbar = tree.addNode(jcomponent, label("JScrollBar"));

		ComponentTreeNode jscrollpane = tree.addNode(jcomponent, label("JScrollPane"));

		ComponentTreeNode jtextcomponent = tree.addNode(jcomponent, label("JTextComponent"));

		ComponentTreeNode jtextarea = tree.addNode(jtextcomponent, label("JTextArea"));

		ComponentTreeNode jtextfield = tree.addNode(jtextcomponent, label("JTextField"));

		ComponentTreeNode jpasswordfield = tree.addNode(jtextfield, label("JPasswordField"));

		ComponentTreeNode jtextpane = tree.addNode(jtextcomponent, label("JTextPane"));

		ComponentTreeNode jeditorpane = tree.addNode(jtextpane, label("JEditorPane"));

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

	public JLabel label(String text) {
		JLabel label = new JLabel(text); //, icon, JLabel.LEFT);
		label.setBackground(Color.orange);
		label.setBorder(border);
		label.setOpaque(true);
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
		JFrame frame = new JFrame("JFC Components");
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add("Center", new JComponentHiearchy());
		frame.setBackground(Color.lightGray);
		frame.setBounds(0, 0, 640, 480);
		frame.setDefaultCloseOperation(3);
		frame.show();
	}

}