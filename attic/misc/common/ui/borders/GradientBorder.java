package common.ui.borders;


import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class GradientBorder implements Border {
	protected int thickness;
	protected Color outside;
	protected Color inside;

	public GradientBorder() {
		this(7, Color.orange, Color.red);
	}

	public GradientBorder(int thickness) {
		this(thickness, Color.orange, Color.red);
	}

	public GradientBorder(int thickness, Color outside, Color inside) {
		this.thickness = thickness;
		this.outside = outside;
		this.inside = inside;
	}

	public boolean isBorderOpaque() {
		return true;
	}

	public Insets getBorderInsets(Component component) {
		return new Insets(thickness, thickness, thickness, thickness);
	}

	public Color getInsideColor(Component c) {
		if (inside == null)
			inside = c.getBackground();
		return inside;
	}

	public Color getOutsideColor(Component c) {
		if (outside == null)
			outside = c.getParent().getBackground();
		return outside;
	}

	public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
		Color[] color = interpolate(getOutsideColor(c), getInsideColor(c), thickness);
		for (int i = 0; i < thickness; i++) {
			g.setColor(color[i]);
			g.drawLine(x + i, y + i, x + w - i - 1, y + i);
			g.drawLine(x + i, y + i, x + i, x + h - i - 1);
			g.drawLine(x + w - i - 1, y + i, x + w - i - 1, y + h - i - 1);
			g.drawLine(x + i, y + h - i - 1, x + w - i - 1, y + h - i - 1);
		}
	}

	public static Color[] interpolate(Color source, Color target, int units) {
		Color[] array = new Color[units];

		int sRed = source.getRed();
		int sGreen = source.getGreen();
		int sBlue = source.getBlue();

		int tRed = target.getRed();
		int tGreen = target.getGreen();
		int tBlue = target.getBlue();

		float unitRed, unitGreen, unitBlue;
		unitRed = (float)(tRed - sRed) / (float)(units - 1);
		unitGreen = (float)(tGreen - sGreen) / (float)(units - 1);
		unitBlue = (float)(tBlue - sBlue) / (float)(units - 1);

		int red, green, blue;
		for (int i = 0; i < units; i++) {
			red = sRed + (int)(unitRed * i);
			green = sGreen + (int)(unitGreen * i);
			blue = sBlue + (int)(unitBlue * i);
			array[i] = new Color(red, green, blue);
		}
		return array;
	}
}