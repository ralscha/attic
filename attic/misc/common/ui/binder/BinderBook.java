package common.ui.binder;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

public class BinderBook extends JPanel {
  protected BinderLayout layout;

  public BinderBook() {
    setOpaque(false);
    setLayout(layout = new BinderLayout(10));
    setBorder( BorderFactory.createCompoundBorder(new CurvedBorder(7),
               BorderFactory.createEmptyBorder(8, 8, 8, 8)));
  }

  public void paintComponent(Graphics gc) {
    Graphics2D g = (Graphics2D) gc;
    int w = getSize().width;
    int h = getSize().height;
    int m = w / 2;
    int x = 33;
    g.setColor(Color.black);
    g.drawLine(m - x - 1, 0, m - x - 1, h);
    g.drawLine(m + x - 1, 0, m + x - 1, h);
    g.setColor(Color.white);
    g.drawLine(m - x + 1, 0, m - x + 1, h);
    g.drawLine(m + x + 1, 0, m + x + 1, h);

    int z = 27;
    RoundRectangle2D shape = new RoundRectangle2D.Float(m - z, 20, z * 2, h - 40, 15, 15);
    GradientPaint gradient =
      new GradientPaint(m - z, 0, Color.white, m + z, 0, Color.lightGray);
    g.setPaint(gradient);
    g.fill(shape);
    g.setColor(Color.black);
    g.draw(shape);
  }

  public void increment() {
    int page = layout.getPage();
    int count = getComponentCount();
    if (page + 2 <= count) {
      layout.setPage(page + 2);
      revalidate();
    }
  }

  public void decrement() {
    int page = layout.getPage();
    if (page - 2 >= 0) {
      layout.setPage(page - 2);
      revalidate();
    }
  }

  public int getPageNumber(JComponent child) {
    int count = getComponentCount();
    for (int i = 0; i < count; i++) {
      if (child == getComponent(i))
        return i;
    }
    return -1;
  }
}

