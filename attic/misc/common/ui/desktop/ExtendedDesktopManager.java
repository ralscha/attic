package common.ui.desktop;

import java.awt.*;
import javax.swing.*;

public class ExtendedDesktopManager
  extends DefaultDesktopManager
  implements DesktopConstants
{
  protected boolean getBooleanProperty(JComponent frame, String name)
  {
    Object obj = frame.getClientProperty(name);
    return (obj == null ? false : ((Boolean)obj).booleanValue());
  }
  
  public void dragFrame(JComponent component, int x, int y)
  {
    if (component instanceof JInternalFrame)
    {
      JInternalFrame frame = (JInternalFrame)component;
      if (getBooleanProperty(component, CONSTRAINED))
      {
        JDesktopPane desktop = frame.getDesktopPane();
        int width = desktop.getSize().width;
        int height = desktop.getSize().height;
        int w = component.getSize().width;
        int h = component.getSize().height;
        // Adjust location, if necessary
        if (x < 0) x = 0;
        if (x + w > width) x = width - w;
        if (y < 0) y = 0;
        if (y + h > height) y = height - h;
      }
      if (!frame.isMaximum())
      {
        Rectangle bounds = frame.getNormalBounds();
        bounds.setLocation(x, y);
        frame.setNormalBounds(bounds);
      }
    }
    super.dragFrame(component, x, y);
  }

  public void resizeFrame(JComponent component, 
    int x, int y, int w, int h)
  {
    if (component instanceof JInternalFrame &&
        getBooleanProperty(component, CONSTRAINED))
    {
      JInternalFrame frame = (JInternalFrame)component;
      JDesktopPane desktop = frame.getDesktopPane();
      int width = desktop.getSize().width;
      int height = desktop.getSize().height;
      // Adjust size, if necessary
      if (x < 0) w += x;
      if (x + w > width) w = width - x;
      if (y < 0) h += y;
      if (y + h > height) h = height - y;
      // Adjust location, if necessary
      if (x < 0) x = 0;
      if (x + w > width) x = width - w;
      if (y < 0) y = 0;
      if (y + h > height) y = height - h;
    }
    super.resizeFrame(component, x, y, w, h);
  }
  
  public void iconifyFrame(JInternalFrame frame)
  {
    if (getBooleanProperty(frame, ROLLABLE))
    {
      frame.setNormalBounds(frame.getBounds());
      frame.setSize(
        frame.getNormalBounds().width,
        frame.getSize().height -
        frame.getContentPane().getSize().height);
    }
    else
    {
      super.iconifyFrame(frame);
    }
  }

  public void deiconifyFrame(JInternalFrame frame)
  {
    if (getBooleanProperty(frame, ROLLABLE))
    {
      frame.setBounds(frame.getNormalBounds());
    }
    else
    {
      super.deiconifyFrame(frame);
    }
  }
}

