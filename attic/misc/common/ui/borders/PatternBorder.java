package common.ui.borders;


import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.border.*;

public class PatternBorder extends MatteBorder {
	public static final int[] DEFAULT = {4, 4, 1, 0, 1, 0, 0, 1, 0, 1, 1, 0, 1, 0, 0, 1, 0, 1};
	public static final int[] CROSS = {4, 4, 0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 1, 1, 0, 0, 0};
	public static final int[] VERT = {4, 4, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0};
	public static final int[] HORZ = {4, 4, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1};
	public static final int[] GRID = {4, 4, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 1};
	public static final int[] ROUND = {4, 4, 0, 1, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0};
	public static final int[] NW_TRIANGLE = {4, 4, 1, 1, 1, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0,
                                    		0, 0, 0};
	public static final int[] NE_TRIANGLE = {4, 4, 0, 1, 1, 1, 0, 0, 1, 1, 0, 0, 0, 1, 0,
                                    		0, 0, 0};
	public static final int[] SW_TRIANGLE = {4, 4, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 1,
                                    		1, 1, 0};
	public static final int[] SE_TRIANGLE = {4, 4, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 0,
                                    		1, 1, 1};
	public static final int[] DIAMOND = {4, 4, 0, 1, 0, 0, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0};
	public static final int[] DOTS = {4, 4, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0};
	public static final int[] DOT = {4, 4, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0};
	public static final int[] SLASH = {4, 4, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1};
	public static final int[] BACKSLASH = {4, 4, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0,
                                   		0, 0};
	public static final int[] THICK_VERT = {4, 4, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1,
                                    		1, 0};
	public static final int[] THICK_HORZ = {4, 4, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0,
                                    		0, 0};
	public static final int[] THICK_GRID = {4, 4, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1,
                                    		1, 0};
	public static final int[] THICK_SLASH = {4, 4, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 1,
                                    		0, 0, 1};
	public static final int[] THICK_BACKSLASH = {4, 4, 0, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 0,
    		1, 0, 0, 1};

	public PatternBorder() {
		super(createIcon(DEFAULT, Color.black));
	}

	public PatternBorder(int[] pattern) {
		super(createIcon(pattern, Color.black));
	}

	public PatternBorder(int[] pattern, Color color) {
		super(createIcon(pattern, color));
	}

	public PatternBorder(int[] pattern, Color color, int thickness) {
		this(thickness, thickness, thickness, thickness, pattern, color);
	}

	public PatternBorder(int top, int left, int bottom, int right, int[] pattern) {
		super(top, left, bottom, right, createIcon(pattern, Color.black));
	}

	public PatternBorder(int top, int left, int bottom, int right, int[] pattern, Color color) {
		super(top, left, bottom, right, createIcon(pattern, color));
	}

	public static Icon createIcon(int[] array, Color color) {
		int w = array[0];
		int h = array[1];
		BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		int rgb = color.getRGB();
		for (int x = 0; x < w; x++) {
			for (int y = 0 ; y < h; y++) {
				image.setRGB(x, y, array[y * w + x + 2] > 0 ? rgb : 0);
			}
		}
		return new ImageIcon(image);
	}
}
