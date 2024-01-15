package common.ui.ctree;

import java.awt.Color;
import java.awt.Insets;
import java.awt.Graphics;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.CellRendererPane;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeModelEvent;

public class JComponentTree extends JPanel implements ComponentTreeConstants,
TreeModelListener {
	protected ComponentTreeLayout treeLayout;
	protected CellRendererPane pane;

	public JComponentTree() {
		setBackground(Color.lightGray);
		setForeground(Color.black);
		treeLayout = new ComponentTreeLayout(this);
		setLayout(treeLayout);
	}

	public JComponentTree(int hgap, int vgap) {
		setBackground(Color.lightGray);
		setForeground(Color.black);
		treeLayout = new ComponentTreeLayout(this, hgap, vgap);
		setLayout(treeLayout);
	}

	public JComponentTree(int direction, int alignment, int linetype) {
		setBackground(Color.lightGray);
		setForeground(Color.black);
		treeLayout = new ComponentTreeLayout(this, direction, alignment, linetype);
		setLayout(treeLayout);
	}

	public JComponentTree(int direction, int alignment, int linetype, int hgap, int vgap) {
		setBackground(Color.lightGray);
		setForeground(Color.black);
		treeLayout = new ComponentTreeLayout(this, direction, alignment, linetype, hgap, vgap);
		setLayout(treeLayout);
	}

	public ComponentTreeNode addNode(ComponentTreeNode parent, Component child) {
		ComponentTreeNode node = new ComponentTreeNode(child);
		if (parent == null)
			setRoot(node);
		else
			treeLayout.addNode(parent, node);
		add(child);
		return node;
	}

	public void setDirection(int direction) {
		treeLayout.setDirection(direction);
		setSize(getPreferredSize());
		doLayout();
		repaint();
	}

	public int getDirection() {
		return treeLayout.getDirection();
	}

	public void setAlignment(int alignment) {
		treeLayout.setAlignment(alignment);
		doLayout();
		repaint();
	}

	public int getAlignment() {
		return treeLayout.getAlignment();
	}

	public void setLineType(int lineType) {
		treeLayout.setLineType(lineType);
		doLayout();
		repaint();
	}

	public int getLineType() {
		return treeLayout.getLineType();
	}

	public void setLineColor(Color linecolor) {
		treeLayout.setLineColor(linecolor);
		doLayout();
		repaint();
	}

	public Color getLineColor() {
		return treeLayout.getLineColor();
	}

	public void setLineShadow(Color lineshadow) {
		treeLayout.setLineShadow(lineshadow);
		doLayout();
		repaint();
	}

	public Color getLineShadow() {
		return treeLayout.getLineShadow();
	}

	public void setRoot(ComponentTreeNode root) {
		treeLayout.setRoot(root);
	}

	public ComponentTreeNode getRoot() {
		return treeLayout.getRoot();
	}

	public void setModel(DefaultTreeModel model) {
		treeLayout.setModel(model);
	}

	public DefaultTreeModel getModel() {
		return treeLayout.getModel();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		treeLayout.drawLines(this, g);
	}

	public Insets getInsets() {
		return new Insets(10, 10, 10, 10);
	}

	public void treeNodesChanged(TreeModelEvent event) {
		doLayout();
		repaint();
	}

	public void treeNodesInserted(TreeModelEvent event) {
		doLayout();
		repaint();
	}

	public void treeNodesRemoved(TreeModelEvent event) {
		doLayout();
		repaint();
	}

	public void treeStructureChanged(TreeModelEvent event) {
		doLayout();
		repaint();
	}

}