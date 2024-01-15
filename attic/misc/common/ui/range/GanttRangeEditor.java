package common.ui.range;

import java.awt.*;
import javax.swing.*;

public class GanttRangeEditor extends JPanel
  implements RangeEditor
{
  public static final int TASK = 1;
  public static final int GROUP = 2;
  
  protected int type;
  protected JRange range;
  protected BoundedRangeModel model;
  protected boolean hasFocus;
  
  public GanttRangeEditor(int type)
  {
    this.type = type;
  }
  
  public int getWidth()
  {
    return type == GROUP ? 5 : 3;
  }
  
  public JComponent getRangeEditorComponent(
    JRange range, BoundedRangeModel model,
    boolean hasFocus)
  {
    this.range = range;
    this.model = model;
    this.hasFocus = hasFocus;
    return this;
  }
  
  protected void paintComponent(Graphics g)
  {
    if (type == GROUP)
    {
      int w = getSize().width;
      int h = getSize().height;
      int val = BoundedRangeUtil.getScaledValuePos(model, w);
      int pos = BoundedRangeUtil.getScaledExtentPos(model, w);
      Polygon poly1 = makePolygon(val, h);
      Polygon poly2 = makePolygon(pos - 1, h);
      g.setColor(hasFocus ? Color.blue : range.getForeground());
      g.fillPolygon(poly1);
      g.fillPolygon(poly2);
    }
  }

  protected Polygon makePolygon(int x, int h)
  {
    int m = h / 2;
    Polygon poly = new Polygon();
    poly.addPoint(x - m, 0);
    poly.addPoint(x + m, 0);
    poly.addPoint(x + m, m);
    poly.addPoint(x, h);
    poly.addPoint(x - m, m);
    return poly;
  }
}

