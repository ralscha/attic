package common.ui.cellborder;
import java.awt.*;
import javax.swing.*;

public class ArrowIcon
  implements Icon, SwingConstants
{
  protected int direction;
  protected int width, height;
  
  public ArrowIcon()
  {
    this(SOUTH, 6, 6);
  }
  
  public ArrowIcon(int direction)
  {
    this(direction, 6, 6);
  }

  public ArrowIcon(int direction, int width, int height)
  {
    this.direction = direction;
    this.width = width;
    this.height = height;
  }

  public int getIconWidth()
   {
     return width;
  }
  
  public int getIconHeight()
  {
    return height;
  }

  public void paintIcon(Component c, Graphics g, int x, int y)
  {
    g.setColor(c.getForeground());
    
    int edge = 0;
    int w = width - 1;
    int h = height - 1;
    if (direction == SOUTH)
    {
      for (int i = 0; i < height; i++)
      {
        g.drawLine(x + edge, y + i, x + w - edge, y + i);
        edge += i % 2;
      }
    }
    if (direction == NORTH)
    {
      for (int i = height - 1; i >= 0; i--)
      {
        g.drawLine(x + edge, y + i, x + w - edge, y + i);
        edge += (i + 1) % 2;
      }
    }
    if (direction == EAST)
    {
      for (int i = 0; i < width; i++)
      {
        g.drawLine(x + i, y + edge, x + i, y + h - edge);
        edge += i % 2;
      }
    }
    if (direction == WEST)
    {
      for (int i = width - 1; i >= 0; i--)
      {
        g.drawLine(x + i, y + edge, x + i, y + h - edge);
        edge += (i + 1) % 2;
      }
    }
  }
}

