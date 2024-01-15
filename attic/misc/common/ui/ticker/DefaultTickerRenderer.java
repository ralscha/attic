package common.ui.ticker;
/*
=====================================================================

  DefaultTickerRenderer.java
  
  Created by Claude Duguay
  Copyright (c) 2001
  
=====================================================================
*/

import java.awt.*;
import javax.swing.*;

public class DefaultTickerRenderer extends JLabel
  implements TickerRenderer
{
  public static final Icon defaultIcon =
    new ImageIcon("page.gif");
    
  protected Color normalColor = Color.black;
  protected Color mouseOverColor = Color.blue;
  protected Color mouseClickedColor = Color.red;

  public DefaultTickerRenderer() {}
  
  public void setNormalColor(Color color)
  {
    normalColor = color;
  }

  public void setMouseOverColor(Color color)
  {
    mouseOverColor = color;
  }

  public void setMouseClicked(Color color)
  {
    mouseClickedColor = color;
  }

  public JComponent getTickerRendererComponent(
    JTicker ticker, Object value, int state)
  {
    setForeground(normalColor);
    if (state == STATE_MOUSE)
    {
      setForeground(mouseOverColor);
    }
    if (state == STATE_CLICK)
    {
      setForeground(mouseClickedColor);
    }
    
    if (value instanceof Action)
    {
      Action action = (Action)value;
      Icon icon = (Icon)action.getValue(Action.SMALL_ICON);
      String name = (String)action.getValue(Action.NAME);
      setText(name);
      setIcon(icon);
    }
    else
    {
      setText(value.toString());
      setIcon(defaultIcon);
    }
    return this;
  }
}

