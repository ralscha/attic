package common.ui.borders;


import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.border.*;

public class PaintBorder implements Border {
	protected Paint paint;
	protected int thickness;

	public PaintBorder() {
		this(Color.blue, 3);
	}

	public PaintBorder(Paint paint, int thickness) {
		this.paint = paint;
		this.thickness = thickness;
	}

	public boolean isBorderOpaque() {
		return true;
	}

	public Insets getBorderInsets(Component component) {
		return new Insets(thickness, thickness, thickness, thickness);
	}

	public void paintBorder(Component c, Graphics gc, int x, int y, int w, int h) {
		Graphics2D g = (Graphics2D) gc;
		Rectangle outside = new Rectangle(x, y, w, h);
		Rectangle inside = new Rectangle(x + thickness, y + thickness, w - thickness * 2,
                                 		h - thickness * 2);
		Area area = new Area(outside);
		area.subtract(new Area(inside));
		g.setPaint(paint);
		g.fill(area);
	}

}
