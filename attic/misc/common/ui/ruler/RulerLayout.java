package common.ui.ruler;

import java.awt.*;
import javax.swing.*;
import common.ui.layout.AbstractLayout;

public class RulerLayout extends AbstractLayout
  implements SwingConstants
{
  protected RulerModel model;

  public RulerLayout(RulerModel model)
  {
    this(model, 0, 0);
  }
  
  public RulerLayout(RulerModel model, int hgap, int vgap)
  {
    super(hgap, vgap);
    this.model = model;
  }

  public Dimension minimumLayoutSize(Container parent)
  {
    int h = 0, w = 0;
    Insets insets = parent.getInsets();
    int width = insets.left + insets.right;
    int height = insets.top + insets.bottom;
    int count = parent.getComponentCount();
    int orientation = model.getOrientation();
    for (int i = 0; i < count; i++)
    {
      Dimension size = parent.getComponent(i).getMinimumSize();
      if (orientation == NORTH || orientation == SOUTH)
      {
        if (size.width > w)
          w = size.width;
        h += size.height;
      }
      else
      {
        if (size.height > h)
          h = size.height;
        w += size.width;
      }
    }
    return new Dimension(w + width, h + height);
  }

  public Dimension preferredLayoutSize(Container parent)
  {
    int h = 0, w = 0;
    Insets insets = parent.getInsets();
    int width = insets.left + insets.right;
    int height = insets.top + insets.bottom;
    int count = parent.getComponentCount();
    int orientation = model.getOrientation();
    for (int i = 0; i < count; i++)
    {
      Dimension size = parent.getComponent(i).getPreferredSize();
      if (orientation == NORTH || orientation == SOUTH)
      {
        if (size.width > w)
          w = size.width;
        h += size.height;
      }
      else
      {
        if (size.height > h)
          h = size.height;
        w += size.width;
      }
    }
    return new Dimension(w + width, h + height);
  }

  public void layoutContainer(Container parent)
  {
    int count = parent.getComponentCount();
    Insets insets = parent.getInsets();
    Dimension parentSize = parent.getSize();
    int width = parentSize.width - (insets.left + insets.right);
    int height = parentSize.height - (insets.top + insets.bottom);
    int x = insets.left;
    int y = insets.top;
    
    double length = model.getLengthInUnits();
    int orientation = model.getOrientation();
    boolean fixed = model.isFixed();
    
    Component ticks = parent.getComponent(0);
    Component labels = parent.getComponent(1);
      
    Dimension ticksSize = ticks.getPreferredSize();
    Dimension labelsSize = labels.getPreferredSize();
    
    switch (orientation)
    {
      case NORTH:
      {
        ticks.setBounds(x, y + labelsSize.height,
          width, ticksSize.height);
        labels.setBounds(x, y,
          width, labelsSize.height);
        break;
      }
      case SOUTH:
      {
        ticks.setBounds(x, y,
          width, ticksSize.height);
        labels.setBounds(x, y + ticksSize.height,
          width, labelsSize.height);
        break;
      }
      case WEST:
      {
        ticks.setBounds(x + labelsSize.width, y,
          ticksSize.width, height);
        labels.setBounds(x, y,
          labelsSize.width, height);
        break;
      }
      case EAST:
      {
        ticks.setBounds(x, y,
          ticksSize.width, height);
        labels.setBounds(x + ticksSize.height, y,
          labelsSize.width, height);
        break;
      }
    }
  }
}

