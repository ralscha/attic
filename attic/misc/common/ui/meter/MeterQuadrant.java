package common.ui.meter;

import java.awt.*;
import javax.swing.*;

public class MeterQuadrant
{
  protected boolean ne, se, sw, nw;
  
  public MeterQuadrant(double start, double extent)
  {
    double end = (start + extent) % 360;
    if (end == 0) end = 360;

    // Determine first quadrant
    int first = 0;
    if (start >= 0 && start < 90) first = 1;
    if (start >= 90 && start < 180) first = 2;
    if (start >= 180 && start < 270) first = 3;
    if (start >= 270 && start < 360) first = 4;
    
    // Determine last quadrant
    int last = 0;
    if (end > 0 && end <= 90) last = 1;
    if (end > 90 && end <= 180) last = 2;
    if (end > 180 && end <= 270) last = 3;
    if (end > 270 && end <= 360) last = 4;
    
    // Flag quadrants between first and last, inclusive
    if (first > last) last += 4;
    ne = (first <= 1 && last >= 1) || (last > 4 && last >= 5);
    se = (first <= 2 && last >= 2) || (last > 4 && last >= 6);
    sw = (first <= 3 && last >= 3) || (last > 4 && last >= 7);
    nw = (first <= 4 && last >= 4) || (last > 4 && last >= 8);
  }

  /*
    There are 18 possibilities:
    1) 4 possible single quadrants
    2) 4 possible adjacent pairs
    3) 4 possible triads
    4) The whole shebang
    NOTE: 
      3 and 4 are equivalant for this purpose,
      requiring all 4 quadrants to be drawn.
  */
  public Dimension getPreferredSize(int radius)
  {
    int w = radius * 2;
    int h = radius * 2;
    int quadrantCount =
      (ne ? 1 : 0) + (se ? 1 : 0) +
      (sw ? 1 : 0) + (nw ? 1 : 0);
    if (quadrantCount == 1)
    {
      w = radius;
      h = radius;
    }
    if (quadrantCount == 2)
    {
      w = radius * ((nw && ne) || (sw && se) ? 2 : 1);
      h = radius * ((ne && se) || (nw && sw) ? 2 : 1);
    }
    return new Dimension(w, h);
  }
  
  public Dimension getSize(Dimension size)
  {
    int w = 2;
    int h = 2;
    int quadrantCount =
      (ne ? 1 : 0) + (se ? 1 : 0) +
      (sw ? 1 : 0) + (nw ? 1 : 0);
    if (quadrantCount == 1)
    {
      w = 1;
      h = 1;
    }
    if (quadrantCount == 2)
    {
      w = ((nw && ne) || (sw && se) ? 2 : 1);
      h = ((ne && se) || (nw && sw) ? 2 : 1);
    }
    return new Dimension(size.width / w, size.height / h);
  }
  
  public Point getCenter(Dimension size, Insets insets)
  {
    int x = (size.width - insets.left - insets.right) / 2;
    int y = (size.height - insets.top - insets.bottom) / 2;
    int quadrantCount =
      (ne ? 1 : 0) + (se ? 1 : 0) +
      (sw ? 1 : 0) + (nw ? 1 : 0);
    if (quadrantCount == 1)
    {
      if (ne || se) x = 0;
      if (sw || se) y = 0;
      if (nw || sw) x = size.width;
      if (nw || ne) y = size.height;
    }
    if (quadrantCount == 2)
    {
      if (ne && se) x = 0;
      if (se && sw) y = 0;
      if (nw && sw) x = size.width;
      if (nw && ne) y = size.height;
    }
    return new Point(x, y);
  }
  
  public String toString()
  {
    return "ne=" + ne + ", se=" + se +
      ", sw=" + sw + ", nw=" + nw;
  }
}

