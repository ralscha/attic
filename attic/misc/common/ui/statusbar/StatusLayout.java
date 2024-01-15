package common.ui.statusbar;

import java.awt.*;
import java.util.*;
import common.ui.layout.AbstractLayout;

public class StatusLayout extends AbstractLayout {
	protected Hashtable table = new Hashtable();

	public StatusLayout() {}

	public StatusLayout(int hgap, int vgap) {
		super(hgap, vgap);
	}

	public void addLayoutComponent(Component comp, Object constraints) {
		if (constraints instanceof StatusArea) {
			table.put(comp, constraints);
		} else
			throw new IllegalArgumentException("Constraint parameter must be of type StatusArea");
	}

	public void removeLayoutComponent(Component comp) {
		table.remove(comp);
	}

	public Dimension minimumLayoutSize(Container parent) {
		Insets insets = parent.getInsets();
		int count = parent.getComponentCount();
		int width = 0;
		int height = 0;
		for (int i = 0; i < count; i++) {
			if (i > 0)
				width += hgap;
			Component child = parent.getComponent(i);
			Dimension size = child.getMinimumSize();
			width += size.width;
			if (size.height > height)
				height = size.height;
		}
		return new Dimension(width + insets.left + insets.right,
                     		height + insets.top + insets.bottom);
	}

	public Dimension preferredLayoutSize(Container parent) {
		Insets insets = parent.getInsets();
		int count = parent.getComponentCount();
		int width = 0;
		int height = 0;
		for (int i = 0; i < count; i++) {
			if (i > 0)
				width += hgap;
			Component child = parent.getComponent(i);
			Dimension size = child.getPreferredSize();
			width += size.width;
			if (size.height > height)
				height = size.height;
		}
		return new Dimension(width + insets.left + insets.right,
                     		height + insets.top + insets.bottom);
	}

	public void layoutContainer(Container parent) {
		Insets insets = parent.getInsets();

		int count = parent.getComponentCount();
		int gaps = hgap * ((count > 0) ? count - 1 : 0);
		// Count fixed width and relative total
		int fixedWidth = 0;
		float relativeTotal = 0;
		for (int i = 0; i < count; i++) {
			Component child = parent.getComponent(i);
			Dimension size = child.getPreferredSize();

			if (table.containsKey(child)) {
				StatusArea constraint = (StatusArea) table.get(child);
				if (!constraint.isRelativeWidth()) {
					fixedWidth += (int) constraint.getRequiredWidth(child);
				} else {
					relativeTotal += constraint.getRequiredWidth(child);
				}
			} else
				fixedWidth += child.getPreferredSize().width;
		}
		int availableWidth =
  		parent.getSize().width - fixedWidth - gaps - (insets.left + insets.right);

		// Now do the layout
		int position = insets.left;
		int height = parent.getSize().height - (insets.top + insets.bottom);
		int width;
		for (int i = 0; i < count; i++) {
			Component child = parent.getComponent(i);
			Dimension size = child.getPreferredSize();
			if (table.containsKey(child)) {
				StatusArea constraint = (StatusArea) table.get(child);
				if (!constraint.isRelativeWidth()) {
					width = (int) constraint.getRequiredWidth(child);
				} else {
					float required = constraint.getRequiredWidth(child);
					width = (int)(required / relativeTotal * availableWidth);
				}
			} else {
				width = child.getPreferredSize().width;
			}
			child.setBounds(position, insets.top, width, height);
			position += width + hgap;
		}
	}
}

