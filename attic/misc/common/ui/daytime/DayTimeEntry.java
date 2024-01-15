package common.ui.daytime;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;

public class DayTimeEntry extends JPanel
  implements FocusListener, ComponentListener,
    MouseListener, MouseMotionListener, Comparable
{
  protected static Border normalBorder = new DayTimeBorder(false);
  protected static Border selectBorder = new DayTimeBorder(true);
  
  protected JDayTime dayTime;
  protected JComponent component;
  protected boolean selected;
  protected Rectangle rect;
  protected Point anchor;
  protected int edge = 4;

  public DayTimeEntry(JDayTime dayTime, double pos, double len, JComponent component)
  {
    this.dayTime = dayTime;
    this.component = component;
    setOpaque(false);
    setBorder(normalBorder);
    setLayout(new BorderLayout());
    setPosLen(pos, len, 200);
    component.addFocusListener(this);
    component.addMouseMotionListener(this);
    add(BorderLayout.CENTER, component);
    addMouseMotionListener(this);
    addMouseListener(this);
  }
  
  public void setPosLen()
  {
    setPosLen(getPos(), getLen());
  }
  
  public void setPosLen(double pos, double len)
  {
    setPosLen(pos, len, getSize().width);
  }
  
  public void setPosLen(double pos, double len, int width)
  {
    double unit = dayTime.cellHeight * dayTime.getDivisions();
    setBounds(1, (int)(pos * unit + 1),
      width, (int)(len * unit + 7));
    adjustForOverlaps();
    revalidate();
  }
  
  protected double getPos()
  {
    return Math.round(
      ((double)getBounds().y - 1.0) / (double)dayTime.cellHeight) /
      (double)dayTime.getDivisions();
  }
  
  protected double getLen()
  {
    return Math.round(
      ((double)getBounds().height - 7.0) / (double)dayTime.cellHeight) /
      (double)dayTime.getDivisions();
  }
  
  public void focusGained(FocusEvent event)
  {
    selected = true;
    setBorder(selectBorder);
    dayTime.layers.moveToFront(this);
    repaint();
  }
  
  public void focusLost(FocusEvent event)
  {
    selected = false;
    setBorder(normalBorder);
    repaint();
  }
  
  public void mouseClicked(MouseEvent event) {}
  public void mouseEntered(MouseEvent event) {}
  public void mouseExited(MouseEvent event) {}
  
  public void mousePressed(MouseEvent event)
  {
    anchor = event.getPoint();
    rect = getBounds();
    if (event.getX() < edge)
      component.requestFocus();
  }

  public void mouseReleased(MouseEvent event)
  {
    anchor = null;
    DayTimeGroup list = dayTime.group;
    for(int i = 0; i < list.size(); i++)
    {
      list.getEntry(i).setPosLen();
    }
  }
  
  public void mouseDragged(MouseEvent event)
  {
    if (anchor == null) return;
    int h = getSize().height;
    Point home = getLocation();
    Point pos = event.getPoint();
    Point point = SwingUtilities.convertPoint(
      this, pos, getParent());
    if (anchor.x < edge)
    {
      setLocation(home.x, point.y - anchor.y);
    }
    if (anchor.y < edge)
    {
      setBounds(home.x, point.y - anchor.y, getSize().width,
        rect.height + rect.y - point.y + anchor.y);
    }
    if (anchor.y > rect.height - edge)
    {
      setSize(getSize().width, pos.y);
    }
    revalidate();
  }
  
  public void mouseMoved(MouseEvent event)
  {
    Object source = event.getSource();
    int x = event.getX();
    if (x < edge)
    {
      setCursor(
        Cursor.getPredefinedCursor(
          Cursor.MOVE_CURSOR));
      return;
    }
    if (source == component || !selected)
    {
      setCursor(
        Cursor.getPredefinedCursor(
          Cursor.DEFAULT_CURSOR));
      return;
    }
    int y = event.getY();
    int h = getSize().height;
    if (y < edge || y > h - edge)
    {
      setCursor(Cursor.getPredefinedCursor(
        Cursor.N_RESIZE_CURSOR));
    }
  }
  
  public void componentHidden(ComponentEvent event) {}
  public void componentShown(ComponentEvent event) {}
  public void componentMoved(ComponentEvent event) {}
  
  public void componentResized(ComponentEvent event)
  {
    setPosLen();
  }

  protected DayTimeGroup getIntersectionList()
  {
    DayTimeGroup list = new DayTimeGroup();
    int count = dayTime.group.size();
    for (int i = 0; i < count; i++)
    {
      DayTimeEntry other = dayTime.group.getEntry(i);
      if (other != this && other.intersects(this))
      {
        list.add(other);
      }
    }
    return list;
  }
  
  public boolean intersects(DayTimeEntry entry)
  {
    return !(
        (entry.getPos() + entry.getLen() <= getPos()) ||
        (entry.getPos() >= getPos() + getLen()));
  }
  
  protected void adjustForOverlaps()
  {
    Component parent = getParent();
    if (parent == null) return;
    int width = parent.getSize().width - 10;

    DayTimeGroup list = getIntersectionList();
    list.add(this);
    Collections.sort(list);
    
    int unit = width / list.size();
    for (int i = 0; i < list.size(); i++)
    {
      DayTimeEntry entry = list.getEntry(i);
      Rectangle rect = entry.getBounds();
      int x = 1 + unit * i;
      entry.setBounds(x, rect.y, unit - 1, rect.height);
    }
  }
  
  public int compareTo(Object other)
  {
    if (other instanceof DayTimeEntry)
    {
      DayTimeEntry entry = (DayTimeEntry)other;
      if (entry == this) return 0;
      int index = dayTime.group.indexOf(entry);
      double pos = entry.getPos();
      double len = entry.getLen();
      if (dayTime.group.indexOf(this) == index) return 0;
      if (dayTime.group.indexOf(this) < index) return -1;
    }
    return 1;
  }
  
  public String toString()
  {
    return "DayTimeEntry(" +
      getPos() + "," + getLen() + ")";
  }
}

