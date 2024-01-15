package common.ui.ruler;

import java.awt.*;
import javax.swing.*;
import common.ui.layout.AbstractLayout;

public class RulerLabelsLayout extends AbstractLayout
  implements SwingConstants
{
  protected RulerModel model;

  public RulerLabelsLayout(RulerModel model)
  {
    this(model, 0, 0);
  }
  
  public RulerLabelsLayout(RulerModel model, int hgap, int vgap)
  {
    super(hgap, vgap);
    this.model = model;
  }

  public Dimension minimumLayoutSize(Container parent)
  {
    int h = 0, w = 0;
    int count = parent.getComponentCount();
    int orientation = model.getOrientation();
    for (int i = 0; i < count; i++)
    {
      Dimension size = parent.getComponent(i).getMinimumSize();
      if (orientation == NORTH || orientation == SOUTH)
      {
        if (size.height > h)
          h = size.height;
        w += size.width;
      }
      else
      {
        if (size.width > w)
          w = size.width;
        h += size.height;
      }
    }
    return new Dimension(w + 1, h + 1);
  }

  public Dimension preferredLayoutSize(Container parent)
  {
    int h = 0, w = 0;
    int count = parent.getComponentCount();
    int orientation = model.getOrientation();
    for (int i = 0; i < count; i++)
    {
      Dimension size = parent.getComponent(i).getPreferredSize();
      if (orientation == NORTH || orientation == SOUTH)
      {
        if (size.height > h)
          h = size.height;
        w += size.width;
      }
      else
      {
        if (size.width > w)
          w = size.width;
        h += size.height;
      }
    }
    return new Dimension(w + 1, h + 1);
  }

  public void layoutContainer(Container parent)
  {
    int count = parent.getComponentCount();
    int last = count - 1;
    
    Dimension dim = parent.getSize();
    double w = dim.width - 1;
    double h = dim.height - 1;
    
    boolean fixed = model.isFixed();
    double increment = model.getIncrement();
    double length = model.getLengthInUnits();
    double anchor = model.getAnchor();
    int orientation = model.getOrientation();
    boolean forward = model.getDirection();
    
    for (int i = 0; i < count; i++)
    {
      Component child = parent.getComponent(i);
      Dimension size = child.getPreferredSize();
      int width = size.width;
      int height = size.height;
      
      boolean vertical = (orientation == WEST || orientation == EAST);
      double side = vertical ? height : width;
      double edge = vertical ? dim.height : dim.width;
      double pixels = model.getPixelsPerUnit(edge);
      double pos = (int)((i + anchor) * pixels + 1);
      double offset = anchor * pixels;
      double half = side / 2;
      int factor = increment < 0 ? -1 : 1;
      if (forward)
      {
        if (i > 0) pos -= half;
        if (i == 0 && pos > half) pos -= half;
        if (i >= last && pos > edge - half) pos -= half;
      }
      else
      {
        pos = edge - pos - half;
        if (i == 0 && pos > edge - side) pos = pos -= half;
        if (i >= last && pos < 0) pos += half + 1;
      }
      
      switch (orientation)
      {
        case NORTH:
          child.setBounds((int)pos, 0, width, height);
          break;
        case SOUTH:
          child.setBounds((int)pos, dim.height - height,
            width, height);
          break;
        case WEST:
          child.setBounds(dim.width - width - 1,
            (int)pos, width, height);
          break;
        case EAST:
          child.setBounds(1, (int)pos, width, height);
          break;
      }
    }
  }
}

