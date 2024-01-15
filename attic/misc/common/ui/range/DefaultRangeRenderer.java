package common.ui.range;

import java.awt.*;
import javax.swing.*;

public class DefaultRangeRenderer extends JPanel
  implements RangeRenderer
{
  protected BoundedRangeModel model;
  
  public DefaultRangeRenderer()
  {
    setBackground(Color.orange.darker());
  }
  
  public JComponent getRangeRendererComponent(
    JRange range, BoundedRangeModel model,
    boolean hasFocus)
  {
    this.model = model;
    setForeground(hasFocus ? Color.blue : Color.green);
    return this;
  }
  
  protected void paintComponent(Graphics gc)
  {
    Graphics2D g = (Graphics2D)gc;
    int w = getSize().width;
    int h = getSize().height;
    g.setColor(getBackground());
    g.fillRect(0, 0, w, h);
    paintStripes(
      g, getBackground().darker(),
      getBackground(), 0, 0, w, h);
    g.setColor(getForeground());
    int pos = BoundedRangeUtil.getScaledValuePos(model, w);
    int len = BoundedRangeUtil.getScaledExtentLen(model, w);
    paintRectangle(
      g, getForeground().darker(),
      getForeground(), pos, 0, len, h);
  }

  protected void paintStripes(
    Graphics2D g, Color odd, Color even,
    int x, int y, int w, int h)
  {
    for (int i = 0; i < h; i++)
    {
      g.setColor(i % 2 == 0 ? odd : even);
      g.drawLine(x, y + i, x + w, y + i);
    }
  }
    
  protected void paintRectangle(
    Graphics2D g, Color edge, Color center,
    int x, int y, int w, int h)
  {
    int m = h / 2;
    int p = y + m;
    paintCurve(g, edge, center, x, y, w, m);
    paintCurve(g, center, edge, x, p, w, m);
  }
  
  protected void paintCurve(
    Graphics2D g, Color source, Color target,
    int x, int y, int w, int h)
  {
    GradientPaint paint = new GradientPaint(
      x, y, source, x, y + h, target);
    g.setPaint(paint);
    g.fillRect(x, y, w, h);
  }
}

