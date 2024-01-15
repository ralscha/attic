package common.ui.pattern;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class PaintCellRenderer extends JPanel
  implements ListCellRenderer
{
  protected Border normalBorder =
    new LineBorder(Color.white, 2);
  protected Border selectBorder =
    new LineBorder(Color.black, 2);
  protected Border focusBorder =
    new LineBorder(Color.blue, 2);
    
  protected Paint paint;

  public PaintCellRenderer()
  {
    setPreferredSize(new Dimension(70, 16));
  }
  
  public void paintComponent(Graphics gc)
  {
    Graphics2D g = (Graphics2D)gc;
    int w = getSize().width;
    int h = getSize().height;
    Insets insets = getInsets();
    Rectangle rect = new Rectangle(
      insets.left, insets.top,
      w - (insets.left + insets.right),
      h - (insets.top + insets.bottom));
    g.setPaint(paint);
    g.fill(rect);
  }
  
  public Component getListCellRendererComponent(
    JList list, Object value, int index,
    boolean isSelected, boolean hasFocus)
  {
    Border border = isSelected ? selectBorder : normalBorder;
    setBorder(hasFocus ? focusBorder : border);
    if (value instanceof Paint)
    {
      paint = (Paint)value;
    }
    return this;
  }
}

