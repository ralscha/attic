package common.ui.scroller;

import java.awt.*;
import java.awt.event.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;

public class JTabBar extends JPanel
  implements SwingConstants, MouseListener
{
  protected ArrayList labels;
  protected int gap;
  protected Dimension size;
  protected int selected = 0;
  protected Rectangle[] list;
  protected SingleSelectionModel model;

  public JTabBar()
  {
    this(new ArrayList(), 8);
  }
  
  public JTabBar(ArrayList labels)
  {
    this(labels, 8);
  }
  
  public JTabBar(ArrayList labels, int gap)
  {
    setOpaque(true);
    this.labels = labels;
    this.gap = gap;
    addMouseListener(this);
  }
  
  public void setModel(SingleSelectionModel model)
  {
    this.model = model;
  }
  
  public void addTab(String text)
  {
    labels.add(text);
  }
  
  protected Rectangle getTextBounds(Graphics2D g, String text, int x)
  {
    Font font = getFont();
    FontRenderContext context = g.getFontRenderContext();
    TextLayout layout = new TextLayout(text, font, context);
    Rectangle2D rect = layout.getBounds();
    return new Rectangle((int)rect.getX() + x, (int)rect.getY(),
      (int)rect.getWidth(), (int)rect.getHeight());
  }
  
  public Dimension calculateSize(Graphics2D g, int gap)
  {
    int width = 0;
    int height = 16;
    int count = labels.size();
    for (int i = 0; i < count; i++)
    {
      Rectangle rect = getTextBounds(g, labels.get(i).toString(), 0);
      width += rect.width + gap;
      if (rect.height > height)
        height = rect.height;
    }
    width += gap + 1;
    return new Dimension(width, height);
  }
  
  public Dimension getPreferredSize()
  {
    if (size == null)
      size = calculateSize((Graphics2D)getGraphics(), gap);
    return size;
  }
  
  public void paintComponent(Graphics gc)
  {
    Graphics2D g = (Graphics2D)gc;
    size = calculateSize(g, gap);
    int width = getSize().width;
    int height = getSize().height;
    
    g.setColor(getBackground());
    g.fillRect(0, 0, width, height);
    
    int x = gap;
    list = new Rectangle[labels.size()];
    for (int i = 0; i < labels.size(); i++)
    {
      list[i] = getTextBounds(g, labels.get(i).toString(), x);
      x += list[i].width + gap;
    }
    for (int i = labels.size() - 1; i > selected; i--)
    {
      drawTab(g, list[i], i);
    }
    for (int i = 0; i <= selected; i++)
    {
      drawTab(g, list[i], i);
    }
  }
  
  protected void drawTab(Graphics2D g, Rectangle rect, int index)
  {
    int height = getSize().height - 2;
    GeneralPath fill = new GeneralPath();
    fill.moveTo(rect.x - gap, 0);
    fill.lineTo(rect.x, height);
    fill.lineTo(rect.x + rect.width + 1, height);
    fill.lineTo(rect.x + rect.width + gap + 1, 0);
    
    g.setColor(index == selected ? Color.white : getBackground());
    g.fill(fill);
    
    GeneralPath draw = new GeneralPath();
    draw.moveTo(rect.x - gap, 0);
    draw.lineTo(rect.x, height);
    draw.lineTo(rect.x + rect.width, height);
    draw.lineTo(rect.x + rect.width + gap, 0);
    
    g.setColor(Color.black);
    g.draw(draw);
    g.drawString(labels.get(index).toString(), rect.x,
      height - ((height - rect.height) / 2) - 1);
  }
  
  public void mousePressed(MouseEvent event)
  {
    if (list == null) repaint();
    int x = event.getX();
    int tolerance = gap / 2;
    for (int i = 0; i < list.length; i++)
    {
      int xx = list[i].x;
      if (x >= xx - tolerance &&
        x <= xx + list[i].width + tolerance)
      {
        selected = i;
        model.setSelectedIndex(i);
        repaint();
        return;
      }
    }
  }
  
  public void mouseReleased(MouseEvent event) {}
  public void mouseClicked(MouseEvent event) {}
  public void mouseEntered(MouseEvent event) {}
  public void mouseExited(MouseEvent event) {}
}

