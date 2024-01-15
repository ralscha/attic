package common.ui.cellborder;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class CellBorder implements Border
{
  protected Insets insets;
  protected boolean fill;
  protected Color color;
  
  public CellBorder()
  {
    this(new Insets(1, 1, 1, 1), Color.black, true);
  }
  
  public CellBorder(Insets insets)
  {
    this(insets, Color.black, true);
  }
  
  public CellBorder(Insets insets, boolean fill)
  {
    this(insets, Color.black, fill);
  }
  
  public CellBorder(Insets insets, Color color)
  {
    this(insets, color, true);
  }
  
  public CellBorder(Insets insets, Color color, boolean fill)
  {
    this.insets = insets;
    setColor(color);
    setFill(fill);
  }
    
  public boolean getFill()
  {
    return fill;
  }
  
  public void setFill(boolean fill)
  {
    this.fill = fill;
  }
  
  public boolean isBorderOpaque()
  {
    return fill;
  }
  
  public Color getColor()
  {
    return color;
  }
  
  public void setColor(Color color)
  {
    this.color = color;
  }
  
  public Insets getInsets()
  {
    return insets;
  }
  
  public Insets getBorderInsets(Component c)
  {
    return insets;
  }
  
  public void paintBorder(Component c,
    Graphics g, int x, int y, int w, int h)
  {
    g.setColor(color);
    if (fill)
    {
      g.fillRect(x, y, w, insets.top);
      g.fillRect(x, y, insets.left, h);
      g.fillRect(x, y + h - insets.bottom, w, insets.bottom);
      g.fillRect(x + w - insets.right, y, insets.right, h);
    }
    else
    {
      g.drawRect(x, y, w - 1, h - 1);
      g.drawRect(
        x + insets.left - 1, y + insets.top - 1,
        w - insets.left - insets.right + 1,
        h - insets.top - insets.bottom + 1);
    }
  }
}

