package common.ui.ctree;

import java.awt.*;
import java.util.*;

import javax.swing.tree.*;
import javax.swing.event.*;

import common.ui.layout.*;

public class ComponentTreeLayout extends AbstractLayout implements ComponentTreeConstants {
	protected int direction = EAST;
	protected int alignment = CENTER;
	protected int linetype = STRAIGHT;
	protected Color linecolor, lineshadow;

	protected TreeModelListener listener;
	protected DefaultTreeModel model;

	public ComponentTreeLayout(TreeModelListener listener) {
		this(listener, SOUTH, CENTER, SQUARE, 10, 10);
	}

	public ComponentTreeLayout(TreeModelListener listener, int hgap, int vgap) {
		this(listener, SOUTH, CENTER, SQUARE, hgap, vgap);
	}

	public ComponentTreeLayout(TreeModelListener listener, int direction, int alignment,
                           	int linetype) {
		this(listener, direction, alignment, linetype, 10, 10);
	}

	public ComponentTreeLayout(TreeModelListener listener, int direction, int alignment,
                           	int linetype, int hgap, int vgap) {
		super(hgap, vgap);
		this.listener = listener;
		this.direction = direction;
		this.alignment = alignment;
		this.linetype = linetype;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public int getDirection() {
		return direction;
	}

	public void setAlignment(int alignment) {
		this.alignment = alignment;
	}

	public int getAlignment() {
		return alignment;
	}

	public void setLineType(int linetype) {
		this.linetype = linetype;
	}

	public int getLineType() {
		return linetype;
	}

	public void setLineColor(Color linecolor) {
		this.linecolor = linecolor;
	}

	public Color getLineColor() {
		return linecolor;
	}

	public void setLineShadow(Color lineshadow) {
		this.lineshadow = lineshadow;
	}

	public Color getLineShadow() {
		return lineshadow;
	}

	public void setRoot(ComponentTreeNode root) {
		setModel(new DefaultTreeModel(root));
	}

	public ComponentTreeNode getRoot() {
		return (ComponentTreeNode) model.getRoot();
	}

	public void setModel(DefaultTreeModel model) {
		this.model = model;
		model.nodeStructureChanged(getRoot());
	}

	public DefaultTreeModel getModel() {
		return model;
	}

	public void addNode(ComponentTreeNode parent, ComponentTreeNode child) {
		model.insertNodeInto(child, parent, parent.getChildCount());
	}

	public Dimension minimumLayoutSize(Container container) {
		Dimension dim = getMinimumSize(getRoot());
		Insets insets = container.getInsets();
		int vertInsets = insets.top + insets.bottom;
		int horzInsets = insets.left + insets.right;
		dim.width += horzInsets;
		dim.height += vertInsets;
		return dim;
	}

	private Dimension getMinimumSize(ComponentTreeNode node) {
		if (!node.getComponent().isVisible())
			return new Dimension(0, 0);

		Dimension dim = node.getComponent().getMinimumSize();
		Dimension minimumSize = new Dimension(dim.width, dim.height);

		int children = model.getChildCount(node);
		if (direction == EAST || direction == WEST) {
			int width = 0;
			int height = 0;
			if (children > 0) {
				Dimension size;
				ComponentTreeNode child;
				for (int i = 0; i < children; i++) {
					child = (ComponentTreeNode) model.getChild(node, i);
					if (child.getComponent().isVisible()) {
						size = getMinimumSize(child);
						height += size.height + vgap;
						if (size.width > width)
							width = size.width;
					}
				}
			}
			minimumSize = new Dimension(minimumSize.width + width + hgap,
                            			Math.max(minimumSize.height, height - vgap));
		}
		if (direction == NORTH || direction == SOUTH) {
			int width = 0;
			int height = 0;
			if (children > 0) {
				Dimension size;
				ComponentTreeNode child;
				for (int i = 0; i < children; i++) {
					child = (ComponentTreeNode) model.getChild(node, i);
					if (child.getComponent().isVisible()) {
						size = getMinimumSize(child);
						width += size.width + hgap;
						if (size.height > height)
							height = size.height;
					}
				}
			}
			minimumSize = new Dimension(Math.max(minimumSize.width, width - vgap),
                            			minimumSize.height + height + vgap);
		}
		return minimumSize;
	}

	public Dimension preferredLayoutSize(Container container) {
		Dimension dim = getPreferredSize(getRoot());
		Insets insets = container.getInsets();
		int vertInsets = insets.top + insets.bottom;
		int horzInsets = insets.left + insets.right;
		dim.width += horzInsets;
		dim.height += vertInsets;
		return dim;
	}

	private Dimension getPreferredSize(ComponentTreeNode node) {
		if (!node.getComponent().isVisible())
			return new Dimension(0, 0);

		Dimension dim = node.getComponent().getPreferredSize();
		Dimension preferredSize = new Dimension(dim.width, dim.height);

		int children = model.getChildCount(node);
		if (direction == EAST || direction == WEST) {
			int width = 0;
			int height = 0;
			if (children > 0) {
				Dimension size;
				ComponentTreeNode child;
				for (int i = 0; i < children; i++) {
					child = (ComponentTreeNode) model.getChild(node, i);
					if (child.getComponent().isVisible()) {
						size = getPreferredSize(child);
						height += size.height + vgap;
						if (size.width > width)
							width = size.width;
					}
				}
			}
			preferredSize = new Dimension(preferredSize.width + width + hgap,
                              			Math.max(preferredSize.height, height - vgap));
		}
		if (direction == NORTH || direction == SOUTH) {
			int width = 0;
			int height = 0;
			if (children > 0) {
				Dimension size;
				ComponentTreeNode child;
				for (int i = 0; i < children; i++) {
					child = (ComponentTreeNode) model.getChild(node, i);
					if (child.getComponent().isVisible()) {
						size = getPreferredSize(child);
						width += size.width + hgap;
						if (size.height > height)
							height = size.height;
					}
				}
			}
			preferredSize = new Dimension(Math.max(preferredSize.width, width - hgap),
                              			preferredSize.height + height + vgap);
		}
		return preferredSize;
	}

	public void layoutContainer(Container container) {
		Insets insets = container.getInsets();
		Dimension dim = container.getSize();
		Dimension prefered = getPreferredSize(getRoot());
		int vertInsets = insets.top + insets.bottom;
		int horzInsets = insets.left + insets.right;

		if (direction == WEST)
			layout(getRoot(), dim.width - insets.right, insets.top);
		if (direction == NORTH)
			layout(getRoot(), insets.left, dim.height - insets.bottom);
		if (direction == EAST || direction == SOUTH)
			layout(getRoot(), insets.left, insets.top);
	}

	public void layout(ComponentTreeNode node, int x, int y) {
		if (!node.getComponent().isVisible())
			return;

		int children = model.getChildCount(node);
		if (direction == EAST || direction == WEST) {
			Dimension size = node.getComponent().getPreferredSize();
			Dimension down = getLayoutSize(node);

			int pos = y;
			if (alignment == MIDDLE) {
				pos = y + (down.height - size.height) / 2;
			}
			if (alignment == BOTTOM) {
				pos = y + (down.height - size.height);
			}

			if (direction == EAST) {
				node.getComponent().setBounds(x, pos, size.width, size.height);
				x += Math.max(size.width, down.width) + hgap;
			} else {
				node.getComponent().setBounds(x - size.width, pos, size.width, size.height);
				x -= Math.max(size.width, down.width) + hgap;
			}

			ComponentTreeNode child;
			for (int i = 0; i < children; i++) {
				child = (ComponentTreeNode) model.getChild(node, i);
				if (child.getComponent().isVisible()) {
					layout(child, x, y);
					y += getLayoutSize(child).height + vgap;
				}
			}
		}
		if (direction == NORTH || direction == SOUTH) {
			Dimension size = node.getComponent().getPreferredSize();
			Dimension right = getLayoutSize(node);

			int pos = x;
			if (alignment == CENTER) {
				pos = x + (right.width - size.width) / 2;
			}
			if (alignment == RIGHT) {
				pos = x + (right.width - size.width);
			}

			if (direction == SOUTH) {
				node.getComponent().setBounds(pos, y, size.width, size.height);
				y += Math.max(size.height, right.height) + vgap;
			} else {
				node.getComponent().setBounds(pos, y - size.height, size.width, size.height);
				y -= Math.max(size.height, right.height) + vgap;
			}

			ComponentTreeNode child;
			for (int i = 0; i < children; i++) {
				child = (ComponentTreeNode) model.getChild(node, i);
				if (child.getComponent().isVisible()) {
					layout(child, x, y);
					x += getLayoutSize(child).width + hgap;
				}
			}
		}
	}

	private Dimension getLayoutSize(ComponentTreeNode node) {
		if (!node.getComponent().isVisible())
			return new Dimension(0, 0);

		Dimension dim = node.getComponent().getPreferredSize();
		Dimension layoutSize = new Dimension(dim.width, dim.height);

		int children = model.getChildCount(node);
		if (direction == EAST || direction == WEST) {
			int width = 0;
			int height = 0;
			if (children > 0) {
				Dimension size;
				ComponentTreeNode child;

				for (int i = 0; i < children; i++) {
					child = (ComponentTreeNode) model.getChild(node, i);
					if (child.getComponent().isVisible()) {
						size = getLayoutSize(child);
						height += size.height + vgap;
						if (size.width > width)
							width = size.width;
					}
				}
			}
			layoutSize = new Dimension(width, Math.max(layoutSize.height, height - vgap));
		}
		if (direction == NORTH || direction == SOUTH) {
			int width = 0;
			int height = 0;
			if (children > 0) {
				Dimension size;
				ComponentTreeNode child;
				for (int i = 0; i < children; i++) {
					child = (ComponentTreeNode) model.getChild(node, i);
					if (child.getComponent().isVisible()) {
						size = getLayoutSize(child);
						width += size.width + hgap;
						if (size.height > height)
							height = size.height;
					}
				}
			}
			layoutSize = new Dimension(Math.max(layoutSize.width, width - hgap), height);
		}
		return layoutSize;
	}

	/**
	  * This needs to be called from the container directly because there's
	  * no aparent way to make the container call it automatically.
	 **/
	public void drawLines(Container cont, Graphics g) {
		if (linecolor == null)
			linecolor = cont.getBackground().brighter();
		if (lineshadow == null)
			lineshadow = cont.getBackground().darker();
		drawLines(getRoot(), g, lineshadow, linecolor);
	}

	private void drawLines(ComponentTreeNode node, Graphics g, Color dark, Color lite) {
		if (!node.getComponent().isVisible())
			return;

		int children = model.getChildCount(node);
		if (direction == EAST || direction == WEST) {
			Rectangle dim = node.getComponent().getBounds();
			int x0, y0, x1, y1, x2, y2;

			if (direction == EAST) {
				x0 = dim.x + dim.width;
				x1 = x0 + hgap / 2;
				x2 = x0 + hgap - 1;
			} else {
				x0 = dim.x;
				x1 = x0 - hgap / 2;
				x2 = x0 - hgap + 1;
			}
			y0 = dim.y;
			y1 = dim.y + dim.height / 2;

			ComponentTreeNode child;
			for (int i = 0; i < children; i++) {
				child = (ComponentTreeNode) model.getChild(node, i);
				if (child.getComponent().isVisible()) {
					Rectangle bounds = child.getComponent().getBounds();
					y2 = bounds.y + bounds.height / 2;

					if (linetype == SQUARE) {
						drawLine(g, dark, lite, x0, y1, x1, y1);
						drawLine(g, dark, lite, x1, y2, x2, y2);
						if (y1 != y2)
							drawLine(g, dark, lite, x1, y1, x1, y2);
						if (i == 0) {
							if (alignment == LEFT)
								y0 = Math.min(y2, y1);
							if (alignment == RIGHT)
								y0 = Math.max(y2, y1);
						}
						if (children >= 1 && alignment != CENTER)
							drawLine(g, dark, lite, x1, y0, x1, y2);
					} else {
						drawLine(g, dark, lite, x0, y1, x2, y2);
					}
					drawLines(child, g, dark, lite);
				}
			}
		}
		if (direction == NORTH || direction == SOUTH) {
			Rectangle dim = node.getComponent().getBounds();
			int x0, y0, x1, y1, x2, y2;

			if (direction == SOUTH) {
				y0 = dim.y + dim.height;
				y1 = y0 + vgap / 2;
				y2 = y0 + vgap - 1;
			} else {
				y0 = dim.y;
				y1 = y0 - vgap / 2;
				y2 = y0 - vgap + 1;
			}
			x0 = dim.x;
			x1 = dim.x + dim.width / 2;

			ComponentTreeNode child;
			for (int i = 0; i < children; i++) {
				child = (ComponentTreeNode) model.getChild(node, i);
				if (child.getComponent().isVisible()) {
					Rectangle bounds = child.getComponent().getBounds();
					x2 = bounds.x + bounds.width / 2;

					if (linetype == SQUARE) {
						drawLine(g, dark, lite, x1, y0, x1, y1);
						drawLine(g, dark, lite, x2, y1, x2, y2);
						if (x1 != x2)
							drawLine(g, dark, lite, x1, y1, x2, y1);
						if (i == 0) {
							if (alignment == LEFT)
								x0 = Math.min(x2, x1);
							if (alignment == RIGHT)
								x0 = Math.max(x2, x1);
						}
						if (children >= 1 && alignment != CENTER)
							drawLine(g, dark, lite, x0, y1, x2, y1);
					} else {
						drawLine(g, dark, lite, x1, y0, x2, y2);
					}
					drawLines(child, g, dark, lite);
				}
			}
		}
	}

	private void drawLine(Graphics g, Color dark, Color lite, int x1, int y1, int x2, int y2) {
		g.setColor(lite);
		g.drawLine(x1, y1, x2, y2);
		g.setColor(dark);
		g.drawLine(x1 + 1, y1 + 1, x2 + 1, y2 + 1);
	}

}