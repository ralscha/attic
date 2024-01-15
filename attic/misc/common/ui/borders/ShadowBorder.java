package common.ui.borders;


import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class ShadowBorder implements Border, SwingConstants {
	protected int shadow = 3;
	protected Color color;
	protected int corner = SOUTH_EAST;

	public ShadowBorder() {
		this(4, Color.gray, SOUTH_EAST);
	}

	public ShadowBorder(int shadow) {
		this(shadow, Color.gray, SOUTH_EAST);
	}

	public ShadowBorder(int shadow, Color color) {
		this(shadow, color, SOUTH_EAST);
	}

	public ShadowBorder(int shadow, Color color, int corner) {
		this.shadow = shadow;
		this.color = adjustAlpha(color, 128);
		this.corner = corner;
	}

	private Color adjustAlpha(Color color, int alpha) {
		return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
	}

	public Insets getBorderInsets(Component component) {
		switch (corner) {
			case NORTH_WEST:
				return new Insets(shadow, shadow, 0, 0);
			case NORTH_EAST:
				return new Insets(shadow, 0, 0, shadow);
			case SOUTH_WEST:
				return new Insets(0, shadow, shadow, 0);
			default:
				return new Insets(0, 0, shadow, shadow);
		}
	}

	public boolean isBorderOpaque() {
		return false;
	}

	public void paintBorder(Component component, Graphics g, int x, int y, int w, int h) {
		g.setColor(color);
		if (corner == SOUTH_EAST) {
			g.fillRect(x + w - shadow, y + shadow, shadow, h - shadow);
			g.fillRect(x + shadow, y + h - shadow, w - shadow * 2, shadow);
		}
		if (corner == SOUTH_WEST) {
			g.fillRect(x, y + shadow, shadow, h - shadow);
			g.fillRect(x + shadow, y + h - shadow, w - shadow * 2, shadow);
		}
		if (corner == NORTH_EAST) {
			g.fillRect(x + w - shadow, y, shadow, h - shadow);
			g.fillRect(x + shadow, y, w - shadow * 2, y + shadow);
		}
		if (corner == NORTH_WEST) {
			g.fillRect(x, y, shadow, h - shadow);
			g.fillRect(x + shadow, y, w - shadow * 2, y + shadow);
		}
	}
}