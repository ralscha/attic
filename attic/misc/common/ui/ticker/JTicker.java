package common.ui.ticker;
/*
=====================================================================

  JTicker.java
  
  Created by Claude Duguay
  Copyright (c) 2001
  
=====================================================================
*/

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.event.*;

public class JTicker extends JPanel
  implements ActionListener, ListDataListener,
    MouseListener, MouseMotionListener
{
  // Property instance variables
  protected TickerRenderer renderer;
  protected TickerModel model;
  protected int interval = 40;
  protected int increment = -6;
  protected int gap = 20;
  
  // Internal instance variables
  protected CellRendererPane renderPane =
    new CellRendererPane();
  protected boolean mousePressed;
  protected int mouseOver = -1;
  protected int[] positions;
  protected int offset = 0;
  protected Point anchor;
  protected Timer timer;
  
  public JTicker()
  {
    this(new DefaultTickerModel());
  }
  
  public JTicker(TickerModel model)
  {
    this(model, new DefaultTickerRenderer());
  }
  
  public JTicker(TickerModel model, TickerRenderer renderer)
  {
    setRenderer(renderer);
    setModel(model);
    init();
  }
  
  public void init()
  {
    calculatePositionArray();

    addMouseListener(this);
    
    setBackground(Color.white);
    setBorder(BorderFactory.createLineBorder(Color.black, 1));
    
    setPreferredSize(calculatePreferredSize());
    
    timer = new Timer(interval, this);
    timer.start();
  }

// --------------------------------------------------------
// Model
// --------------------------------------------------------
  
  public void addItem(Object item)
  {
    model.add(item);
  }
  
  public void removeItem(Object item)
  {
    model.remove(item);
  }
  
// --------------------------------------------------------
// Accessors
// --------------------------------------------------------
  
  public void setGap(int gap)
  {
    this.gap = gap;
  }
  
  public int getGap()
  {
    return gap;
  }
  
  public void setIncrement(int increment)
  {
    this.increment = increment;
  }
  
  public int getIncrement()
  {
    return increment;
  }
  
  public void setInterval(int interval)
  {
    this.interval = interval;
    timer.stop();
    timer = new Timer(interval, this);
    timer.start();
  }
  
  public int getInterval()
  {
    return interval;
  }
  
  public void setRenderer(TickerRenderer renderer)
  {
    this.renderer = renderer;
    calculatePositionArray();
  }
  
  public TickerRenderer getRenderer()
  {
    return renderer;
  }
  
  public void setModel(TickerModel model)
  {
    if (this.model != null)
    {
      this.model.removeListDataListener(this);
    }
    this.model = model;
    this.model.addListDataListener(this);
    calculatePositionArray();
  }
  
  public TickerModel getModel()
  {
    return model;
  }

// --------------------------------------------------------
// ListDataListener
// --------------------------------------------------------

  public void intervalAdded(ListDataEvent event)
  {
    calculatePositionArray();
    setPreferredSize(calculatePreferredSize());
  }
  
  public void intervalRemoved(ListDataEvent event)
  {
    calculatePositionArray();
    setPreferredSize(calculatePreferredSize());
  }
  
  public void contentsChanged(ListDataEvent event)
  {
    calculatePositionArray();
    setPreferredSize(calculatePreferredSize());
  }
  
// --------------------------------------------------------
// MouseListener
// --------------------------------------------------------

  public void mousePressed(MouseEvent event)
  {
    mousePressed = true;
    timer.stop();
    repaint();
    fireActionEvent();
  }
  
  public void mouseReleased(MouseEvent event)
  {
    mousePressed = false;
    timer.restart();
    repaint();
  }
  
  public void mouseClicked(MouseEvent event) {}
  
  public void mouseEntered(MouseEvent event)
  {
    addMouseMotionListener(this);
    anchor = event.getPoint();
    highlight(event.getX());
  }
  
  public void mouseExited(MouseEvent event)
  {
    removeMouseMotionListener(this);
    mouseOver = -1;
    anchor = null;
  }

// --------------------------------------------------------
// MouseMotionListener
// --------------------------------------------------------

  public void mouseMoved(MouseEvent event)
  {
    anchor = event.getPoint();
    highlight(event.getX());
  }
  
  public void mouseDragged(MouseEvent event) {}

// --------------------------------------------------------
// ActionListener
// --------------------------------------------------------

  public void actionPerformed(ActionEvent event)
  {
    offset += increment;
    int last = positions.length - 1;
    int min = -positions[last];
    if (offset < min) offset = 0;
    if (anchor != null) highlight(anchor.x);
    repaint();
  }
  
// --------------------------------------------------------
// Support
// --------------------------------------------------------
  
  protected void calculatePositionArray()
  {
    if (model == null) return;
    int pos = 0;
    int count = model.getSize();
    positions = new int[count + 1];
    positions[0] = 0;
    for (int i = 0; i < count; i++)
    {
      Object value = model.getElementAt(i);
      JComponent component = renderer.
        getTickerRendererComponent(this,
          value, TickerRenderer.STATE_NORMAL);
      Dimension size = component.getPreferredSize();
      pos += size.width + gap;
      positions[i + 1] = pos;
    }
  }
  
  protected Dimension calculatePreferredSize()
  {
    int width = 0;
    int height = 0;
    int count = model.getSize();
    for (int i = 0; i < count; i++)
    {
      Object value = model.get(i);
      JComponent component = renderer.
        getTickerRendererComponent(this,
          value, TickerRenderer.STATE_NORMAL);
      Dimension cell = component.getPreferredSize();
      width = Math.max(width, cell.width);
      height = Math.max(height, cell.height);
    }
    Insets insets = getInsets();
    return new Dimension(
      width + insets.left + insets.right,
      height + insets.top + insets.bottom);
  }
  
  public int getState(int index, Object value)
  {
    if (index == mouseOver &&
      value instanceof Action)
    {
      if (mousePressed)
      {
        return TickerRenderer.STATE_CLICK;
      }
      return TickerRenderer.STATE_MOUSE;
    }
    return TickerRenderer.STATE_NORMAL;
  }
  
  public void fireActionEvent()
  {
    if (mouseOver == -1) return;
    Object value = model.get(mouseOver);
    if (value instanceof Action)
    {
      Action action = (Action)value;
      if (action != null)
      {
        ActionEvent event = new ActionEvent(this,
          ActionEvent.ACTION_PERFORMED, "Action");
        action.actionPerformed(event);
      }
    }
  }

// --------------------------------------------------------
// Display
// --------------------------------------------------------

  public void highlight(int x)
  {
    mouseOver = -1;
    Insets insets = getInsets();
    int right = positions[positions.length - 1];
    int count = positions.length - 1;
    for (int i = 0; i < count * 2; i++)
    {
      int index = (i < count) ? i : i - count;
      int adjust = insets.left + offset;
      int head = positions[index] + adjust;
      int tail = positions[index + 1] + adjust;
      if (i >= count)
      {
        head += right;
        tail += right;
      }
      if (x >= head && x <= tail)
      {
        mouseOver = index;
        return;
      }
    }
  }
  
  public void paintComponent(Graphics g)
  {
    int w = getSize().width;
    int h = getSize().height;
    Insets insets = getInsets();
    g.setColor(getBackground());
    g.fillRect(insets.left, insets.top,
      w - (insets.left + insets.right),
      h - (insets.top + insets.bottom));
    int count = model.getSize();
    int right = positions[positions.length - 1];
    for (int i = 0; i < count * 2; i++)
    {
      int index = (i < count) ? i : i - count;
      int adjust = insets.left + offset;
      int head = positions[index] + adjust;
      int tail = positions[index + 1] + adjust;
      if (i >= count)
      {
        head += right;
        tail += right;
      }
      if (head < w && tail > 0)
      {
        Object value = model.getElementAt(index);
        int state = getState(index, value);
        JComponent component = renderer.
          getTickerRendererComponent(this, value, state);
        Dimension size = component.getPreferredSize();
        renderPane.paintComponent(g, component, this,
          head, insets.top, size.width, size.height);
      }
    }
  }
}

