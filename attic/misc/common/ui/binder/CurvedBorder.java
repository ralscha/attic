package common.ui.binder;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class CurvedBorder implements Border {
  // We use same integers values as EtchedBorder
  public static final int RAISED = 0;
  public static final int LOWERED = 1;

  public static final int ROUNDED = 0;
  public static final int PLATEAU = 1;

  protected int type = RAISED;
  protected int curve = ROUNDED;
  protected int thickness = 1;
  protected int percent = 10;
  protected Color start;

  public CurvedBorder() {
    this(RAISED , ROUNDED, 5, 10, null);
  }

  public CurvedBorder(int thickness) {
    this(RAISED , ROUNDED, thickness, 10, null);
  }

  public CurvedBorder(int type, int curve, int thickness) {
    this(type, curve, thickness, 10, null);
  }

  public CurvedBorder(int type, int curve, int thickness, int percent) {
    this(type, curve, thickness, percent, null);
  }

  public CurvedBorder(int type, int curve, int thickness, int percent, Color start) {
    this.type = type;
    this.curve = curve;
    this.thickness = thickness;
    this.percent = percent;
    this.start = start;
  }

  public boolean isBorderOpaque() {
    return true;
  }

  public Insets getBorderInsets(Component component) {
    return new Insets(thickness, thickness, thickness, thickness);
  }

  public Color getStartColor(Component c) {
    if (start == null)
      start = c.getBackground();
    return start;
  }

  public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
    Color hi = getStartColor(c);
    Color lo = getStartColor(c);
    if (curve == ROUNDED) {
      for (int i = thickness - 1; i >= 0; i--) {
        drawLines(g, hi, lo, x, y, w, h, i);
        if (type == RAISED) {
          hi = brighter(hi, percent);
          lo = darker(lo, percent);
        } else {
          hi = darker(hi, percent);
          lo = brighter(lo, percent);
        }
      }
    } else {
      for (int i = 0; i < thickness; i++) {
        drawLines(g, hi, lo, x, y, w, h, i);
        if (type == RAISED) {
          hi = brighter(hi, percent);
          lo = darker(lo, percent);
        } else {
          hi = darker(hi, percent);
          lo = brighter(lo, percent);
        }
      }
    }
  }

  private void drawLines(Graphics g, Color hi, Color lo, int x, int y, int w, int h, int i) {
    g.setColor(hi);
    g.drawLine(x + i, y + i, x + w - i - 1, y + i);
    g.drawLine(x + i, y + i, x + i, x + h - i - 1);
    g.setColor(lo);
    g.drawLine(x + w - i - 1, y + i, x + w - i - 1, y + h - i - 1);
    g.drawLine(x + i, y + h - i - 1, x + w - i - 1, y + h - i - 1);
  }

  public static Color brighter(Color color, double percent) {
    int r = color.getRed();
    int g = color.getGreen();
    int b = color.getBlue();
    double factor = percent / 100;
    int red = r + (int)((255 - r) * factor);
    int green = g + (int)((255 - g) * factor);
    int blue = b + (int)((255 - b) * factor);
    return new Color(red, green, blue);
  }

  public static Color darker(Color color, double percent) {
    int r = color.getRed();
    int g = color.getGreen();
    int b = color.getBlue();
    double factor = percent / 100;
    int red = Math.max((int)(r * (1 - factor)), 0);
    int green = Math.max((int)(g * (1 - factor)), 0);
    int blue = Math.max((int)(b * (1 - factor)), 0);
    return new Color(red, green, blue);
  }
}

