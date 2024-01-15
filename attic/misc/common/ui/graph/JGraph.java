package common.ui.graph;
/*
=====================================================================

  JGraph.java

  Created by Claude Duguay
  Copyright (c) 1999

=====================================================================
*/

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class JGraph extends JPanel implements MouseListener, MouseMotionListener,
KeyListener, FocusListener {
	protected CellRendererPane rendererPane = new CellRendererPane();
	protected GraphNodeRenderer nodeRenderer = new DefaultGraphNodeRenderer();
	protected GraphEdgeRenderer edgeRenderer = new DefaultGraphEdgeRenderer();
	protected GraphModel model = new DefaultGraphModel();

	protected boolean showStress;
	protected GraphNode selection;
	protected boolean selectionFixed;
	protected int focusIndex = 0;

	public JGraph() {
		addKeyListener(this);
		addFocusListener(this);
		addMouseListener(this);
	}

	public GraphModel getModel() {
		return model;
	}

	public void showStress(boolean show) {
		showStress = show;
	}
	
	public void setModel(GraphModel model) {
		this.model = model;
	}

	public void paintNode(Graphics g, GraphNode node, boolean hasFocus) {
		Component component = nodeRenderer.getGraphNodeRendererComponent(this, node, hasFocus);
		Dimension size = component.getPreferredSize();
		rendererPane.paintComponent(g, component, this, (int) node.getX() - (size.width / 2),
                            		(int) node.getY() - (size.height / 2), size.width, size.height);
	}

	public void paintEdge(Graphics g, GraphEdge edge) {
		Component component =
  		edgeRenderer.getGraphEdgeRendererComponent(this, edge, showStress);
		Dimension size = getSize();
		rendererPane.paintComponent(g, component, this, 0, 0, size.width, size.height);
	}

	public void paintComponent(Graphics g) {
		Dimension size = getSize();
		g.setColor(getBackground());
		g.fillRect(0, 0, size.width, size.height);
		for (int i = 0; i < model.getEdgeCount(); i++) {
			paintEdge(g, model.getEdge(i));
		}
		for (int i = 0; i < model.getNodeCount(); i++) {
			paintNode(g, model.getNode(i), hasFocus() && i == focusIndex);
		}
	}

	private double distance(Point point, GraphNode node) {
		double x = point.getX() - node.getX();
		double y = point.getY() - node.getY();
		return Math.sqrt(x * x + y * y);
	}

	public boolean isFocusTraversable() {
		return true;
	}

	public void focusLost(FocusEvent event) {
		repaint();
	}

	public void focusGained(FocusEvent event) {
		repaint();
	}

	public void keyTyped(KeyEvent event) {}
	public void keyReleased(KeyEvent event) {}
	public void keyPressed(KeyEvent event) {
		int code = event.getKeyCode();
		if (code == KeyEvent.VK_UP || code == KeyEvent.VK_LEFT) {
			focusIndex++;
			int max = model.getNodeCount() - 1;
			if (focusIndex > max)
				focusIndex = 0;
			repaint();
		}
		if (code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_DOWN) {
			focusIndex--;
			int max = model.getNodeCount() - 1;
			if (focusIndex < 0)
				focusIndex = max;
			repaint();
		}
		if (code == KeyEvent.VK_SPACE) {
			GraphNode node = model.getNode(focusIndex);
			node.setFixed(!node.isFixed());
			repaint();
		}
	}

	public void mouseClicked(MouseEvent event) {}
	public void mouseEntered(MouseEvent event) {}
	public void mouseExited(MouseEvent event) {}
	public void mouseMoved(MouseEvent event) {}

	public void mousePressed(MouseEvent event) {
		requestFocus();
		addMouseMotionListener(this);
		double closest = Double.MAX_VALUE;
		Point point = event.getPoint();
		for (int i = 0; i < model.getNodeCount(); i++) {
			GraphNode node = model.getNode(i);
			double dist = distance(point, node);
			if (dist < closest) {
				selection = node;
				closest = dist;
				focusIndex = i;
			}
		}
		boolean rightButton = SwingUtilities.isRightMouseButton(event);
		if (rightButton)
			selection.setFixed(!selection.isFixed());
		selectionFixed = selection.isFixed();
		selection.setFixed(true);
		selection.setX(point.getX());
		selection.setY(point.getY());
		repaint();
	}

	public void mouseReleased(MouseEvent event) {
		removeMouseMotionListener(this);
		selection.setX(event.getPoint().getX());
		selection.setY(event.getPoint().getY());
		selection.setFixed(selectionFixed);
		selection = null;
		repaint();
	}

	public void mouseDragged(MouseEvent event) {
		selection.setX(event.getPoint().getX());
		selection.setY(event.getPoint().getY());
		repaint();
	}
}
