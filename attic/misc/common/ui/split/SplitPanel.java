package common.ui.split;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;

public class SplitPanel extends JPanel
  implements MouseListener, MouseMotionListener
{
  protected Rectangle apex;
  protected SplitViewLayout layout;
  protected SplitBar vert, horz;
  protected int xAnchor = -1;
  protected int yAnchor = -1;
  
  protected Cursor normalCursor =
    Cursor.getPredefinedCursor(
      Cursor.DEFAULT_CURSOR);
  protected Cursor verticalCursor =
    Cursor.getPredefinedCursor(
      Cursor.N_RESIZE_CURSOR);
  protected Cursor horizontalCursor =
    Cursor.getPredefinedCursor(
      Cursor.W_RESIZE_CURSOR);
  protected Cursor resizeCursor =
    Cursor.getPredefinedCursor(
      Cursor.MOVE_CURSOR);

  public SplitPanel()
  {
    setOpaque(false);
    apex = new Rectangle(0, 0, 4, 4);
    setLayout(layout = new SplitViewLayout());
    add(vert = new SplitBar(), SplitViewLayout.VERT);
    add(horz = new SplitBar(), SplitViewLayout.HORZ);
    addMouseMotionListener(this);
    addMouseListener(this);
  }

  protected boolean containsX(Rectangle rect, int x)
  {
    return x >= rect.x && x <= rect.x + rect.width;
  }
  
  protected boolean containsY(Rectangle rect, int y)
  {
    return y >= rect.y && y <= rect.y + rect.height;
  }
  
  public void mouseEntered(MouseEvent event)
  {
    setCursor(normalCursor);
  }
  
  public void mouseExited(MouseEvent event)
  {
    setCursor(normalCursor);
  }
  
  public void mouseClicked(MouseEvent event) {}
  
  public void mousePressed(MouseEvent event)
  {
    int x = event.getX();
    int y = event.getY();
    if (containsX(apex, x))
    {
      vert.setTransparency(false);
      vert.repaint();
      xAnchor = x;
    }
    if (containsY(apex, y))
    {
      horz.setTransparency(false);
      horz.repaint();
      yAnchor = y;
    }
  }
  
  public void mouseReleased(MouseEvent event)
  {
    xAnchor = -1;
    yAnchor = -1;
    int w = getSize().width;
    int h = getSize().height;
    horz.setTransparency(true);
    horz.setBounds(0, apex.y, w, apex.height);
    vert.setTransparency(true);
    vert.setBounds(apex.x, 0, apex.width, h);
    layout.setApex(apex);
    doLayout();
  }
  
  public void mouseMoved(MouseEvent event)
  {
    int x = event.getX();
    int y = event.getY();
    
    boolean horz = containsX(apex, x);
    boolean vert = containsY(apex, y);
    if (vert && horz)
    {
      setCursor(resizeCursor);
    }
    else if (horz)
    {
      setCursor(horizontalCursor);
    }
    else if (vert)
    {
      setCursor(verticalCursor);
    }
    else
    {
      setCursor(normalCursor);
    }
  }
  
  public void mouseDragged(MouseEvent event)
  {
    int w = getSize().width;
    int h = getSize().height;
    int x = event.getX();
    int y = event.getY();
    if (xAnchor == -1) x = apex.x;
    if (yAnchor == -1) y = apex.y;
    if (x < 0) x = 0;
    if (y < 0) y = 0;
    int ww = w - apex.width;
    int hh = h - apex.height;
    if (x > ww) x = ww;
    if (y > hh) y = hh;
    apex = new Rectangle(x, y, 4, 4);
    
    horz.setBounds(0, apex.y, w, apex.height);
    vert.setBounds(apex.x, 0, apex.width, h);
    repaint();
  }
}

