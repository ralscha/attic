package common.ui.borders;


import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class StyleBorder implements Border {
	protected BasicStroke stroke;
	protected Color color;

	public static final float[] DASH_LONG = {9f, 5f};
	public static final float[] DASH_PLAIN = {6f, 5f};
	public static final float[] DASH_SHORT = {3f, 5f};
	public static final float[] DOT_LONG = {1f, 12f};
	public static final float[] DOT_PLAIN = {1f, 9f};
	public static final float[] DOT_SHORT = {1f, 5f};
	public static final float[] DASH_DOT = {9f, 5f, 1f, 5f};
	public static final float[] DASH_DOT_DOT = {9f, 5f, 1f, 5f, 1f, 5f};
	public static final float[] DASH_DASH_DOT = {9f, 5f, 9f, 5f, 1f, 5f};

	protected StyleBorder() {
		this(DASH_DOT);
	}

	protected StyleBorder(int thickness) {
		this(new BasicStroke(thickness), Color.black);
	}

	protected StyleBorder(int thickness, int cap, int join) {
		this(new BasicStroke(thickness, cap, join), Color.black);
	}

	protected StyleBorder(float[] dash) {
		this(new BasicStroke(1, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER, 1f, dash,
                     		0f), Color.black);
	}

	protected StyleBorder(int thickness, float[] dash) {
		this(new BasicStroke(thickness, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER, 1f,
                     		dash, 0f), Color.black);
	}

	protected StyleBorder(int thickness, float[] dash, Color color) {
		this(new BasicStroke(thickness, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER, 1f,
                     		dash, 0f), color);
	}

	protected StyleBorder(int thickness, int cap, int join, float[] dash, Color color) {
		this(new BasicStroke(thickness, cap, join, 1f, dash, 0f), color);
	}

	protected StyleBorder(BasicStroke stroke) {
		this(stroke, Color.black);
	}

	protected StyleBorder(BasicStroke stroke, Color color) {
		this.stroke = stroke;
		this.color = color;
	}

	private static float[] adjustStyle(int thickness, float[] style) {
		for (int i = 0; i < style.length; i++)
			style[i] *= thickness;
		return style;
	}

	public boolean isBorderOpaque() {
		return false;
	}

	public Insets getBorderInsets(Component component) {
		int thickness = (int) stroke.getLineWidth();
		return new Insets(thickness, thickness, thickness, thickness);
	}

	public void paintBorder(Component c, Graphics gc, int x, int y, int w, int h) {
		Graphics2D g = (Graphics2D) gc;
		g.setColor(color);
		int thickness = (int) stroke.getLineWidth();
		int mid = (int)(stroke.getLineWidth() / 2f);
		Shape shape = new Rectangle(x + mid, y + mid, w - thickness, h - thickness);
		g.setStroke(stroke);
		g.draw(shape);
	}
}
