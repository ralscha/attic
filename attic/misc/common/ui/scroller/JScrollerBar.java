package common.ui.scroller;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.plaf.*;
import javax.swing.event.*;
import javax.accessibility.*;

public class JScrollerBar extends JScrollBar
  implements ActionListener
{
  public static final boolean NORMAL = false;
  public static final boolean EXTENDED = true;

  protected JScrollBar scrollBar;
  protected ScrollerButton minButton, maxButton;

  public JScrollerBar()
  {
    this(VERTICAL, 0, 10, 0, 100, null, null, NORMAL);
  }

  public JScrollerBar(int orientation)
  {
    this(orientation, 0, 10, 0, 100, null, null, NORMAL);
  }

  public JScrollerBar(
    JComponent before, JComponent after)
  {
    this(VERTICAL, 0, 10, 0, 100, before, after, NORMAL);
  }

  public JScrollerBar(int orientation,
    JComponent before, JComponent after)
  {
    this(orientation, 0, 10, 0, 100, before, after, NORMAL);
  }

  public JScrollerBar(int orientation,
    JComponent before, JComponent after, boolean extended)
  {
    this(orientation, 0, 10, 0, 100, before, after, extended);
  }

  public JScrollerBar(int orientation,
    int value, int extent, int min, int max)
  {
    this(orientation, value, extent, min, max, null, null, NORMAL);
  }
  
  public JScrollerBar(int orientation,
    int value, int extent, int min, int max,
    JComponent before, JComponent after)
  {
    this(orientation, value, extent, min, max, before, after, NORMAL);
  }
  
  public JScrollerBar(int orientation,
    int value, int extent, int min, int max,
    JComponent before, JComponent after,
    boolean extended)
  {
    JPanel panel = new JPanel(new BorderLayout());
    panel.add(BorderLayout.CENTER, scrollBar =
      new JScrollBar(orientation, value, extent, min, max));
    if (extended)
    {
      minButton = new ScrollerButton(orientation == VERTICAL ? 
        ScrollerButton.TOP : ScrollerButton.LEFT);
      maxButton = new ScrollerButton(orientation == VERTICAL ?
        ScrollerButton.BOTTOM : ScrollerButton.RIGHT);
    
      minButton.addActionListener(this);
      maxButton.addActionListener(this);

      panel.add(orientation == VERTICAL ?
        BorderLayout.NORTH : BorderLayout.WEST, 
        borderPanel(orientation, minButton));
      panel.add(orientation == VERTICAL ?
        BorderLayout.SOUTH : BorderLayout.EAST,
        borderPanel(orientation, maxButton));
    }
    
    setLayout(new BorderLayout(4, 4));
    add(BorderLayout.CENTER, panel);
    if (before != null) add(orientation == VERTICAL ?
        BorderLayout.NORTH : BorderLayout.WEST,
        borderPanel(orientation, before));
    if (after != null) add(orientation == VERTICAL ?
      BorderLayout.SOUTH : BorderLayout.EAST,
        borderPanel(orientation, after));
  }

  public void actionPerformed(ActionEvent event)
  {
    Object source = event.getSource();
    if (source == minButton)
    {
      setValue(getMinimum());
    }
    if (source == maxButton)
    {
      setValue(getMaximum());
    }
  }

  protected JComponent borderPanel(int orientation, JComponent child)
  {
    if (!(child instanceof ScrollerButton)) return child;
    boolean vertical = orientation == VERTICAL;
    JPanel panel = new JPanel(new GridLayout());
    panel.setBorder(BorderFactory.createEmptyBorder(
      vertical ? 0 : 1, vertical ? 1 : 0, 0, 0));
    panel.add(child);
    return panel;
  }

  public void addAdjustmentListener(AdjustmentListener listener)
  {
    scrollBar.addAdjustmentListener(listener);
  }

  public AccessibleContext getAccessibleContext()
  {
    return scrollBar.getAccessibleContext();
  }
  
  public int getBlockIncrement()
  {
    return scrollBar.getBlockIncrement();
  }
  
  public int getBlockIncrement(int direction)
  {
    return scrollBar.getBlockIncrement(direction);
  }
  
  public int getMaximum()
  {
    return scrollBar.getMaximum();
  }
  
  public Dimension getMaximumSize() 
  {
    return scrollBar.getMaximumSize();
  }
  
  public int getMinimum() 
  {
    return scrollBar.getMinimum();
  }
  
  public Dimension getMinimumSize() 
  {
    return scrollBar.getMinimumSize();
  }
  
  public BoundedRangeModel getModel() 
  {
    return scrollBar.getModel();
  }
  
  public int getOrientation() 
  {
    return scrollBar.getOrientation();
  }
  
  public ScrollBarUI getUI() 
  {
    return scrollBar.getUI();
  }
  
  public String getUIClassID() 
  {
    return scrollBar.getUIClassID();
  }
  
  public int getUnitIncrement() 
  {
    return scrollBar.getUnitIncrement();
  }
  
  public int getUnitIncrement(int direction) 
  {
    return scrollBar.getUnitIncrement(direction);
  }
  
  public int getValue() 
  {
    return scrollBar.getValue();
  }
  
  public boolean getValueIsAdjusting() 
  {
    return scrollBar.getValueIsAdjusting();
  }
  
  public int getVisibleAmount() 
  {
    return scrollBar.getVisibleAmount();
  }
  
  public void removeAdjustmentListener(AdjustmentListener listener) 
  {
    scrollBar.removeAdjustmentListener(listener);
  }
  
  public void setBlockIncrement(int blockIncrement) 
  {
    scrollBar.setBlockIncrement(blockIncrement);
  }
  
  public void setEnabled(boolean b) 
  {
    scrollBar.setEnabled(b);
  }
  
  public void setMaximum(int maximum) 
  {
    scrollBar.setMaximum(maximum);
  }
  
  public void setMinimum(int minimum) 
  {
    scrollBar.setMinimum(minimum);
  }
  
  public void setModel(BoundedRangeModel newModel) 
  {
    scrollBar.setModel(newModel);
  }
  
  public void setOrientation(int orientation) 
  {
    scrollBar.setOrientation(orientation);
  }
  
  public void setUnitIncrement(int unitIncrement) 
  {
    scrollBar.setUnitIncrement(unitIncrement);
  }
  
  public void setValue(int value) 
  {
    scrollBar.setValue(value);
  }
  
  public void setValueIsAdjusting(boolean b) 
  {
    scrollBar.setValueIsAdjusting(b);
  }
  
  public void setValues(int newValue, int newExtent, int newMin, int newMax) 
  {
    scrollBar.setValues(newValue, newExtent, newMin, newMax);
  }
  
  public void setVisibleAmount(int extent) 
  {
    scrollBar.setVisibleAmount(extent);
  }
  
  public void updateUI()
  {
    if (scrollBar != null)
      scrollBar.updateUI();
  }
}

