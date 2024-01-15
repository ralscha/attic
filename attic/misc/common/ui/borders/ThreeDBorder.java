package common.ui.borders;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class ThreeDBorder implements Border, BorderConstants {
	protected int type = RAISED;
	protected int thickness = 1;
	protected Color highlight;
	protected Color shadow;

	public ThreeDBorder() {
		this(RAISED , 1, null, null);
	}

	public ThreeDBorder(int type) {
		this(type , 1, null, null);
	}

	public ThreeDBorder(int type, int thickness) {
		this(type, thickness, null, null);
	}

	public ThreeDBorder(int type, int thickness, Color highlight, Color shadow) {
		this.type = type;
		this.thickness = thickness;
		this.highlight = highlight;
		this.shadow = shadow;
	}

	public boolean isBorderOpaque() {
		return true;
	}

	public Insets getBorderInsets(Component component) {
		return new Insets(thickness, thickness, thickness, thickness);
	}

	public Color getHightlightColor(Component c) {
		if (highlight == null)
			highlight = c.getBackground().brighter();
		return highlight;
	}

	public Color getShadowColor(Component c) {
		if (shadow == null)
			shadow = c.getBackground().darker();
		return shadow;
	}

	public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
		Color hi = (type == RAISED ? getHightlightColor(c) : getShadowColor(c));
		Color lo = (type == RAISED ? getShadowColor(c) : getHightlightColor(c));

		for (int i = thickness - 1; i >= 0; i--) {
			g.setColor(hi);
			g.drawLine(x + i, y + i, x + w - i - 1, y + i);
			g.drawLine(x + i, y + i, x + i, x + h - i - 1);

			g.setColor(lo);
			g.drawLine(x + w - i - 1, y + i, x + w - i - 1, y + h - i - 1);
			g.drawLine(x + i, y + h - i - 1, x + w - i - 1, y + h - i - 1);
		}
	}

}
