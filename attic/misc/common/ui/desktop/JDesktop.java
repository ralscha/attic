package common.ui.desktop;

import java.awt.*;
import java.beans.*;
import javax.swing.*;

public class JDesktop extends JDesktopPane
{
  public JDesktop()
  {
    setDesktopManager(new ExtendedDesktopManager());
  }
  
  public void cascade()
  {
    cascade(
      new Rectangle(5, 5, 300, 150),
      new Dimension(10, 10));
  }

  public void cascade(
    Rectangle firstWindowBounds,
    Dimension offsets)
  {
    Rectangle bounds = (Rectangle)firstWindowBounds.clone();
    Component[] list = getResetFrames(0);
    for (int i = 0; i < list.length; i++)
    {
      list[i].setBounds(bounds);
      bounds.x += offsets.width;
      bounds.y += offsets.height;
    }
  }

  public void tileVertical()
  {
    Component[] list = getResetFrames(0);
    int count = list.length;
    int cols = (int)Math.sqrt(count);
    int rows = cols;
    if (rows * cols < count) rows++;
    if (rows * cols < count) cols++;
    int w = getSize().width / cols;
    int h = getSize().height / rows;
    int i = 0;
    for (int x = 0; x < cols; x++)
    {
      if (x == cols - 1)
      {
        rows = count - rows * (cols - 1);
        h = getSize().height / rows;
      }
      for (int y = 0; y < rows; y++)
      {
        list[i].setBounds(x * w, y * h, w, h);
        i++;
      }
    }
  }
  
  public void tileHorizontal()
  {
    Component[] list = getResetFrames(0);
    int count = list.length;
    int cols = (int)Math.sqrt(count);
    int rows = cols;
    if (rows * cols < count) cols++;
    if (rows * cols < count) rows++;
    int w = getSize().width / cols;
    int h = getSize().height / rows;
    int i = 0;
    for (int y = 0; y < rows; y++)
    {
      if (y == rows - 1)
      {
        cols = count - cols * (rows - 1);
        w = getSize().width / cols;
      }
      for (int x = 0; x < cols; x++)
      {
        list[i].setBounds(x * w, y * h, w, h);
        i++;
      }
    }
  }

  protected Component[] getResetFrames(int layer)
  {
    Component[] list = getComponentsInLayer(layer);
    for (int i = 0; i < list.length; i++)
    {
      resetFrame(list[i]);
    }
    // Re-fetch to keep the correct order
    return getComponentsInLayer(layer);
  }
  
  protected void resetFrame(Component component)
  {
    // Demaximize
    if (component instanceof JInternalFrame)
    {
      JInternalFrame frame = (JInternalFrame)component;
      try
      {
        if (frame.isMaximum())
          frame.setMaximum(false);
      }
      catch (PropertyVetoException e) {}
    }
    // Deiconify
    if (component instanceof JInternalFrame.JDesktopIcon)
    {
      JInternalFrame frame = 
        ((JInternalFrame.JDesktopIcon)component).
          getInternalFrame();
      try
      {
        frame.setIcon(false);
      }
      catch (PropertyVetoException e) {}
    }
  }
}

