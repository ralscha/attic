package common.ui.borders;


import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class EdgeBorder implements Border, BorderConstants, SwingConstants {
	protected int type = LOWERED;
	protected int edge = NORTH;

	public EdgeBorder() {
		this(LOWERED, SOUTH);
	}

	public EdgeBorder(int edge) {
		this(LOWERED, edge);
	}

	public EdgeBorder(int type, int edge) {
		this.type = type;
		this.edge = edge;
	}

	public Insets getBorderInsets(Component component) {
		switch (edge) {
			case SOUTH:
				return new Insets(0, 0, 2, 0);
			case EAST:
				return new Insets(0, 2, 0, 0);
			case WEST:
				return new Insets(0, 0, 0, 2);
			default:
				return new Insets(2, 0, 0, 0);
		}
	}

	public boolean isBorderOpaque() {
		return true;
	}

	public void paintBorder(Component component, Graphics g, int x, int y, int w, int h) {
		if (type == RAISED)
			g.setColor(component.getBackground().brighter());
		else
			g.setColor(component.getBackground().darker());
		switch (edge) {
			case SOUTH:
				g.drawLine(x, y + h - 2, w, y + h - 2);
				break;
			case EAST:
				g.drawLine(x + w - 2, y, x + w - 2, y + h);
				break;
			case WEST:
				g.drawLine(x, y, x, y + h);
				break;
			default:
				g.drawLine(x, y, x + w, y);
		}
		if (type == RAISED)
			g.setColor(component.getBackground().darker());
		else
			g.setColor(component.getBackground().brighter());
		switch (edge) {
			case SOUTH:
				g.drawLine(x, y + h - 1, w, y + h - 1);
				break;
			case EAST:
				g.drawLine(x + w - 1, y, x + w - 1, y + h);
				break;
			case WEST:
				g.drawLine(x + 1, y, x + 1, y + h);
				break;
			default:
				g.drawLine(x, y + 1, x + w, y + 1);
		}
	}
}