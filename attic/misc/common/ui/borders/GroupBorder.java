package common.ui.borders;


import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;

public class GroupBorder extends AbstractBorder {
	protected Vector borders;

	public GroupBorder() {
		borders = new Vector();
	}

	public GroupBorder(Border[] list) {
		borders = new Vector(list.length);
		for (int i = 0; i < list.length; i++) {
			addBorder(list[i]);
		}
	}

	public void addBorder(Border border) {
		borders.addElement(border);
	}

	public void removeBorder(Border border) {
		borders.removeElement(border);
	}

	public void removeBorderAt(int index) {
		borders.removeElementAt(index);
	}

	public void replaceBorderAt(Border border, int index) {
		borders.setElementAt(border, index);
	}

	public void removeAllBorders() {
		borders.removeAllElements();
	}

	public Border getBorder(int index) {
		return (Border) borders.elementAt(index);
	}

	public int size() {
		return borders.size();
	}

	public Insets getBorderInsets(Component c) {
		Insets insets;
		Border border;
		int top = 0, left = 0, bottom = 0, right = 0;
		for (int i = 0; i < size(); i++) {
			border = getBorder(i);
			if (border != null) {
				insets = border.getBorderInsets(c);
				top += insets.top;
				left += insets.left;
				right += insets.right;
				bottom += insets.bottom;
			}
		}
		return new Insets(top, left, bottom, right);
	}

	public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
		Insets insets;
		Border border;
		for (int i = 0; i < size(); i++) {
			border = getBorder(i);
			if (border != null) {
				border.paintBorder(c, g, x, y, w, h);
				insets = border.getBorderInsets(c);
				x += insets.left;
				y += insets.top;
				w = w - insets.right - insets.left;
				h = h - insets.bottom - insets.top;
			}
		}
	}
}