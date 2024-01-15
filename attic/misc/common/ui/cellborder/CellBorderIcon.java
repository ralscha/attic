package common.ui.cellborder;

import java.awt.*;
import javax.swing.*;

public class CellBorderIcon
  implements Icon, SwingConstants
{
  protected int edge, size;
  
  public CellBorderIcon(int edge)
  {
    this(edge, 16);
  }
  
  public CellBorderIcon(int edge, int size)
  {
    this.edge = edge;
    this.size = size;
  }
  
  public int getIconWidth()
  {
    return size;
  }

  public int getIconHeight()
  {
    return size;
  }
  
  public void paintIcon(
    Component c, Graphics g, int x, int y)
  {
    int s = size - 2;
    g.setColor(Color.gray);
    for (int i = 2; i <= s; i += 2)
    {
      g.drawLine(x + i, y + 2, x + i, y + 2);
      g.drawLine(x + 2, y + i, x + 2, y + i);
      g.drawLine(x + i, y + s, x + i, y + s);
      g.drawLine(x + s, y + i, x + s, y + i);
    }
    g.setColor(Color.black);
    if (edge == NORTH)
    {
      g.drawLine(x + 2, y + 2, x + s, y + 2);
    }
    if (edge == SOUTH)
    {
      g.drawLine(x + 2, y + s, x + s, y + s);
    }
    if (edge == WEST)
    {
      g.drawLine(x + 2, y + 2, x + 2, y + s);
    }
    if (edge == EAST)
    {
      g.drawLine(x + s, y + 2, x + s, y + s);
    }
  }
}

