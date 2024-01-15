package common.ui.daytime;

import java.awt.*;
import javax.swing.border.*;

public class DayTimeBorder implements Border
{
  protected Color selectedColor = Color.blue;
  protected Color normalColor = Color.blue;
  protected boolean selected;
  protected int edge = 4;
 
  public DayTimeBorder(boolean selected)
  {
    this.selected = selected;
  }

  public boolean isBorderOpaque()
  {
    return false;
  }

  public Insets getBorderInsets(Component c)
  {
    return new Insets(edge, edge, edge, 1);
  }

  public void paintBorder(Component c,
    Graphics g, int x, int y, int w, int h)
  {
    if (selected)
    {
      g.setColor(selectedColor);
      g.fillRect(x, y, x + w, edge);
      g.fillRect(x, y + h - edge, w, edge);
      g.fillRect(x, y, edge, h);
      g.drawLine(x + w - 1, y, x + w - 1, y + h);
    }
    else
    {
      g.setColor(normalColor);
      g.fillRect(x, y + edge - 1, edge, h - edge * 2 + 1);
      g.drawLine(x, y + edge - 1, x + w, y + edge - 1);
      g.drawLine(x, y + h - edge, x + w, y + h - edge);
      g.drawLine(x + w - 1, y + edge, x + w - 1, y + h - edge - 1);
    }
  }
}

