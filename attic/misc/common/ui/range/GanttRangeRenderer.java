package common.ui.range;

import java.awt.*;
import javax.swing.*;

public class GanttRangeRenderer extends JPanel
  implements RangeRenderer
{
  public static final int TASK = 1;
  public static final int GROUP = 2;
  
  protected int type;
  protected JRange range;
  protected BoundedRangeModel model;
  protected boolean hasFocus;
  
  public GanttRangeRenderer(int type)
  {
    this.type = type;
  }
  
  public JComponent getRangeRendererComponent(
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
    int w = getSize().width;
    int h = getSize().height;
    g.setColor(range.getBackground());
    g.fillRect(0, 0, w, h);
    
    int pos = BoundedRangeUtil.getScaledValuePos(model, w);
    int len = BoundedRangeUtil.getScaledExtentLen(model, w);
    if (type == GROUP)
    {
      int m = h / 2;
      g.setColor(hasFocus ? Color.blue : range.getForeground());
      g.fillRect(pos, 0, len, m + 1);
    }
    else
    {
      g.setColor(hasFocus ? Color.blue : Color.green);
      g.fillRect(pos, 0, len, h);
    }
  }
}

