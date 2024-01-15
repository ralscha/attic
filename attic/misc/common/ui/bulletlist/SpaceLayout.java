package common.ui.bulletlist;

import java.awt.*;
import javax.swing.*;

public class SpaceLayout extends common.ui.layout.AbstractLayout
  implements SwingConstants
{
  protected int orientation;
  protected int hgap, vgap;
  
  public SpaceLayout()
  {
    this(VERTICAL, 0, 0);
  }
  
  public SpaceLayout(int orientation)
  {
    this(orientation, 0, 0);
  }
  
  public SpaceLayout(int hgap, int vgap)
  {
    this(VERTICAL, hgap, vgap);
  }
  
  public SpaceLayout(int orientation, int hgap, int vgap)
  {
    this.orientation = orientation;
    this.hgap = hgap;
    this.vgap = vgap;
  }

  public Dimension minimumLayoutSize(Container parent)
  {
    Insets insets = parent.getInsets();
    int w = insets.left + insets.right;
    int h = insets.top + insets.bottom;
    int width = 0, height = 0;
    int count = parent.getComponentCount();
    for (int i = 0; i < count; i++)
    {
      Component child = parent.getComponent(i);
      Dimension size = child.getMinimumSize();
      if (orientation == VERTICAL)
      {
        width = Math.max(width, size.width);
        if (i > 0) height += vgap;
        height += size.height;
      }
      else
      {
        height = Math.max(height, size.height);
        if (i > 0) width += hgap;
        width += size.width;
      }
    }
    return new Dimension(width + w, height + h);
  }

  public Dimension preferredLayoutSize(Container parent)
  {
    Insets insets = parent.getInsets();
    int w = insets.left + insets.right;
    int h = insets.top + insets.bottom;
    int width = 0, height = 0;
    int count = parent.getComponentCount();
    for (int i = 0; i < count; i++)
    {
      Component child = parent.getComponent(i);
      Dimension size = child.getPreferredSize();
      if (orientation == VERTICAL)
      {
        width = Math.max(width, size.width);
        if (i > 0) height += vgap;
        height += size.height;
      }
      else
      {
        height = Math.max(height, size.height);
        if (i > 0) width += hgap;
        width += size.width;
      }
    }
    return new Dimension(width + w, height + h);
  }

  public void layoutContainer(Container parent)
  {
    Insets insets = parent.getInsets();
    int count = parent.getComponentCount();
    if (count < 1) return;
    if (count == 1)
    {
      parent.getComponent(0).setBounds(0, 0, 
        parent.getSize().width, parent.getSize().height);
      return;
    }
    Dimension size = parent.getSize();
    Dimension pref = preferredLayoutSize(parent);
    int y = insets.top;
    int x = insets.left;
    if (orientation == VERTICAL)
    {
      int gap = (size.height - pref.height) / (count - 1);
      int width = size.width - (insets.left + insets.right);
      for (int i = 0; i < count; i++)
      {
        Component child = parent.getComponent(i);
        Dimension dim = child.getPreferredSize();
        child.setBounds(x, y, width, dim.height);
        y += dim.height + gap + vgap;
      }
    }
    else
    {
      int gap = (size.width - pref.width) / (count - 1);
      int height = size.height - (insets.top + insets.bottom);
      for (int i = 0; i < count; i++)
      {
        Component child = parent.getComponent(i);
        Dimension dim = child.getPreferredSize();
        child.setBounds(x, y, dim.width, height);
        x += dim.width + gap + hgap;
      }
    }
  }

  public static void main(String[] args)
  {
    
    JFrame frame = new JFrame("SpaceLayout Test");
    frame.getContentPane().setLayout(new SpaceLayout(VERTICAL, 10, 10));
    frame.getContentPane().add(new JTextField("Test"));
    frame.getContentPane().add(new JTextField("Test"));
    frame.getContentPane().add(new JTextField("Test"));
    frame.getContentPane().add(new JTextField("Test"));
    frame.pack();
    frame.setVisible(true);
  }
}

