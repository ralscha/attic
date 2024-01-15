package common.ui.borders;


import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.basic.*;

public class DragBorder extends PatternBorder implements Border {
	protected boolean corners = true;
	protected boolean sides = true;
	protected int thickness = 1;

	public DragBorder() {
		this(Color.black, 7, true, true);
	}

	public DragBorder(Color color) {
		this(color, 7, true, true);
	}

	public DragBorder(Color color, int thickness) {
		this(color, thickness, false, false);
	}

	public DragBorder(Color color, int thickness, boolean corners, boolean sides) {
		super(BACKSLASH, color, thickness);
		this.thickness = thickness;
		this.corners = corners;
		this.sides = sides;
	}

	private void drawAnchor(Graphics g, int x, int y) {
		int t = thickness - 1;
		g.setColor(Color.white);
		g.fillRect(x, y, t, t);
		g.setColor(Color.black);
		g.drawRect(x, y, t, t);
	}

	public void paintBorder(Component component, Graphics g, int x, int y, int w, int h) {
		super.paintBorder(component, g, x, y, w, h);
		int r = w - thickness;
		int b = h - thickness;
		if (corners) {
			drawAnchor(g, x, y);
			drawAnchor(g, r, y);
			drawAnchor(g, x, b);
			drawAnchor(g, r, b);
		}
		int c = x + (w - thickness) / 2;
		int m = y + (h - thickness) / 2;
		if (sides) {
			drawAnchor(g, x, m);
			drawAnchor(g, r, m);
			drawAnchor(g, c, y);
			drawAnchor(g, c, b);
		}
	}
}
