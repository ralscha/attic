package common.ui.range;

import java.awt.*;
import javax.swing.*;

public class DefaultRangeEditor extends JPanel
  implements RangeEditor
{
  protected BoundedRangeModel model;
  
  public DefaultRangeEditor() {}
  {
    setBackground(Color.black);
    setForeground(Color.white);
  }
  
  public int getWidth()
  {
    return 3;
  }
  
  public JComponent getRangeEditorComponent(
    JRange range, BoundedRangeModel model,
    boolean hasFocus)
  {
    this.model = model;
    return this;
  }
  
  protected void paintComponent(Graphics g)
  {
    int w = getSize().width;
    int h = getSize().height;
    int val = BoundedRangeUtil.getScaledValuePos(model, w);
    int pos = BoundedRangeUtil.getScaledExtentPos(model, w);
    g.setColor(getBackground());
    g.fillRect(val - 1, 0, 3, h);
    g.fillRect(pos - 1, 0, 3, h);
    g.setColor(getForeground());
    g.drawLine(val, 0, val, h);
    g.drawLine(pos, 0, pos, h);
  }
}

