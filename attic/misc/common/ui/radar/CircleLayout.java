package common.ui.radar;

import java.awt.*;
import java.awt.geom.*;
import common.ui.layout.*;

public class CircleLayout
  extends AbstractLayout
{
  protected Component center;

  protected Dimension getLargest(Container parent)
  {
    int width = 0, height = 0;
    int count = parent.getComponentCount();
    for (int i = 0; i < count; i++)
    {
      Component child = parent.getComponent(i);
      if (child == center) continue;
      Dimension size = child.getPreferredSize();
      if (size.width > width)
        width = size.width;
      if (size.height > height)
        height = size.height;
    }
    return new Dimension(width, height);
  }
  
  public Dimension minimumLayoutSize(Container parent)
  {
    int count = parent.getComponentCount();
    Dimension largest = getLargest(parent);
    int size = Math.max(largest.width, largest.height) * (count / 2);
    Dimension pref = center.getMinimumSize();
    return new Dimension(pref.width + size, pref.height + size);
  }

  public Dimension preferredLayoutSize(Container parent)
  {
    int count = parent.getComponentCount();
    Dimension largest = getLargest(parent);
    int size = Math.max(largest.width, largest.height) * (count / 2);
    Dimension pref = center.getPreferredSize();
    return new Dimension(pref.width + size, pref.height + size);
  }
  
  public void addLayoutComponent(Component comp, Object constraints)
  {
    if (constraints instanceof String &&
      constraints.equals(BorderLayout.CENTER))
    {
      center = comp;
    }
  }
  
  public void layoutContainer(Container parent)
  {
    Insets insets = parent.getInsets();
    int top = insets.top;
    int left = insets.left;
    int right = parent.getWidth() -
      (insets.left + insets.right);
    int bottom = parent.getHeight() -
      (insets.top + insets.bottom);
    int width = right - left;
    int height = bottom - top;
    
    Dimension largest = getLargest(parent);
    double radius = Math.min(
      width - (largest.width * 2),
      height - (largest.height * 2)) / 2;
    int centerX = left + (width / 2);
    int centerY = top + (height / 2);
    
    int count = parent.getComponentCount();
    center.setBounds(
      (int)(centerX - radius),
      (int)(centerY - radius),
      (int)(radius * 2),
      (int)(radius * 2));
    
    double unit = 360.0 / (double)(center != null ? count - 1 : count);
    double angle = 180;
    for (int i = 0; i < count; i ++)
    {
      Component child = parent.getComponent(i);
      if (child == center) continue;
      Dimension size = child.getPreferredSize();
      int x = (int)(centerX + (radius * Math.sin(Math.toRadians(angle))));
      int y = (int)(centerY + (radius * Math.cos(Math.toRadians(angle))));
      int w = size.width;
      int h = size.height;
      int posX = x - (w / 2);
      int posY = y - (h / 2);
      int adjust = 3;
      if (x >= centerX + adjust) posX += w / 2;
      if (y >= centerY + adjust) posY += h / 2;
      if (x <= centerX - adjust) posX -= w / 2;
      if (y <= centerY - adjust) posY -= h / 2;
      child.setBounds(posX, posY, w, h);
      angle -= unit;
    }
  }
}

