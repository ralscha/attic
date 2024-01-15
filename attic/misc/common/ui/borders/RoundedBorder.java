package common.ui.borders;


import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class RoundedBorder implements Border, BorderConstants {
	protected Color foreground, background;
	protected int thickness;
	protected int corners;
	protected int sides;

	public RoundedBorder() {
		this(Color.red, null, 7, ALL_CORNERS, ALL_SIDES);
	}

	public RoundedBorder(int thickness) {
		this(Color.red, null, thickness, ALL_CORNERS, ALL_SIDES);
	}

	public RoundedBorder(Color color, int thickness) {
		this(color, color, thickness, ALL_CORNERS, ALL_SIDES);
	}

	public RoundedBorder(Color foreground, Color background, int thickness, int corners,
                     	int sides) {
		this.foreground = foreground;
		this.background = background;
		this.thickness = thickness;
		this.corners = corners;
		this.sides = sides;
	}

	public boolean isBorderOpaque() {
		return false;
	}

	public Insets getBorderInsets(Component component) {
		boolean north = (sides & N_SIDE) == N_SIDE;
		boolean south = (sides & S_SIDE) == S_SIDE;
		boolean east = (sides & E_SIDE) == E_SIDE;
		boolean west = (sides & W_SIDE) == W_SIDE;
		return new Insets(north ? thickness : 0, west ? thickness : 0,
                  		south ? thickness : 0, east ? thickness : 0);
	}

	public Color getForeground(Component c) {
		if (foreground == null)
			foreground = c.getForeground();
		return foreground;
	}

	public Color getBackground(Component c) {
		if (background == null)
			background = c.getBackground();
		return background;
	}

	public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
		int thick = thickness;
		int diam = thickness * 2;

		boolean north = (sides & N_SIDE) == N_SIDE;
		boolean south = (sides & S_SIDE) == S_SIDE;
		boolean east = (sides & E_SIDE) == E_SIDE;
		boolean west = (sides & W_SIDE) == W_SIDE;

		boolean nw = (corners & NW_CORNER) == NW_CORNER;
		boolean ne = (corners & NE_CORNER) == NE_CORNER;
		boolean sw = (corners & SW_CORNER) == SW_CORNER;
		boolean se = (corners & SE_CORNER) == SE_CORNER;

		g.setColor(getForeground(c));

		if (north)
			g.fillRect(x + thick, y, x + w - thick * 2, y + thick);
		if (south)
			g.fillRect(x + thick, y + h - thick, x + w - thick * 2, y + thick);
		if (east)
			g.fillRect(x + w - thick, y + thick, x + thick, y + h - thick * 2);
		if (west)
			g.fillRect(x, y + thick, x + thick, y + h - thick * 2);

		if (north || west) {
			g.setColor(getBackground(c));
			g.fillRect(x, y, thick, thick);
			g.setColor(getForeground(c));
			if (nw)
				g.fillArc(x, y, diam, diam, 90, 90);
			else
				g.fillRect(x, y, thick, thick);
		}

		if (north || east) {
			g.setColor(getBackground(c));
			g.fillRect(x + w - thick, y, thick, thick);
			g.setColor(getForeground(c));
			if (ne)
				g.fillArc(x + w - diam, y, diam, diam, 0, 90);
			else
				g.fillRect(x + w - thick, y, thick, thick);
		}

		if (south || west) {
			g.setColor(getBackground(c));
			g.fillRect(x, y + h - thick, thick, thick);
			g.setColor(getForeground(c));
			if (sw)
				g.fillArc(x, y + h - diam, diam, diam, 180, 90);
			else
				g.fillRect(x, y + h - thick, thick, thick);
		}

		if (south || east) {
			g.setColor(getBackground(c));
			g.fillRect(x + w - thick, y + h - thick, thick, thick);
			g.setColor(getForeground(c));
			if (se)
				g.fillArc(x + w - diam, y + h - diam, diam, diam, 270, 90);
			else
				g.fillRect(x + w - thick, y + h - thick, thick, thick);
		}
	}

}
